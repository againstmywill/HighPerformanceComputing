package com.mycompany.k_nearest_neighbors;

import java.io.IOException;

/**
 *
 * @author grand
 */
public class kNN {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        int[][] trainingImages = mnist.mnistImageReader("data/train-images.idx3-ubyte");
        int[] trainingLabels = mnist.mnistLabelReader("data/train-labels.idx1-ubyte");
        
        int[][] testImages = mnist.mnistImageReader("data/t10k-images.idx3-ubyte");
        int[] testLabels = mnist.mnistLabelReader("data/t10k-labels.idx1-ubyte");
        
        System.out.print(trainingLabels[0]);
        

    }
    
}
