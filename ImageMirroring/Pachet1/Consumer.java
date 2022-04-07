package Pachet1;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.Color;
import java.nio.Buffer;
public class Consumer extends Worker {
    String location; 
    private Conector buffer;
 //mostenita din worker   
    public Consumer(Conector buffer) {
        super(false);
        this.buffer = buffer;
    }

    @Override
    //se creeaza cele 4 segmente
    public void starter() throws InterruptedException {
        for (int i = 0; i < 4; i++) {
            BufferedImage image = new BufferedImage(buffer.getWidth(), buffer.getHeight(), BufferedImage.TYPE_INT_RGB);
            image = buffer.write(i); 
            try {//le denumim segm0...3
                location = "seg_" + String.valueOf(i + 1) + ".bmp";
                ImageIO.write(image, "bmp", new File(location));
            } catch (IOException e1){ }

            //Afisarea numarului consumerului curent
            System.out.println("C" + i);

            try {
                sleep((int)(Math.random() * 1000));
            } catch (InterruptedException e) { }
        }
    }
}