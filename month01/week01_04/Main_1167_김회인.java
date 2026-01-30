package study;

import java.io.*;
import java.util.*;

public class Main_1167_트리의지름 {
	static class Node{
		int to;
		int distance;
		
		Node(int to, int distance){
			this.to = to;
			this.distance = distance;
		}
	}
	
	static int N;
	static ArrayList<ArrayList<Node>> arrayList;
	public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    	StringTokenizer st;// = new StringTokenizer(br.readLine());
    	
    	N = Integer.parseInt(br.readLine());
    	
    	arrayList = new ArrayList<>();
    	
    	for(int i=0; i<=N; i++) {
    		arrayList.add(new ArrayList<Node>()); 
    	}
    	
    	for(int i=1; i<=N; i++) {
    		st = new StringTokenizer(br.readLine());
    		int num = Integer.parseInt(st.nextToken());
    		
    		while(true) {
    			
    			int a = Integer.parseInt(st.nextToken());
    			if(a == -1) break;
    			int b = Integer.parseInt(st.nextToken());
    			
    			arrayList.get(num).add(new Node(a,b));
    		}
    	}
    	
    	// 임의의 점 하나 뽑아서 가장 먼 점을 찾는다
    	// 그 먼점에서 가장 먼점을 찾고 그 거리가 답이된다
    	// 임의의 점 -> 가장 먼점 -> 가장 먼점의 가장 먼 거리 찾기
    	
    	Node node_a = dfs(1);
    	Node node_b = dfs(node_a.to);
    	
    	bw.write(Integer.toString(node_b.distance));
    	bw.flush();
    	bw.close();
    	br.close();
    }
	
	static Node dfs(int n)
	{
		boolean visit[] = new boolean[N+1];
		Queue<Node> que = new LinkedList<>();
		que.add(new Node(n,0));
		//System.out.println("visit[] : " + visit.length);
		visit[n] = true;
		
		Node max= new Node(n,0);
		
		while(!que.isEmpty()) {
			Node node = que.poll();
			//System.out.println("node : " + node.to);
			
			if(max.distance < node.distance) max = new Node(node.to, node.distance);
			
			
			for(Node next : arrayList.get(node.to))
			{
				//System.out.println("node : " + node.to + " next.to : " + next.to + " " + visit[next.to]);
				if(visit[next.to]) continue;
				visit[next.to] = true;
				//System.out.println("aa");
				que.add(new Node(next.to,node.distance + next.distance));
			}			
		}
		
		return max;
	}
}
