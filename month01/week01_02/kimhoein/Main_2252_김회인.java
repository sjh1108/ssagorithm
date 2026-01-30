package study;
import java.io.*;
import java.util.*;

public class Main_2580_김회인 {
	/*
	 * rank를 정해서 앞에 누가 있어야 나올 수 있는지를 구한다
	 * rank 0 이면 바로 나올 수 있음.. rank 1이면 앞에 한사람이 더 나와야 나올 수 있음..
	 * 이걸 이용하면 rank 순서대로 0이면 que 에 넣어주고
	 * 저장된 값에서 rank-- 해주고 0 이라면 que에 다음 차례에 들어가야 할 다음 사람을 넣어준다.
	 * 이렇게 위상 정렬을 수행 해준다.
	 * 
	 */
	
	public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    	StringTokenizer st = new StringTokenizer(br.readLine());
    	
    	int N = Integer.parseInt(st.nextToken());
    	int M = Integer.parseInt(st.nextToken());
    	
    	ArrayList<ArrayList<Integer>> arrayList = new ArrayList<>();
    	
    	int array[] = new int[N+1];
    	
    	for(int i=0; i<=N; i++) {
    		arrayList.add(new ArrayList());
    	}
    	
    	for(int i=0; i<M; i++) {
    		st = new StringTokenizer(br.readLine());
    		int a = Integer.parseInt(st.nextToken());
    		int b = Integer.parseInt(st.nextToken());
    		
    		arrayList.get(a).add(b);
    		array[b]++;	// 여기서 rank 값 수정 해줘서 앞에 어떤 사람이 와야 내 차례가 오는지 알 수 있음
    	}
    	
    	Queue<Integer> q = new LinkedList<Integer>();
    	
    	for(int i=1; i<array.length; i++)
    	{
    		if(array[i] == 0)
    		{
    			q.add(i);
    		}
    	}
    	
    	while(!q.isEmpty())
    	{
    		int now = q.poll();
    		
    		for(int next : arrayList.get(now))
    		{
    			array[next]--;	// rank 값 -1 해줘서 앞에 사람 하나 빠진거 표시 해주고
    			if(array[next] == 0)	// 만약 차례가 되었다면 큐에 넣어준다.
    			{
    				q.add(next);
    			}
    		}
    		
    		bw.write(Integer.toString(now) + " ");
    	}
    	
    	//bw.write(state);
    	bw.flush();
    	bw.close();
    	br.close();
    }
    
	
}
