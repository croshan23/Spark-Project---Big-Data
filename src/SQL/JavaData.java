package SQL;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JavaData {

    public static List<JavaMatches> sampleData() throws IOException {
        return sampleDataAsStrings().stream().map(line -> {
            String[] parts = line.split(",");
            return new JavaMatches(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3], parts[4],parts[5], parts[6], parts[7]);
        }).collect(Collectors.toList());
    }

    public static List<String> sampleDataAsStrings() throws IOException {
        List<String> ret = new ArrayList<String>();
        BufferedReader r = new BufferedReader(new FileReader(new File("src/SQL/sample_data.csv")));
        String line;
        while ((line=r.readLine()) != null) {
            ret.add(line);
        }
        r.close();
        return ret;
    }
}
