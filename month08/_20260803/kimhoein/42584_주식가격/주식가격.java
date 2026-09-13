import java.io.*;
import java.util.*;

class Solution {
    
    class Node implements Comparable<Node>
    {
        int index;
        int price;
        
        Node(int index, int price)
        {
            this.index = index;
            this.price = price;
        }
        
        @Override 
        public int compareTo(Node other)
        {
            return Integer.compare(other.price, this.price);
        }
    }
    
    public int[] solution(int[] prices) {
        int[] answer = new int[prices.length];
        PriorityQueue<Node> pq = new PriorityQueue<>();

        int time;
        for(time = 0; time < prices.length; time++)
        {
            while(!pq.isEmpty())
            {
                if(prices[time] >= pq.peek().price) break;
                
                Node node = pq.poll();
                answer[node.index] = time - node.index;
            }
            
            pq.add(new Node(time,prices[time]));
        }
        
        while(!pq.isEmpty())
        {
            Node node = pq.poll();
            answer[node.index] = time - node.index-1;
        }
        
        return answer;
    }
}
