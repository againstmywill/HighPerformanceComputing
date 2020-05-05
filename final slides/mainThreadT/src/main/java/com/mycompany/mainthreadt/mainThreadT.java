/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mainthreadt;


// Java program to illustrate defining Thread 
// by extending Thread class 
  
// Here we cant extends any other class 
class mainThreadT extends Thread  
{ 
    public void run() 
    { 
        System.out.println("Run method executed by child Thread"); 
    } 
    public static void main(String[] args) 
    { 
        mainThreadT t = new mainThreadT(); 
        t.start(); 
        System.out.println("Main method executed by main thread"); 
    } 
} 

