package algostudy.baek.b12851;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_12851_이용호 {
//큐에 이동지점 넣기
static int[][] map = new int[100001][2]; //map[][0] K까지 걸린 시간, [][1] 최단 거리 방법 수
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		if(N==K) { //동생과 수빈이 위치가 같으면
			System.out.println(0);
			System.out.println(1);
		}
		else {
			bfs(N, K);
			System.out.println(map[K][0]);
			System.out.println(map[K][1]);
			
		}
	}
	
	static void bfs(int N, int K) {
		Queue<int[]> queue = new LinkedList<>();
		queue.offer(new int[] {N,0});// {현재위치, 걸린시간} 큐에 추가
		
		while(!queue.isEmpty()) {
			int[] now = queue.poll();
			int front = now[0]+1; // int[] nxPos = {now+1, now-1, now*2}이런식으로도 가능 할듯
			int back = now[0]-1;
			int teleport = now[0]*2;
			int nxtime = now[1]+1;
			if(front <=100000 && front > 0) { // 이동한 위치가 적절한지 체크
				if(map[front][0] > nxtime | map[front][0] == 0) { //처음 도착했거나 더빨리 도착했을때
					map[front][0] = nxtime; // 현재 걸린시간으로 갱신
					map[front][1] = 1; // 최단시간 방법 수 초기화
					queue.offer(new int[] {front,nxtime}); //큐에 현재위치와 걸린시간 추가
				}
				else if(map[front][0] == nxtime) { //도착시간이 같을때
					map[front][1]++; //최단시간 방법 ++
					queue.offer(new int[] {front,nxtime});//큐에 현재위치와 걸린시간 추가
				}
			}
			if(back >=0 && back < 100000) {
				if(map[back][0] > nxtime | map[back][0] == 0) {
					map[back][0] = nxtime;
					map[back][1] = 1;
					queue.offer(new int[] {back,nxtime});
				}
				else if(map[back][0] == nxtime) {
					map[back][1]++;
					queue.offer(new int[] {back,nxtime});
				}
			}
			if(teleport > 0 && teleport <=100000) {
				if(map[teleport][0] > nxtime | map[teleport][0] == 0) {
					map[teleport][0] = nxtime;
					map[teleport][1] = 1;
					queue.offer(new int[] {teleport,nxtime});
				}
				else if(map[teleport][0] == nxtime) {
					map[teleport][1]++;
					queue.offer(new int[] {teleport,nxtime});
				}
			}
			
		}
	}

}
