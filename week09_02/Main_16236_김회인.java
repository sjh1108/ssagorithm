package study;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_16236_김회인 {
	// 클래스에서 먹이 우선 순서 정해주기
	public static class Shark implements Comparable<Shark> {
        int x, y, distance;

        public Shark(int x, int y, int distance) {
            this.x = x;
            this.y = y;
            this.distance = distance;
        }

        @Override
        public int compareTo(Shark o) {
            if (this.distance != o.distance) 
        	{
            	return this.distance- o.distance;
        	}
            
            if (this.x == o.x) return this.y - o.y;
            
            return this.x - o.x;
            
        }
    }
	
	static int N;
    static int map[][];
    static PriorityQueue<Shark> pq = new PriorityQueue<>();
    static int delta[][] = {{-1,0},{1,0},{0,-1},{0,1}};		// 상하좌우

    
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;//= new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(br.readLine());
		map = new int[N][N];
		Queue<Shark> queue = new LinkedList();
		 
		for (int i = 0; i < N; i++) 
		{	
			st = new StringTokenizer(br.readLine());
		        
	        for (int j = 0; j < N; j++) 
	        {
	        	map[i][j] = Integer.parseInt(st.nextToken());
	        	
	            if (map[i][j] == 9) 
	            {
	            	map[i][j] = 0; // 상어 위치를 0으로 초기화
	                queue.add(new Shark(i, j, 0));
	            }
	            
	        }
		}
		
		// 초기 상어 크기 2로 BFS 시작
		bfs(queue, 2);
		
        int move = 0;
        int cnt = 0;
        int sharkSize = 2;
        
        // 먹을 수 있는 물고기가 남아있을 동안 반복
        while (!pq.isEmpty()) 
        {
            Shark now = pq.poll();
            map[now.x][now.y] = 0;
            
            if (++cnt == sharkSize)
            {
                cnt = 0;
                sharkSize++;
            }
            move += now.distance;
            
            // 새 BFS 시작 (먹은 위치에서 다시 출발) 이동 했으므로 우선 순위가 바뀐다
            queue = new LinkedList();
            queue.add(new Shark(now.x, now.y, 0));
            bfs(queue, sharkSize);
        }
	    
        bw.write(Integer.toString(move));
        bw.flush();
        bw.close();
        br.close();
        
	}
	
	// BFS 탐색 (먹을 수 있는 물고기 후보를 pq에 넣음)
	 private static void bfs(Queue<Shark> queue, int sharkSize) {
        pq = new PriorityQueue<>();
        boolean visited[][] = new boolean[N][N];
        while (!queue.isEmpty()) 
        {
            Shark now = queue.poll();

            for (int i = 0; i < 4; i++) 
            {
                int nx = now.x + delta[i][0];
                int ny = now.y + delta[i][1];

                if (!(nx >= 0 && nx < N  && ny >= 0 &&  ny < N)) continue;
                
                if(map[nx][ny] > sharkSize || visited[nx][ny]) continue;
                
                visited[nx][ny] = true;
                queue.add(new Shark(nx, ny, now.distance + 1));
             // 먹을 수 있는 물고기라면 후보에 추가
                if (map[nx][ny] != 0 && map[nx][ny] < sharkSize) 
                {
                    pq.add(new Shark(nx, ny, now.distance + 1));
                }

            }
        }
    }

}
