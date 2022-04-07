package Pachet1;
import javax.imageio.ImageIO;

import Pachet1.Algorithm;

import java.awt.image.BufferedImage;
import java.io.*;
//extinde Algorithm
//2 argumente
public class Image extends Algorithm{
    private static BufferedImage originalImage = null, resultImage = null;

    //Metoda mostenita din initialMask
    public void mostenire(String...args) {
        for (String a : args) {
            System.out.println("[clasa Image] Aplicatia a inceput sa ruleze!");
        }
        
    }

    //Metoda Open
    public static void openImage(String sourceImage) {
        try {//se da imaginea .bmp, in cazul in care nu merge, avem eroare
            originalImage = ImageIO.read(new File(sourceImage));
            resultImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        } catch (Exception e) {
            System.out.println("[openImage] Eroare: " + e);
        }
    }

    //Metoda Save
    public static void saveImage(String path, BufferedImage resultImage) {
        try {//salvam imaginea si avem eroare daca nu se poate salva
            ImageIO.write(resultImage, "bmp", new File(path));
        } catch (Exception e) {
            System.out.println("[saveImage] Eroare: " + e);
        }
    }

    //Getter-i 
    //returnam imaginea initiala
    public static BufferedImage getOriginalImage() {
        return originalImage;
    }
    //returnam noua imagine
    public static BufferedImage getResultImage() {
        return resultImage;
    }

}