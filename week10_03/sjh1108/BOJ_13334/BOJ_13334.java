package week10_03.sjh1108.BOJ_13334;
import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine()); // 사람의 수

        // 각 사람의 [시작점, 끝점]을 저장할 2차원 배열
        int[][] arr = new int[N][2];
        StringTokenizer st;
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());

            int h = Integer.parseInt(st.nextToken()); // 집 위치
            int o = Integer.parseInt(st.nextToken()); // 사무실 위치

            // 집과 사무실 위치 중 더 작은 값을 시작점(arr[i][0])으로,
            // 더 큰 값을 끝점(arr[i][1])으로 저장 (방향 정규화)
            if(h <= o) { 
                arr[i][0] = h;
                arr[i][1] = o;
            } else{
                arr[i][0] = o;
                arr[i][1] = h;
            }
        }

        // [핵심 1] 모든 선분을 '끝점(arr[1])' 기준으로 오름차순 정렬
        // 만약 끝점이 같다면, '시작점(arr[0])' 기준으로 오름차순 정렬
        Arrays.sort(arr, (o1, o2) -> {
            if(o1[1] == o2[1]){
                return Integer.compare(o1[0], o2[0]); // 끝점 같으면 시작점 오름차순
            }
            return Integer.compare(o1[1], o2[1]); // 끝점 오름차순
        });

        int L = Integer.parseInt(br.readLine()); // 철로의 길이

        int max = 0; // 철로에 포함될 수 있는 최대 사람 수
        // [핵심 2] 우선순위 큐 (Min-Heap, 오름차순)
        // 철로에 포함될 *가능성이 있는* 선분들의 '시작점'을 저장
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        // 끝점 기준으로 정렬된 모든 선분(road)을 순회
        for(int[] road: arr){
            // 1. 필터링: 현재 선분의 길이 자체가 철로 길이(L)보다 크면
            //    이 선분은 절대 철로에 포함될 수 없으므로 스킵
            if(road[1] - road[0] > L) continue;

            // 2. 후보 추가: 현재 선분은 L보다 짧거나 같으므로,
            //    일단 후보군(pq)에 '시작점'을 추가
            pq.offer(road[0]);

            // 3. 후보 제거 (핵심 로직):
            //    현재 선분의 '끝점'(road[1])을 기준으로 철로를 놓는다고 가정
            //    (철로의 범위: [road[1] - L, road[1]])
            //    이때 pq에 저장된 시작점들 중, 철로의 시작(road[1] - L)보다
            //    '왼쪽'에 있는 (즉, 너무 멀어서 포함될 수 없는) 선분들을 모두 제거
            while(!pq.isEmpty()){
                // pq.peek() = 후보군 중 가장 작은 시작점 (가장 왼쪽에 있는 시작점)
                // (현재 끝점 - 가장 왼쪽 시작점) > L  <=>  가장 왼쪽 시작점 < 현재 끝점 - L
                // 즉, L 길이의 철로로 커버가 불가능하다면
                if(road[1] - pq.peek() > L){
                    // 커버 불가능한 시작점은 pq에서 제거
                    pq.poll();
                } else {
                    // 커버가 가능하다면, 이 시작점 및 pq에 남은 (더 큰) 시작점들은
                    // 모두 [road[1] - L, road[1]] 범위 내에 있으므로 제거 중지
                    break;
                }
            }
            
            // 4. 최대값 갱신:
            //    위의 제거 과정이 끝나면, pq에 남아있는 원소의 개수(pq.size())는
            //    '현재 끝점(road[1])을 기준으로 철로를 놓았을 때 포함되는 선분의 최대 개수'가 됨
            //    이 값을 기존의 최대값(max)과 비교하여 갱신
            max = Math.max(max, pq.size());
        }

        // 모든 선분을 순회하며 찾은 최대값 출력
        System.out.println(max);
    }
}