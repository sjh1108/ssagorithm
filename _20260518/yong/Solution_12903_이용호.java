package algo;

public class Solution_12903_이용호 {
	public String solution(String s) {
		int sLen = s.length();
		System.out.println("len: " + sLen);
		int idx = sLen/2;
		String answer = "";
		if(sLen % 2 == 0) {
			answer = s.substring(idx-1, idx+1);
		}else {
			answer = s.substring(idx, idx+1);
		}
		
        return answer;
    }
	public static void main(String[] args) {
		Solution_12903_이용호 sol = new Solution_12903_이용호();
		String res = sol.solution("helio");
		System.out.println(res);

	}

}
