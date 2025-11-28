package algostudy.baek.week11_4;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_16928_이용호 {

	/*
	 * 10 * 10
	 * 주사위 굴린 결과 100초과시 이동 불가
	 * 사다리칸 -> 위로 올라감(번호 커짐)
	 * 뱀 -> 내려감(번호 작아짐)
	 * 100번칸에 도착하기 위해 주사위 굴려야 하는 횟수 최솟값
	 */
		public static int[] map, minMap;
		public static void main(String[] args) throws IOException {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			StringTokenizer st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken()); // 사다리 수
			int M = Integer.parseInt(st.nextToken()); // 뱀 수
			
			map = new int[101]; // 1 based 사다리, 뱀 도착위치 저장용
			minMap = new int[101]; // 최소 주사위 횟수 저장용
			Arrays.fill(minMap, 100);
			for(int i = 0; i < N + M; i++) {
				st = new StringTokenizer(br.readLine());
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());
				map[from] = to;
			}
			
			bfs(1);
		}
		public static void bfs(int start) {
			Queue<int[]> q = new LinkedList<>();
			q.add(new int[] {start,0});
			
			while(!q.isEmpty()) {
				int[] now = q.poll();
				// 이미 더 적은 횟수로 도착 가능한 곳 이면 패스
				if(minMap[now[0]] > now[1]) {
					minMap[now[0]] = now[1];
				}
				else {
					continue;
				}
				if(now[0] == 100) {
					System.out.println(now[1]);
					break;
				}
				// 한번에 이동할수 있는 거리(주사위 굴렸을때 이동할수 있는 거리)
				for(int i = 1; i <= 6; i++) {
					// 주사위 굴리고 다음 위치가 100 초과하면 패스 
					int next = now[0] + i;
					if(next > 100) continue;
					// 사다리나 뱀이면
					if(map[next] != 0) {
						// 사다리 뱀 이동위치, 횟수++
						q.add(new int[] {map[next], now[1]+1});
					}
					else {
						// 다음위치, 횟수 ++
						q.add(new int[] {next,now[1]+1});
					}
					
				}
			}
			
		}

}
