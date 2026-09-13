import java.io.*;
import java.util.*;

class Solution {
    
    class Node
    {
        int time;
        int index;
        
        Node(int time, int index)
        {
            this.time = time;
            this.index = index;
        }
        
    }
    
    public int solution(int bridge_length, int weight, int[] truck_weights) {
        int answer = 0;
        
        int cur_truck_count = bridge_length;    // 다리위 트럭 개수
        int cur_weight = weight;             // 현재 여유 무게
        int cur_index = 0;              // 현재 들어갈 트럭 인덱스
        
        Queue<Node> dq = new ArrayDeque<>();
        
        
        for(answer =1; answer <=100000001; answer++)
        {
            
            if(dq.isEmpty() && cur_index >= truck_weights.length) break;
            
            if(cur_index < truck_weights.length && cur_weight >= truck_weights[cur_index] && cur_truck_count > 0)
            {
               //System.out.println("aaa");
                cur_weight -= truck_weights[cur_index]; 
                cur_truck_count--;
                dq.add(new Node(answer, cur_index));
                cur_index++;
            }
            
            
            while(!dq.isEmpty() && (answer - dq.peek().time+1) >= bridge_length)
            {
                Node node = dq.poll();
                cur_truck_count++;
                cur_weight += truck_weights[node.index];
            }
            
        }
        
        
        return answer;
    }
}
