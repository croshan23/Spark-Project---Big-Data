package Streamming;

import java.util.regex.Pattern;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.StorageLevels;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

/**
 * Use DataFrames and SQL to count words in UTF8 encoded, '\n' delimited text
 * received from the network every second.
 *
 * Usage: CreditCardTransactionStreaming <hostname> <port> <hostname> and <port>
 * describe the TCP server that Spark Streaming transaction connect to receive
 * data.
 *
 * To run this on your local machine, you need to first run a Netcat server `$
 * nc -lk 9999` and then run the example `$ bin/run-example
 * Streamming.CreditCardTransactionStreaming localhost 9999`
 */
public final class Twitter {
	private static final Pattern SPACE = Pattern.compile(",");

	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			System.err
					.println("Usage: CreditCardTransaction <hostname> <port>");
			System.exit(1);
		}

		// Create the context with a 1 second batch size
		SparkConf sparkConf = new SparkConf().setAppName("twitter")
				.setMaster("local[2]").set("spark.executor.memory", "1g");
		@SuppressWarnings("resource")
		JavaStreamingContext ssc = new JavaStreamingContext(sparkConf,
				Durations.seconds(20));

		// Create a JavaReceiverInputDStream on target ip:port and count the
		// words in input stream of \n delimited text (eg. generated by 'nc')
		// Note that no duplication in storage level only for running locally.
		// Replication necessary in distributed scenario for fault tolerance.
		JavaReceiverInputDStream<String> lines = ssc.socketTextStream(args[0],
				Integer.parseInt(args[1]), StorageLevels.MEMORY_AND_DISK);

		JavaDStream<Person> persons = lines
				.map(new Function<String, Person>() {
					private static final long serialVersionUID = 1L;

					@Override
					public Person call(String x) throws Exception {
						String[] splits = SPACE.split(x);
						if (splits.length < 5)
							return null;

						return new Person(splits[0], Integer
								.parseInt(splits[1]), splits[2], splits[3],
								Long.parseLong(splits[4]));
					}

				});
		// Convert RDDs of the words DStream to DataFrame and run SQL query
		persons.foreachRDD((rdd, time) -> {
			if (rdd.partitions().isEmpty())
				return;
			SparkSingleton instance = SparkSingleton.getInstance(rdd.context());
			instance.insertIntoPerson(rdd);
		});

		ssc.start();
		ssc.awaitTermination();
	}
}