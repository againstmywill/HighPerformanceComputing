
package com.mycompany.k_nearest_neighbors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;




public class Validator {
    
    public Integer[] list;
    public static int[][] arrImages=new int[10000][];
    public static int[] arrLabels=new int[10000];
    public static int[][] listImages=new int[50000][];
    public static int[] listLabels=new int[50000];
    
    
    public Validator(int[][] images, int[] labels){
      Random randNum = new Random();
      Set<Integer>set = new LinkedHashSet<Integer>();
      while (set.size() < 10000) {
         set.add(randNum.nextInt(60000));
      }
      
      Integer[] list = new Integer[set.size()];
      set.toArray(list);
      this.list=list;
      Arrays.parallelSort(list);
      
       List<int[]> listImages=new ArrayList<>();
       List<Integer> listLabels= new ArrayList<>();
      
      
      for(int i=0;i<images.length; ++i){
          listImages.add(images[i]);
          listLabels.add(labels[i]);
      }
      
      
     
     
      int index;
      for(int i=list.length-1;i>=0;--i){
          index=list[i];
          arrImages[i]=listImages.get(index);
          arrLabels[i]=listLabels.get(index);
          listImages.remove(index);
          listLabels.remove(index);
      }
      
      this.listImages=listImages.toArray(this.listImages);
      for(int i=0;i<listLabels.size();++i)this.listLabels[i]=listLabels.get(i);
    
    }
    
}
