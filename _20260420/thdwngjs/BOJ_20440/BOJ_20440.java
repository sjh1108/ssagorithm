import java.io.*;
import java.util.*;

// 스위핑(이벤트 기반) 풀이
// 각 모기의 등장/퇴장 시간을 이벤트로 변환하여
// 동시에 가장 많은 모기가 존재하는 시간 구간과 그 수를 구한다.
class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        StringTokenizer st;

        // TreeMap을 이용한 이벤트 저장 (시간순 자동 정렬)
        // 등장 시점(e)에 +1, 퇴장 시점(x)에 -1
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());

            int e = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());

            map.put(e, map.getOrDefault(e, 0) + 1);
            map.put(x, map.getOrDefault(x, 0) - 1);
        }

        int sum = 0;   // 현재 시점의 모기 수 (누적합)
        int max = 0;   // 최대 동시 모기 수
        int start = 0, end = 0; // 최대 구간의 시작/끝 시간
        boolean flag = true;    // 최대 구간의 끝을 아직 찾지 못했는지 여부
        // 시간순으로 이벤트를 꺼내며 스위핑
        while(!map.isEmpty()){
            Map.Entry<Integer, Integer> entry = map.pollFirstEntry();
            int time = entry.getKey();
            sum += entry.getValue();

            if(sum > max){
                // 새로운 최댓값 갱신 → 구간 시작점 기록
                max = sum;
                start = time;
                flag = true;
            } else if(sum < max && flag){
                // 최댓값에서 처음 줄어드는 시점 → 구간 끝점 기록
                end = time;
                flag = false;
            }
        }

        System.out.println(max);
        System.out.println(start + " " + end);
    }
}