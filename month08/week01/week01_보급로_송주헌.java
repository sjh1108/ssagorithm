package month08.week01;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

class week01_보급로_송주헌
{
	private static final int MAX_TIME = 1000001;
	private static int N;

	private static int[][] map;
	private static int[][] cost;

	private static int[] dx = {0, 1, 0, -1};
	private static int[] dy = {1, 0, -1, 0};

	static class Node{
		private int x;
		private int y;

		public Node(int x, int y){
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}
	}
	public static void main(String args[]) throws Exception
	{
		// System.setIn(new FileInputStream("res/input.txt"));

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T;
		T=Integer.parseInt(br.readLine());
		for(int test_case = 1; test_case <= T; test_case++)
		{
			sb.append("#" + test_case + " ");
			N = Integer.parseInt(br.readLine());

			map = new int[N][N];
			cost = new int[N][N];
			
			for(int i = 0; i < N; i++){
				String str = br.readLine();
				for(int j = 0; j < N; j++){
					map[i][j] = str.charAt(j) - '0';
				}

				Arrays.fill(cost[i], MAX_TIME);
			}
			cost[0][0] = 0;
			sb.append(BFS() + "\n");
		}
		System.out.println(sb);
	}

	private static int BFS(){
		Queue<Node> q = new LinkedList<>();
		q.add(new Node(0, 0));

		while(!q.isEmpty()){
			Node cur = q.poll();

			int x = cur.getX(), y = cur.getY();
			for(int d = 0; d < 4; d++){
				int nx = x + dx[d];
				int ny = y + dy[d];

				if(nx < 0 | nx > N-1 | ny < 0 | ny > N-1) continue;

				int nCost = cost[x][y] + map[nx][ny];
				if(cost[nx][ny] > nCost){
					q.add(new Node(nx, ny));

					cost[nx][ny] = nCost;
				}
			}
		}

		return cost[N-1][N-1];
	}
}