package week12_03.thdwngjs.BOJ_9177;

import java.io.*;
import java.util.*;

// 백준 9177 - 단어 섞기 (BFS)
class Main {
    private static char[] w1, w2, w3;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine()); // 테스트 케이스 개수
        
        StringBuilder sb = new StringBuilder();
        int T = 0;
        while(T++ < n) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            w1 = st.nextToken().toCharArray(); // 첫 번째 단어
            w2 = st.nextToken().toCharArray(); // 두 번째 단어
            w3 = st.nextToken().toCharArray(); // 섞인 단어 (목표)

            if(bfs())   sb.append("Data set " + T + ": yes\n");
            else        sb.append("Data set " + T + ": no\n");
        }
        System.out.println(sb);
    }
    
    private static boolean bfs() {
        Queue<int[]> q = new ArrayDeque<>();
        // check[i][j]: w1의 i번째 문자, w2의 j번째 문자까지 사용하여
        // w3의 (i+j)번째 문자까지 매칭에 성공했는지 여부 (중복 방문 방지)
        // 최대 길이가 200이므로 201 크기 할당
        boolean[][] check = new boolean[201][201];
        
        // {w1 인덱스, w2 인덱스, w3 인덱스}
        q.add(new int[] {0,0,0});
        check[0][0] = true;
        
        while(!q.isEmpty()) {
            int[] cur = q.poll();
            int fir = cur[0];
            int sec = cur[1];
            int thr = cur[2];
            
            // w3의 모든 문자를 다 만들었다면 성공
            if(thr == w3.length) {
                return true;
            }
            
            // 1. w1의 현재 문자를 사용하여 w3의 현재 문자를 맞추는 경우
            // - w1 인덱스가 범위 내여야 함
            // - 해당 상태(fir+1, sec)를 방문한 적이 없어야 함
            // - 문자가 일치해야 함
            if(fir < w1.length && !check[fir + 1][sec]
                && w1[fir] == w3[thr]) {

                q.add(new int[] {fir + 1, sec, thr + 1});
                check[fir + 1][sec] = true;
            }
            
            // 2. w2의 현재 문자를 사용하여 w3의 현재 문자를 맞추는 경우
            if(sec < w2.length && !check[fir][sec + 1]
                && w2[sec] == w3[thr]) {

                q.add(new int[] {fir, sec + 1, thr + 1});
                check[fir][sec + 1] = true;
            }
        }

        // 큐가 빌 때까지 w3를 완성하지 못했다면 실패
        return false;
    }
}