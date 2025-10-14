package algostudy.baek;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_1325_이용호 {
	static ArrayList<ArrayList<Integer>> graph;
	static int result[];
	static boolean[] visited;
	static int cnt;
	static int max;
	public static void main(String[] args) throws IOException {
		// N, M 입력받기
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		/// 인접 리스트 초기화
		graph = new ArrayList<>();
		for(int i = 0;i <= N;i++) {
			graph.add(new ArrayList<>());
		}
		max = 0;
		for(int i = 0;i < M;i++) {
			//신뢰관계 A, B입력받기
			st = new StringTokenizer(br.readLine());
			int A = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());
			graph.get(B).add(A); //"A가 B를 신뢰한다"
								 // B해킹시 -> A도 해킹 
								 // B -> A (B에 A를 저장)
		}
		result = new int[N+1]; //해킹 가능한 결과 저장하는곳
		for(int i = 1;i <= N;i++) {
			visited = new boolean[N+1]; //방문 초기화
			cnt = 0; // 해킹 가능한 컴퓨터 초기화
			dfs(i); 
			result[i]=cnt;
			if(cnt > max) {
				max = cnt;
			}
		}
		for(int i = 1;i <= N;i++) {
			if(result[i] == max) {
				System.out.print(i+" ");
			}
		}
		
	}
	public static void dfs(int x) {
		visited[x] = true;
		cnt++;
		for(int nx : graph.get(x)) { //dfs 탐색
			if(!visited[nx]) {
				dfs(nx);
			}
		}
		
	}

}