package SQL;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SQLContext;

public class JavaIPLDataset {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		SparkConf sparkConf = new SparkConf().setAppName("IPLFilter")
				.setMaster("local[*]");

		JavaSparkContext sc = new JavaSparkContext(sparkConf);

		SQLContext sqlContext = new SQLContext(sc);

		List<JavaMatches> data = JavaData.sampleData();

		Dataset<JavaMatches> dataset = sqlContext.createDataset(data,
				Encoders.bean(JavaMatches.class));
		
		Dataset<JavaMatches> IPLdataset;

		System.out
				.println("-------------------- IPL MATCH DATASET -------------------------\n");
		System.out.println("1. Search match info based on year\n"
				+ "2. Search match info based on winning team\n"
				+ "3. Search match info based on team name\n\n");
		System.out.print("Please Enter Your Search Option:\t");
		int option = Integer.parseInt(br.readLine().trim());

		String filename = "";
		switch(option){
			case 1:
				System.out.print("Please Enter Year(2008-2016):\t");
				int year = Integer.parseInt(br.readLine().trim());
				filename = "outputYear";
				IPLdataset = dataset
						.filter((FilterFunction<JavaMatches>) matches -> (matches
								.getSeason() == year));
				break;
			case 2:
				System.out.print("Please Enter Name of Winning Team:\t");
				String wteam = br.readLine().trim();
				filename = "outputWinTeam";
				IPLdataset = dataset
						.filter((FilterFunction<JavaMatches>) matches -> (matches
								.getWinner().equals(wteam)));
				break;
			case 3:
				System.out.print("Please Enter Name of the Team:\t");
				String team = br.readLine().trim();
				filename = "outputTeam";
				IPLdataset = dataset
						.filter((FilterFunction<JavaMatches>) matches -> (matches
								.getTeam1().equals(team) || matches.getTeam2().equals(team)));
				break;
			default:
				IPLdataset = dataset;
				filename = "outputAll";
		}
		
		Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(filename+".txt"), "utf-8"));
		
		writer.write("Season\t\tPlace\t\tTeam1\t\t\t\tTeam2\t\t\t\tWinner\t\t\t"
				+ "Win_by_runs\t\tWin_by_wickets\t\tMan_of_the_match\n\n");


		List<JavaMatches> data1 = new ArrayList<JavaMatches>();
		data1 = IPLdataset.collectAsList();

		for (JavaMatches p : data1) {
			writer.write(p.toString() + "\n");
		}
		System.out.println(">>DONE");
		writer.close();

	}
}