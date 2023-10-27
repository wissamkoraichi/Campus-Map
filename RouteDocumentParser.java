import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RouteDocumentParser {

    public String[][] scanLocations(String filename) {
        String[][] locations = new String[14][2];

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine(); // skip the header line
            while ((line = br.readLine()) != null) {
                String[] splitLine = line.split(",");
                int id = Integer.parseInt(splitLine[0]);
                locations[id-1] = new String[]{splitLine[0], splitLine[1]};
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return locations;
    }

    public void printLocations(String[][] locations) {
        for (int i = 1; i < locations.length; i++) {
            if (locations[i] != null) {
                System.out.println(locations[i][0] + ". " + locations[i][1]);
            }
        }
    }

    public List<int[]> scanRoutes(String filename) {
        List<int[]> routes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine(); // skip the header line
            while ((line = br.readLine()) != null) {
                String[] splitLine = line.split(",");
                int start = Integer.parseInt(splitLine[0]);
                int end = Integer.parseInt(splitLine[1]);
                int distance = Integer.parseInt(splitLine[2]);
                routes.add(new int[]{start, end, distance});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return routes;
    }
}
	