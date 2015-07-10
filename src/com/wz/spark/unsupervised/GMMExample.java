package com.wz.spark.unsupervised;

import java.util.ArrayList;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.clustering.GaussianMixture;
import org.apache.spark.mllib.clustering.GaussianMixtureModel;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.rdd.RDD;


/** Exploring the GMM Clustering model. Does not work **/
public class GMMExample {
	/**
	 * 
	 * @param args
	 * 0: training file
	 * 1: testing file
	 * 2: train to Train and save the model, test to predict against model
	 * @throws Exception
	 */
	public static void main(String[] args) {
		if(args[2].equalsIgnoreCase("train")) {
			train(args);
		} else if (args[2].equalsIgnoreCase("test")) {
			test(args);
		} else {
			System.out.println("Invalid arguments");
		}
	}


	private static void test(String[] args) {
		// Create a spark configuration
		SparkConf conf = new SparkConf().setAppName("GMM Example").setMaster("local[2]").set("spark.executor.memory","1g");
		JavaSparkContext sc = new JavaSparkContext(conf);
		
		GaussianMixtureModel gmm = GaussianMixtureModel.load(sc.sc(), "gmmModel.sparkmodel");
		RDD<Object> predictions = gmm.predict(loadData(args[1], sc, getMappingFunction(",", 0)).rdd());
		List<Integer> list = new ArrayList<Integer>();
		predictions.toJavaRDD().collect().forEach(obj->{
			list.add(((Integer)obj));
		});
		list.forEach(System.out::println);
//		RDD<double[]> predictions = gmm.predictSoft(loadData(args[1], sc, getMappingFunction(",", 0)).rdd());
	}


	public static void train(String[] args) {
		// Create a spark configuration
		SparkConf conf = new SparkConf().setAppName("GMM Example").setMaster("local[2]").set("spark.executor.memory","1g");
		JavaSparkContext sc = new JavaSparkContext(conf);
		// Load and parse data
		JavaRDD<Vector> trainData = loadData(args[0], sc, getMappingFunction(",",1));

		GaussianMixtureModel gmm = new GaussianMixture().setK(10).run(trainData.rdd());

		gmm.save(sc.sc(), "gmmModel.sparkmodel");
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

	private static JavaRDD<Vector> loadData(String path, JavaSparkContext sc, Function<String, Vector> f) {
		JavaRDD<String> data = sc.textFile(path);
		JavaRDD<Vector> parsedData = data.map(f);
		parsedData.cache();
		return parsedData;
	}

}
