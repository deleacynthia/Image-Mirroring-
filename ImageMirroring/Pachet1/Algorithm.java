package Pachet1;
import javax.imageio.ImageIO;

import Pachet1.secondMask;

import java.awt.image.BufferedImage;
import java.io.*; //mosteneste secondMask 

public class Algorithm extends secondMask{
//metoda principala 
	//argumente: imaginea originala si cea in oglinda
    public static void mirrorImage(BufferedImage originalImage, BufferedImage resultImage) { //Implementarea algoritmului de mirroring
        for (int y = 0; y < originalImage.getHeight(); y++) {
            for (int lx = 0, rx = originalImage.getWidth() - 1; lx < originalImage.getWidth(); lx++, rx--) {
                resultImage.setRGB(rx, y, originalImage.getRGB(lx, y)); //ce este in stanga se duce in dreapta
            }
        }
    }
//afisare
    public void mostenire(String...args) {
        System.out.println("[clasa Algorithm] Hello world!");
    }
}