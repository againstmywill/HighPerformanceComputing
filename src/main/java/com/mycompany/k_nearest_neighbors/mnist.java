package com.mycompany.k_nearest_neighbors;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



public class mnist {
    
    public static int[][] mnistImageReader(String s) throws IOException{
       Path file = Paths.get(s);
       byte[] b = Files.readAllBytes(file);
        
       int magicNumber= (b[0]<<24)&0xff000000|
       (b[1]<<16)&0x00ff0000|
       (b[2]<< 8)&0x0000ff00|
       (b[3]<< 0)&0x000000ff;

       if (magicNumber!=2051){
           System.out.print("Wrong File");   
           System.exit(0);
       }
       
       int numberImages= (b[4]<<24)&0xff000000|
       (b[5]<<16)&0x00ff0000|
       (b[6]<< 8)&0x0000ff00|
       (b[7]<< 0)&0x000000ff;
       
       int imageRows= (b[8]<<24)&0xff000000|
       (b[9]<<16)&0x00ff0000|
       (b[10]<< 8)&0x0000ff00|
       (b[11]<< 0)&0x000000ff;
       
       int imageColumns= (b[12]<<24)&0xff000000|
       (b[13]<<16)&0x00ff0000|
       (b[14]<< 8)&0x0000ff00|
       (b[15]<< 0)&0x000000ff;
        
        int[][] list = new int[numberImages][imageRows*imageColumns];
        addVectors(list,b, numberImages, imageRows*imageColumns);
        
        return list;
    }
    
    public static void addVectors(int[][] a, byte[] b, int numMat, int rowCol){
        int beginning;
        int end;
        int e = 0;
      
        for(int i = 0; i<numMat; ++i){
            beginning=i*rowCol+16;
            end=beginning+rowCol;
            
            for(int k=beginning;k<end; ++k){
                a[i][e]=b[k] & 0xFF;
                e++;        
            }
            e=0;
        }
    }
    
    public static int[] mnistLabelReader(String s) throws IOException{
        Path file = Paths.get(s);
        byte[] b = Files.readAllBytes(file);
        
        int magicNumber = (b[0]<<24)&0xff000000|
           (b[1]<<16)&0x00ff0000|
           (b[2]<< 8)&0x0000ff00|
           (b[3]<< 0)&0x000000ff;
        
        if (magicNumber!=2049){
           System.out.print("Wrong File");   
           System.exit(0);
        }
        int numberLabels = (b[4]<<24)&0xff000000|
           (b[5]<<16)&0x00ff0000|
           (b[6]<< 8)&0x0000ff00|
           (b[7]<< 0)&0x000000ff;
       
        int[] list = new int[numberLabels];
        
        for(int i = 0; i < numberLabels; ++i)list[i]=b[i+8] & 0xFF;
        
        return list;
    }
    
    public static void printImage(int[] a){
        for(int i=0;i<28*28;i++){
            if(i%28==0)System.out.print("\n");
            if(a[i]>0)System.out.print("X");
            else System.out.print(0);
        }
    }
}
