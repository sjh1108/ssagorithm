import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_1263_손홍민 {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;
	
	// 알고리즘 분류 : 최단 경로, 플로이드 워셜
	// 풀이 순서 : 
	// 1. 그래프 초기 상태 입력
	// 2. 플로이드 워셜로 각 정점별 최소 이동 횟수 등록 및 갱신
	// 3. 각 정점별 다른 정점으로 이동하는 거리의 합을 구하고 그 중 최소값을 반환
	
	// 정점의 개수
	static int v;
	// 다른 정점과의 연결 여부를 저장할 인접 행렬
	static int[][] graph;
	
	public static void main(String[] args) throws Exception {
		int tc = Integer.parseInt(br.readLine());
		for(int i=1; i<=tc; i++) {
			init();
			floydWarshall();
			
			sb.append("#" + i + " " + check() + "\n");
		}
		System.out.println(sb);
	}
	
	static void init() throws Exception {
		st = new StringTokenizer(br.readLine());
		
		v = Integer.parseInt(st.nextToken());
		graph = new int[v][v];
		
		// 인접 행렬 등록
		for(int i=0; i<v; i++) {
			for(int j=0; j<v; j++) {
				int x = Integer.parseInt(st.nextToken());
				if(i != j && x == 0) x = v+1;
				graph[i][j] = x;
			}
 		}
	}
	
	// 플로이드 워셜을 이용한 최소 이동 횟수 갱신
	static void floydWarshall() {
		for(int i=0; i<v; i++) {
			for(int j=0; j<v; j++) {
				if(i==j || graph[j][i] > v) continue;
				for(int k=0; k<v; k++) {
					if(k==i || k==j) continue;
					graph[j][k] = Math.min(graph[j][k], graph[j][i] + graph[i][k]);
				}
			}
		}
	}
	
	// 정점 x가 다른 모든 정점으로 이동하기 위해 최소 몇 번 이동해야 하는지 확인
	// 정점 본인은 항상 0으로 기록된 상태
	// 각 행의 값을 모두 더하면 각 정점 x와 다른 모든 정점 사이의 거리가 됨
	static int check( ) {
		int min = Integer.MAX_VALUE;
		for(int i=0; i<v; i++) {
			int cnt = 0;
			for(int j=0; j<v; j++) {
				cnt += graph[i][j];
			}
			
			min = Math.min(min, cnt);
		}
		
		return min;
	}

}
