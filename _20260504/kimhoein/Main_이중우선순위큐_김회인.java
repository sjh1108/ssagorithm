package study;
import java.io.*;
import java.util.*;

public class Main_이중우선순위큐_김회인 {

	
	static class Node implements Comparable<Node>
	{
		int num;
		boolean erase;
		
		Node(int num)
		{
			this.num = num;
		}

		@Override
		public int compareTo(Node o) {
			return Long.compare(this.num, o.num);
		}
		

	}
    
	static PriorityQueue<Node> Max_pq = new PriorityQueue<>(Collections.reverseOrder());
	static PriorityQueue<Node> Min_pq = new PriorityQueue<>();
	static String[] operation = {"I -45", "I 653", "D 1", "I -642", "I 45", "I 97", "D 1", "D -1", "I 333"};
	public static void main(String[] args) throws InterruptedException, IOException {
		 System.out.println(Arrays.toString(solution(operation)));
	}
	
	public static int[] solution(String[] operations) {
        int[] answer = new int[2];
        
        //StringTokenizer st = new StringTokenizer(operatio);
        
        for(int i=0; i<operations.length; i++)
        {
        	String[] parts = operations[i].split(" ");
        	
        	if(parts[0].equals("I"))
        	{
        		Node n = new Node(Integer.parseInt(parts[1]));
        		
        		Max_pq.add(n);
        		Min_pq.add(n);
        	}
        	else if(parts[1].equals("1"))
        	{
        		while(!Max_pq.isEmpty())
        		{
        			if(Max_pq.peek().erase) Max_pq.poll();
        			else break;
        		}
        		
        		if(!Max_pq.isEmpty()) 
    			{
        			Node n = Max_pq.poll();
        			n.erase = true;
        		}
        	}
        	else
        	{
        		while(!Min_pq.isEmpty())
        		{
        			if(Min_pq.peek().erase) Min_pq.poll();
        			else break;
        		}
        		
        		if(!Min_pq.isEmpty()) 
    			{
        			Node n = Min_pq.poll();
        			n.erase = true;
        		}
        	}
        }
        
		while(!Max_pq.isEmpty())
		{
			if(Max_pq.peek().erase) Max_pq.poll();
			else break;
		}
		
		while(!Min_pq.isEmpty())
		{
			if(Min_pq.peek().erase) Min_pq.poll();
			else break;
		}
		
		if(Max_pq.isEmpty())
		{
			return new int[] {0,0};
		}
		
		answer[0] = Max_pq.poll().num;
		answer[1] = Min_pq.poll().num;
		
        return answer;
        
        
    }
	
	
}