package study;
import java.io.*;
import java.util.*;


public class Main_7662_김회인 {
    /*
     * 두개의 pq를 만든다 하나는 최대 값 pq 하나는 최소 값 pq pq 의 값 비교는 node를 만들고 하나는 id 하나는 value value에 따라서 정렬을 하며
     * id 값을 boolean으로 체크하는 배열을 하나 만들어서 만약 제거시 true를 체크 해주고 뺄 때 true인지 아닌지 체크 하여
     * 이미 뺀것을 두번 빼는 불상사를 막는다.
     */
	
	static class Node implements Comparable<Node>
	{
		long value;
		boolean erase = false;
		
		Node(long value)
		{
			this.value = value;
		}

		@Override
		public int compareTo(Node o) {
			return Long.compare(this.value, o.value);
		}
		

	}
    		
    
    public static void main(String[] args) throws InterruptedException, IOException {
       BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
       BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
       StringTokenizer st; // = new StringTokenizer(br.readLine());
       
       int t = Integer.parseInt(br.readLine());
       
       for(int T = 0; T<t; T++)
       {
    	   
    	   int n = Integer.parseInt(br.readLine());
    	   
    	   PriorityQueue<Node> pq_min = new PriorityQueue<>();
    	   PriorityQueue<Node> pq_max = new PriorityQueue<>(Collections.reverseOrder());
    	   
    	   for(int i=0; i<n; i++)
    	   {
    		   st = new StringTokenizer(br.readLine());
    		   
    		   String s = st.nextToken();
    		   long num = Long.parseLong(st.nextToken());
    		   
    		   if(s.equals("I"))
    		   {
    			   Node node = new Node(num);
    			   pq_min.add(node);
    			   pq_max.add(node);
    		   }
    		   
    		   if(s.equals("D") && num == 1) pq_poll(pq_max);
    		   else if(s.equals("D") && num == -1) pq_poll(pq_min);
    		   
    		   
    	   }
    	   
    	   //System.out.println(pq_max.size() + " " + pq_min.size());
    	   
    	   pq_peek(pq_max);
    	   pq_peek(pq_min);
    	   
    	   
    	   //System.out.println(pq_max.size() + " " + pq_min.size());
    	   
    	   if(pq_min.isEmpty()) bw.write("EMPTY\n");
    	   else bw.write(Long.toString(pq_max.peek().value) + " " + Long.toString(pq_min.peek().value)+"\n");
    	   
       }
       
       bw.flush();
       bw.close();

    }
    
    static void pq_poll(PriorityQueue<Node> pq)
    {
    	 while(!pq.isEmpty())
  	   {
  		   Node node = pq.poll();
  		   if(node.erase == false) {
  			   node.erase = true;
  			   break;
  		   }
  	   }
    }
    
    static void pq_peek(PriorityQueue<Node> pq)
    {
    	 while(!pq.isEmpty())
  	   {
  		   Node node = pq.peek();
  		   if(node.erase == false) {
  			   //node.erase = true;
  			   
  			   break;
  		   }
  		   
  		   pq.poll();
  	   }
    }
}