package month08.week04.kimhoein;
import java.util.*;
import java.io.*;

public class Main_1766_김회인{
	
    public static void main(String[] args)throws IOException{
       
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int depth[] = new int[N+1];
        
        ArrayList<ArrayList<Integer>> arraylist = new ArrayList<ArrayList<Integer>>();
        
        for(int i=0; i<= N ; i++)
        {
        	arraylist.add(new ArrayList<Integer>());
        }
        
        for(int i=0; i<M; i++)
        {
        	st = new StringTokenizer(br.readLine());
        	int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            arraylist.get(a).add(b);
            depth[b]++;
        }
        
        
        for(int i=0; i<= N ; i++)
        {
        	Collections.sort(arraylist.get(i)); //(new ArrayList<Integer>());
        }
        
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        
        for(int i=1; i<=N; i++)
        {
        	if(depth[i] == 0)
        	{
        		pq.add(i);
        	}
        }
        
        while(!pq.isEmpty())
        {
        	int now = pq.poll();
        	//System.out.print( now + " ");
        	bw.write(Integer.toString(now) + " ");
        	for(int next : arraylist.get(now))
        	{
        		depth[next]--;
        		if(depth[next] == 0)
        		{
        			pq.add(next);
        		}
        	}
        }
        
        bw.flush();
        bw.close();
        br.close();
    }
    
    
    
}