package study;
import java.io.*;
import java.util.*;


public class Main_21939_김회인 {
    /*
     * 
     * 문제 리스트 pq에입력
     * 난이도 순으로 정렬
     * 난이도 같다면 문제 번호 큰순으로 정렬
     * 
     * add L p 문제 추가
     * 
     * solved p 문제 해결 -> boolean 하나 만들어서 true로 처리 해주고 이후에 true라면 pass 
     * false 인걸 출력 해줄 것
     * 
     * pq는 난이도 순 정렬 하나
     * pq는 난이도 반대 순 정렬 하나
     * 
     * 
     * 
	*/
	
	static class Node implements Comparable<Node>
	{
		int difficult;
		int num;
		
		Node(int difficult, int num)
		{
			this.difficult = difficult;
			this.num = num;
		}

		@Override
		public int compareTo(Node o) {
			
			if(this.difficult == o.difficult)
			{
				return this.num - o.num;
			}
			return this.difficult - o.difficult;
		}
		
		
	}
	
	
    static int[] solved;	
    
    public static void main(String[] args) throws InterruptedException, IOException {
       BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
       BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
       StringTokenizer st; // = new StringTokenizer(br.readLine());
       
       int n = Integer.parseInt(br.readLine());
       
       PriorityQueue<Node> pq_min = new PriorityQueue<>();
       PriorityQueue<Node> pq_max = new PriorityQueue<>(Collections.reverseOrder());
       
       solved = new int[100001];
       
       for(int i=0; i<n; i++)
       {
    	   st = new StringTokenizer(br.readLine());
    	   int num = Integer.parseInt(st.nextToken());
    	   int difficult = Integer.parseInt(st.nextToken());
    	   
    	   Node node = new Node(difficult, num);
    	   solved[num] = difficult; 
    	   pq_min.add(node);
    	   pq_max.add(node);
       }
       
       int m = Integer.parseInt(br.readLine());
       
       for(int i=0; i<m; i++)
       {
    	   st = new StringTokenizer(br.readLine());
    	   String s = st.nextToken();
    	   
    	   int num = Integer.parseInt(st.nextToken());
    	   if(s.equals("add"))
    	   {
        	   int difficult = Integer.parseInt(st.nextToken());
        	   
        	   Node node = new Node(difficult, num);
        	   solved[num] = difficult;
        	   pq_min.add(node);
        	   pq_max.add(node);
    	   }
    	   else if(s.equals("recommend") && num == 1)
    	   {
    		   bw.write(Integer.toString(poll(pq_max)) + "\n");
    	   }
    	   else if(s.equals("recommend") && num == -1)
    	   {
    		   bw.write(Integer.toString(poll(pq_min)) + "\n");
    	   }
    	   else if(s.equals("solved"))
    	   {
    		   
    		   solved[num] = 0;
    	   }
       }
       
       
       
       bw.flush();
       bw.close();

    }
    
    static int poll(PriorityQueue<Node> pq)
    {
    	while(!pq.isEmpty())
    	{
    		Node node = pq.peek();
    		
    		if(solved[node.num] == node.difficult)
    		{
    			return node.num;
    		}
    		
    		pq.poll();
    	}
    	
		return 0;
    	
    }
    
}