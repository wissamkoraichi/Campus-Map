import java.util.Arrays;
import java.util.List;

public class Map {
    private String[][] locations;
    private int[][] adjacencyMatrix;

    public Map(String[][] locations, List<int[]> routes) {
        this.locations = locations;
        this.adjacencyMatrix = buildGraph(locations.length, routes);
    }

    private int[][] buildGraph(int numberOfLocations, List<int[]> routes) {
        int[][] adjacencyMatrix = new int[numberOfLocations][numberOfLocations];

        for (int[] route : routes) {
            if (route.length < 3) {
                // Invalid route, skip it
                continue;
            }

            int start = route[0] - 1;
            int end = route[1] - 1;
            int distance = route[2];

            if (start < 0 || start >= numberOfLocations || end < 0 || end >= numberOfLocations) {
                // Invalid start/end index, skip it
                continue;
            }

            adjacencyMatrix[start][end] = distance;
        }

        return adjacencyMatrix;
    }



    public int[] getRoute(int start, int end) {
        int n = locations.length;
        int[] dist = new int[n];
        int[] prev = new int[n];
        boolean[] visited = new boolean[n];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);
        dist[start - 1] = 0;

        for (int i = 0; i < n - 1; i++) {
            int u = getMinDistVertex(dist, visited);
            if (u == -1) {
                break;
            }

            visited[u] = true;
            for (int v = 0; v < n; v++) {
                if (!visited[v] && adjacencyMatrix[u][v] != 0 && dist[u] != Integer.MAX_VALUE
                        && dist[u] + adjacencyMatrix[u][v] < dist[v]) {
                    dist[v] = dist[u] + adjacencyMatrix[u][v];
                    prev[v] = u;
                }
            }
        }

        int[] path = getPath(prev, start - 1, end - 1);
        if (path != null && path.length <= 5) {
            return path;
        } else {
            return null;
        }
    }

    public int[] getAlternateRoute(int start, int end) {
        boolean[] visited = new boolean[locations.length];
        int[] path = new int[5];
        int[] firstRoute = getRoute(start, end);

        if (firstRoute != null && firstRoute[0] != 0) {
            visited[firstRoute[0] - 1] = true;
        }

        return dfs(start - 1, end - 1, visited, path, 0);
    }
    
    public int[] getShortestRoute(int start, int end) {
        int n = locations.length;
        int[] dist = new int[n];
        int[] prev = new int[n];
        boolean[] visited = new boolean[n];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);
        dist[start - 1] = 0;

        for (int i = 0; i < n - 1; i++) {
            int u = getMinDistVertex(dist, visited);
            if (u == -1) {
                break;
            }

            visited[u] = true;
            for (int v = 0; v < n; v++) {
                if (!visited[v] && adjacencyMatrix[u][v] != 0 && dist[u] != Integer.MAX_VALUE
                        && dist[u] + adjacencyMatrix[u][v] < dist[v]) {
                    dist[v] = dist[u] + adjacencyMatrix[u][v];
                    prev[v] = u;
                }
            }
        }

        int[] path = getPath(prev, start - 1, end - 1);
        if (path != null && path.length <= 5) {
            return path;
        } else {
            return null;
        }
    }

    private int getMinDistVertex(int[] dist, boolean[] visited) {
        int minDist = Integer.MAX_VALUE;
        int minIndex = -1;
        for (int i = 0; i < dist.length; i++) {
            if (!visited[i] && dist[i] < minDist) {
                minDist = dist[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    private int[] getPath(int[] prev, int start, int end) {
        int[] path = new int[locations.length];
        int pathIndex = locations.length - 1;

        for (int i = end; i != -1; i = prev[i]) {
            path[pathIndex] = i + 1;
            pathIndex--;
        }

        int[] trimmedPath = new int[locations.length - 1 - pathIndex];
        System.arraycopy(path, pathIndex + 1, trimmedPath, 0, locations.length - 1 - pathIndex);
        return trimmedPath;
    }

    private int[] dfs(int current, int end, boolean[] visited, int[] path, int pathIndex) {
        visited[current] = true;
        path[pathIndex] = current + 1;

        if (current == end) {
            return path;
        }

        if (pathIndex == path.length - 1) {
            return null;
        }

        for (int neighbor = 0; neighbor < adjacencyMatrix[current].length; neighbor++) {
            if (adjacencyMatrix[current][neighbor] > 0 && !visited[neighbor]) {
                int[] newPath = path.clone();
                int[] result = dfs(neighbor, end, visited.clone(), newPath, pathIndex + 1);
                if (result != null) {
                    return result;
                }
            }
        }

        return null;
    }

    public void printRoute(int[] hops) {
        if (hops == null || hops.length == 0 || hops[0] == 0) {
            System.out.println("No route found");
            return;
        }

        int totalDistance = 0;

        for (int i = 0; i < hops.length - 1; i++) {
            if (hops[i] == 0 || hops[i + 1] == 0) {
                break; // Break the loop when there's a gap in the hops array
            }

            int from = hops[i] - 1;
            int to = hops[i + 1] - 1;
            int distance = adjacencyMatrix[from][to];

            if (distance == 0) {
                break;
            }

            System.out.printf("Go from %s to %s, Distance: %d%n", locations[from][1], locations[to][1], distance);
            totalDistance += distance;
        }

        System.out.printf("Total Distance: %d%n", totalDistance);
    }
}


