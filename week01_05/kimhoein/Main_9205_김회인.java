package study;
import java.io.*;
import java.util.*;

public class Main_9205_김회인{
	/*
	맥주 20개? 50m
	-> 1000의 거리를 갈 수 있다
	여기서 편의점에서 마시고 출발 가능 함을 알 수 있다면
	만약 편의점이라면 1050m 이동이 가능 하다? 이정도가 약간의 함정
	
	이동 거리내 라면 이동 가능한 간선으로 미리 추가
	add로 서로 추가
	
	arraylist 에서 각각 추가해서 
	닿는지 닿지 않는지 체크 해서 간선 추가
	
	간선 추가뒤 마지막에 간선 bfs 돌려서
	닿으면 종료
	끝까지 닿지 않으면 sad 출력

	 */
	static class Node
	{
		int id;
		int x;
		int y;
		
		Node(int x, int y, int id){
			this.x = x;
			this.y = y;
			this.id = id;
		}
	}
	
	public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    	StringTokenizer st;// = new StringTokenizer(br.readLine());
    	
    	int T = Integer.parseInt(br.readLine());
    	
    	for(int t=0; t<T; t++) {
    		
    		int N = Integer.parseInt(br.readLine());
    		
    		st = new StringTokenizer(br.readLine());
    		int a = Integer.parseInt(st.nextToken());
    		int b = Integer.parseInt(st.nextToken());
    		
    		Node node_list[] = new Node[N+2];
    		
    		Node home = new Node(a,b,0);
    		node_list[0] = home;
    		
    		for(int i=1; i<=N; i++) {
    			
    			st = new StringTokenizer(br.readLine());
    			a = Integer.parseInt(st.nextToken());
    			b = Integer.parseInt(st.nextToken());
    			
    			node_list[i] = new Node(a,b,i);
    			
    		}
    		
    		st = new StringTokenizer(br.readLine());
    		a = Integer.parseInt(st.nextToken());
    		b = Integer.parseInt(st.nextToken());
    		
    		Node festival = new Node(a,b,N+1);
    		node_list[N+1] = festival;
    		
    		ArrayList<ArrayList<Node>> arrayList = new ArrayList<>();
    		
    		for(int i=0; i<N+2; i++) {
    			arrayList.add(new ArrayList<Node>());
    		}
    		
    		for(int i=0; i<N+2; i++) {
    			for(int j=i+1; j<N+2; j++) {
    				int distance = Math.abs(node_list[i].x - node_list[j].x) + Math.abs(node_list[i].y - node_list[j].y);
    				
    				if(distance <= 1000) {
    					//System.out.println("ad");
    					arrayList.get(j).add(node_list[i]);
    					arrayList.get(i).add(node_list[j]);
    				}
    			}
    		}
    		
    		
    		Queue<Node> que = new LinkedList<>();
    		boolean visit[] = new boolean[N+2];
    		que.add(home);
    		
    		visit[0] = true;
    		
    		while(!que.isEmpty()) {
    			Node node = que.poll();
    			//System.out.println("node.id : " + node.id);
    			if(node.id == festival.id) break;
    			
    			
    			for(Node next : arrayList.get(node.id)) {
    				//System.out.println("for in node.id : " + next.id);
    				
    				if(visit[next.id]) continue;
    				visit[next.id] = true;
    				
    				que.add(next);
    			}
    		}
    		
    		if(visit[festival.id]) bw.write("happy\n");
    		else bw.write("sad\n");
    	}

    	//bw.write(Integer.toString(total));
    	bw.flush();
    	bw.close();
    	br.close();
    }
	

}
