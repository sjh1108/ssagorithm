package week11_03.sjh1108.BOJ_28707;

import java.io.*;
import java.util.*;

// 백준 28707 - 배열 정렬 (다익스트라)
class Main{
    // map: 다익스트라의 'dist' 배열(최소 비용 테이블) 역할
    // Key(String): 배열의 현재 상태(순열)를 문자열로 변환한 값 (예: "132")
    // Value(Integer): 해당 상태(순열)에 도달하기까지의 '최소 비용'
    private static HashMap<String, Integer> map = new HashMap<>();
    
    // M개의 연산(스왑) 정보를 저장할 리스트
    private static List<Command> commands = new ArrayList<>();

    // 스왑 연산의 정보를 담는 클래스
    private static class Command{
        int x, y, cost; // 인덱스 x와 y를 바꾸는 데 드는 비용(cost)

        public Command(int x, int y, int cost) {
            this.x = x;
            this.y = y;
            this.cost = cost;
        }

        // 현재 배열(arr)에 스왑 연산을 적용한 배열을 반환
        // (주의: 이 메소드는 원본 배열 arr 자체를 수정함.
        //  따라서 호출하는 쪽에서 원본을 유지하려면 clone()을 사용해야 함)
        public int[] execute(int[] arr){
            int temp = arr[x];
            arr[x] = arr[y];
            arr[y] = temp;
            return arr;
        }
    }

    // int[] 배열을 HashMap의 Key로 사용하기 위해 고유한 문자열로 변환하는 헬퍼 함수
    private static String arrayToString(int[] arr){
        StringBuilder sb = new StringBuilder();
        for(int num : arr){
            sb.append(num); // 숫자를 문자열로 이어붙임 (예: [1, 10, 2] -> "1102")
        }
        return sb.toString().trim(); // (trim()은 여기서는 큰 의미 없음)
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine()); // 배열의 크기 N
        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] original = new int[N]; // 초기 배열 상태
        int[] answer = new int[N]; // 목표 배열 상태 (정렬된 상태)
        for (int i = 0; i < N; i++) {
            // 초기 상태와 목표 상태 배열에 모두 입력값을 저장
            answer[i] = original[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(answer); // 목표 상태(answer)를 오름차순으로 정렬
        
        // [다익스트라 시작점 설정]
        // 초기 상태(original)에 도달하는 비용은 0
        map.put(arrayToString(original), 0);

        int M = Integer.parseInt(br.readLine()); // 연산(스왑)의 개수
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()) - 1; // 1-based 인덱스 -> 0-based 인덱스
            int y = Integer.parseInt(st.nextToken()) - 1; // 1-based 인덱스 -> 0-based 인덱스
            int cost = Integer.parseInt(st.nextToken());
            commands.add(new Command(x, y, cost)); // 연산 목록에 추가
        }

        // [다익스트라 우선순위 큐(Min-Heap)]
        // 큐에는 '배열의 상태'(int[])를 저장
        // 정렬 기준: 'map'에 저장된 해당 배열 상태의 '최소 비용'이 적은 순서
        Queue<int[]> q = new PriorityQueue<>(Comparator.comparingInt(o -> map.get(arrayToString(o))));
        
        // 큐에 시작 노드(초기 배열 상태) 추가
        q.add(original);

        while(!q.isEmpty()){
            // 현재 맵(dist)에서 비용이 가장 적은 상태(cur)를 꺼냄
            int[] cur = q.poll();
            String curKey = arrayToString(cur); // map에서 비용을 조회하기 위한 키

            // [종료 조건]
            // 만약 현재 꺼낸 상태가 목표 상태(answer)와 같다면,
            // 다익스트라 알고리즘 특성상 이 경로가 최소 비용이므로 탐색 종료
            if(curKey.equals(arrayToString(answer))){
                break;
            }
            
            // [최적화 - 생략됨]
            // (만약 map.get(curKey)가 큐에서 꺼낸 시점의 비용보다 크다면 스킵하는 로직이
            //  일반적인 다익스트라에 포함되지만, 이 코드는 해당 로직 없이도 정답은 나옴)

            // 현재 상태(cur)에서 M개의 모든 연산(스왑)을 시도
            for(Command cmd: commands){
                
                // [중요]
                // 원본 'cur' 배열이 변경되면 안 되므로,
                // 복제(clone)한 배열을 가지고 연산을 수행
                int[] nxt = cmd.execute(cur.clone());
                String nxtKey = arrayToString(nxt); // 연산 후 다음 상태의 키

                // [다익스트라 - 릴렉세이션(Relaxation)]
                // (현재 상태까지의 비용) + (이번 연산 비용)
                int newCost = map.get(curKey) + cmd.cost;

                // Case 1: 다음 상태(nxt)를 방문한 적이 없다면 (최초 방문)
                if(!map.containsKey(nxtKey)){
                    map.put(nxtKey, newCost); // 최소 비용 기록
                    q.add(nxt); // 큐에 다음 상태 추가
                } 
                // Case 2: 다음 상태(nxt)를 방문한 적은 있지만,
                //         새로 계산한 비용(newCost)이 기존 비용(map.get(nxtKey))보다 *저렴하다면*
                else{
                    if(map.get(nxtKey) > newCost){
                        map.put(nxtKey, newCost); // 최소 비용 갱신
                        q.add(nxt); // 갱신된 비용으로 큐에 다시 추가
                    }
                }
            }
        } // while(다익스트라) 종료

        // 목표 상태(answer)의 문자열 키
        String ansKey = arrayToString(answer);
        
        // map(최소 비용 테이블)에서 목표 상태의 최소 비용을 조회
        // 만약 map에 ansKey가 없다면 (즉, 도달 불가능), -1을 반환
        System.out.println(map.getOrDefault(ansKey, -1));
    }
}