package study;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;


public class Main_17135_김회인 {
	static class Node
	{
		int x;
		int y;
		int t;
		
		Node(int x, int y, int t)
		{
			this.x = x;
			this.y = y;
			this.t = t;
			
		}
		
		Node(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
	}
	static int D;
	static int archer[];
	static boolean visit_castle[];
	static boolean attack[][];
	static int N,M;
	static int delta[][] = {{0,-1},{-1,0},{0,1}};
	static int map[][];
	static int temp_map[][];
	static int count=0;
	static int max_count=0;
	static List<Node> attack_poll = new ArrayList<>();
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());			// 성벽의 길이 -> 궁수가 존재 할 수 있는 곳
		D = Integer.parseInt(st.nextToken());
		
		archer = new int[3];		// 궁수가 존재 할 수 있는 위치
		visit_castle = new boolean[M];		// 궁수가 존재하는 위치 체크
		map = new int[N+1][M];				// 원래 맵
		temp_map = new int[N+1][M];			// 복사 해갈 맵
		
		for(int i=0; i<N;i++)		// 맵 입력
		{
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<M;j++)
			{
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		Depth(0,0);
		
		bw.write(Integer.toString(max_count));
		bw.flush();
		bw.close();
		br.close();
	}
	
	static void Depth(int d, int index)		// 궁수의 위치를 뽑아주는 함수
	{
		if(d == 3)
		{
			//System.out.println(Arrays.toString(archer));		// 테스트용 차후에 공격 루틴 실행 하고 최대값 받아 올 것 bfs 실행
			count=0;
			round();
			max_count = Math.max(max_count, count);
			return;
		}
		
		for(int i=index; i<M; i++)		// 중복 없이 순서 상관 x 모든 경우의 수 나열
		{
			if(visit_castle[i]) continue;
			archer[d] = i;
			visit_castle[i] = true;
			Depth(d+1,i+1);
			visit_castle[i] = false;
		}
	}
	
	static void round()
	{
		map_copy();
		
		for(int i=N; i>=0; i--)// 한칸 씩 전진 하는 함수
		{
			
			attack_count(i);	// 화살병들이 화살을 쏘는 구간
			if(i==0) continue;
			
			for(int j=0; j<M;j++)
			{
				temp_map[i-1][j] = 0; //지나 가면서 삭제
			}
		}
		max_count = Math.max(max_count, count);
	}
	
	static void attack_count(int row)
	{
		attack = new boolean[N][M];
		for(int i=0; i<archer.length; i++)
		{
			attak_bfs(row,archer[i]);
		}
		
		
		
		for(Node n : attack_poll)			// 공격 받았던 부분들은 다 0으로 바꿔준다.
		{	
			temp_map[n.x][n.y] = 0;		
		}
		attack_poll.clear();
	}
	
	static void attak_bfs(int x,int y)		// x 값은 어차피 고정 N-1 y 값()만 가져와서 넣어준다.
	{
		Queue<Node> q= new LinkedList<>();
		q.add(new Node(x,y,0));
		
		while(!q.isEmpty())
		{
			Node node = q.poll();
			
			                                                                                                                                                                                     
			for(int i=0; i<delta.length; i++)
			{
				int dx = (int) node.x + delta[i][0];
				int dy = (int) node.y + delta[i][1];
				
				if(!(dx>=0 && dx <N && dy>=0 && dy<M)) continue;		//배열 범위 체크
				if(attack_check(dx,dy)) return;		// 공격 가능한지 체크
				if(node.t >= D-1) continue;	// 공격 범위 밖이면  추가 ㄴㄴ
				
				q.add(new Node(dx,dy,node.t+1));	 // 공격할 병사가 없다면 추가 탐색
			}
		}
	}
	
	static boolean attack_check(int x,int y) // 공격 가능한지 체크 하고 공격 가능하면 
	{
		
		if(temp_map[x][y]==1)						//
		{
			if(!attack[x][y])  
			{
				count++;
				attack_poll.add(new Node(x,y));
				attack[x][y]=true;
			}
			
			return true;		// true 
		}
		return false;
	}
	
	static void map_copy()		// 맵 복사 함수
	{
		for(int i=0; i<map.length;i++)
		{
			for(int j=0; j<map[0].length;j++)
			{
				temp_map[i][j] = map[i][j];
			}
		}
	}
	
}

