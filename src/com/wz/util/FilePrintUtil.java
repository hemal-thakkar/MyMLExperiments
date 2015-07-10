package com.wz.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.api.java.JavaRDD;

public class FilePrintUtil {
	
	public static void main(String[] args) throws IOException {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map.put(0, 3);
		map.put(1, 8);
		map.put(2, 4);
		map.put(3, 0);
		map.put(4, 7); //9 or 7
		map.put(5, 5); //6 or 5
		map.put(6, 6); 
		map.put(7, 2); 
		map.put(8, 9); //9 or 4
		map.put(9, 1); // 1 slightly blur
		fileMapIntPerLine(map, "testCentroids.txt", "predictions.txt");
		System.out.println("done");
	}

	public static void printIntRDDSingleValue(JavaRDD<Integer> intRDD,
			String fileName) throws IOException {
		FileWriter fw = new FileWriter(fileName);
		long k = intRDD.count();
		List<Integer> list = intRDD.collect();
		list.forEach(i->{
			try {
				fw.write(""+i+System.lineSeparator());
				fw.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		fw.close();
	}

	public static void fileMapIntPerLine(Map<Integer,Integer> map, String inFile, String outFile) throws IOException {
		FileWriter fw = new FileWriter(outFile);
		BufferedReader br = new BufferedReader(new FileReader(inFile));
		String line;
		int i = 1;
		while ((line = br.readLine()) != null) {
			fw.write(i+","+map.get(Integer.parseInt(line))+System.lineSeparator());
			i++;
		}
		fw.flush();
		fw.close();
		br.close();
	}

}
