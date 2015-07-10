package com.wz.util;

/**
 * All methods print data to console.
 * @author hemal
 *
 */
public class ConsolePrintUtil {

	public static void main(String[] args) {
		
	}
	
	/**
	 * Used to convert a comma separated string representing a vector to a Matrix.
	 * @param str: vector in String (comma separated) format
	 * @param breakAtSize the size of columns
	 * @Useage
	 * - Print to console and use with Octave
	 */
	public static void covertVectorToArray(String str, int breakAtSize) {
//		String str = "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,15,52,148,236,254,253,21,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,36,146,211,252,252,252,253,252,100,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,29,153,241,253,252,242,160,134,253,252,118,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,11,143,246,252,252,243,153,42,0,132,253,252,21,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,175,252,252,182,103,35,0,0,71,228,253,137,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,107,254,222,106,0,0,0,0,101,253,253,177,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,27,63,16,0,0,0,15,164,247,252,137,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,62,192,252,251,91,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,114,239,253,252,245,99,11,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,150,239,252,253,252,252,252,218,132,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,25,183,253,253,253,255,239,211,239,253,255,183,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,36,201,252,252,196,168,63,42,0,42,142,253,252,21,0,0,0,0,0,0,0,0,0,0,0,0,0,0,106,253,252,180,42,0,0,0,0,0,106,253,252,21,0,0,0,0,0,0,0,0,0,0,0,0,0,0,35,190,110,7,0,0,0,0,0,0,132,253,252,21,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,98,176,246,253,137,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,29,87,166,253,253,253,253,132,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,15,85,182,232,252,253,252,252,231,124,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,20,197,232,237,252,252,252,244,214,126,126,51,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,197,252,252,253,252,221,162,49,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,92,217,208,147,68,21,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0";
		String[] arr = str.split(",");
		String s = "";
		for (int i = 0; i < arr.length; i++) {
			if((i+1)%breakAtSize == 0) {
				s = s + arr[i] + ";";
			} else {
				s = s + arr[i] + " ";
			}
		}
		System.out.println(s);
	}
	
}
