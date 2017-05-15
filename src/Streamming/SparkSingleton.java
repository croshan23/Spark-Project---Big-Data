package Streamming;

import java.util.Iterator;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;


public final class SparkSingleton {
	private static SparkSingleton instance;
	private SparkSession spark;

	private SparkSingleton(SparkContext cs) {
		SparkConf sparkConf = new SparkConf()
				.setMaster("local[2]")
				.set("hive.metastore.warehouse.dir",
						"file:/user/hive/warehouse")
				.set("hive.metastore.uris", "thrift://127.0.0.1:9083");

		spark = SparkSession.builder().config(sparkConf).enableHiveSupport()
				.getOrCreate();

		spark.sql("CREATE TABLE IF NOT EXISTS Person (Name STRING, Age INT, Phone STRING, Profession STRING, Salary INT)");
	}

	public static SparkSingleton getInstance(SparkContext cs) {
		if (instance == null) {

			instance = new SparkSingleton(cs);
		}
		return instance;
	}

	public void insertIntoPerson(JavaRDD<Person> rdd) {
		Dataset<Row> record = spark.createDataFrame(rdd, Person.class);
		record.createOrReplaceTempView("currentPerson");
		Dataset<Row> df = spark
				.sql("select Name, Age, Phone, Profession, Salary from currentPerson");
		df.show();
		df.write().format("orc").mode(SaveMode.Append)
				.insertInto("default.person");
	}
}