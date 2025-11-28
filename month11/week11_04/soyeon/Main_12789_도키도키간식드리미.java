package week;// 실3

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

/**
 * 문제 이해
 * - T자 모양 거리 (한 모서리에서 간식받음, 한 모서리에 줄 서 있음, 한 모서리는 비어있음)
 * - 순서가 어긋난 줄에서 또 다른 줄을 이용해 순서대로 간식을 받을 수 있는지 확인하라
 * - 또 다른 줄은 스택으로 관리
 * - 순서가 맞지 않는 사람들을 스택에 저장가능.
 * 
 * 의문
 * - 지금 줄 서있는 곳도 이용할 수 있는가?
 * - 일단 이용할 수 없다는 전제하에 작업
 *
 * 스택
 * - push, pop, peek
 * 큐
 * - offer, poll, peek
 * 
 * 비상
 * - 아니 왜..이렇게 안 풀리지 ... 단계가 안 짜짐..
 * - 코드가 지저분해진 것 같다. 이런문제에 왜 이렇게 취약하지
 * - 단계를 짜고 했음에도 코드로 안 옮겨지는 것 같음.
 */
public class Main_12789_도키도키간식드리미 {

	static int N; // 학생들의 수  1000이하
	static int now = 1; // 현재 간식 받아야 하는 사람 번호표
	static ArrayDeque<Integer> queue = new ArrayDeque<>(); // 학생들 초기 줄  (큐로 관리)
	static ArrayDeque<Integer> stack = new ArrayDeque<>(); // 학생들 추가 줄 (스택으로 관리)
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++) {
			queue.offer(Integer.parseInt(st.nextToken()));
		}// 입력끝
		
		while(true) {
			// 번호표를 모두가 소모하면 종료
			if(now == N+1) {
				System.out.println("Nice");
				return;
			}
			// true인 경우 간식을 받은 사람이 있음 
			if(!checkLine() ) {
				System.out.println("Sad");
				return;
			}
		}

	}
	
	// 초기줄과 추가줄의 첨단을 확인해서 번호표와 맞는 사람이 있으면 제거하고 return true
	private static boolean checkLine() {
		
		// 1. 양 줄을 확인하기
		if(!queue.isEmpty() &&queue.peek() == now) {
			queue.poll();
			now++;
			return true;
		} else if(!stack.isEmpty() && stack.peek() == now) {
			stack.pop();
			now++;
			return true;
		}
		
		// 문제상황. 체크를 마쳤는데 기존 줄이 비었어. 더이상 할수있는 게 없음. 
		if(queue.isEmpty()) {
			return false;
		}
		// 2. 없으면 기존줄에서 한명을 추가줄로 옮김 
		stack.push(queue.pop());
		
		return true;
		
	}
}
