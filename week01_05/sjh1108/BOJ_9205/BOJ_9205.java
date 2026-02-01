package week01_05.sjh1108.BOJ_9205;

import java.io.*;
import java.util.*;

// 백준 9205 - 맥주 마시면서 걸어가기 (BFS)
class Main {
    static class Point{
        int x, y;

        Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine()); // 테스트 케이스 개수
        List<Point> list; // 좌표 저장 리스트 (0: 집, 1~N: 편의점, N+1: 락 페스티벌)
        List<List<Integer>> graph; // 인접 리스트

        StringBuilder sb = new StringBuilder();

        while(T-- > 0){
            int N = Integer.parseInt(br.readLine()); // 편의점 개수

            list = new ArrayList<>();
            graph = new ArrayList<>();
            
            // 좌표 입력
            // 총 N+2개의 좌표 (집 1 + 편의점 N + 페스티벌 1)
            for(int i = 0; i < N+2; i++){
                StringTokenizer st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());

                list.add(new Point(x, y));
            }

            // 그래프 초기화
            for(int i = 0; i < N+2; i++){
                graph.add(new ArrayList<>());
            }

            // [그래프 생성]
            // 두 지점 사이의 거리가 1000미터 이하라면 이동 가능 (맥주 20병 * 50미터 = 1000미터)
            // 모든 노드 쌍에 대해 거리를 계산하여 연결 여부 결정
            for(int i = 0; i < N+1; i++){
                for(int j = i+1; j < N+2; j++){
                    if(mahattan(list.get(i), list.get(j)) <= 1000){
                        graph.get(i).add(j);
                        graph.get(j).add(i); // 양방향 연결
                    }
                }
            }

            // BFS 탐색 결과에 따라 happy 또는 sad 출력
            // 0번 노드(집)에서 N+1번 노드(페스티벌)까지 갈 수 있는지 확인
            sb.append(bfs(graph, N) ? "happy" : "sad");
            sb.append('\n');
        }

        System.out.println(sb);
    }

    // 맨해튼 거리 계산 (|x1 - x2| + |y1 - y2|)
    private static int mahattan(Point p1, Point p2){
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
    }

    // BFS 탐색 (도달 가능성 확인)
    private static boolean bfs(List<List<Integer>> graph, int N) {
        Queue<Integer> q = new ArrayDeque<>();
        q.offer(0); // 시작점(집) 큐에 삽입
 
        boolean[] visited = new boolean[N + 2];
        visited[0] = true;
 
        while (!q.isEmpty()) {
            int now = q.poll();
 
            // 현재 위치가 도착점(락 페스티벌)이라면 성공
            if (now == N + 1) {
                return true;
            }
 
            // 현재 위치에서 이동 가능한 다음 위치들 탐색
            for (int next : graph.get(now)) {
                if (!visited[next]) {
                    visited[next] = true;
                    q.offer(next);
                }
            }
        }
 
        // 큐가 빌 때까지 도착점에 도달하지 못했다면 실패
        return false;
    }
}