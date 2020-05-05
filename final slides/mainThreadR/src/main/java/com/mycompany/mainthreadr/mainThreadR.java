package com.mycompany.mainthreadr;

class Geeks { 
    public static void m1() 
    { 
        System.out.println("Hello Visitors"); 
    } 
} 
  
// code provided by Bishal Kumar Dubey at geeksforgeeks
class mainThreadR extends Geeks implements Runnable { 
    public void run() 
    { 
        System.out.println("Run method executed by child Thread"); 
    } 
    public static void main(String[] args) 
    { 
        mainThreadR t = new mainThreadR(); 
        t.m1(); 
        Thread t1 = new Thread(t); 
        t1.start(); 
        System.out.println("Main method executed by main thread"); 
    } 
} 
