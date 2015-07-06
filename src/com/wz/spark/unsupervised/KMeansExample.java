package com.wz.spark.unsupervised;

import java.io.FileNotFoundException;
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

public class KMeansExample {
    
    public static void main(String[] args) throws Exception {
        
        // Create a spark configuration
        SparkConf conf = new SparkConf().setAppName("K-means Example").setMaster("local[2]").set("spark.executor.memory","1g");
        JavaSparkContext sc = new JavaSparkContext(conf);

        // Load and parse data
        String path = "/Users/hemal/Documents/Kaggle/DigitRecognizer/train.csv";
        JavaRDD<String> data = sc.textFile(path);
        JavaRDD<Vector> parsedData = data.map(
                new Function<String, Vector>() {
                    public Vector call(String s) {
                        String[] sarray = s.split(",");
                        double[] values = new double[sarray.length-1];
                        for (int i = 1; i < sarray.length; i++)
                            values[i-1] = Double.parseDouble(sarray[i]);
                        return Vectors.dense(values);
                    }
                }
                );
        parsedData.cache();

        // Cluster the data into 10 classes using KMeans and output the cluster centroids
        int numClusters = 10;
        int numIterations = 50;
        KMeansModel clusters = KMeans.train(parsedData.rdd(), numClusters, numIterations);
        FileWriter fw = new FileWriter("cetroids.txt");
        int[] varNum = {1};
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
                varNum[0]++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        fw.close();
    }
}
