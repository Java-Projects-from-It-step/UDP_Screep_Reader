package client;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Sender implements Runnable {

    DatagramSocket client = null;
    InetAddress address = null;
    int port = 0;

    private int interval = 0;
    private boolean running = true;

    public Sender(int interval, String address, int port) throws UnknownHostException {
        this.interval = interval;
        this.address = InetAddress.getByName(address);
        this.port = port;
    }

    @Override
    public void run() {
        try {
            // 63 kb for datagram
            client = new DatagramSocket();
            ScreenShot screenShot = null;

            while (running) {
                screenShot = new ScreenShot();
                BufferedImage original = screenShot.makeScreenShot(0, 0, -1, -1);
// resized datagram
                BufferedImage resized = ScreenShot.resizeImage(original, original.getType(),
                        original.getWidth() / 3, original.getHeight() / 3);
                byte[] imageBytes = screenShot.bufferedImageToByteArray(original);



                ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);

                while (bais.available() > 0) {
                    byte[] part = bais.readNBytes(63 * 1024);
                    DatagramPacket pack = new DatagramPacket(part, part.length, address, port);

                    client.send(pack); //blocking method
                }

                Thread.sleep(interval);
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
