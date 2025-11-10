package study;
import java.io.*;
import java.util.*;

public class Main_1697_김회인 {
	static int N;
	static int K;
	static int[] check = new int[100001];
	
    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    	StringTokenizer st = new StringTokenizer(br.readLine());
    	
    	N = Integer.parseInt(st.nextToken());
    	K = Integer.parseInt(st.nextToken());
    	
    	
    	
    	
    	bw.write(Integer.toString(bfs(N)));
    	bw.flush();
    	bw.close();
    	br.close();
    }
    
    static int bfs(int num)
    {
    	Queue<Integer> q = new LinkedList<>();
    	
    	q.add(num);
    	check[num] = 1;
    	
    	if(N==K) return 0;
    	
    	while(!q.isEmpty())
    	{
    		int temp = q.poll();
    		
    		for(int i=0; i<3; i++)
    		{
    			int next=0;
    			
    			if(i==0) next =temp +1;
    			else if( i ==1) next =temp -1;
    			else if( i ==2) next = temp*2;
    			
    			if(next == K) {
    				return check[temp];
    			}
    			
    			if(next >= 0 && next < check.length && check[next] == 0) {
    				q.add(next);
    				check[next] = check[temp] + 1;
    			}

    		}
    	}
    	
    	return 0;
    }
    

}
