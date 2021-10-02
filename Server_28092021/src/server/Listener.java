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

public class Listener implements Runnable{

    private Manager frame = null;
    DatagramSocket listener = null;
    byte[]buf = null;

    public Listener(Manager frame) throws SocketException {
        listener = new DatagramSocket(8888);
        this.frame = frame;

    }

    @Override
    public void run() {
            ByteArrayOutputStream baos = null;
        while(true){
            buf = new byte[1024*63];
            baos = new ByteArrayOutputStream();
            DatagramPacket in_pack = new DatagramPacket(buf,buf.length);
            try {
                listener.receive(in_pack); //blocking method
                System.out.println("Getting from client bytes length: "+in_pack.getLength());
                baos.write(in_pack.getData());

                if (in_pack.getLength()!=63*1024){
                    listener.receive(in_pack);
                    baos.write(in_pack.getData());

                }





                // in_pack.getData() getting bytes
                System.out.println("Length of picture "+ baos.toByteArray().length );
                BufferedImage image = ImageIO.read(new ByteArrayInputStream(baos.toByteArray()));
                frame.setImage(image);
                frame.graphics.drawImage(image,0,0,image.getWidth(),image.getHeight(),null);
                frame.panel.repaint();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
