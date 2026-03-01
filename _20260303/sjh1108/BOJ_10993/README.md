import java.io.*;
import java.util.*;

class Main {
    private static int num;
    private static int[] arr;
    private static boolean[] visited;
    private static List<Integer> list;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        arr = new int[N + 1];
        visited = new boolean[N + 1];
        for (int i = 1; i <= N; i++) {
            arr[i] = Integer.parseInt(br.readLine());
        }
        list = new LinkedList<>();

        for (int i = 1; i <= N; i++) {
            visited[i] = true;
            num = i;
            dfs(i);
            visited[i] = false;
        }

        Collections.sort(list);
        System.out.println(list.size());
        for (int i : list) {
            System.out.println(i);
        }
    }

    public static void dfs(int i) {
        if (arr[i] == num) list.add(num);
        
        if (!visited[arr[i]]) {
            visited[arr[i]] = true;
            dfs(arr[i]);
            visited[arr[i]] = false;
        }
    }
}