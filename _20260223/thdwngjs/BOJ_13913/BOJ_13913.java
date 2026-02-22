package _20260223.thdwngjs.BOJ_13913;

import java.io.*;
import java.util.*;

// 백준 13913 - 숨바꼭질 4 (BFS + 경로 추적)
class Main {
    private static int N, K;

    // arr[i] : i 위치에 도달하기 바로 직전의 위치(부모 노드)를 저장하는 배열
    // - 방문 여부 체크(값이 -1이 아니면 방문함)와 경로 역추적 역할을 동시에 수행
    private static int[] arr;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken()); // 수빈이의 위치
        K = Integer.parseInt(st.nextToken()); // 동생의 위치

        // 시작 위치와 도착 위치가 같은 경우 예외 처리
        if(N == K){
            System.out.println(0);
            System.out.println(N);
            return;
        }

        // 최대 범위 설정 (K의 최대값이 100,000이므로, x2 이동을 고려하여 여유있게 설정)
        arr = new int[200_001];
        Arrays.fill(arr, -1); // -1로 초기화하여 미방문 상태 표시
        arr[N] = N; // 시작점 방문 처리 (자신의 부모를 자신으로 설정)

        Queue<Integer> q = new ArrayDeque<>();
        q.add(N);

        // BFS 탐색
        while(!q.isEmpty()){
            int cur = q.poll();
            
            // 동생을 찾으면 탐색 종료
            if(cur == K) break;

            Integer ans;

            // 1. 앞으로 걷기 (X + 1)
            ans = next(cur+1, cur);
            if(ans != null) q.add(ans);

            // 2. 뒤로 걷기 (X - 1)
            ans = next(cur-1, cur);
            if(ans != null) q.add(ans);
            
            // 3. 순간이동 (X * 2)
            ans = next(cur*2, cur);
            if(ans != null) q.add(ans);
        }

        // 경로 역추적 (도착점 K부터 시작점 N까지 거슬러 올라감)
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        int cur = K;

        while(cur != N){
            stack.push(cur); // 스택에 담아 순서를 뒤집음 (LIFO)
            cur = arr[cur];  // 직전 위치로 이동
        }
        
        StringBuilder sb = new StringBuilder();
        // 스택의 크기가 곧 최단 시간 (이동 횟수)
        sb.append(stack.size() + "\n");
        
        // 시작점 먼저 출력
        sb.append(N + " ");
        // 스택에 담긴 경로(N의 다음 위치부터 K까지)를 순차적으로 출력
        while(!stack.isEmpty()){
            sb.append(stack.pop() + " ");
        }

        System.out.println(sb);
    }

    // 다음 위치가 유효한지 검사하고 방문 처리 및 큐에 삽입할 값을 반환
    private static Integer next(int next, int cur){
        // 범위를 벗어난 경우 무시
        if(!checkMap(next)) return null;

        // 이미 방문한 곳(최단 시간이 아닌 경우)이면 무시
        if(arr[next] != -1) return null;
        
        // 유효한 위치라면, arr 배열에 '이전 위치(cur)'를 기록
        arr[next] = cur;

        return next;
    }

    // 위치가 0 ~ 200,000 범위 내에 있는지 확인
    private static boolean checkMap(int p){
        return p >= 0 && p < 200_001;
    }
}