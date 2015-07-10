package com.wz.spark.unsupervised;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.clustering.KMeans;
import org.apache.spark.mllib.clustering.KMeansModel;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;

import com.wz.util.FilePrintUtil;

/**
 * 
 * @author hemal
 *
 */
public class KMeansExample {

	/**
	 * 
	 * @param args
	 * 0: training file
	 * 1: testing file
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		// Create a spark configuration
		SparkConf conf = new SparkConf().setAppName("K-means Example").setMaster("local[2]").set("spark.executor.memory","1g");
		JavaSparkContext sc = new JavaSparkContext(conf);

		// Load and parse data
		JavaRDD<Vector> trainData = loadData(args[0], sc, getMappingFunction(",",1));

		// Cluster the data into 10 classes using KMeans and output the cluster centroids
		KMeansModel clusters = clusterDataAndPrintCentroids(trainData, 10, 50);

		// Predict Centroids
		
		JavaRDD<Vector> testData = loadData(args[1], sc, getMappingFunction(",", 0));
		JavaRDD<Integer> testCentroids = clusters.predict(testData);
		printPredictions(testCentroids);
		
	}

	private static void printPredictions(JavaRDD<Integer> testCentroids) throws IOException {
		FilePrintUtil.printIntRDDSingleValue(testCentroids,"testCentroids.txt");
	}

	private static Function<String, Vector> getMappingFunction(String splitString, int start) {
		// TODO Auto-generated method stub
		return new Function<String, Vector>() {
			private static final long serialVersionUID = 1L;

			public Vector call(String s) {
				String[] sarray = s.split(splitString);
				int offset = start == 0 ? 0 : 1;
				double[] values = new double[sarray.length-offset];
				for (int i = start; i < sarray.length; i++)
					values[i-offset] = Double.parseDouble(sarray[i]);
				return Vectors.dense(values);
			}
		};
	}

	private static KMeansModel clusterDataAndPrintCentroids(JavaRDD<Vector> parsedData,
			int numClusters, int numIterations) throws IOException {
		KMeansModel clusters = KMeans.train(parsedData.rdd(), numClusters, numIterations);
		int[] varNum = {1};
		FileWriter fw = new FileWriter("cetroids.txt");
		Arrays.stream(clusters.clusterCenters()).forEach(vector->{
			double[] arr = vector.toArray();
			String str = "";
			for (int i=0; i<arr.length;i++) {
				if((i+1)%28==0) {
					str = str + arr[i] + ";";
				} else {
					str = str + arr[i] + " ";
				}
			}
			try {
				fw.write("x"+varNum[0] + " = [" + str+"];\n");
				fw.flush();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			varNum[0]++;

		});
		try {
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clusters;
	}

	private static JavaRDD<Vector> loadData(String path, JavaSparkContext sc, Function<String, Vector> f) {
		JavaRDD<String> data = sc.textFile(path);
		JavaRDD<Vector> parsedData = data.map(f);
		parsedData.cache();
		return parsedData;
	}
}
