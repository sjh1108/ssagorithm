package baek;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_1966_이용호 {
/*
 * 중요도 확인하는 큐 -> 우선순위 큐(내림차순으로 재정의)
 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		for(int tc = 0; tc < T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken()); // 큐 개수
			int idx = Integer.parseInt(st.nextToken()); // 궁금한 idx
			
//			System.out.println("N = "+N+"idx = "+idx);
			PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> {
				return b - a;
			});
			Queue<int[]> q = new LinkedList<>(); 
			
			//큐 원소 입력받기
			st = new StringTokenizer(br.readLine());
			for(int i = 0; i < N; i++) {
				int p = Integer.parseInt(st.nextToken());
				q.add(new int[] {i,p}); //{idx, p}
				pq.add(p);
			}
			int cnt = 0;
			//우선순위대로 출력
			while(!pq.isEmpty()) {
				int nowP = pq.poll(); //출력될 우선순위
				while(true) {
					int[] arr = q.poll(); //큐에서 첫번째 배열 poll
//					System.out.println(arr[1] + " " + nowP);
					if(arr[1] == nowP) {//출력될 우선순위와 큐의 우선순위가 같으면
//						System.out.print(arr[1] + " ");
						cnt++;
						if(arr[0] == idx) { //찾고싶은 문서였다면
							System.out.println(cnt);//순서 출력
							pq.clear();
							break;
						}
						break;
					}
					else {
						q.add(arr);
					}
				}
			}
		}
	}

}
