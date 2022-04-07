package Pachet2;
import javax.imageio.ImageIO;



import Pachet1.Conector;
import Pachet1.Consumer;
import Pachet1.Image;
import Pachet1.Producer;


import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

public class Main {
	
	public static String EnterData(Scanner data, String status){// introduc calea in linie de comanda
        String path = null;
        System.out.println("Enter the path to the image (" + status + "):");
        path = data.nextLine();
        return path;
    }

    public static void main(String[] args) { 
        /* Verificarea initiala, daca avem sau nu suficiente argumente in linia de comanda */
//    	if (args.length != 2) {
//            System.out.println("In linia de comanda nu se regasesc cele 2 argumente necesare!");
//    		System.exit(0);//mesaj ca nu exista, inchidem aplicatia
//        } 
    	String path1 = null;
    	String path2 = null;
    	
    	if(args.length == 0){
    		System.out.println("Introduce date din consola");
    		Scanner data1 = new Scanner(System.in);
    		path1 = EnterData(data1, "imagine originala"); 
    		Scanner data2 = new Scanner(System.in);
    		path2 = EnterData(data2, "imagine originala"); 
    		
    	}
    	else{
    		path1 = args[0];
    		path2 = args[1];
    	}
    		
    	

    	
        //variabile ce masoara timpul unei metode
        long startTime, endTime;
        /* Deschidem imaginea sursa */
        startTime = System.nanoTime();//masuram timpul unei metode
        Image.openImage(path1);
        endTime = System.nanoTime();//pana cand numara
        System.out.println("Operatia de deschidere a unei imagini a durat " + (endTime - startTime) / 1000000 + " \n");

        
        /*Rezolvare multithreading*/
        startTime = System.nanoTime();
        //se creeaza un nou conector pentru imaginea initiala, finala si sectiuni
        Conector buffer = new Conector(path1); 
        buffer.partImage = new BufferedImage(Image.getOriginalImage().getWidth(), Image.getOriginalImage().getHeight(), BufferedImage.TYPE_INT_RGB);
        BufferedImage sectiuni = null;
        //starile producerului si consumerului
        Producer producerThread = new Producer(buffer);
        Consumer consumerThread = new Consumer(buffer);
        producerThread.start();
        consumerThread.start();
        try {//sincronizare
            consumerThread.join();
        } catch(InterruptedException e) {}
        
        //se creeaza cele 4 sectiuni ale imaginii principale
        for (int numar_sectiune = 0; numar_sectiune < 4; numar_sectiune++) {
            try {// se creeaza fiecare sectiune apoi mirror pe sectiuni
                sectiuni = ImageIO.read(new File("seg_" + String.valueOf(numar_sectiune + 1) + ".bmp"));
                for (int i = 0; i < sectiuni.getHeight() - 1; i++) {
                    for (int j = 0; j < sectiuni.getWidth() - 1; j++) {
                        buffer.partImage.getRaster().setDataElements(j, numar_sectiune * sectiuni.getHeight(), buffer.partImage.getColorModel().getDataElements(sectiuni.getRGB(j, i), null));
                    }
                }
            } catch (IOException e) {}
        }
        for (int i = 0; i < 4; i++) {//Aplicam algoritmul de mirroring pentru fiecare sectiune de imagine
            String path = "seg_" + String.valueOf(i + 1) + ".bmp";
            Image.openImage(path);
            Image.mirrorImage(Image.getOriginalImage(), Image.getResultImage());
            Image.saveImage(path, Image.getResultImage());
        }//afisam timpul multithreadhingului
        endTime = System.nanoTime();
        System.out.println("\nMultithreading-ul + rotirea segmentelor de imagine a durat " + (endTime - startTime) / 1000000 + " ms");

        //Aplicam algoritmul de mirroring pe fiecare sectiune de imagine
        startTime = System.nanoTime();
        Image.openImage(path1); //deschidem imaginea
        Image.mirrorImage(Image.getOriginalImage(), Image.getResultImage());//mirror
        Image.saveImage(path2, Image.getResultImage());//salvam
        endTime = System.nanoTime(); //timp
        System.out.println("\n[pe imaginea completa] Algoritmul a durat " + (endTime - startTime) / 1000000 + " ms");
    }
}