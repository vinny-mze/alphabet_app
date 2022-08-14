//Mean Filter Parallel Implemantation
// Muzerengwa Vincent MZRVIN001
//10/08/2022

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import javax.imageio.ImageIO;

public class MeanFilterParallel {

    static class MeanFilter extends RecursiveTask<Integer[][]> {

        //Variables
        private int xLow, xHigh, yLow, yHigh, filter;
        private int[][] colourArray;
        private boolean sLength;
        private int maxThresh = 10000;

   //Constructor
        public MeanFilter
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

    
        private Integer[][] meanFilter() {

            int xWidth = xHigh - xLow;
            int xHeight = yHigh - yLow;
            int width = colourArray[0].length;
            int height = colourArray.length;
            int radius = (filter - 1) / 2;
            int blocks = filter * filter;
            Integer[][] pixels = new Integer[xHeight][xWidth];

            int yIndex = 0;

            for (int y = yLow; y < yHigh; y++) {
                int xIndex = 0;
                for (int x = xLow; x < xHigh; x++) {
                    int xStart = x - radius;
                    int yStart = y - radius;
                    int red = 0;
                    int green = 0;
                    int blue = 0;
                    int alpha = 0;
		    for (int i = xStart; i < xStart + filter; i++) {
                        for (int j = yStart; j < yStart + filter; j++) {
                            if (!(i < 0 || i >= width || j < 0 || j >= height)) {

                                int pix = colourArray[j][i];

                                
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

                    int colour = (ava << 24) | (avr << 16) | (avg << 8) |avb;
                    pixels[yIndex][xIndex] = colour;
                    xIndex++;

                }
                yIndex++;
            }

            return pixels;
        }

        @Override
        protected Integer[][] compute() {

            if ((xHigh - xLow) * (yHigh - yLow) <= maxThresh) {
                return meanFilter();
             } 
        	else {

                
                if (sLength) {

                    int mid = xLow + (xHigh - xLow) / 2;
                    MeanFilter left = new MeanFilter(xLow, yLow, mid, yHigh, filter, colourArray, !sLength);
                    MeanFilter right = new MeanFilter(mid, yLow, xHigh, yHigh, filter, colourArray, !sLength);
                    left.fork();
                    Integer[][] rightSect = right.compute();
                    Integer[][] leftSect = left.join();

                    return merge(leftSect, rightSect, sLength);

                } else {
                    int mid = yLow + (yHigh - yLow) / 2;
                    MeanFilter top = new MeanFilter(xLow, yLow, xHigh, mid, filter, colourArray, !sLength);
                    MeanFilter bottom = new MeanFilter(xLow, mid, xHigh, yHigh, filter, colourArray, !sLength);
                    top.fork();
                    Integer[][] rightSect = bottom.compute();
                    Integer[][] leftSect = top.join();

                    return merge(leftSect, rightSect, sLength);
                }
            }

        }
    }

    
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

    
    public static void main(String args[]) throws IOException {

        BufferedImage img = ImageIO.read(new File("images/", args[0]));
        int xLength = img.getWidth();
        int yLength = img.getHeight();
        int[][] colourArray = new int[yLength][xLength];

        for (int y = 0; y < yLength; y++) {
            for (int x = 0; x < xLength; x++) {
                colourArray[y][x] = img.getRGB(x, y);
            }
        }

        MeanFilter meanFilter = new MeanFilter(0, 0, xLength, yLength, Integer.parseInt(args[2]), colourArray, true);

        // Create the Thread Pool
        int threadNum = Runtime.getRuntime().availableProcessors();
        ForkJoinPool forkJoinPool = new ForkJoinPool(threadNum);

        System.out.println("Performing the mean filter ...");
        long t1 = System.currentTimeMillis();
        Integer[][] pixels = forkJoinPool.invoke(meanFilter);
        long timeTaken = System.currentTimeMillis() - t1;

        //Output
        File output = new File("filtered_images/", args[1]);
        BufferedImage imageOut = new BufferedImage(xLength, yLength, BufferedImage.TYPE_INT_RGB);

        // Set every pixel in place
        for (int x = 0; x < xLength; x++) {
            for (int y = 0; y < yLength; y++) {
                int colour = pixels[y][x];
                imageOut.setRGB(x, y, colour);
            }
        }

        ImageIO.write(imageOut, "jpeg", output);
        System.out.println("The mean filter took " + timeTaken + " milliseconds to complete.");

    }

}
