import java.io.*;
import java.util.*;
class Main {

    private static int N, M;

    private static List<List<int[]>> road;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        road = new ArrayList<>();
        for(int i = 0; i <= N; i++) road.add(new ArrayList<>());

        int t = Integer.parseInt(st.nextToken());

        for(int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            road.get(a).add(new int[]{b, c});
            road.get(b).add(new int[]{a, c});
        }
        
        Queue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));
        pq.add(new int[] {1, 0});

        int sum = 0;
        
        boolean[] visited = new boolean[N+1];
        while(!pq.isEmpty()){
            int[] cur = pq.poll();
            int to = cur[0];
            int cost = cur[1];

            if(visited[to]) continue;
            visited[to] = true;

            sum += cost;

            for(int[] con : road.get(to)){
                int nxt = con[0];

                if(visited[nxt]) continue;

                pq.add(con);
            }
        }

        sum += ((N-1) * (N-2) / 2) * t;
        
        System.out.println(sum);
    }
}