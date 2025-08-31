

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_1247_장상민 {
	static class Point{
		int x,y;
		Point(int x, int y){
			this.x = x;
			this.y = y;
		}
		
	}
	static final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static Point company, home;
	static Point[] customers;
	static int T, N, min;
	static boolean[] visited;
	static StringBuilder sb = new StringBuilder();
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		T = Integer.parseInt(input.readLine());
		
		for (int i = 0; i < T; i++) {
		N = Integer.parseInt(input.readLine());
		
		tokens = new StringTokenizer(input.readLine());
		//회사
		int c1 = Integer.parseInt(tokens.nextToken());
		int c2 = Integer.parseInt(tokens.nextToken());
		company = new Point(c1, c2);
		
		//집
		int h1 = Integer.parseInt(tokens.nextToken());
		int h2 = Integer.parseInt(tokens.nextToken());
		home = new Point(h1, h2);
		
		//고객
		customers = new Point[N];
		for (int j = 0; j < N; j++) {	
			int x = Integer.parseInt(tokens.nextToken());
			int y = Integer.parseInt(tokens.nextToken());
			customers[j] = new Point(x, y);
		}
		
		visited = new boolean[N];
        min = Integer.MAX_VALUE;

        dfs(0, 0, company.x, company.y);

        sb.append("#").append(T).append(" ").append(min).append("\n");
        
		}
		System.out.println(sb.toString());
	}

	static void dfs(int depth, int dist, int x, int y) {
	    if (dist >= min) return; // 가지치기
	
	    if (depth == N) {
	        dist += Math.abs(x - home.x) + Math.abs(y - home.y);
	        min = Math.min(min, dist);
	        return;
	    }
	
	    for (int i = 0; i < N; i++) {
	        if (!visited[i]) {
	            visited[i] = true;
	            int d = Math.abs(x - customers[i].x) + Math.abs(y - customers[i].y);
	            dfs(depth + 1, dist + d, customers[i].x, customers[i].y);
	            visited[i] = false;
	        }
	    }
	}
}