package com.vortexlab.bigdata.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class OrderAnalysisJob {

    public static void main(String[] args) {
        SparkConf conf =
                new SparkConf()
                        .setAppName("order-analysis")
                        .setMaster("local");

        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> data = sc.textFile("hdfs://localhost/order");

        long count = data.count();

        System.out.println("订单数量:" + count);
        sc.close();
    }
}
