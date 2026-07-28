import java.util.*;
import java.io.*;

public class 주괴_두_개씩_합치기 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		
		PriorityQueue<Long> pq = new PriorityQueue<>();
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		for(int i = 0; i < N; i++) {
			long w = Long.parseLong(st.nextToken());
			pq.offer(w);
		}
		
		long result = 0;
		
		while(pq.size() > 1) {
			// 가장 가벼운 주괴
			long left = pq.poll();
			long next = pq.poll();
			
			// 두 주괴 합
			long sum = left + next;
			
			result += sum;
			
			pq.offer(sum); // 합친 주괴 큐에 넣기
		}
		
		System.out.println(result);
	}

}
