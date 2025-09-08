package month08.week02.kimhoein;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main_18809_김회인 {
	private static class Node{
		int x,y,color;
		
		Node(int x, int y)
		{
			this.x = x;
			this.y = y;
			
		}
		
		Node(int x, int y, int color)
		{
			this.x = x;
			this.y = y;
			this.color = color;
			
		}
	}
	
	static final int EMPTY = 0, GREEN = -1, RED =1, UNION = 999999;
	static int R,C,red,green, MAX = -1;
	static boolean[][] map;
	static int[][] visit;
	static int[] pick;
	static ArrayList<Node> target = new ArrayList<>();
	static Queue<Node> q = new LinkedList<>();
	
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		// 기본 정보 값들 입력
		init(stoi(st.nextToken()), stoi(st.nextToken()), stoi(st.nextToken()), stoi(st.nextToken()));
		
		for (int i = 0; i < R; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < C; j++) {
				switch (stoi(st.nextToken())) {
				case 2:
					target.add(new Node(i, j));		// 타겟(용액 심을곳 지정)
				case 1:
					map[i][j] = true;				// 맵
					break;
				}
			}
		}
		pick = new int[target.size()];				
		backTracking(green, red, 0);
		System.out.println(MAX);
	}

	private static void backTracking(int green, int red, int start) {	// 백 트래킹
		if (green == 0 && red == 0) {
			MAX = Math.max(MAX, bfs());								// 기존 값과 비교 큰거 남기기
			return;
		}
		if (green > 0) {											// red / 선택 x green/ 선택 x 4지 선다
			for (int i = start; i < target.size(); i++) {
				if (pick[i] == EMPTY) {
					pick[i] = GREEN;
					backTracking(green - 1, red, Math.max(i + 1, start));
					pick[i] = EMPTY;
				}
			}
		}
	
		if (red > 0) {
			for (int i = start; i < target.size(); i++) {
				if (pick[i] == EMPTY) {
					pick[i] = RED;
					backTracking(green, red - 1, Math.max(i + 1, start));
					pick[i] = EMPTY;
				}
			}
		}
	}

	private static void bfsInit() {
		for (int i = 0; i < R; i++)
			for (int j = 0; j < C; j++)
				visit[i][j] = EMPTY;
		for (int i = 0; i < target.size(); i++) {
			if (pick[i] != EMPTY) {
				q.add(new Node(target.get(i).x, target.get(i).y, pick[i]));
				visit[target.get(i).x][target.get(i).y] = pick[i];
			}
		}
	}

	private static int bfs() {
		int count = 0, step = 2;
		bfsInit();
		while (!q.isEmpty()) {
			int size = q.size();
			for (int s = 0; s < size; s++) {
				Node n = q.poll();
				if (visit[n.x][n.y] == UNION) continue;
				for (int i = 0; i < 4; i++) {
					int nx = n.x + dx[i];
					int ny = n.y + dy[i];
	
					if (nx >= 0 && ny >= 0 && nx < R && ny < C && map[nx][ny]) {
						if (visit[nx][ny] == EMPTY) {
							visit[nx][ny] = n.color * step;
							q.add(new Node(nx, ny, n.color));
						} else if (visit[nx][ny] != UNION) {
							if (visit[nx][ny] * n.color < 0 && visit[nx][ny] + n.color * step == 0) {
								count++;
								visit[nx][ny] = UNION;
							}
						}
					}
				}
			}
			step++;
		}
		return count;
	}

	private static void init(int N, int M, int g, int r) {
		R = N;
		C = M;
		green = g;
		red = r;
		map = new boolean[R][C];
		visit = new int[R][C];
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}
	
	 


