/**/

//
package com.gmail.heberhpc.array_viewer.examples;

//
import java.util.Random;
import com.gmail.heberhpc.array_viewer.algorithms.ExperimentalAlgorithms;
import com.gmail.heberhpc.array_viewer.core.VerticalBarArray;

//
public class ExampleOfUseExperimental {
	
	public static void main (String [] args) {
		
		//random "factory"
		Random rand= new Random();
		
		//random array 
		int size=100;
		int [] randomic = new int[size];
		for (int i = 0 ; i < randomic.length ; i ++) {
			randomic[i]= rand.nextInt(400);
			System.out.println(randomic[i]);
		}
		
		//create a instance for test
		VerticalBarArray data = new VerticalBarArray(randomic);
		
		//setup a velocity: the higher n, the slower effect!!!
		data.setPauseVelocity(1);

		//applying test 
		ExperimentalAlgorithms.squareInsertionSortSelection(data);		
	}
}
