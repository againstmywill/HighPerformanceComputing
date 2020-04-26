package com.mycompany.k_nearest_neighbors;

import java.util.Arrays;
import java.util.Comparator;
import java.io.IOException;



public class kNN {


    public static void main(String[] args) throws IOException {
        
        int[][] trainingImages = mnist.mnistImageReader("data/train-images.idx3-ubyte");
        int[] trainingLabels = mnist.mnistLabelReader("data/train-labels.idx1-ubyte");
        
        int[][] testImages = mnist.mnistImageReader("data/t10k-images.idx3-ubyte");
        int[] testLabels = mnist.mnistLabelReader("data/t10k-labels.idx1-ubyte");
        // our programs guess based on the knn implementaion
        int[] guess = new int[testImages.length];
        // array to hold the distance of all the traininng points from the test point
        double[][] distance= new double[trainingImages.length][2];
        int k=245;
        double dist;
        
        for(int i=0; i<testImages.length;i++){
            for(int j=0; j<trainingImages.length; j++){
                dist=0;
                for(int l=0;l<trainingImages[j].length;++l){
                    dist=dist+Math.pow((trainingImages[j][l]-testImages[i][l]),2);
                }
                distance[j][1]=trainingLabels[j];
                distance[j][0]=Math.sqrt(dist/trainingImages[j].length);
            }
            // sorts the array from least distance from the test point to most distance
            Arrays.sort(distance, Comparator.comparingDouble(o -> o[0]));
            guess[i]=neighbors(distance,k);
        }
       //see how accurate the knn is
        double accuracy=0;
        for(int i = 0; i<testLabels.length;i++) if(guess[i]==testLabels[i]) accuracy=accuracy+1;
        
        accuracy=accuracy/testLabels.length*100;
        System.out.print(accuracy);
  
    }
    
    // since the digits are 0-9 set up the indexs to match the digits
    public static int neighbors(double[][] a, int b){
        int[] popularityContest=new int[10];
        for(int i=0; i<b; i++){ popularityContest[(int)a[i][1]]++;
            
        }
        
        return findMaxIndex(popularityContest,0,popularityContest.length-1);
    }
    
    public static int findMaxIndex(int[] a,int beginning, int end){
        if(beginning-end==0)return beginning;
        
        int middle=(beginning+end)/2;
        int left = findMaxIndex(a,beginning,middle);
        int right = findMaxIndex(a,middle+1,end);
        
        if(a[right]>a[left])return right;
        else return left;
    }
    

    
}
