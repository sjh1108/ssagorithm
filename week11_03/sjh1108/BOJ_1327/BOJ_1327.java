package week11_03.sjh1108.BOJ_1327;

import java.util.*;
import java.io.*;

// 백준 1327 - 소트 게임 (BFS)
class Main {
    private static int N, K; // N: 숫자의 개수, K: 뒤집을 구간의 길이

    private static String num, ans; // num: 초기 순열(문자열), ans: 목표 순열(문자열)
    // BFS 방문 여부를 체크하기 위한 Set (이미 방문한 순열 문자열을 저장)
    private static Set<String> visited = new HashSet<>();
    
    // BFS 큐에 저장할 상태(노드)
    static class Node{
        String n; // 현재 순열을 나타내는 문자열
        int cnt; // 이 순열에 도달하기까지의 연산(뒤집기) 횟수
        public Node(String n, int cnt) {
            this.n = n;
            this.cnt = cnt;
        }
    }
    
    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); // 숫자의 개수 (1~N)
        K = Integer.parseInt(st.nextToken()); // 한 번에 뒤집을 원소의 개수

        st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder(); // 목표 문자열(ans)을 만들기 위한 빌더
        num = ""; // 입력받은 초기 순열을 저장할 문자열
        for(int i = 0; i < N; i++){
            num += st.nextToken(); // 띄어쓰기 없이 숫자를 이어붙여 문자열로 만듦 (예: "1 3 2" -> "132")
            sb.append(i+1); // 목표 문자열(ans)은 1부터 N까지 오름차순 (예: "123")
        }
        ans = sb.toString(); // 목표 문자열 완성

        // BFS를 수행하고 최소 연산 횟수를 출력
        System.out.println(bfs());
    }

    /**
     * BFS를 사용하여 'num' 상태에서 'ans' 상태로 가는 최소 연산 횟수를 찾습니다.
     * @return 최소 연산 횟수 (도달 불가능하면 -1)
     */
    private static int bfs(){
        Queue<Node> q = new ArrayDeque<>(); // BFS용 큐

        // 시작 상태(초기 순열, 연산 횟수 0)를 큐에 추가
        q.add(new Node(num, 0));
        // 시작 상태 방문 처리 (이 코드가 빠져있지만, 있어도 됨. 
        //                    없어도 첫 노드는 바로 poll 되므로 큰 영향 X.
        //                    다만, 시작과 목표가 같다면 0을 바로 리턴해야 하므로
        //                    큐에 넣기 전에 체크하거나, 큐에서 빼자마자 체크하는 지금 로직이 타당)
        // visited.add(num); 

        while(!q.isEmpty()){ // 큐가 빌 때까지 반복
            Node cur = q.poll(); // 큐에서 현재 상태(노드)를 꺼냄

            // [목표 도달 확인]
            // 현재 순열(cur.n)이 목표 순열(ans)과 같다면
            if(cur.n.equals(ans)){
                return cur.cnt; // 현재까지의 연산 횟수(최소 횟수) 반환
            }

            // [연산 수행]
            // K개의 원소를 뒤집는 모든 경우의 수를 탐색
            // i: 뒤집기 시작할 인덱스 (0부터 N-K 까지 가능)
            for(int i = 0; i <= N-K; i++){
                // 현재 순열을 문자 배열로 변환 (문자열은 불변이므로)
                char[] arr = cur.n.toCharArray();

                // [K개 뒤집기]
                // (i)번 인덱스부터 K개의 문자를 뒤집는다 (j는 스왑할 쌍의 오프셋)
                for(int j = 0; j < K/2; j++){
                    // (i+j) 와 (i + K - 1 - j) 위치의 문자를 스왑
                    char tmp = arr[i+j];
                    arr[i+j] = arr[i + K - 1 - j];
                    arr[i + K - 1 - j] = tmp;
                }

                // 뒤집기 연산이 완료된 새로운 순열(문자열)
                String nxt = new String(arr);

                // 이 새로운 순열(nxt)을 방문한 적이 없다면
                if(!visited.contains(nxt)){
                    // 큐에 추가 (연산 횟수 + 1)
                    q.add(new Node(nxt, cur.cnt+1));
                    // 방문 처리
                    visited.add(nxt);
                }
            }
        }
        
        // 큐가 비었는데 목표 순열에 도달하지 못했다면 (불가능한 경우)
        return -1;
    }
}