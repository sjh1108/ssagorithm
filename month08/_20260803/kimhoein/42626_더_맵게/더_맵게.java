import java.io.*;
import java.util.*;

class Solution {
    public int solution(int[] scoville, int K) {
        int answer = 0;
        
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int count = 0;
        for(int i=0; i<scoville.length; i++)
        {
            pq.add(scoville[i]);
        }
        
        while(pq.peek() < K)
        {
            if(pq.size() < 2) return -1;
            
            int a = pq.poll();
            int b = pq.poll()*2;
            
            pq.add(a+b);
            count++;
        }
        
        answer = count;
        return answer;
    }
}
