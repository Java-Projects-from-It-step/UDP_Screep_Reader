package main;

import client.Sender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;

public class Manager {
    public static Sender sender = null;


    public static void main(String[] args) {

        //region Gui
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(500,200));
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        //row1
        JPanel row1 = new JPanel();
        row1.setLayout(new BoxLayout(row1, BoxLayout.X_AXIS));

        JLabel lbAdress = new JLabel("Address");
        lbAdress.setSize(40,5);
        JTextField tbAddress = new JTextField();
        tbAddress.setText("localhost");
        row1.add(lbAdress);
        row1.add(tbAddress);
        main.add(row1);

        //row2
        JPanel row2 = new JPanel();
        row2.setLayout(new BoxLayout(row2, BoxLayout.X_AXIS));

        JLabel lbPort = new JLabel("Port");
        lbPort.setSize(40,5);
        JTextField tbPort = new JTextField();
        tbPort.setText("8888");
        row2.add(lbPort);
        row2.add(tbPort);
        main.add(row2);

        //row3
        JPanel row3 = new JPanel();
        row3.setLayout(new BoxLayout(row3, BoxLayout.X_AXIS));

        JLabel lbInterval = new JLabel("Interval");
        lbInterval.setSize(60,5);
        JTextField tbInterval = new JTextField();
        tbInterval.setText("1000");
        row3.add(lbInterval);
        row3.add(tbInterval);
        main.add(row3);

        //row4
        JPanel row4 = new JPanel();
        row4.setLayout(new BoxLayout(row4, BoxLayout.X_AXIS));

        JButton btnStart = new JButton("Start");
        JButton btnStop = new JButton("Stop");

        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sender !=null){
                    sender.setRunning(false);
                    sender = null;
                    btnStart.setEnabled(true);
                    btnStop.setEnabled(false);
                }
            }
        });

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sender = new Sender(Integer.parseInt(tbInterval.getText()),tbAddress.getText(),Integer.parseInt(tbPort.getText()));
                    Thread thread = new Thread(sender);
                    thread.setDaemon(true);
                    btnStart.setEnabled(false);
                    btnStop.setEnabled(true);
                    thread.start();
                } catch (UnknownHostException ex) {
                    ex.printStackTrace();
                }
                if (sender !=null){
                    sender.setRunning(true);
                    btnStart.setEnabled(false);
                    btnStop.setEnabled(true);
                }
            }
        });

        row4.add(btnStart);
        row4.add(btnStop);
        main.add(row4);
        frame.add(main);
        //frame.pack();
        frame.setVisible(true);



        //endregion
        System.out.println("Client Started");




    }
}
