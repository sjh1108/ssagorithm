package month08.week04.kimhoein;


import java.util.*;
import java.io.*;

public class Main_14567_김회인{
	
    public static void main(String[] args)throws IOException{
       
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int N = Integer.parseInt(st.nextToken());		// N,M 입력
        int M = Integer.parseInt(st.nextToken());
        ArrayList<ArrayList<Integer>> arraylist = new ArrayList<>();
        
        int indegree[] = new int[N+1];		// 몇개의 선수 과목이 필요한지
        int term[] = new int[N+1];			// 몇 학기에 들을 수 있는 과목인지
        		
        for(int i=0; i<=N; i++)		
        {
        	arraylist.add(new ArrayList<>());
        }
        
        for(int i=0; i<M; i++)
        {
        	st = new StringTokenizer(br.readLine());
        	 int a = Integer.parseInt(st.nextToken());
             int b = Integer.parseInt(st.nextToken());
             
        	arraylist.get(a).add(b);
        	indegree[b]++;
        }
        
        Queue<Integer> q = new LinkedList<Integer>();
        
        for(int i=1; i<=N; i++)
        {
        	if(indegree[i] == 0)
        	{
        		q.add(i);
        		term[i] = 1;
        	}
        }
        
        while(!q.isEmpty())
        {
        	int now = q.poll();
        	
        	for(int next : arraylist.get(now))		
        	{
        		indegree[next]--;
        		
        		// 선수 과목중 가장 늦은 학기에서 +1
        		if(term[next] < term[now] + 1) term[next] = term[now] + 1;

        		if(indegree[next] == 0)
        		{
        			q.add(next);
        		}
        	}
        }
        
        for(int i=1; i<=N; i++)
        {
        	bw.write(Integer.toString(term[i]) + " ");
        }
        
        bw.flush();
        bw.close();
        br.close();
    }
    
    
    
}