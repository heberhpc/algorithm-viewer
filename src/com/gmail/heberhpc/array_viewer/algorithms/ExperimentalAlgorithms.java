/**/

//
package com.gmail.heberhpc.array_viewer.algorithms;

//
import com.gmail.heberhpc.array_viewer.core.VerticalBarArray;

//
public class ExperimentalAlgorithms {
	
	//square insertion sort + selection sort
	public static void squareInsertionSortSelection(VerticalBarArray data) {
		
		data.setAlgorithmName("Square Insertion Sort - Fase 1: By slices");
		
		int square = (int) Math.sqrt(data.length());		
		int slices = square;
		int start = 0;
		int end;
		
		//first fase:insertion by slices
		for (int i = 0; i < slices ; i ++) {
			
			start=i*square;System.out.println("start:"+start);
			end=start+square; System.out.println("end:"+end);
			
			for (int k = start ; k < end; k++) {
				int cur = data.read(k);
				int j = k ;
				while (j > start && data.read(j-1) > cur ) {
					data.write(j, data.read(j-1));
					j--;
				}
				data.write(j, cur);
			}
		}
		
		//second fase:selection sort
		SortingAlgorithms.selectionSort(data);			
	}
}
