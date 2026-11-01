import java.util.*;
import java.io.*;

public class Main {

    static ArrayList<Integer>[] arr;
    static long[] Maintenance;
    static int[] parents;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st; // = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(br.readLine());

        Maintenance = new long[N + 1];
        //int[][] arr = new int[N][N];

        arr = new ArrayList[N + 1];
        parents = new int[N + 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            Maintenance[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i <= N; i++) {
            arr[i] = new ArrayList<>();
        }

        for (int i = 0; i < N - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            arr[a].add(b);
            arr[b].add(a);
        }

        dfs(1, 0);

        for (int i = 1; i <= N; i++) {
            bw.write(Integer.toString(parents[i]) + " " + Long.toString(Maintenance[i]) + "\n");
        }

        //bw.write();
        bw.flush();
        bw.close();
    }

    static long dfs(int id, int parent) {
        //if(arr[id].size() == 1) return Maintenance[id];
        parents[id] = parent;
        long total = 0;

        for (int a : arr[id]) {
            if (a == parent) continue;
            total += dfs(a, id);
        }
        Maintenance[id] += total;
        return Maintenance[id];
    }
}
