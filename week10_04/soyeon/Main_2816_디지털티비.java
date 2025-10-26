package bk.bronze; //1

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 문제 이해
 * - 주어진 TV채널 리스트에서 KBS1를 첫번째, KBS2를 두번재로 오게 만들어라
 * 
 * 조작 
 * - 화살표를 한 칸 아래로 내린다. (채널 i에서 i+1로)
 * - 화살표를 위로 한 칸 올린다. (채널 i에서 i-1로)
 * - 현재 선택한 채널을 한 칸 아래로 내린다. (채널 i와 i+1의 위치를 바꾼다. 화살표는 i+1을 가리키고 있는다)
 * - 현재 선택한 채널을 위로 한 칸 올린다. (채널 i와 i-1의 위치를 바꾼다. 화살표는 i-1을 가리키고 있다)
 *
 * 입력 
 * - 채널 수  N (N <= 100)
 * - 채널 이름 N개 
 * 
 * 출력
 * - 눌러야하는 버튼 공백없이 출력 
 * 
 * 후기
 * - 딲딱해요
 * - 자꾸 반복문의 조건 변수를 반복문에서 변경 시키는 실수를 한다.  
 */
public class Main_2816_디지털티비 {

	static int N, now;
	static int kbs1Idx, kbs2Idx;
	static String[] list;
	static StringBuilder sb = new StringBuilder();
	public static void main(String[] args) throws  Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		
		N = Integer.parseInt(br.readLine());
		list = new String[N];
		
		for(int i = 0; i < N; i++) {
			list[i] = br.readLine();
			if(list[i].equals("KBS1")) kbs1Idx = i;
			if(list[i].equals("KBS2")) kbs2Idx = i;
		}// 입력 끝
		
		search();
		
		System.out.println(sb.toString());
	}
	
	private static void search() {
		boolean plus = false;
		

		list.toString();
		
		if(list[0].equals("KBS1") && list[1].equals("KBS2")) return;

		// kbs1이 더 아래에 있으면 올렸을 경우 kbs의 인덱스가 +1됨
		if(kbs1Idx > kbs2Idx) kbs2Idx++; 
		
		// KBS1을 먼저 맞추기
		if(!list[0].equals("KBS1")) {
			moveTo(kbs1Idx); //3
			switchTo(0);
		}
		
		// KBS2 맞추기
		if(!list[1].equals("KBS2")) {
			moveTo(kbs2Idx);
			switchTo(1);			
		}
		
	}

	// x까지 이동
	private static void moveTo(int x) {
		
		int abs = Math.abs(now - x);
		for(int i = 0; i < abs; i++) {
			System.out.println( i + "번 째 ");
			if(now < x) {
				sb.append(1);
				now++;
			}else {
				sb.append(2);
				now--;
			}
			
		}
	}
	
	// x까지 옮기기
	private static void switchTo(int x) {
		
		int abs = Math.abs(now - x);
		for(int i = 0; i < abs; i++) {
			if(now < x) {
				sb.append(3);
				now++;
			}else {
				sb.append(4);
				now--;
			}
			
		}
	}
}