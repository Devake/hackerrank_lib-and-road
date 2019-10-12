import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;
import java.util.Collections;

public class Solution {
    static private long dfs(int n, int current, long currentGroupSize, boolean[] visited, ArrayList<ArrayList<Integer>> adjList){
        System.out.println("dfs - current: " + current + ", currentGroupSize: " + currentGroupSize);
        if(visited[current] == true || n == currentGroupSize){
            return currentGroupSize;
        }
        visited[current] = true;
        ArrayList<Integer> currentCityAdjList = adjList.get(current);
        if(currentCityAdjList.size() == 0){
            System.out.println(current+": no adj cities");
            return 1;
        } else if (currentCityAdjList.size() == n-1) {
            System.out.println("All cities connected with each other");
            return n;
        }
        long totalCitiesInGroup = currentGroupSize + 1;
        for(Integer adjCity : currentCityAdjList){
            System.out.println("adj city: " + adjCity);
            totalCitiesInGroup = dfs(n, adjCity-1, totalCitiesInGroup, visited, adjList);
            if(totalCitiesInGroup == n){
                return n;
            }
            System.out.println("totalCitiesInGroup: " + totalCitiesInGroup);
        }
        return totalCitiesInGroup;
    }

    static private ArrayList<ArrayList<Integer>> buildAdjList(int n, int[][] cities){
        ArrayList<ArrayList<Integer>> ret = new ArrayList<ArrayList<Integer>>();
        for(int i = 0; i < n; ++i){
            ret.add(new ArrayList<Integer>());
        }
        for(int[] edge : cities){
            ret.get(edge[0]-1).add(edge[1]);
            ret.get(edge[1]-1).add(edge[0]);
        }
        return ret;
    }
    // Complete the roadsAndLibraries function below.
    static long roadsAndLibraries(int n, int c_lib, int c_road, int[][] cities) {
        System.out.println(n + " " + c_lib + " " + c_road);

        if(c_lib < c_road){
            return (long)n * (long)c_lib;
        }

        boolean[] visited = new boolean[n];
        ArrayList<ArrayList<Integer>> adjList = buildAdjList(n, cities);
        System.out.println(adjList);
        long buildCost = 0;
        for(int i = 0; i < n; ++i){
            long citiesInGroup = dfs(n, i, 0, visited, adjList);
            System.out.println("i:" + i + " citiesInGroup: " + citiesInGroup);
            if (citiesInGroup == n){
                return ((citiesInGroup-1) * (long)c_road) + (long)c_lib;
            } else if(citiesInGroup > 0){
                buildCost += ((citiesInGroup-1) * (long)c_road) + (long)c_lib;
            }
            System.out.println("buildCost:" +buildCost);
        }
        System.out.println("total buildCost:" +buildCost);
        return buildCost;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\anguskwong\\code\\java\\LibAndRoad\\libandroad_testcase_4.txt"));
        String str = "";
        while((str=br.readLine())!=null && str.length()!=0) {
            int q = Integer.parseInt(str);
            for (int qItr = 0; qItr < q; qItr++) {
                String[] nmC_libC_road = br.readLine().split(" ");
                int n = Integer.parseInt(nmC_libC_road[0]);
                int m = Integer.parseInt(nmC_libC_road[1]);
                int c_lib = Integer.parseInt(nmC_libC_road[2]);
                int c_road = Integer.parseInt(nmC_libC_road[3]);
                int[][] cities = new int[m][2];

                for (int i = 0; i < m; i++) {
                    String[] citiesRowItems = br.readLine().split(" ");
                    for (int j = 0; j < 2; j++) {
                        int citiesItem = Integer.parseInt(citiesRowItems[j]);
                        cities[i][j] = citiesItem;
                    }
                }
                long result = roadsAndLibraries(n, c_lib, c_road, cities);
                System.out.println(result);
            }
        }
    }
}
