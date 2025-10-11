package week10_02.sjh1108.BOJ_16928;

import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        // 입력
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        N += Integer.parseInt(st.nextToken());

        // 사다리와 뱀 모두 해당 위치로 가면 필수로 타야하기 때문에 동일한 HashMap을 통해 관리
        HashMap<Integer, Integer> ladderSnake = new HashMap<>();
        while(N-- > 0){
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            ladderSnake.put(a, b);
        }

        // 방문 처리 배열
        boolean[] visited = new boolean[101];
        int[] arr = new int[101];

        Queue<Integer> q = new ArrayDeque<>();
        q.add(1);
        arr[1] = 0;
        visited[1] = true;

        // 시간 순서대로 q에 넣기 떄문에 정렬의 필요 없음
        while(!q.isEmpty()){
            int cur = q.poll();

            // 주사위를 돌려 각 주사위에 해당하는 위치로 이동
            for(int i = 1; i <= 6; i++){
                int nxt = cur + i;

                // 위치가 100이 넘으면 갈 수 없음
                if(nxt > 100) break;
                // 방문한 위치는 재방문할 필요 없음
                if(visited[nxt]) continue;
                
                visited[nxt] = true;

                // 만약 해당 위치에 뱀, 사다리가 존재한다면
                // 뱀, 사다리 사용
                if(ladderSnake.containsKey(nxt)){
                    // 뱀 사다리 사용한 이후 위치
                    int ls = ladderSnake.get(nxt);
                    if(!visited[ls]){
                        visited[ls] = true;
                        q.add(ls);
                        arr[ls] = arr[cur]+1;
                    }
                } else{
                    // 만약 다음 위치가 100이면 출력 후 루프 종료
                    if(nxt == 100){
                        System.out.println(arr[cur] + 1);
                        return;
                    }

                    // 다음 위치에 이동처리
                    q.add(nxt);
                    arr[nxt] = arr[cur] + 1;
                }
            }
        }
    }
}