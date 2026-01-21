package study;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class Solution_5656_김회인 {
	static int N;		// 총알 개수
	static int H;
	static int W;
	
	static int bullet[];		// 턴별로 총알이 어디로 날라갈지 저장 해주는 arr
	static int map[][];// = new int[][];
	static int map_origin[][];
	static int delta[][] = {{-1,0},{1,0},{0,-1},{0,1}};
	static int min=0;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;// = new StringTokenizer(br.readLine());
		int T = Integer.parseInt(br.readLine());
		
		
		for(int t=1; t<=T; t++)
		{
			
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());
			H = Integer.parseInt(st.nextToken());
			bullet = new int[N];
			map = new int[H][W];
			map_origin = new int[H][W];
			min = Integer.MAX_VALUE;
			
			for(int i=0; i<H; i++)
			{
				st = new StringTokenizer(br.readLine());
				for(int j=0; j<W; j++)
				{
					map_origin[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			depth(0);
	
			
			bw.write("#"+Integer.toString(t) + " " + Integer.toString(min)+"\n");
		}
		bw.flush();
		bw.close();
		br.close();
	}
	
	static void shoot()
	{
	
		for(int b=0; b<bullet.length; b++)	// 구슬 개수 만큼 쏴줄 포문
		{
			for(int i=0; i<H; i++)		// 날라가는 구슬의 높이?
			{
				
				if(map[i][bullet[b]] >= 1) 
				{
					// 만약 벽돌을 만났다면? 일단 구슬을 쏜 줄에서 가장 높은 벽돌을 만난다.
					boomb(i,bullet[b],map[i][bullet[b]]-1);
					break; 
				}
			}
			gravitation(); // 다음 구슬 쏘기 전에 중력에 따라 벽돌 정리
		}
	}
	
	static void boomb(int x, int y,int n)	// 벽돌 숫자 -1 해서 넣어 줄 것
	{
		map[x][y] = 0;		// 부서진 벽돌은 0으로
		
		for(int i=1; i<=n; i++)
		{
			for(int j=0; j<delta.length; j++)
			{
				int dx = x + delta[j][0]*i;
				int dy = y + delta[j][1]*i;
				
				if(!(dx>=0 && dx<H && dy >=0 && dy<W)) continue; // 배열 경계 체크
				
				if(map[dx][dy]>1) boomb(dx,dy,map[dx][dy]-1);	// 만약 부순 벽돌의 수가 2 이상이라면?
				
				map[dx][dy] = 0;		// 부서진 벽돌은 0으로
				
			}
		}
	}
	
	static void depth(int d)
	{
		if(N == d)
		{
			//System.out.println(Arrays.toString(bullet));
			for(int i=0; i<H; i++)
			{
				for(int j=0; j<W; j++)
				{
					map[i][j] = map_origin[i][j];
				}
			}
			shoot();	// 조합다 만들었으면 조합대로 구슬 쏴주기
			int result =0;
			for(int i=0; i<H; i++)
			{
				for(int j=0; j<W; j++)
				{
					if(map[i][j] > 0) result++;
				}
			}
			min = Math.min(min, result);
			return;
		}
		
		for(int i=0; i<W;i++)		// 레인 개수 w
		{
			bullet[d] = i;
			depth(d+1);
			
		}
		
	}
	
	static void gravitation()
	{
		for(int w=0; w<W; w++)
		{
			
			List<Integer> temp = new LinkedList<>();
			
			for(int i=H-1; i>=0; i--)
			{
				if(map[i][w] != 0) 
				{
					temp.add(map[i][w]);
					map[i][w] =0;
				}
			}
			int count=0;
			for(int i = H-1; i>H-1-temp.size(); i--)
			{
				map[i][w] = temp.get(count++);
			}
		}
	}
}
