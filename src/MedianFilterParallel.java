import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import javax.imageio.ImageIO;

public class MedianFilterParallel {

    static class MedianFilter extends RecursiveTask<Integer[][]> {

        // variables
        private int xLow, xHigh, yLow, yHigh, filter;
        private int[][] colourArray;
        private boolean sLength;
        private int maxThresh = 10000;
        
        //Constructor
        public MedianFilter
           (
                int xLow,
                int yLow,
                int xHigh,
                int yHigh,
                int filter,
                int[][] colourArray,
                boolean sLength
                ) 
                {
                this.xLow = xLow;
                this.yLow = yLow;
                this.xHigh = xHigh;
                this.yHigh = yHigh;
                this.filter = filter;
                this.colourArray = colourArray;
               this.sLength = sLength;
        }
 //Creating a 2D array for the pixels of the images
        private Integer[][] medianFilter() {
            int xWidth = xHigh - xLow;
            int xHeight = yHigh - yLow;
            int width = colourArray[0].length;
            int height = colourArray.length;
            int radius = (filter - 1) / 2;

            Integer[][] pixels = new Integer[xHeight][xWidth];

            int yIndex = 0;

            for (int y = yLow; y < yHigh; y++) {
                int xIndex = 0;
                for (int x = xLow; x < xHigh; x++) {

                   
                    int xStart = x - radius;
                    int yStart = y - radius;

                    ArrayList<Integer> red = new ArrayList<Integer>();
                    ArrayList<Integer> green = new ArrayList<Integer>();
                    ArrayList<Integer> blue = new ArrayList<Integer>();
                    ArrayList<Integer> alpha = new ArrayList<Integer>();

                    for (int i = xStart; i < xStart + filter; i++) {
                        for (int j = yStart; j < yStart + filter; j++) {

                            if (!(i < 0 || i >= width || j < 0 || j >= height)) {

                                int pix = colourArray[j][i];
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
                    pixels[yIndex][xIndex] = colour;
                    xIndex++;

                }
                yIndex++;
            }

            return pixels;
        }
//Divide and conquer implemantation of parallel algorithm
      
        private static Integer[][] merge(Integer[][] sectionA, Integer[][] sectionB, boolean sLength) {

            if (sLength) {

                Integer[][] spec = new Integer[sectionA.length][sectionA[0].length + sectionB[0].length];

                for (int i = 0; i < spec.length; i++) {
                    int index = 0;

                    for (int a : sectionA[i]) {
                        spec[i][index] = a;
                        index++;
                    }

                    for (int b : sectionB[i]) {
                        spec[i][index] = b;
                        index++;
                    }
                }

                return spec;
            } else {
                int h = sectionA.length + sectionB.length;
                Integer[][] spec = new Integer[h][sectionA.length];

                int index = 0;

                for (Integer[] a : sectionA) {
                    spec[index] = a;
                    index++;
                }

                for (Integer[] b : sectionB) {
                    spec[index] = b;
                    index++;
                }

                return spec;

            }

        }

        @Override
        
        //Divide and conquer implemantation of parallel algorithm
        protected Integer[][] compute() {
            if ((xHigh - xLow) * (yHigh - yLow) <= maxThresh) {
            
                return medianFilter();
             } 
            else {

               //Finding the median of the pixels
                if (sLength) {

                    int mid = xLow + (xHigh - xLow) / 2;
                    MedianFilter left = new MedianFilter(xLow, yLow, mid, yHigh, filter, colourArray, !sLength);
                    MedianFilter right = new MedianFilter(mid, yLow, xHigh, yHigh, filter, colourArray, !sLength);
                    left.fork();
                    Integer[][] rightSect = right.compute();
                    Integer[][] leftSect = left.join();

                    return merge(leftSect, rightSect, sLength);

                }
                 else {
                    int mid = yLow + (yHigh - yLow) / 2;
                    MedianFilter top = new MedianFilter(xLow, yLow, xHigh, mid, filter, colourArray, !sLength);
                    MedianFilter bottom = new MedianFilter(xLow, mid, xHigh, yHigh, filter, colourArray, !sLength);
                    top.fork();
                    Integer[][] rightSect = bottom.compute();
                    Integer[][] leftSect = top.join();

                    return merge(leftSect, rightSect, sLength);
                }
            }
        }

    }
    
   // main method
    public static void main(String args[]) throws IOException {

        String path = args[0];
        BufferedImage img = ImageIO.read(new File("images/", path));
        int xLength = img.getWidth();
        int yLength = img.getHeight();
        int[][] colourArray = new int[yLength][xLength];

        for (int y = 0; y < yLength; y++) {
            for (int x = 0; x < xLength; x++) {
                colourArray[y][x] = img.getRGB(x, y);
            }
        }

        MedianFilter meanFilter = new MedianFilter(0, 0, xLength, yLength, Integer.parseInt(args[2]), colourArray, true);

        // Create the Thread Pool
        int threadNum = Runtime.getRuntime().availableProcessors();
        ForkJoinPool forkJoinPool = new ForkJoinPool(threadNum);

        System.out.println("Performing the median filter ...");
        long t1 = System.currentTimeMillis();
        Integer[][] pixels = forkJoinPool.invoke(meanFilter);
        long timeTaken = System.currentTimeMillis() - t1;

        // The output Image
        File output = new File("filtered_images/", args[1]);
        BufferedImage outImage = new BufferedImage(xLength, yLength, BufferedImage.TYPE_INT_RGB);

        // Assigning positions to picels to achieve filtering
        for (int x = 0; x < xLength; x++) {
            for (int y = 0; y < yLength; y++) {
                int colour = pixels[y][x];
                outImage.setRGB(x, y, colour);
            }
        }

        ImageIO.write(outImage, "jpeg", output);
        System.out.println("The median filter took " + timeTaken + " milliseconds to complete.");

    }
}
