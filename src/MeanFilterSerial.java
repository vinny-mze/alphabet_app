//Mean Filter Serial Implemantation
// Muzerengwa Vincent MZRVIN001
//10/08/2022

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MeanFilterSerial {

    public static void main(String[] args) throws IOException {

        String path = args[0];
        String out = args[1];
        int filter = Integer.parseInt(args[2]);

        File f = new File("images/", path);
        BufferedImage imag = ImageIO.read(f);

       
        int height = imag.getHeight();
        int width = imag.getWidth();
        
        int[][] processed = new int[width][height];
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int radius = (int) ((filter - 1) / 2);
        int blocks = (filter * filter);

        long cTime = System.currentTimeMillis();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                int xStart = x - radius;
                int yStart = y - radius;

                int red = 0;
                int green = 0;
                int blue = 0;
                int alpha = 0;

                for (int i = xStart; i < xStart + filter; i++) {
                    for (int j = yStart; j < yStart + filter; j++) {

                        if (!(i < 0 || i >= width || j < 0 || j >= height)) {

                            int pix = imag.getRGB(i, j);

                            red += (pix >> 16) & 0xff;
                            green += (pix >> 8) & 0xff;
                            blue += pix & 0xff;
                            alpha += (pix >> 24) & 0xff;

                        }
                    }
                }

                int avr = (int) (red / blocks);
                int avg = (int) (green / blocks);
                int avb = (int) (blue / blocks);
                int ava = (int) (alpha / blocks);

                int colour = (ava << 24) | (avr << 16) | (avg << 8) | avb;
                processed[x][y] = colour;
                outputImage.setRGB(x, y, colour);

            }
        }
        long timeTaken = System.currentTimeMillis() - cTime;

        System.out.println(
                "The mean filter took " + timeTaken + " milliseconds when a " + filter + "x" + filter + " was used.");

        // write to output file
        File outputfile = new File("filtered_images", out);
        ImageIO.write(outputImage, "jpeg", outputfile);

    }

}