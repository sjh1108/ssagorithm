import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


class Solution {
    
    static int answer = 0;
    static int[][] dungeons;
    static boolean visited[];
    
    public int solution(int k, int[][] dungeons) {
        //int answer = -1;
        
        this.dungeons = dungeons;
        visited = new boolean[dungeons.length];
        
        dfs(k,0);
        
        return answer;
    }
    
    public void dfs(int Fatigue, int count)
    {
        answer = Math.max(answer,count);
        
        for(int i = 0; i<dungeons.length; i++)
        {
            System.out.println("i : " + i + " count : " + count);
            if(visited[i]) continue;
            if(Fatigue < dungeons[i][0]) continue; 
            
            visited[i] = true;
            
            
            dfs(Fatigue - dungeons[i][1] , count + 1);
            
            visited[i] = false;
        }
    }
}
