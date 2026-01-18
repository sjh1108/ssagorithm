package study;
import java.io.*;
import java.util.*;

public class Main_3665_김회인 {
	/*
		테케 개수는 막지막에 넣도록
		N 팀개수
		팀 입력
		m 등수 바뀐 개수
		등수 바꿔줄 것
		
		등수 바꿔주는 개수가 팀 개수에 비해 많아서
		arraylist -> linkedlist
		
		등수 바꾸기는 linkedlist에서 제거
		추가 진행
		
		나머지는 평상시 하던데로..
		
		어려웠던 점은 이거를 위상정렬로 보기가 어렵고 생각 잘못하면
		그냥 자리만 바꾸면 되는거 아닌가? 이렇게 생각하기 쉽다

	 */
	
	public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    	StringTokenizer st;// = new StringTokenizer(br.readLine());
    	
    	int t = Integer.parseInt(br.readLine());
    	
    	for(int T=0; T<t; T++) {
    		
    		int N = Integer.parseInt(br.readLine());
    		int rank[] = new int[N+1];
    		int indegree[] = new int[N+1];
    		
    		List<LinkedList<Integer>> linkedlist = new LinkedList<>();
    		
    		for(int i=0; i<=N; i++) {
    			linkedlist.add(new LinkedList<>());
    		}
    		
    		st = new StringTokenizer(br.readLine());
    		
    		for(int i=1; i<=N; i++) {
    			rank[i] = Integer.parseInt(st.nextToken());
    		}
    		
    		for(int i=1; i<N; i++) {
    			for(int j=i+1; j<=N;j++) {
    				//System.out.println(rank[i] + " " + rank[j]);
    				linkedlist.get(rank[i]).add(rank[j]);
    				indegree[rank[j]]++;
    			}
    		}
    		
    		
    		int M = Integer.parseInt(br.readLine());
    		
    		for(int i=0; i<M; i++) {
    			st = new StringTokenizer(br.readLine());
    			
    			int a = Integer.parseInt(st.nextToken());
    			int b = Integer.parseInt(st.nextToken());
    			
    			if(linkedlist.get(b).contains(a)) {
    				linkedlist.get(b).remove(Integer.valueOf(a));
    				linkedlist.get(a).add(b);
    				indegree[b]++;
    				indegree[a]--;
    			}
    			else
    			{
    				linkedlist.get(a).remove(Integer.valueOf(b));
    				linkedlist.get(b).add(a);
    				indegree[a]++;
    				indegree[b]--;
    			}
    			
    		}
    		
    		//System.out.println("indegree[1] : " + indegree[1]);
    		Queue<Integer> q = new LinkedList<Integer>();
    		Queue<Integer> ans = new LinkedList<Integer>();
    		
    		for(int i=1; i<=N;i++) {
    			if(indegree[rank[i]] == 0) q.add(rank[i]);
    		}
    		
    		while(!q.isEmpty()) {
    			int cur = q.poll();
    			//System.out.println("cur " + cur);
    			
    			for(int i : linkedlist.get(cur)) {
    				indegree[i]--;
    				//System.out.println("indegree[i] : " + indegree[i] + " " + i);
    				if(indegree[i] == 0) q.add(i);
    			}
    			ans.add(cur);
    		}
    		
    		//System.out.println("ans.size : " + ans.size());
    		if(ans.size() != N) bw.write("IMPOSSIBLE");
    		else {
    			for(int i=0; i< N; i++) {
    				bw.write(Integer.toString(ans.poll()) + " ");
    			}
    		}
    		
    		bw.write("\n");
    	}
    	
    	bw.flush();
    	bw.close();
    	br.close();
    }
    
	
}
