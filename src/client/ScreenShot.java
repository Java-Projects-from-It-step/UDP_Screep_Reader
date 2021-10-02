package client;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ScreenShot {
    private Robot robot;

    public BufferedImage makeScreenShot(int x1, int y1,int x2, int y2){
    if (x2==-1 & y2 ==-1){
        // получаем размер экрана если нам нужно зделать весь экран
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        x2 = screenSize.width;
        y2 = screenSize.height;
    }
    // для класса робот в конструктор
    GraphicsEnvironment gEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice gDevice = gEnvironment.getDefaultScreenDevice();
    try {
        robot = new Robot(gDevice);

    } catch (AWTException e) {
        e.printStackTrace();
    }
        return robot.createScreenCapture(new Rectangle(x1,y1,x2,y2));
    }

    public byte[] bufferedImageToByteArray(BufferedImage image){

        // переводим картинку в поток так легко перевести поток в байтовый массив
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            // картинку переводим в поток
            ImageIO.write(image,"jpg",baos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return baos.toByteArray();
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, int type, int widht, int height){
        // пустая картинка с нашими размерами
        BufferedImage resizedImage = new BufferedImage(widht,height,type);
        // для рисования нужно создать объект Graphics

        Graphics2D g = resizedImage.createGraphics();
        // рисуем c размером 0 0 координамы.
        g.drawImage(originalImage,0,0,widht,height,null);
        // нужно освободить ресурсы
        g.dispose();
        return resizedImage;
    }
}
