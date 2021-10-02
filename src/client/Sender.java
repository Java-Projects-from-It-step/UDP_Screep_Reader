package client;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Sender implements Runnable{

    DatagramSocket client =null;
    InetAddress address =null;
    int port =0;

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
            // 60 kb for datagram
            client = new DatagramSocket();
            ScreenShot screenShot=null;

            while (running){
            screenShot = new ScreenShot();
// resize datagrams
            BufferedImage original = screenShot.makeScreenShot(0,0,-1,-1);
            BufferedImage resized = ScreenShot.resizeImage(original, original.getType(),
                    original.getWidth()/3, original.getHeight()/3);
            byte[] imageBytes = screenShot.bufferedImageToByteArray(original);

                System.out.println("Image size is "+imageBytes.length);
                // using while cycle, add event for cancel, change form
//https://stackoverflow.com/questions/2253912/splitting-a-byte-array/2253926
                if (imageBytes.length>63*1024){

                    // создать ByteArrayInputStream
                    ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);

                    while(bais.available()>0){
                        byte[] part = bais.readNBytes(63*1024);
                        System.out.println("Length of part "+ part.length );


                        DatagramPacket pack = new DatagramPacket(part,part.length,address,port);

                        client.send(pack); //blocking method

                    }
                    //todo delete
                    //в цикле получить только 61*1024 байта
                    //
                    //передать байты по частям
                    // соеденить байты
                    //


                }else{
                    DatagramPacket pack = new DatagramPacket(imageBytes,imageBytes.length,address,port);
                    client.send(pack); //blocking method
                }

                Thread.sleep(interval);
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

    }
    public void setRunning(boolean running){
        this.running = running;
    }
}
