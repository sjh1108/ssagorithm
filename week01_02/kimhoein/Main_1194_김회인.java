package study;
import java.io.*;
import java.util.*;

public class Main_1194_김회인 {
	static class Node{
		int x;
		int y;
		int count;
		int mask=0;
		
		Node(int x, int y, int count, int mask){
			this.x = x;
			this.y = y;
			this.count = count;
			this.mask = mask;
		}
	}
	static boolean visit[][][];
	static char map[][];
	static int delta[][] = {{1,0},{0,1},{-1,0},{0,-1}};
	static int row, col;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		row = Integer.parseInt(st.nextToken());
		col = Integer.parseInt(st.nextToken());
		
		int start_x=0,start_y = 0;
		
		visit = new boolean[64][row][col];
		map = new char[row][col];
		
		for(int i=0; i<row; i++)
		{
			String s = br.readLine();
			for(int j=0; j<col;j++) {
				map[i][j] = s.charAt(j);
				
				if(map[i][j] == '0') {
					start_x = i;
					start_y = j;
				}
			}
		}
		
		System.out.println(bfs(start_x,start_y));
		
	}
	
	static int bfs(int x, int y) {
		
		Queue<Node> que = new LinkedList<>();
		
		que.add(new Node(x,y,0,0));
		
		while(!que.isEmpty()) {
			Node node = que.poll();
			
			for(int i=0; i<4; i++) {
				int dx= node.x + delta[i][0];
				int dy= node.y + delta[i][1];
				
				//System.out.println("mask : " + node.mask + " dx : " + dx + " dy : " + dy);
				if(!(dx >= 0 && dx < row && dy >=0 && dy < col)) continue;
				if(visit[node.mask][dx][dy]) continue;
				visit[node.mask][dx][dy] = true;
				
				//System.out.println("mask : " + node.mask + " dx : " + dx + " dy : " + dy + "map : " + map[dx][dy]);
				
				if(map[dx][dy] == '#') continue;
				
				if(map[dx][dy] == '1') {
					return node.count + 1;
				}
				else if('a' <= map[dx][dy] && map[dx][dy] <= 'f') {
					//System.out.println("af : " + mask(map[dx][dy]));
					int mask = node.mask | (1 <<mask(map[dx][dy]));
					visit[node.mask][dx][dy] = true;
					//System.out.println("mask : " + mask);
					que.add(new Node(dx,dy,node.count + 1,mask));
					
				}else if('A' <= map[dx][dy] && map[dx][dy] <= 'F') {
					//System.out.println("AF진입 : " + (1 << mask(map[dx][dy])) + " " + node.mask);
					if((node.mask &(1 << mask(map[dx][dy]))) != 0){
						//System.out.println("통과");
						//
						que.add(new Node(dx,dy,node.count + 1,node.mask));
					}
				}else {
					//System.out.println("else 진입 : " + map[dx][dy]);
					que.add(new Node(dx,dy,node.count + 1,node.mask));
				}
				
			}
		}
		
		return -1;
	}
	
	static int mask(char c) {
		int num = 0;
		
		if(c >= 'A' && c <= 'F') {
		    c = (char)(c + ('a' - 'A')); 
		}
		
		switch(c) {
			case 'a':
				num = 0;
				break;
			case 'b':
				num = 1;
				break;
			case 'c':
				num = 2;
				break;
			case 'd':
				num = 3;
				break;
			case 'e':
				num = 4;
				break;
			case 'f':
				num = 5;
				break;
			
		}
		return num;
	}
}
