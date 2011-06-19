package main;

import java.util.ArrayList;
import java.util.List;

public class TestNeuralNetworks
{

	public static void main(String[] args)
	{
		testOpenCloseRightHand();
		testRightHandLeftHand();
		testNumFingers();
		testVowels();
	}
	
	public static void testOpenCloseRightHand()
	{
		List<String> dirs = new ArrayList<String>();
		dirs.add("test/right/fingers/0/");
		dirs.add("test/right/fingers/5/");
		
		Util.testInstances("OpenCloseRightHand", dirs);
	}
	
	public static void testRightHandLeftHand()
	{
		List<String> dirs = new ArrayList<String>();
		dirs.add("test/right/fingers/5/");
		dirs.add("test/left/fingers/5/");
		
		Util.testInstances("RightHandLeftHand", dirs);
	}
	
	public static void testNumFingers()
	{
		List<String> dirs = new ArrayList<String>();
		dirs.add("test/right/fingers/0/");
		dirs.add("test/right/fingers/1/");
		dirs.add("test/right/fingers/2/");
		dirs.add("test/right/fingers/3/");
		dirs.add("test/right/fingers/4/");
		dirs.add("test/right/fingers/5/");
		
		Util.testInstances("NumFingers", dirs);
	}
	
	public static void testVowels()
	{
		List<String> dirs = new ArrayList<String>();
		dirs.add("test/right/vowels/a/");
		dirs.add("test/right/vowels/e/");
		dirs.add("test/right/vowels/i/");
		dirs.add("test/right/vowels/o/");
		dirs.add("test/right/vowels/u/");
		
		Util.testInstances("Vowels", dirs);
	}

}
