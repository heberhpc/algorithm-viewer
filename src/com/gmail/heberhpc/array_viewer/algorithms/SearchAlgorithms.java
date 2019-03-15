/**/


package com.gmail.heberhpc.array_viewer.algorithms;


import com.gmail.heberhpc.array_viewer.core.VerticalBarArray;


public class SearchAlgorithms {
	
	public static void linearSearc(int target, VerticalBarArray data) {
		
		data.setAlgorithmName("Linear Search ---> Searchig for:"+ target);
		
		for (int i = 0 ; i < data.length() ; i++) {
			if(data.read(i) == target) {
				data.markValueAsFound(i);
				System.out.println("found, index: "+i);
				break;
			}else {
				System.out.println("not found, index: "+i);
			}
		}
	}
	
	
	public static int InteractiveBinarySearch(VerticalBarArray data, int key) {
			    
		data.setAlgorithmName("InteractiveBinarySearch");
		
		int low=0;
		int high = data.length();
		int index = Integer.MAX_VALUE;
		while (low <= high) {
			int mid = (low + high) / 2;
			if (data.read(mid) < key) {
				low = mid + 1;
			} else if (data.read(mid) > key) {
				high = mid - 1;
			} else if (data.read(mid) == key) {
				index = mid;
				break;
			}
		}
		data.markValueAsFound(index);
		return index;
	}
}