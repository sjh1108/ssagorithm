package week11_03.Minsang.BOJ_1244;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_1244 {
	// 첫째 줄 : 스위치 개수 : switchNum
	// 둘째 줄 : 각 스위치의 상태 (ON : 1, OFF : 0) : status
	// 셋째 줄 : 학생 수 : stud
	// 넷째 줄 ~ : 학생 수 줄에 맞게 [ 학생의 성별, 학생이 받은 수 ]  : gender, takeNum
	static int switchNum, stud;
	static int[] status;
	// 출력해야할 것 : 스위치들의 마지막 상태를 출력
	// 남학생 : 1 = 받은 수의 배수 -> 스위치 상태 변경
	// 여학생 : 2 = 받은 수의 스위치를 중심으로 좌우 대칭이면서 가장 많은 스위치를 포함하는 구간의 스위치 상태 변경
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		switchNum = Integer.parseInt(br.readLine());
		
		status = new int[switchNum+1];
		
		st = new StringTokenizer(br.readLine());
		for(int i = 1; i <= switchNum; i++) {
			status[i] = Integer.parseInt(st.nextToken()); // ON : 1, OFF : 0
		}
		
		stud = Integer.parseInt(br.readLine());
		
		for(int i = 0; i < stud; i++) {
			st = new StringTokenizer(br.readLine());
			
			int gender = Integer.parseInt(st.nextToken()); // 1 : 남학생, 2 : 여학생
			int takeNum = Integer.parseInt(st.nextToken()); // 받을 수
			
			// 남자
			if(gender == 1) {
				// 배수 표현을 어떻게 하지 ..?
				// takeNum*2
				for(int j = takeNum; j <= switchNum; j+=takeNum) { // j+=takeNum
					if(status[j] == 0) {
							status[j] = 1;
						} 
						else if (status[j] == 1){
							status[j] = 0;
					}
				}
			// 여자
			} else if (gender == 2) {
				if(status[takeNum] == 0) {
					status[takeNum] = 1;
				} else if (status[takeNum] == 1) {
					status[takeNum] = 0;
				}
				// 좌우 대칭 검사
				int left = takeNum -1; // 왼쪽 방향
				int right = takeNum +1; // 오른쪽 방향
				
				while(left >= 1 && right <= switchNum) {
					if(status[left] == status[right]) {
						if(status[left] == 0) {
							status[left] = 1;
							status[right] = 1;
							
						} else if(status[left] == 1){
							status[left] = 0;
							status[right] = 0;
						}
						left--;
						right++;
					} else {
						break;
					}
				}
				
			}
		}
			
			
		// 마지막 스위치까지 한 줄에 20개씩 출력
		for(int i = 1; i <= switchNum; i++) {
			System.out.print(status[i] + " ");
			if(i % 20 == 0) {
				System.out.println();
			}
		}
		
	
	}

}
