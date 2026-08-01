import java.io.*;
import java.util.*;

class Solution {
    
    class Node
    {
        String node;
        int count;
        
        Node(String node, int count)
        {
            this.node = node;
            this.count = count;
        }
        
    }
    static String target;
    static String words[];
    
    static boolean visit[];
    
    public int solution(String begin, String target, String[] words) {
        int answer = 0;
        this.words = words;
        this.target = target;
        visit = new boolean[words.length];
        
        int count = bfs(begin);
        
        answer = count;
        
        return answer;
    }
    
    public int bfs(String begin)
    {
        Queue<Node> q = new ArrayDeque<>();
        q.add(new Node(begin, 0));
        
        while(!q.isEmpty())
        {
            Node curNode = q.poll();
            
            for(int i=0; i<words.length; i++)
            {
                if(visit[i]) continue;
                if(!check(curNode.node,words[i])) continue;
                
                visit[i] = true;
                
                if(target.equals(words[i])) return curNode.count+1;

                q.add(new Node(words[i],curNode.count + 1));
                
            }
        }
        
        return 0;
       
        
    }
    
    public boolean check(String s1, String s2)
    {
        int count=0;
        for(int i=0; i<s1.length(); i++)
        {
            if(s1.charAt(i) != s2.charAt(i))
            {
                count++;
            }
        }
        
        if(count == 1) return true;
        
        return false;
    }
    
}
