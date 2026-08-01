import java.io.*;
import java.util.*;

class Solution {
    
    static String[] words = {"A","E","I","O","U"};
    static StringBuilder sb = new StringBuilder();
    static int count=0;
    static String word;
    
    public int solution(String word) {
        int answer = 0;
        this.word = word;
        
        //sb = new StringBuilder();
        
        dfs(0);
        
        answer = count;
        
        return answer;
    }
    
    public boolean dfs(int depth)
    {
        if(depth == 5) return false;
        
        for(int i=0; i<5; i++)
        {
            sb.append(words[i]);
            
            count++;
            //System.out.println(sb + " " + sb.toString().equals(word));
            
            if (sb.toString().equals(word)) return true;
            
            if(dfs(depth + 1)) return true;
            
            sb.deleteCharAt(sb.length() - 1);
 
        }
        
        return false;
    }
}
