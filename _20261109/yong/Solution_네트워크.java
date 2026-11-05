import java.util.*;
/*
1 ~ n까지의 컴퓨터끼리 연결상태 주어지고, 네트워크가 몇개인지 구하는 문제
1번 컴퓨터부터 bfs를 돌리면서 네트워크 하나 확인
이후 네트워크에 속하지 않은 컴퓨터를 기준으로 계속 bfs를 돌려서 네트워크들을 추가적으로 확인
bfs 시작 수 == 네트워크 수
*/
class Solution {
    public static boolean[] visited;
    public static int staticN;
    public static int[][] staticComputers;

    public int solution(int n, int[][] computers) {
        int answer = 0;
        staticN = n;
        staticComputers = computers;
        visited = new boolean[n];

        for(int i = 0; i < n; i++){
            if(visited[i]) continue;
            bfs(i);
            answer++;
        }

        return answer;
    }

    public void bfs(int i){
        Queue<Integer> q = new ArrayDeque<>();
        q.offer(i);
        visited[i] = true;

        while(!q.isEmpty()){
            int now = q.poll();

            for(int next = 0; next < staticN; next++){
                if(staticComputers[now][next] == 0) continue;
                if(visited[next]) continue;
                
                q.offer(next);
                visited[next] = true;
            }
        }
    }
}