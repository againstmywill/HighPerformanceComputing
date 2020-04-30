package com.mycompany.k_nearest_neighbors;

import static com.mycompany.k_nearest_neighbors.kNN.neighbors;
import java.util.Arrays;
import java.util.Comparator;
import java.io.IOException;



public class kNN {
  
    public static void main(String[] args) throws IOException, InterruptedException {
        long startTime = System.nanoTime();
        int[][] trainingImages = mnist.mnistImageReader("data/train-images.idx3-ubyte");
        int[] trainingLabels = mnist.mnistLabelReader("data/train-labels.idx1-ubyte");
        
        int[][] testImages = mnist.mnistImageReader("data/t10k-images.idx3-ubyte");
        int[] testLabels = mnist.mnistLabelReader("data/t10k-labels.idx1-ubyte");
        double accuracy=0;

        int k=13;
       
       
        int[] guess = new int[testImages.length];
        
        distance a = new distance(guess, trainingImages, testImages, trainingLabels, k, 0, 2500);
        distance b = new distance(guess, trainingImages, testImages, trainingLabels, k, 2500, 5000);
        distance c = new distance(guess, trainingImages, testImages, trainingLabels, k, 5000, 7500);
        distance d = new distance(guess, trainingImages, testImages, trainingLabels, k, 7500, 10000);
        
        Thread t1= new Thread(a);
        Thread t2= new Thread(b);
        Thread t3= new Thread(c);
        Thread t4= new Thread(d);
        
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t1.join();
        t2.join();
        t3.join();
        t4.join();
       
        /*
        double[][] distance= new double[trainingImages.length][2];
        
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
        */
        for(int i = 0; i<testLabels.length;i++) if(guess[i]==testLabels[i]) accuracy=accuracy+1; 
        
        accuracy=accuracy/testLabels.length*100;
        System.out.print(accuracy);
        long endTime = System.nanoTime();
        System.out.println("Took "+(endTime - startTime) + " ns"); 
        int[] f = testImages[5];
        mnist.printImage(f);
        System.out.print("\n"+guess[5]);
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

 class distance implements Runnable{
    double[][] distArr = new double[60000][2];
    int[] guess;
    int[][] mnistTrainArray;
    int[][] mnistTestArray;
    int[] mnistTrainLabels;
    int knn;
    int begin;
    int end;
    public distance(int[] guess,int[][] mnistTrainArray, int[][] mnistTestArray, int[] mnistTrainLabels,int knn, int begin, int end){
         this.guess=guess;
         this.mnistTrainArray=mnistTrainArray;
         this.mnistTestArray=mnistTestArray;
         this.mnistTrainLabels=mnistTrainLabels;
         this.knn=knn;
         this.begin=begin;
         this.end=end;
    }
    
    @Override
    public void run(){
       
       
        for(int i=begin; i<end;++i){
            for(int j=0; j<mnistTrainArray.length; ++j){
            
                for(int k=0;k<mnistTrainArray[j].length;++k){
                    distArr[j][0]=distArr[j][0]+(mnistTrainArray[j][k]-mnistTestArray[i][k])*(mnistTrainArray[j][k]-mnistTestArray[i][k]);
                }
                distArr[j][1]=mnistTrainLabels[j];
                distArr[j][0]=Math.sqrt(distArr[j][0]/mnistTrainArray[j].length);
            }
            // sorts the array from least distance from the test point to most distance
            Arrays.sort(distArr, Comparator.comparingDouble(o -> o[0]));
            guess[i]=neighbors(distArr,knn);
        }
        
    }
 } 