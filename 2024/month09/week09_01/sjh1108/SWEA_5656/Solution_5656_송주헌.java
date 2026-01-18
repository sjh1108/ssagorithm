package week09_01.sjh1108.SWEA_5656;

import java.util.*;

class Solution {
	private static int N, W, H;
	private static int min;

	private static int[][] map;

	private static int[] dx = {-1, 1, 0, 0};
	private static int[] dy = {0, 0, -1, 1};

	// 파괴할 벽돌 찾기
	private static int findBrick(int ball){
		for(int h = 0; h < H; h++){
			if(map[h][ball] != 0){
				return h;
			}
		}
		return -1;
	}

	// 벽돌 파괴
	private static void destoryBrick(int h, int w){
		int size = -1;

		Queue<int[]> q = new ArrayDeque<>();
		q.add(new int[]{h, w});

		while(!q.isEmpty()){
			int[] cur = q.poll();

			int x = cur[0];
			int y = cur[1];
			size = map[x][y] - 1;
			map[x][y] = 0;

			int cx, cy;
			for(int d = 0; d < 4; d++){
				cx = x;
				cy = y;

				for(int l = 0; l < size; l++){
					int nx = cx + dx[d];
					int ny = cy + dy[d];

					if(!isIn(nx, ny)) break;
					cx = nx; cy = ny;

					if(map[nx][ny] == 0) continue;
					q.add(new int[]{nx, ny});
				}
			}
		}
	}
	private static boolean isIn(int x, int y){
		return (x >= 0 && x < H) && (y >= 0 && y < W);
	}

	// 파괴 후 중력 적용
	private static void gravity(){
		for(int w = 0; w < W; w++){
			int idx = H-1;
			for(int h = H-1; h >= 0; h--){
				if(map[h][w] != 0){
					map[idx--][w] = map[h][w];
				}
			}
			while(idx >= 0){
				map[idx--][w] = 0;
			}
		}
	}

	// 순열, dfs, 백트래킹
	private static void permutation(int depth, int ball){
		if(depth == N){
			int sum = 0;
			
			for(int i = 0; i < H; i++){
				for(int j = 0; j < W; j++){
					if(map[i][j] != 0) ++sum;
				}
			}
			
			min = Math.min(min, sum);
			return;
		}
		
		int[][] copyMap = new int[H][W];
		copy(map, copyMap);

		int height = findBrick(ball);
		if(height != -1) destoryBrick(height, ball);
		gravity();

		for(int ballIndex = 0; ballIndex < W; ballIndex++){
			permutation(depth+1, ballIndex);
		}
		
		copy(copyMap, map);
	}
	private static void copy(int[][] origin, int[][] copy){
		for(int i = 0; i < H; i++){
			copy[i] = Arrays.copyOf(origin[i], W);
		}
	}

	public static void main(String[] args) throws Exception
	{
		Scanner sc = new Scanner(System.in);

		int T = sc.nextInt();
		for(int t = 1; t <= T; t++){
			N = sc.nextInt();
			W = sc.nextInt();
			H = sc.nextInt();

			map = new int[H][W];
			for(int i = 0; i < H; i++){
				for(int j = 0; j < W; j++){
					map[i][j] = sc.nextInt();
				}
			}

			min = Integer.MAX_VALUE;

			for(int i = 0; i < W; i++){
				permutation(0, i);
			}

			System.out.println("#" + t + " " + min);
		}
		sc.close();
	}
}