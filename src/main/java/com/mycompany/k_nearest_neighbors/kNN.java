package com.mycompany.k_nearest_neighbors;

import static com.mycompany.k_nearest_neighbors.kNN.neighbors;
import java.util.Arrays;
import java.util.Comparator;
import java.io.IOException;



public class kNN {
  
    public static void main(String[] args) throws IOException, InterruptedException {
        
        int[][] trainingImages = mnist.mnistImageReader("data/train-images.idx3-ubyte");
        int[] trainingLabels = mnist.mnistLabelReader("data/train-labels.idx1-ubyte");
        int[][] testImages = mnist.mnistImageReader("data/t10k-images.idx3-ubyte");
        int[] testLabels = mnist.mnistLabelReader("data/t10k-labels.idx1-ubyte");
        
        

        
        int k=3;
        double accuracy=0;
        int[] predictions;
        Boolean testing=false;// flip to true for validation testing
        Thread[] workers = new Thread[4];
        
        if(!testing){
       
        predictions = new int[testImages.length];
         
       
        for(int i=0;i<4;i++){
           
           Task task = new Task(predictions, trainingImages, testImages, trainingLabels,k, i*2500,(i+1)*2500);
           workers[i]=new Thread(task);
           workers[i].start();
        }
        for(int i=0;i<4;i++){
            workers[i].join();
        }
       
        
        
        for(int i = 0; i<testLabels.length;i++) if(predictions[i]==testLabels[i]) accuracy=accuracy+1; 
        
        accuracy=accuracy/testLabels.length*100;
        System.out.print(accuracy);
        
        }else{
            
            Validator vObj= new Validator(trainingImages,trainingLabels);
        
            int[][] valTrainingImages=vObj.listImages;
            int[] valTrainingLabels=vObj.listLabels;
            int [][] valTestImages=vObj.arrImages;
            int[]valTestLabels=vObj.arrLabels;
            
            while(k<30){
                accuracy=0;
                predictions = new int[valTestImages.length];
            
                for(int i=0;i<4;i++){
                    Task task = new Task(predictions, valTrainingImages, valTestImages, valTrainingLabels, k, i*2500,(i+1)*2500);
                    workers[i]=new Thread(task);
                    workers[i].start();
                }
                for(int i=0;i<4;i++){
                    workers[i].join();
                }
                for(int i = 0; i<valTestLabels.length;i++) if(predictions[i]==valTestLabels[i]) accuracy=accuracy+1; 
        
                accuracy=accuracy/valTestLabels.length*100;
                System.out.print(accuracy);
                k++;
                }
        }
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

 class Task implements Runnable{
    double[][] distArr;
    int[] guess;
    int[][] mnistTrainArray;
    int[][] mnistTestArray;
    int[] mnistTrainLabels;
    int knn;
    int begin;
    int end;
    public Task(int[] guess,int[][] mnistTrainArray, int[][] mnistTestArray, int[] mnistTrainLabels,int knn, int begin, int end){
         this.guess=guess;
         this.mnistTrainArray=mnistTrainArray;
         this.mnistTestArray=mnistTestArray;
         this.mnistTrainLabels=mnistTrainLabels;
         this.knn=knn;
         this.begin=begin;
         this.end=end;
         this.distArr=new double[(mnistTrainArray.length)][2];
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
            Arrays.parallelSort(distArr, Comparator.comparingDouble(o -> o[0]));
            guess[i]=neighbors(distArr,knn);
        }
        
    }
 } 