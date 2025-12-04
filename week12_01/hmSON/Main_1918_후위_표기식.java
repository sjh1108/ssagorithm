import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;

public class Main_1918_후위_표기식 {
	
	/*
	 * BOJ_1918 : 후위 표기식 (Gold_2)
	 * 자료구조 및 알고리즘 : 스택
	 *
	 * [문제 요약]
	 * - 중위 표기식(Infix, 예: A+B)이 주어졌을 때, 이를 후위 표기식(Postfix, 예: AB+)으로 변환하라.
	 * - 연산자의 우선순위(*, / > +, -)와 괄호 처리를 올바르게 수행해야 한다.
	 *
	 * [핵심 아이디어]
	 * - 피연산자(A~Z): 순서가 변하지 않으므로 결과 문자열(SB)에 즉시 추가한다.
	 * - 연산자: 스택에 담아두되, 자신보다 우선순위가 높거나 같은 연산자가 스택 top에 있다면
	 * 먼저 연산되어야 하므로 pop하여 결과에 추가한 뒤, 자신을 push한다.
	 * - 괄호: '('는 무조건 push하고, ')'가 나오면 '('를 만날 때까지 모든 연산자를 pop한다.
	 *
	 * [구현 메모]
	 * - '('의 우선순위를 가장 낮게 설정하여, 닫는 괄호가 나오기 전까지는 pop되지 않도록 처리.
	 *
	 * [시간 복잡도]
	 * - 문자열의 길이 N만큼 순회하며 각 문자는 스택에 최대 한 번 들어가고 나온다.
	 * - O(N)
	 */
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 입력 문자열을 문자 배열로 변환
		char[] str = br.readLine().toCharArray();
		int len = str.length;
		
		StringBuilder sb = new StringBuilder(); // 결과(후위 표기식)를 저장할 버퍼
		Deque<Character> operator = new ArrayDeque<>(); // 연산자를 저장할 스택
		
		for(int i=0; i<len; i++) {
			char ch = str[i];
			
			// 1. 피연산자(A~Z)는 바로 출력(결과에 추가)
			if(ch >= 'A' && ch <= 'Z') {
				sb.append(ch);
				continue;
			}
			
			// 2. 여는 괄호 '('는 무조건 스택에 push
			if(ch == '(') {
				operator.addLast(ch);
				continue;
			}
			
			// 3. 닫는 괄호 ')'가 나오면 여는 괄호를 만날 때까지 스택의 연산자를 모두 pop
			if(ch == ')') {
				while(!operator.isEmpty() && operator.peekLast() != '(') {
					sb.append(operator.pollLast());
				}
				operator.pollLast(); // 여는 괄호 '(' 제거
				continue;
			}
			
			// 4. 일반 연산자 (+, -, *, /)
			// 스택의 top에 있는 연산자가 현재 연산자보다 우선순위가 높거나 같다면
			// 먼저 계산되어야 하므로 pop하여 결과에 추가 (우선순위가 낮은 게 올 때까지 반복)
			while(!operator.isEmpty() && priority(operator.peekLast()) >= priority(ch)) {
				sb.append(operator.pollLast());
			}
			// 현재 연산자를 스택에 push
			operator.addLast(ch);
		}
		
		// 5. 스택에 남아있는 모든 연산자를 pop하여 결과에 추가
		while(!operator.isEmpty()) sb.append(operator.pollLast());
		
		System.out.println(sb);
	}
	
	// 연산자 우선순위 반환 함수
	// 숫자가 클수록 높은 우선순위
	public static int priority(char op) {
		if (op == '*' || op == '/') return 2;
		if (op == '+' || op == '-') return 1;
		return 0; // '('는 스택 안에서 가장 낮은 우선순위로 취급하여 임의로 pop되지 않게 함
	}

}