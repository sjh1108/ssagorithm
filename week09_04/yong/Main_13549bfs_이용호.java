package algostudy.baek.m9w4;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main_13549bfs_이용호 {
	static int[] dist;
	static int N, K;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		K = sc.nextInt();
		
		dist = new int[100001]; //0~100000
		Arrays.fill(dist, Integer.MAX_VALUE);
		dist[N] = 0; // 시작위치는 0초
		
		bfs(N);
		System.out.println(dist[K]);
		
	}
	static void bfs(int start) {
		Queue<Integer> q = new LinkedList<>();
		q.add(start);
		while(!q.isEmpty()) {
			int now = q.poll();
			if(now == K) break;
			// 순간이동(현재위치 * 2)
			if(now * 2 <= 100000 && dist[now*2] > dist[now]) {
				dist[now*2] = dist[now];
				q.add(now*2);
			}
			// X+1(현재위치 + 1)
			if(now + 1 <= 100000 && dist[now+1] > dist[now]+1) {
				dist[now+1] = dist[now]+1;
				q.add(now+1);
			}
			// X-1(현재위치 - 1)
			if(now - 1 >= 0 && dist[now-1] > dist[now]+1) {
				dist[now-1] = dist[now]+1;
				q.add(now-1);
			}
		}
	}

}
