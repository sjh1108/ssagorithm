class Solution {
    
    static boolean visit[];
    static int ncount;
    static int[][] computers;
    
    public int solution(int n, int[][] computers) {
        int answer = 0;
        ncount=0;
        visit =  new boolean[computers.length];
        this.computers = computers;
        
        for(int i=0; i<computers.length; i++)
        {
            if(visit[i]) continue;
            
            visit[i] = true;
            ncount++;
            dfs(i);
            
        }
        answer = ncount;
        
        return answer;
    }
    
    public void dfs(int num)
    {
        for(int i=0; i<computers.length; i++)
        {
            if(i == num) continue;
            if(computers[num][i] == 0) continue;
            if(visit[i]) continue;
            
            visit[i] = true;
            dfs(i);
            
        }
    }
    
    
}
