import java.util.*;

public class 구명보트 {
    public static int solution(int[] people, int limit){
        int answer = 0;

        Arrays.sort(people);
        int left = 0;
        int right = people.length-1;
        while (left <= right) {
            int sum = people[left] + people[right];
            // 두명 무게가 limit 초과시 몸무게 많은사람만 태워서 보낸다
            if(sum > limit){
                right--;
                answer++;
            }
            else{
                right--;
                left++;
                answer++;
            }
            // 한명 남으면 보트 한대 추가 후 종료
            if(right == left){
                answer++;
                break;
            }
        }
        return answer;
    }
    public static void main(String[] args){
        int[] people = {70, 50, 80, 50};
        int limit = 100;
        int result = solution(people, limit);
        System.out.println(result);
    }
}
