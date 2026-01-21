package study;
import java.io.*;
import java.util.*;

class Main_1854_김회인 {
	/*
	 * 내가 방문할 노드마다 pq 생성 방문때마다 pq에 이때까지 온 거리를 넣어줌
	 * 그리고 pq의 사이즈는 k만큼 까지
	 * 사이즈가 k만큼 찼는데 더 pq에 넣으려고 한다면?
	 * pq에거 가장 큰 거리를 가진 것고 비교해서 더 크다면 넣지 않고 작더면 넣는다
	 * 그렇게 해서 k번쨰 거리계산한다
	 * 그렇게 마지막에 k번째의 수를 모아서 출력한다
	 */
	static class Node implements Comparable<Node>
	{
		int to;
		int d;
		
		Node(int to, int d)
		{
			this.to = to;
			this.d = d;
		}

		@Override
		public int compareTo(Node o) {
			if(this.d == o.d) return this.to - o.to;
			return this.d - o.d;
		}
		
	}
	static int n;
	static int m;
	static int k;
	
	static List<List<Node>> list = new LinkedList<>();
	static PriorityQueue<Integer>[] pq;
	
    public static void main(String[] args) throws IOException {		// 할당 및 입출력 부분
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        
        pq = new PriorityQueue[n+1];
        
        for(int i=0; i<=n; i++)
        {
        	pq[i] = new PriorityQueue<>(Collections.reverseOrder());
        	list.add(new LinkedList<>());
        }
        
        
        for(int i=0; i<m; i++)
        {
        	st = new StringTokenizer(br.readLine());
        	int a = Integer.parseInt(st.nextToken());
        	int b = Integer.parseInt(st.nextToken());
        	int c = Integer.parseInt(st.nextToken());
        	
        	list.get(a).add(new Node(b,c));
        }
        
        move();
        
        for(int i=1; i<=n; i++)
        {
        	if(pq[i].size() == k) sb.append(pq[i].peek() + "\n");
        	else sb.append("-1\n");
        	
        }
        
        System.out.println(sb);
    }
    
    static public void move()
    {
    	Deque<Node> q = new ArrayDeque<>();
    	q.add(new Node(1,0));
    	pq[1].add(0);
    	
    	while(!q.isEmpty())
    	{
    		Node node = q.poll();
    		//System.out.println("2");
    		if(pq[node.to].size() >= k && pq[node.to].peek() < node.d) continue;	// 가지 치기 이미 기존의 값이 k개 모두 찾는데 그상의 거리를 가진 값이 나온다면 가치가 없으므로 굳이 돌리지 않는다.
    		//System.out.println("2");
    		
    		for(Node next : list.get(node.to)) {
    			//System.out.println("pq size : " + pq[next.to].size() + " " + k);
    			if(pq[next.to].size() < k || pq[next.to].peek() > node.d + next.d) {
    				//System.out.println("pq size : " + pq[next.to].size());
    				if(pq[next.to].size() == k)pq[next.to].poll();
    				pq[next.to].add(node.d + next.d);
    				q.add(new Node(next.to, node.d + next.d));
    			}
    				
    		}
    	}
    }
    
}