package com.codingtest.bk;

import java.util.PriorityQueue;
import java.util.Scanner;

public class Main_1927_최소힙 {
	//삽입, 삭제 logN
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		PriorityQueue<Integer> pq = new PriorityQueue<>();
		// 만약 최대힙을 원한다면 new PriorityQueue<>(Collections.reverseOrder())
		
		
		int n;
		for(int i = 0; i < N; i++) {
			n = sc.nextInt();
			if(n == 0) {
				if(pq.peek() != null) {
					System.out.println(pq.poll());	
				} else {
					System.out.println(0);
				}
				 
			} else {
				pq.add(n);
			}
			
		}
		
		
	}
}
