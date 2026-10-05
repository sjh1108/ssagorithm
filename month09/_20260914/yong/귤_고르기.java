import java.util.*;
public class 귤_고르기 {
    public static int solution(int k, int[] tangerine){
        int answer = 0;

        // 귤 크기 빈도 카운트
        Map<Integer, Integer> map = new HashMap<>();
        for(int size : tangerine){
            map.put(size, map.getOrDefault(size, 0) + 1);
        }
        
        // 해시맵 벨류 리스트화
        List<Integer> counts = new ArrayList<>(map.values());
        
        // 내림차순 정렬
        counts.sort(Collections.reverseOrder());

        int sum = 0;
        for(int c : counts){
            sum += c;
            answer++;
            if(sum >= k) break;
        }

        return answer;
    }
    public static void main(String args[]){
        
        int[] arr = {1, 3, 2, 5, 4, 5, 2, 3};
        int result = solution(6, arr);
        System.out.println(result);

    }
}
