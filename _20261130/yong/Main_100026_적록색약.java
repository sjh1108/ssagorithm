import java.util.*;
import java.io.*;
public class Main_100026_적록색약 {
    static int N;
    static char[][] graph;
    static int dt[][] = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}}; // 위, 오른쪽, 아래, 왼쪽
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        
        // 색상 보드 입력
        graph = new char[N][N];
        for(int i = 0; i < N; i++){
            String line = br.readLine();
            for(int j = 0; j < N; j++){
                graph[i][j] = line.charAt(j);
            }
        }

        // 일반 구역의 수
        int normalRegions = countRegions();

        // 색약 맵으로 교체
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                if(graph[i][j] == 'R'){
                    graph[i][j] = 'G';
                }
            }
        }

        // 색약 구역의 수
        int colorBlindRegions = countRegions();

        System.out.println(normalRegions + " " + colorBlindRegions);
    }

    // 보드 순회하면서 bfs로 구역 세기
    public static int countRegions(){
        int regions = 0;
        boolean[][] visited = new boolean[N][N];
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                if(visited[i][j]){
                    continue;
                }
                else{
                    bfs(i, j, visited);
                    regions++;
                }
            }
        }
        return regions;
    }

    // 타겟 색상 bfs 방문
    public static void bfs(int row, int col, boolean[][] visited){
        // 첫 칸을 타겟 색상으로 지정
        char targetColor = graph[row][col];

        Queue<int[]> q = new ArrayDeque<>();
        q.offer(new int[] {row, col});
        visited[row][col] = true;

        while(!q.isEmpty()){
            int[] cur = q.poll();

            for(int d = 0; d < 4; d++){
                int nx = cur[0] + dt[d][0];
                int ny = cur[1] + dt[d][1];

                // 벽, 방문여부, 타겟색상 체크
                if(nx >= N || ny >= N || nx < 0 || ny < 0) continue;
                if(visited[nx][ny] || graph[nx][ny] != targetColor) continue;

                visited[nx][ny] = true;
                q.offer(new int[] {nx, ny});
            }
        }
    }
}