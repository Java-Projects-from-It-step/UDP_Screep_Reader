package main;

import server.Listener;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.SocketException;

public class Manager extends JFrame {
    public BufferedImage image = new BufferedImage(1, 1, 1);
    public Graphics graphics = null;
    public JPanel panel = new JPanel();

    public Manager() throws HeadlessException {

        panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                graphics = g;
                g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null, null);
            }
        };
        this.add(panel);
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public static void main(String[] args) {
        Manager frame = new Manager();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 900);
        frame.setVisible(true);
        Listener listener = null;
        try {
            listener = new Listener(frame);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        // add cycle for multithreading
        Thread thread = new Thread(listener);
        thread.setDaemon(true);
        thread.start();
        System.out.println("Server started...");

    }
}
