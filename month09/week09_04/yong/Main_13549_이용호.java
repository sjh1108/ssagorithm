package algostudy.baek.m9w4;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main_13549_이용호 {
	static int[] dist;
	static int N, K;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		K = sc.nextInt();
		
		dist = new int[100001]; //0~100000
		Arrays.fill(dist, Integer.MAX_VALUE);
		
		dij(N);
		System.out.println(dist[K]);
		
	}
	static void dij(int start) {
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
		dist[N] = 0; // 시작위치는 0초	
		pq.offer(new int[] {start,0});
		while(!pq.isEmpty()) {
			int[] cur = pq.poll();
			int now = cur[0];
			int time = cur[1];
			
			if(time > dist[now]) continue;
			
			if(now == K) break;
			// 순간이동(현재위치 * 2)
			if(now * 2 <= 100000 && dist[now*2] > time) {
				dist[now*2] = time;
				pq.offer(new int[] {now *2 , time});
			}
			// X+1(현재위치 + 1)
			if(now + 1 <= 100000 && dist[now+1] > time + 1) {
				dist[now+1] = time + 1;
				pq.offer(new int[] {now + 1, time + 1});
			}
			// X-1(현재위치 - 1)
			if(now - 1 >= 0 && dist[now-1] > time + 1) {
				dist[now-1] = time + 1;
				pq.offer(new int[] {now - 1, time + 1});
			}
		}
	}

}
