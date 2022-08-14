//Medeian Filter Parallel Implemantation
// Muzerengwa Vincent MZRVIN001
//10/08/2022

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.imageio.ImageIO;

public class MedianFilterSerial {

    public static void main(String[] args) throws IOException {

        
        String path = args[0];
        String out = args[1];
        int filter = Integer.parseInt(args[2]);
        
        File f = new File("images/", path);
        BufferedImage imag = ImageIO.read(f);
        
        int height = imag.getHeight();
        int width = imag.getWidth();

        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int radius = (int) ((filter - 1) / 2);

    
        long cTime = System.currentTimeMillis();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
            
                int xStart = x - radius;
                int yStart = y - radius;

                ArrayList<Integer> red = new ArrayList<Integer>();
                ArrayList<Integer> green = new ArrayList<Integer>();
                ArrayList<Integer> blue = new ArrayList<Integer>();
                ArrayList<Integer> alpha = new ArrayList<Integer>();

                for (int i = xStart; i < xStart + filter; i++) {
                    for (int j = yStart; j < yStart + filter; j++) {

                        if (!(i < 0 || i >= width || j < 0 || j >= height)) {

                            int pix = imag.getRGB(i, j);

                            red.add((pix >> 16) & 0xff);
                            green.add((pix >> 8) & 0xff);
                            blue.add(pix & 0xff);
                            alpha.add((pix >> 24) & 0xff);

                        }

                        else {
                            red.add(0);
                            green.add(0);
                            blue.add(0);
                            alpha.add(0);
                        }

                    }
                }

                Collections.sort(red);
                Collections.sort(green);
                Collections.sort(blue);
                Collections.sort(alpha);

                int avr = red.get(red.size() / 2);
                int avg = green.get(green.size() / 2);
                int avb = blue.get(blue.size() / 2);
                int ava = alpha.get(alpha.size() / 2);

                int colour = (ava << 24) | (avr << 16) | (avg << 8) | avb;

                outputImage.setRGB(x, y, colour);

            }
        }
        
        long timeTaken = System.currentTimeMillis() - cTime;

        System.out.println(
                "The median filter took " + timeTaken + " milliseconds when a " + filter +
                        "x" + filter + " was used.");

        // write to output file
        File outputfile = new File("filtered_images", out);
        ImageIO.write(outputImage, "jpeg", outputfile);

    }

}