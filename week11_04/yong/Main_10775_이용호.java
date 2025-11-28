package algostudy.baek.week11_4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeSet;

public class Main_10775_이용호 {
	/*
	 * treeset활용해서도 풀어봄
	 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int G = Integer.parseInt(br.readLine());
		int P = Integer.parseInt(br.readLine());
		
		TreeSet<Integer> tree = new TreeSet<>();
		
		for(int i = 0; i <= G; i++) {
			tree.add(i);
		}
		int cnt = 0;
		for(int i = 0; i < P; i++) {
			int gi = Integer.parseInt(br.readLine());
			int x = tree.floor(gi);
			if(x == 0) break;
			cnt++;
			tree.remove(tree.floor(gi));
		}
		System.out.println(cnt);
	}
	
	/*
	 * 기존에 풀었던 방식
	 * G개의 게이트(1~G)
	 * P개의 비행기가 순서대로 도착
	 * 어느곳에도 착륙할곳 없으면 폐쇠(중지)
	 * gi 도킹 하면 다음 gi에 접근하는 비행기는 gi - 1로 도킹해야함 gi -> gi - 1
	 */
	/*
		public static int[] gates;
		public static void main(String[] args) throws NumberFormatException, IOException {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			int G = Integer.parseInt(br.readLine()); // 게이트 수
			int P = Integer.parseInt(br.readLine()); // 비행기 수
			int cnt = 0; // 착륙 비행기 수
			
			// 게이트 도킹 여부(1based)
			gates = new int[G+1]; 
			for(int i = 1; i <= G; i++) {
				gates[i] = i;
			}
			
			for(int i = 0;i < P; i++) {
				int gi = Integer.parseInt(br.readLine()); // 1 ~ gi
				if(find(gi) == 0) break; // 도킹할수 없으면 종료
				cnt++;
				union(find(gi), find(gi)-1); // 부모게이트 연결
			}
			System.out.println(cnt);
		}
		// gi -> gi-1
		public static void union(int a, int b) {
			a = find(a);
			b = find(b);
			gates[a] = b;
		}
		// 부모 찾기, 압축
		public static int find(int x) {
			if(x == gates[x]) return x;
			return gates[x] = find(gates[x]);
		}*/

}
