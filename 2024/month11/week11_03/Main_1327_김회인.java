import java.io.*;
import java.util.*;

public class Main_1327_김회인 {
	
	public static class Pair{
		String s;
		int cnt;
		Pair(String s, int cnt){
			this.s = s;
			this.cnt = cnt;
		}
	}
	
	public static int N, K;
	public static ArrayList<Integer> list = new ArrayList<>();
	public static String number, answer;
	public static Set<String> visited = new HashSet<>();
	
    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    	StringTokenizer st = new StringTokenizer(br.readLine());
    	
    	N = Integer.parseInt(st.nextToken());
    	K = Integer.parseInt(st.nextToken());
    	st = new StringTokenizer(br.readLine());
    	
    	StringBuffer sb = new StringBuffer();
    	for(int i=0; i<N; i++) sb.append(Integer.parseInt(st.nextToken()));
    	number = sb.toString();
    	
    	
    	sb = new StringBuffer();
    	
    	for(int i=1; i<=N; i++) sb.append(i);
    	answer = sb.toString();
    	
    	System.out.println(bfs(number));
//    	bw.write(bfs(answer));
//    	bw.flush();
//    	bw.close();
//    	br.close();
    }
    
    public static int bfs(String number)
    {
    	Queue<Pair> q= new LinkedList<>();
    	q.add(new Pair(number, 0));
    	visited.add(number);
    	
    	while(!q.isEmpty()) {
    		Pair p = q.poll();
    		if(p.s.equals(answer)) return p.cnt;
    		
    		for(int i=0; i<=(N-K); i++) {
    			char[] charArr = p.s.toCharArray();
    			
    			for(int j=0; j<(K/2); j++) {
    				char tmp = charArr[i+j];
    				charArr[i+j] = charArr[i+K-1-j];
    				charArr[i+K-1-j] = tmp;
    			}
    			
    			String n = new String(charArr);
    			
    			if(!visited.contains(n)) {
    				Pair tmpP = new Pair(n,p.cnt+1);
    				q.add(tmpP);
    				visited.add(tmpP.s);
    			}
    		}
    	}
    	
    	return -1;
    }

}
