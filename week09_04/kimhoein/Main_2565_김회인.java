package study;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;


public class Main_2565_김회인{
	
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;// = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(br.readLine());
		int lines[][] =new int[N][2];
		
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			lines[i][0] = Integer.parseInt(st.nextToken());
			lines[i][1] = Integer.parseInt(st.nextToken());
		}
		
		Arrays.sort(lines,(o1,o2) -> o1[0] - o2[0]);
		
		int dp[] = new int[N];
		int len =0;
		
		for(int i=0; i<N; i++)
		{
			int pos = lines[i][1];
			int idx = Arrays.binarySearch(dp, 0,len,pos);
			if(idx <0) idx = -(idx +1);
			dp[idx] = pos;
			if(idx == len) len++;
		}
		
		bw.write(Integer.toString(N-len));
		bw.flush();
		bw.close();
		br.close();
			
	}
	
	
}
