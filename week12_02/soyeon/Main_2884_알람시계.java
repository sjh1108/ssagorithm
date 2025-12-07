package bk.stepByStep; //브3

import java.util.Scanner;

// 3번이나 틀림
// 이런 조건문으로 나누는 경우 
// 머릿속으로 정리가 안 되니까 매번 정답률을 낮추는 거겠지
// 논리적인 사고란 뭘까 
public class Main_2884_알람시계 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int H = sc.nextInt(); //시 
		int M = sc.nextInt(); //분
		
		int newM = M-45;
		if(newM >= 0) {
			System.out.println(H + " " + newM);				
		} else {
			if(H == 0) {
				System.out.print(23);
			} else {
				System.out.print(H-1);
			}
			System.out.println(" " + (60 + newM));// -가 아님. 음수니까.
		}
	}
}

	