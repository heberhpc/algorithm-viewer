/**/

//
package com.gmail.heberhpc.array_viewer.examples;

//
import com.gmail.heberhpc.array_viewer.algorithms.SearchAlgorithms;
import com.gmail.heberhpc.array_viewer.core.VerticalBarArray;

//
public class ExampleOfUseSearch {
	
	public static void main (String [] args) {
		
		int [] fixed = {271, 4, 146, 101, 33, 124, 234, 379, 386, 327, 176, 348, 62, 228, 
				132, 389, 217, 100, 202, 240, 72, 67, 230, 57, 172, 305, 148, 237, 
				191, 114, 308, 369, 71, 334, 66, 204, 359, 69, 68, 98, 293, 83, 
				252, 127, 150, 236, 381, 348, 261, 244, 171, 208, 3, 221, 171, 102, 
				393, 94, 105, 375, 105, 61, 41, 100, 370, 217, 85, 51, 385, 118, 
				197, 51, 180, 325, 252, 317, 299, 319, 191, 393, 360, 399, 129, 138, 
				117, 329, 121, 391, 308, 395, 261, 23, 193, 110, 344, 376, 340, 
				57, 286, 397};
		
		//create a instance for test
		VerticalBarArray data = new VerticalBarArray(fixed);
		
		//setup a velocity: the higher n, the slower effect!!!
		data.setPauseVelocity(45);

		//applying test 
		SearchAlgorithms.linearSearc(395, data);		
	}
}
