package com.codingtest.bk;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

// eight five four nine one seven six three two zero
// 8 5 4 9 1 7 6 3 1
// 문제 설명.
// 주어진 숫자 M 이상 N이하의 숫자를
// 하나씩 뜯어서 문자로 적었을 때로 정렬하라 
// ex) 52 = "five two"
// 입력 숫자는 1~99까지만 나옴 
public class Main_1755_숫자놀이 {

	static StringBuilder sb;
	
	// 정렬을 위한 클래스 생성 
	static class StringNumber implements Comparable<StringNumber>{
		int num; // 원본 숫자 
		int tenNum; // 10의자리수 
		int oneNum; // 1의 자리수 
		String stringNum; // 문자화된 숫자 
		
		public StringNumber(int num) {
			super();
			this.num = num;
			this.oneNum = num%10;
			this.tenNum = num%100/10;
			
			//생성과 동시에 문자열 숫자로 만들어버리기 
			String oneStringNum = changeNumberToString(oneNum);
			String tenStringNum = changeNumberToString(tenNum);
			
			if(tenNum != 0) { // 10미만 숫자 
				this.stringNum = tenStringNum + " " + oneStringNum; 
				return;
			}
			this.stringNum = oneStringNum + "";
		}

		// 이게 아닌 것 같긴 해 
		public String changeNumberToString(int num) {
			switch(num) {
				case 0:
					return "zero";
				case 1: 
					return "one";
				case 2:
					return "two";
				case 3: 
					return "three";
				case 4:
					return "four";
				case 5: 
					return "five";
				case 6:
					return "six";
				case 7: 
					return "seven";
				case 8:
					return "eight";
				case 9: 
					return "nine";
				default:
					return null;
			}
			
		}

		// 그냥 문자열 정렬 
		@Override
		public int compareTo(StringNumber o) {
			return this.stringNum.compareTo(o.stringNum);
		}
	}
	
	static int M, N;
	static PriorityQueue<StringNumber> pq;
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		M = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken()); //입력끝
		
		// pq에 넣으면 되겠지 
		pq = new PriorityQueue<>();
		
		for(int i = M; i <=N; i++) {
			pq.add(new StringNumber(i));
		}
		
		int cnt = 0;
		
		// 출력 
		while(!pq.isEmpty()) {
			sb.append(pq.poll().num).append(" ");
			
			// 10개씩 끊어서 출력하래 
			++cnt;
			if(cnt == 10) {
				sb.append("\n");
				cnt = 0;
			}
		}

		
		System.out.println(sb.toString());
	}
}
