import java.util.*;
import java.io.*;

public class Main
{
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine().trim());
		int[][] pls = new int[N+1][N+1];
		int[][] sub = new int[N+1][N+1];

		StringTokenizer st;
		for(int i = 0; i < N; i++){
			st = new StringTokenizer(br.readLine());

			for(int j = 0; j < N; j++){
				int input = Integer.parseInt(st.nextToken());
				
				pls[i+1][j+1] = pls[i][j] + input;
				sub[i+1][j] = sub[i][j+1] + input;
			}
		}

		int ans = 0;

		for(int size = 2; size <= N; size++){
			for(int x = 0; x < N+1 - size; x++){
				for(int y = 0; y < N+1 - size; y++){
					int a = pls[x + size][y + size] - pls[x][y];
					int b = sub[x + size][y] - sub[x][y + size];

					ans = Math.max(ans, a - b);
				}
			}
		}

		System.out.println(ans);
	}
}
