package _20260323.yong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*
 * node : 문제 번호, 난이도(난이도 클수록 어려운 문제: 조건에 따로 안나와있음;;)
 * recommand x: 1 -> 가장 어려운문제의 문제번호 출력(여러개면 문제번호 큰것)
 * 			   -1 ->	가장 쉬운 문제의 문제번호 출력 (여러개면 문제번호 작은것)
 * add P L: 난이도가 L인 문제번호 P를 추가(추천문제 리스트에 없는 P만 주어짐, solved로 해결된게 문제난이도 바꿔서 들어올순 있음)
 * solved P: 추천 문제리스트에서 문제번호 P를 제거
 * 문제리스트 비었는데 recommand를 주는 예외는 없음
 * sovled도 추천 문제 리스트 비었는데 solved 주는 예외 없음
 * 
 * 추천 리스트 개수 N 최대 100,000
 * 명령어 횟수 M 최대 10,000
 * recommand 할때마다 출력
 * 시간복잡도 예측
 * 최대 개수 최대 정렬 경우의 수
 * logN * M = 17 * 10,000 = 170,000
 * 
 */

public class Main_21939_yong {
	public static class Node{
		int P; int L;
		Node(int P, int L){
			this.P = P;
			this.L = L;
		}
	}
	
	static boolean[] is_solved;
	static int[] level;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		is_solved = new boolean[100001];
		level = new int[100001];
		
		int N = Integer.parseInt(br.readLine()); // 문제 리스트 개수
		// 문제 난이도 내림차순 정렬 문제난이도 같으면 문제 번호 내림차순 
		PriorityQueue<Node> Maxpq = new PriorityQueue<>((a, b) -> {
			if(a.L == b.L) {
				return b.P - a.P;
			}
			return b.L - a.L;
		});
		// 문제 난이도 오름차순 정렬 문제난이도 같으면 문제 번호 오름차순 정렬
		PriorityQueue<Node> Minpq = new PriorityQueue<>((a,b)->{
			if(a.L == b.L) {
				return a.P - b.P;
			}
			return a.L - b.L;
		});
		
		for(int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int P = Integer.parseInt(st.nextToken()); // 해당 문제 번호
			int L = Integer.parseInt(st.nextToken()); // 해당 문제 난이도
			level[P] = L;
			Maxpq.add(new Node(P,L));
			Minpq.add(new Node(P,L));
		}
		
		int M = Integer.parseInt(br.readLine()); // 명령어 개수
		for(int i = 0; i < M; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			String Command = st.nextToken();
			int x; int P; int L;
			
			switch(Command) {
			case "recommend":
				x = Integer.parseInt(st.nextToken());
				int res = 0;
				
				if(x == 1) {
					res = recommend(Maxpq);
				}
				else if(x == -1) {
					res = recommend(Minpq);
				}
				System.out.println(res);
				break;
			case "add":
				P = Integer.parseInt(st.nextToken());
				L = Integer.parseInt(st.nextToken());
				add(P, L, Maxpq, Minpq);
				break;
			case "solved":
				P = Integer.parseInt(st.nextToken());
				solved(P);
				break;
			}
		}
	}
	public static int recommend(PriorityQueue<Node> pq) {
		// 해결된 문제는 패스
		while(true) {
			Node now = pq.peek();
			// 해결 안됐고, level 갱신 안됐으면
			if(!is_solved[now.P] && level[now.P] == now.L) {
				return now.P;
			}
			else {
				pq.poll();
			}
		}	
		
	}
	public static void add(int P, int L, PriorityQueue<Node> Maxpq, PriorityQueue<Node> Minpq) {
		// 다른 난이도로 들어올수도 있음 -> solved false로
		if(is_solved[P]) {
			is_solved[P] = false;
		}
		level[P] = L;
		Maxpq.add(new Node(P,L));
		Minpq.add(new Node(P,L));
	}
	public static void solved(int P) {
		is_solved[P] = true;
	}

}
