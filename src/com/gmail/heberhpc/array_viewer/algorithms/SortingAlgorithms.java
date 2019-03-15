/*Insertion Sort*/


package com.gmail.heberhpc.array_viewer.algorithms;


import com.gmail.heberhpc.array_viewer.core.VerticalBarArray;


public class SortingAlgorithms {
	
	//Insertion Sort
	public static void insertionSort(VerticalBarArray data) {

		//algorithm name
		data.setAlgorithmName("Insertion Sort");
		
		//process
		for (int i = 0 ; i < data.length() ; i++) {
			int current = data.read(i);
			int j = i ;
			while (j > 0 && data.read(j-1) > current ) {
				data.write(j, data.read(j-1));
				j--;
			}
			data.write(j, current);
		}
	}
	
	//Selection Sort
	public static void selectionSort(VerticalBarArray data) {
		
		//algorithm name
		data.setAlgorithmName("Selection Sort");
		
		//process
        for (int i = 0; i < data.length() -1; i++) {
    	
            // Find the minimum
        	int min_index = i; 
            for (int j = i+1; j < data.length() ; j++) 
                if (data.read(j)  < data.read(min_index)) 
                    min_index = j; 
  
            // Swap the found minimum element 
            int temp = data.read(min_index);
            data.write(min_index, data.read(i));
            
            data.write(i, temp);
        }
	}
}
