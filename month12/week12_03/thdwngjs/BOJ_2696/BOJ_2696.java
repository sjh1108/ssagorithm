package week12_03.thdwngjs.BOJ_2696;

import java.util.*;
import java.io.*;

// 백준 2696 - 중앙값 구하기 (우선순위 큐)
class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine()); // 테스트 케이스의 개수
        
        while(T-- > 0){
            int m = Integer.parseInt(br.readLine()); // 수열의 크기
            
            // 중앙값의 개수 출력 (홀수 번째 수마다 출력하므로 m/2 + 1 개)
            sb.append(m/2+1 + "\n");
            
            // 중앙값 구하기 로직 (최대 힙 + 최소 힙)
            // maxHeap: 중앙값 이하의 수들을 저장 (내림차순 정렬, root가 중앙값 후보)
            // minHeap: 중앙값 초과의 수들을 저장 (오름차순 정렬)
            Queue<Integer> max = new PriorityQueue<>(Comparator.reverseOrder());
            Queue<Integer> min = new PriorityQueue<>();

            int cnt = 0; // 한 줄에 10개씩 출력하기 위한 카운터
            StringTokenizer st = null;
            
            for(int i = 0; i < m; i++){
                // 10개 단위로 입력이 줄바꿈되어 들어옴
                if(i % 10 == 0){
                    st = new StringTokenizer(br.readLine());
                }

                int x = Integer.parseInt(st.nextToken());

                // 번갈아가며 힙에 넣기
                // 짝수 번째 인덱스(1번째, 3번째... 수)는 maxHeap에 넣음 -> maxHeap의 크기가 minHeap보다 같거나 1 큼
                if(i % 2 == 0){
                    max.add(x);
                } else{
                    min.add(x);
                }
                
                // [Swap 로직]
                // maxHeap의 최대값이 minHeap의 최소값보다 크다면, 
                // 즉 작은 수들이 모인 곳의 최대값이 큰 수들이 모인 곳의 최소값보다 크다면 모순이므로 교환
                if(!min.isEmpty()){
                    if(max.peek() > min.peek()){
                        int t1 = max.poll();
                        int t2 = min.poll();

                        max.offer(t2);
                        min.offer(t1);
                    }
                }

                // 홀수 번째 수(인덱스 i가 짝수일 때)를 읽은 직후에는
                // 항상 maxHeap의 크기가 minHeap보다 1 크므로, maxHeap의 root가 전체의 중앙값이 됨
                if(i % 2 == 0){
                    sb.append(max.peek()); // 중앙값 기록
                    
                    // 출력 형식 맞추기 (한 줄에 10개씩 출력)
                    // 현재 줄에 10개를 출력했거나(cnt == 9), 마지막 수라면 줄바꿈
                    // 주의: cnt는 출력한 개수가 아니라 공백 개수 제어용으로 사용됨 (0~9)
                    // cnt가 9일 때 줄바꿈하고 0으로 초기화하지 않으면 다음 줄 첫 숫자 앞에 공백이 안 붙음
                    // 여기 로직에서는 cnt가 0~9 사이를 돌면서, 9일 때 줄바꿈을 함.
                    if(cnt == 9 || i == m - 1){
                        sb.append("\n");
                        cnt = 0;
                    } else{
                        sb.append(' ');
                    }
                    cnt++;
                }
            }
        }

        System.out.println(sb);
    }
}