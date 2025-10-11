import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_16637_이용호 {
/*
 * 괄호 추가해서 최대값 구하기
 */
	static int N;
	static char[] expr;
	static int result = Integer.MIN_VALUE;
	
	public static void main(String[] args) throws NumberFormatException, IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		expr = br.readLine().toCharArray();
//		System.out.println(expr[0]);
		back(0, expr[0] - '0');
		System.out.println(result);
	}
	
	static void back(int idx, int cur) {
		if(idx >= N - 1) {
			result = Math.max(result, cur);
			return;
		}
		char oper = expr[idx+1];
		int nextNum = expr[idx+2] - '0';//다음 숫자
		
		//괄호 x
		int noRes = cal(cur, oper, nextNum);
		//System.out.println(noRes);
		back(idx + 2, noRes);
		
		//괄호 o
		if(idx + 2 < N - 1) {//다음 연산 가능하면
			char nextOper = expr[idx+3];
			int jumpNum = expr[idx+4] - '0';
			
			int pre = cal(nextNum, nextOper, jumpNum);
			int yesRes = cal(cur, oper, pre);
			back(idx + 4, yesRes);
		}
	}
	
	static int cal(int n1, char op, int n2) {
		if(op == '+') return n1 + n2;			
		if(op == '-') return n1 - n2;
		return n1 * n2;
	}
}
