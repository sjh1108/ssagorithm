import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int S = Integer.parseInt(br.readLine());

        List<List<long[]>> list = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            list.add(new ArrayList<>());
        }
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            long w = Long.parseLong(st.nextToken());
            list.get(u).add(new long[]{v, w});
            list.get(v).add(new long[]{u, w});
        }

        long[] dist = new long[N + 1];
        Arrays.fill(dist, -1);

        Queue<long[]> pq = new PriorityQueue<>((o1, o2) -> Long.compare(o1[1], o2[1]));
        pq.add(new long[]{S, 0});

        while (!pq.isEmpty()) {
            long[] cur = pq.poll();
            int v = (int) cur[0];
            long w = cur[1];

            if (dist[v] != -1) continue;
            dist[v] = w;

            for (long[] next : list.get(v)) {
                int nv = (int) next[0];
                long nw = next[1] + w;
                pq.add(new long[]{nv, nw});
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= N; i++) {
            sb.append(dist[i]).append('\n');
        }
        System.out.print(sb);
    }
}