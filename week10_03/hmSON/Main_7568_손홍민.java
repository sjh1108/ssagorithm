import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_7568 {
	
	// BOJ_7568 : 덩치(Silver_5)
	// 자로구조 및 알고리즘 : 브루트포스
	
	public static void main(String[] args) throws Exception {
		BufferedReader br =  new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		// 각 사람의 체중, 신장, 덩치 순위
		int[] weights = new int[n];
		int[] heights = new int[n];
		int[] rank = new int[n];
		// 모든 사람의 덩치 순위 초기값을 1로 설정
		Arrays.fill(rank, 1);
		
		for(int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			weights[i] = Integer.parseInt(st.nextToken());
			heights[i] = Integer.parseInt(st.nextToken());
		}
		
		for(int i=0; i<n; i++) {
			for(int j=i+1; j<n; j++) {
				// 체중과 신장 모두 작은 경우 해당 사람의 덩치 순위를 카운트함
				// 그 외의 경우는 취급하지 않음
				if(weights[i] > weights[j] && heights[i] > heights[j]) rank[j]++;
				else if(weights[i] < weights[j] && heights[i] < heights[j]) rank[i]++;
			}
		}
		
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<n; i++) {
			sb.append(rank[i] + "\n");
		}
		System.out.println(sb);
	}
	
}