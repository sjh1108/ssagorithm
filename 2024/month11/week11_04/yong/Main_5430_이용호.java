package algostudy.baek.week11_4;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;

public class Main_5430_이용호 {

	/*
	 * R(뒤집기),D(버리기)
	 * 비어있을때 D 사용하면(버리면) 에러
	 */
		public static boolean pollFirst;
		public static void main(String[] args) throws NumberFormatException, IOException {
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			int T = Integer.parseInt(br.readLine());
			for(int i = 0; i < T; i++) {
				char[] commands = br.readLine().toCharArray(); // 명령어 입력
				int n = Integer.parseInt(br.readLine()); // 배열에 들어가있는 수 개수(안씀)
				ArrayDeque<String> dq = new ArrayDeque<>();
				pollFirst = true; // 초기 D위치
				boolean isError = false;
				StringBuilder sb = new StringBuilder();
				
				// 파씽파씽
				String num = "";
				char[] nums = br.readLine().toCharArray();
				for(char c : nums) {
					if(c != '[' && c != ',' && c != ']') {
						num += c;
					}
					else if(num != "" && (c == ',' || c == ']')) {
						dq.addLast(num);
						num = "";
					}
				}
//				System.out.println(Arrays.toString(dq.toArray()));
//				System.out.println("commands: " + Arrays.toString(commands));
				for(char c : commands) {
					if(c == 'D') {
						if(!dq.isEmpty()) {
							if(pollFirst) {
								dq.pollFirst();
							}
							else {
								dq.pollLast();
							}
						}
						else {
							isError = true;
							break;
						}
					}
					
					else if(c == 'R') {
						// 제거해줄 위치 변환
						/*
						 * if(pollFirst)pollFirst = !pollFirst 이런식으로 해도 됐을듯 
						 */
						if(pollFirst) {
							pollFirst = false;
						}
						else {
							pollFirst = true;
						}
					}	
				}
				if(isError) {
					sb.append("error");
				}
				else if(!pollFirst) {
					sb.append('[');
					while(!dq.isEmpty()) {
						sb.append(dq.pollLast());
						if(dq.size() != 0) sb.append(",");
					}
					sb.append(']');
					
				}
				else {
					sb.append('[');
					while(!dq.isEmpty()) {
						sb.append(dq.pollFirst());
						if(dq.size() != 0) sb.append(",");
					}
					sb.append(']');
				}
				System.out.println(sb);
			}

		}

}
