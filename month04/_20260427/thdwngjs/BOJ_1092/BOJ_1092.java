import java.io.*;
import java.util.*;

class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine()); // 크레인 개수

        int max = 0; // 크레인이 들 수 있는 최대 무게
        List<Integer> crane = new ArrayList<>(N);

        // 크레인 무게 제한 입력 및 최대값 갱신
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++){
            crane.add(Integer.parseInt(st.nextToken()));
            max = Math.max(max, crane.get(i));
        }

        int M = Integer.parseInt(br.readLine()); // 박스 개수

        // 박스 무게 입력, 크레인 최대 무게보다 무거운 박스가 있으면 -1 출력
        List<Integer> box = new ArrayList<>(M);
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < M; i++){
            int b = Integer.parseInt(st.nextToken());
            box.add(b);
            if(b > max){
                System.out.println(-1);
                return;
            }
        }

        // 크레인은 내림차순, 박스는 오름차순 정렬 (그리디: 무거운 박스부터 처리)
        Collections.sort(crane, Collections.reverseOrder());
        Collections.sort(box);

        int answer = 0; // 필요한 최소 시간(분)
        int cur = 0, index = M-1; // cur: 현재 크레인 인덱스, index: 현재 박스 인덱스(무거운 쪽부터)
        while(!box.isEmpty()){
            // 현재 크레인이 현재 박스를 들 수 있으면 박스 제거 후 다음 크레인으로
            if(box.get(index) <= crane.get(cur)){
                box.remove(index);
                cur++;
            }
            // 못 들면 더 가벼운 박스로 이동
            index--;

            // 모든 크레인을 사용했거나 더 이상 확인할 박스가 없으면 한 라운드 종료
            if(cur == N || index == -1){
                cur = 0;
                index = box.size()-1;
                answer++;
            }
        }

        System.out.println(answer);
    }
}
