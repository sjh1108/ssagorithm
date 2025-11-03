package study;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.util.*;

/*
 * ABCDE 관계를 만족하는 친구 관계를  찾기
 * dfs로 관계를 만족하면서 5의 깊이를 만족하면 1
 * 모든 경우를 돌렸는데 없으면 -1
 */
public class Main_13023_김회인 {
	static boolean visit[];
	static List<Integer> list[];
	static int N;
	static int M;
	
    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    	StringTokenizer st = new StringTokenizer(br.readLine());
    	
    	N = Integer.parseInt(st.nextToken());
    	M = Integer.parseInt(st.nextToken());
    	
    	list = new List[N+1];
    	visit = new boolean[N+1];
    	
    	for(int i=0; i<=N; i++)
    	{
    		list[i] = new LinkedList<Integer>();
    	}
    	
    	for(int i=0; i<M; i++)
    	{
    		st = new StringTokenizer(br.readLine());
    		int a = Integer.parseInt(st.nextToken());
        	int b = Integer.parseInt(st.nextToken());
        	
        	list[a].add(b);
        	list[b].add(a);
    	}
    	int ans = 0;
    	
    	for(int i=0; i<N; i++)
    	{
    		Arrays.fill(visit, false);
    		visit[i] = true;
    		if(dfs(i,1)==1) {
    			ans++;
    			break;
    		}
    	}
    	
    	bw.write(Integer.toString(ans));
    	bw.flush();
    	bw.close();
    	br.close();
    }
    
    static int dfs(int start , int depth)
    {
    	
    	if(depth == 5)
    	{
    		return 1;
    	}
    	
    	for(int i : list[start])
    	{
    		if(visit[i]) continue;
    		
    		visit[i] = true;
    		//System.out.println("i : " + i + " start : "+ start);
    		if(dfs(i,depth+1) == 1) return 1;
    		visit[i] = false;
    	}
    	
    	return 0;
    }
}
