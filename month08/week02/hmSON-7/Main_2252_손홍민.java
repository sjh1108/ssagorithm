import java.io.*;
import java.util.*;

public class Main_2252 {
	
	static int n, comp;
	static int[] prevCnt;
	static List<List<Integer>> nextList = new ArrayList<>();
	static List<Integer> resultList = new ArrayList<>();
	
	public static void main(String[] args) throws Exception {
		init(); solve();
	}
	
	public static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		comp = Integer.parseInt(st.nextToken());
		for(int i=0; i<n; i++) {
			nextList.add(new ArrayList<>());
		}
		prevCnt = new int[n];
		for(int i=0; i<comp; i++) {
			st = new StringTokenizer(br.readLine());;
			int smaller = Integer.parseInt(st.nextToken()) - 1;
			int bigger = Integer.parseInt(st.nextToken()) - 1;
			nextList.get(smaller).add(bigger);
			prevCnt[bigger]++;
		}
	}
	
	public static void solve() {
		Queue<Integer> q = new ArrayDeque<>();
		for(int i=0; i<n; i++) {
			if(prevCnt[i] == 0) q.add(i);
		}
		
		while(!q.isEmpty()) {
			int curr = q.poll();
			resultList.add(curr);
			List<Integer> nextNode = nextList.get(curr);
			for(int next : nextNode) {
				prevCnt[next]--;
				if(prevCnt[next] == 0) {
					q.add(next);
				}
			}
		}
		
		StringBuilder sb = new StringBuilder();
		for(int res : resultList) {
			sb.append(res+1).append(" ");
		}
		System.out.println(sb);
	}
	
}