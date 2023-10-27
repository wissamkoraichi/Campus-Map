import java.util.List;
import java.util.Scanner;

public class CampusGuide {

    public static void main(String[] args) {
        RouteDocumentParser parser = new RouteDocumentParser();
        String[][] locations = parser.scanLocations("places.csv");
        List<int[]> routes = parser.scanRoutes("routes.csv");

        parser.printLocations(locations);

        Map campusMap = new Map(locations, routes);

        Scanner scanner = new Scanner(System.in);
        int start;
        int end;

        while (true) {
            System.out.println("Find a route (Enter 22 to exit)");
            System.out.print("Enter the start location: ");
            start = scanner.nextInt();

            if (start == 22) {
                break;
            }

            System.out.print("Enter the destination location: ");
            end = scanner.nextInt();

            System.out.println("Calculating route...");

            int[] route = campusMap.getRoute(start, end);
            if (route == null) {
                System.out.println("No route found or more than 5 hops.");
            } else {
                campusMap.printRoute(route);

                System.out.println("Alternate Route:");
                int[] alternateRoute = campusMap.getAlternateRoute(start, end);
                if (alternateRoute == null) {
                    System.out.println("No alternate route found.");
                } else {
                    campusMap.printRoute(alternateRoute);
                }
            }
        }

        scanner.close();
    }
}
