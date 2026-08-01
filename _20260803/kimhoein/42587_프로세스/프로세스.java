import java.io.*;
import java.util.*;


class Solution {
    
    
    class Node //implements Comparable<Node>
    {
        int index;
        int priority;
        
        Node(int index, int priority)
        {
            this.index = index;
            this.priority = priority;
        }
        
        // @Override
        // public int compareTo(Node other){
        //     return Integer.compare(this.index, other.index);
        // }
    }
    
    public int solution(int[] priorities, int location) {
        
        int answer = 0;
        int order = 0;
        PriorityQueue<Integer> pq = new PriorityQueue(Collections.reverseOrder());

        Queue<Node> dq = new ArrayDeque<>();
        
        for(int i =0 ; i< priorities.length; i++)
        {
            pq.add(priorities[i]);
            dq.add(new Node(i, priorities[i]));
        }
        
        
            
        while(!pq.isEmpty())
        {
            
            if(dq.peek().priority == pq.peek())
            {
                order++;
                if(location == dq.peek().index) break;
                
                dq.poll();
                pq.poll();
            }
            else
            {
                Node node = dq.poll();
                dq.add(node);
            }
            
        }
        
        answer = order;
        
        return answer;
        
    }
}
