package week10_04.sjh1108.BOJ_1863;

import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 스카이라인의 고도가 바뀌는 지점의 수 N
        int N = Integer.parseInt(br.readLine());

        int cnt = 0; // 필요한 최소 건물 개수
        // 고도를 관리하기 위한 스택 (ArrayDeque 사용)
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        
        // 스택의 바닥(지면)으로 높이 0을 먼저 넣어줌
        stack.push(0); 
        StringTokenizer st;
        
        // N개의 고도 변화 지점을 순회
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            // x좌표는 이 문제에서 필요 없으므로 읽고 버림
            st.nextToken(); 
            // y좌표 (현재 고도)
            int b = Integer.parseInt(st.nextToken());

            // 스택의 top (직전 고도) 확인
            int top = stack.peek();

            // [핵심 로직 1]
            // 현재 고도(b)가 직전 고도(top)보다 낮아진 경우
            // (고도가 0이 될 때까지 or 현재 고도보다 낮은 고도를 만날 때까지)
            while(b < top){
                stack.pop(); // 직전 고도를 스택에서 제거
                top = stack.peek(); // 스택의 새로운 top (더 이전 고도) 확인
                
                // pop을 했다는 것은, 해당 고도의 건물이 끝났다는 의미이므로
                // 건물 개수(cnt) 1 증가
                cnt++;
            }
            
            // [핵심 로직 2]
            // (위의 while문이 끝난 후) 현재 고도(b)가 (정리된) top보다 높아진 경우
            // (즉, b > top. b == top인 경우는 같은 건물이므로 아무것도 안함)
            if(top < b){
                // 새로운 고도의 건물이 시작된 것이므로 스택에 push
                stack.push(b);
            }
        } // for문 종료 (모든 좌표 처리 완료)

        // [마무리]
        // 스택에 0 외에 다른 값들이 남아있다면,
        // 그 건물들은 스카이라인 끝까지 이어진 것들이므로
        // 이 건물들도 끝내줘야 함(pop)
        while(!stack.isEmpty()){
            stack.pop(); // 스택에서 pop
            ++cnt; // pop 할 때마다 건물 개수 1 증가
        }
        
        // 맨 처음에 넣었던 바닥(지면, 0)도 마지막에 pop되면서 카운트되었으므로,
        // 최종 카운트에서 1을 빼준다.
        --cnt; 

        System.out.println(cnt); // 최소 건물 개수 출력
    }
}