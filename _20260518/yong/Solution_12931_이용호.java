package algo;

public class Solution_12931_이용호 {
	public int solution(int n) {
        int answer = 0;
        int num = n;
        while(num > 0){
            answer += num % 10;
            num = num / 10;
        }
        // [실행] 버튼을 누르면 출력 값을 볼 수 있습니다.
        System.out.println(answer);
        
        return answer;
    }
	public static void main(String[] args) {
		Solution_12931_이용호 sol = new Solution_12931_이용호();
		int res = sol.solution(14);
		System.out.println(res);

	}

}
