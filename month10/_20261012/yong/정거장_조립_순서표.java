import java.util.*;
import java.io.*;
public class 정거장_조립_순서표 {
    static int N, M;
    static int[] inDegree; // 진입 차수
    static ArrayList<Integer>[] graph; // 인접 리스트 a -> b 표현
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        graph = new ArrayList[N + 1];
        for(int i = 1; i <= N; i++){
            graph[i] = new ArrayList<>();
        }
        inDegree = new int[N + 1];

        // a를 b보다 먼저 끝내야한다. 진입차수 인접리스트: a -> b, b 진입차수 증가
        for(int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            graph[a].add(b);
            inDegree[b]++;
        }

        PriorityQueue<Integer> pq = new PriorityQueue<>(); 

        for(int i = 1; i <= N; i++){
            if(inDegree[i] != 0) continue;
            pq.offer(i);
        }

        StringBuilder sb = new StringBuilder();
        int count = 0; // 사이클 체크용
        while(!pq.isEmpty()) {
            int now = pq.poll();
            count++;
            sb.append(now).append(" ");

            for(int next : graph[now]){
                inDegree[next]--;
                if(inDegree[next] != 0) continue;
                pq.offer(next);
            }
        }

        if(count != N){
            System.out.println(-1);
        }
        else{
            System.out.println(sb);
        }

    }
}
