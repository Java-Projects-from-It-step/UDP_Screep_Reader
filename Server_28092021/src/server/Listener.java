package server;

import main.Manager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Listener implements Runnable {

    private Manager frame = null;
    DatagramSocket listener = null;
    byte[] buf = null;

    public Listener(Manager frame) throws SocketException {
        listener = new DatagramSocket(8888);
        this.frame = frame;
    }

    @Override
    public void run() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedImage image = null;
        DatagramPacket in_pack = null;
        while (true) {
            buf = new byte[1024 * 63];

            in_pack = new DatagramPacket(buf, buf.length);
            try {
                listener.receive(in_pack); //blocking method
                baos.write(in_pack.getData());

                while (in_pack.getLength() == 63 * 1024) {
                    listener.receive(in_pack);
                    baos.write(in_pack.getData());
                }

                image = ImageIO.read(new ByteArrayInputStream(baos.toByteArray()));

                frame.setImage(image);
                frame.graphics.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
                frame.panel.repaint();

                baos.reset();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
