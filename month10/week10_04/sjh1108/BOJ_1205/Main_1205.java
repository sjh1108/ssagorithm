import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        // 랭킹 리스트에 있는 점수의 개수 N
        int N = Integer.parseInt(st.nextToken());
        // 태수의 새로운 점수 score
        int score = Integer.parseInt(st.nextToken());
        // 랭킹 리스트에 올라갈 수 있는 점수의 개수 P
        int P = Integer.parseInt(st.nextToken());

        // 1. 랭킹 리스트가 비어있는 경우 (N == 0)
        // 새로운 점수는 무조건 1등이 됨
        if(N == 0){
            System.out.println(1);
            return; // 프로그램 종료
        }

        // 랭킹 리스트를 저장할 ArrayList
        List<Integer> list = new ArrayList<>();
        st = new StringTokenizer(br.readLine()); // 랭킹 리스트 점수들 입력받기
        for(int i = 0; i < N; i++){
            list.add(Integer.parseInt(st.nextToken()));
        }

        // 2. 랭킹 리스트가 꽉 찼고(N == P), 새 점수가 꼴찌 점수보다 작거나 같은 경우
        //    (list.get(P-1)은 0-based index이므로 P번째, 즉 꼴찌 점수)
        //    이 경우 랭킹에 진입할 수 없음
        if(list.size() == P && score <= list.get(P-1)){
            System.out.println(-1); // -1 출력
            return; // 프로그램 종료
        }

        // 3. 랭킹에 진입 가능한 경우
        
        // 일단 랭킹 리스트에 새로운 점수를 추가
        list.add(score);
        
        // 리스트를 내림차순(점수 높은 순)으로 정렬
        Collections.sort(list, Collections.reverseOrder());

        // 정렬된 리스트를 순회하며 태수의 점수(score) 찾기
        for(int i = 0; i < list.size(); i++){
            // 리스트에서 태수의 점수를 찾았다면
            if(list.get(i) == score){
                // 랭킹은 1등부터 시작하므로 (인덱스 + 1)을 출력
                // (만약 동점이 있다면, 정렬된 리스트에서 가장 앞에 있는 인덱스가
                //  해당 점수의 랭킹이 되므로, 이 코드는 동점 처리를 올바르게 함)
                System.out.println(i+1);
                return; // 프로그램 종료
            }
        }
    }
}