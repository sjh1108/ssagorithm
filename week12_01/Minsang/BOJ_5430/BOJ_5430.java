package week12_01.Minsang.BOJ_5430;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

public class BOJ_5430 {
	static int T;
	// R : 순서를 뒤집기 D : 첫번째 함수 버리기
	static boolean delete;
	static boolean isError;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		T = Integer.parseInt(br.readLine());
		
		for(int tc = 0; tc < T; tc++) {
			char[] cmd = br.readLine().toCharArray();
			int p = Integer.parseInt(br.readLine());
			
			// Dequeue를 어떻게 이용할지 ?
			ArrayDeque<String> dq = new ArrayDeque<>();
			// 처음 함수를 버리는 위치
			boolean deleteF = true;
			boolean isError = false; // 에러안뜨는 것을 default
			
			StringBuilder sb = new StringBuilder();
			
			String num = "";
			char[] arr = br.readLine().toCharArray();
			
			for(char a : arr) {
				if(a != '[' && a != ']' && a!= ',') {
					num += a;
				} else if ( num != "") {
					dq.addLast(num); // 1,2,3,4 -> 숫자 넣어주기
					num = "";
				}
			}
			
			for(char c : cmd) {
				if(c == 'R') {
					if(deleteF) {
						deleteF = false;
					}
					else {
						deleteF = true;
					}
				}
				else if(c == 'D') {
					if(!dq.isEmpty()) {
						if(deleteF) {
							dq.pollFirst();
							
						} else {
							dq.pollLast();
						}
					} else {
						isError = true;
						break;
					}

				}
			}
			if(isError) {
				sb.append("error");
				
			} else if (!deleteF) {
				sb.append('[');
				while(!dq.isEmpty()) {
					sb.append(dq.pollLast());
					if(dq.size() != 0) {
						sb.append(',');
					}
				}
				sb.append(']');
				
			} else {
				sb.append('[');
				while(!dq.isEmpty()) {
					sb.append(dq.pollFirst());
					if(dq.size() != 0) {
						sb.append(',');
					}
				}
				sb.append(']');
			}
			
			System.out.println(sb);
		}
		
		
		// dequeue를 쓰면 굳이 순서를 reverse 하지 않아도 맨뒤의 숫자를 뺄수있다.
		// R => 맨뒤의 숫자부터 시작해서
		// D => 그 순서부터 지우기
		
		
	}

}