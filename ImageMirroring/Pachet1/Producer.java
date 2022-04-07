package Pachet1;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.Color;
import java.nio.Buffer;
public class Producer extends Worker {
    private Conector buffer;
    //verificare
    public Producer(Conector buffer) {
        super(true);
        this.buffer = buffer;
    }

    @Override
    public void starter() {
        for (int i = 0; i < 4; i++) {
            buffer.read();//citim datele de la imagine si formam cele 4 segmente
            System.out.println("P" + i);
        }
    }
}