package Pachet1;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.Color;
import java.nio.Buffer;

//argumente ale imaginii
public class Conector {
    String sourceFile;
    private boolean available = false; 
    private int width = 1, height = 1;
    public BufferedImage partImage = null; 
     
    //prelucrarea de catre consumer a datelor primite de la producer
    public synchronized BufferedImage write (int sectiune) {
        BufferedImage segment = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
        while (available) {//scriem cele 4 segmente si facem o sincronizare
            try { wait(); } 
            catch (InterruptedException e) { e.printStackTrace(); }
        }
        available = true;
        notifyAll();
        
        try {
            partImage = ImageIO.read(new File(this.sourceFile));
            segment = partImage.getSubimage(0, sectiune * this.height, this.width, this.height);
        } catch (IOException e) { e.printStackTrace(); }

        return segment;
    }

    //citirea datelor din imagine de catre producer
    public synchronized void read() {
        try {//se preia 1/4 din datele de la imaginea initiala 
        	//pentru a face segmentele
            partImage = ImageIO.read(new File(this.sourceFile));
            this.width = partImage.getWidth();
            this.height = partImage.getHeight() / 4;
        } catch (IOException e1) { e1.printStackTrace(); }

        while (!available) { 
            try { wait();
            }
            catch (InterruptedException e) { e.printStackTrace(); }
        }
        available = false;
        notifyAll();
    }    
    //constructorul
    public Conector(String sourceFile) { this.sourceFile = sourceFile; }

    //Getters pentru clasa Conector
    public int getHeight() { return height; }

    public int getWidth() { return width; }
}//conectam producer si consumer
