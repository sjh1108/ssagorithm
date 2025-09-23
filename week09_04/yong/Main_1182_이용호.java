package algostudy.baek.m9w4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_1182_이용호 {
	static int[] nums;
	static int N, S, cnt;
	static boolean[] used;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		S = Integer.parseInt(st.nextToken());
		
		nums = new int[N];
		
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++) {
			nums[i] = Integer.parseInt(st.nextToken());
		}
		used = new boolean[N];
		cnt = 0;
		subset(0,0);
		System.out.println(cnt);
	}
	static void subset(int idx, int picked) {
		if(idx == N) {
			int sum = 0;
			for(int i = 0; i < N; i++) {
				if(!used[i]) continue;
				sum += nums[i];
//				System.out.print(i + " ");
			}

			if(picked > 0 && sum == S) cnt++; 
			return;
		}
		
		used[idx] = true;
		subset(idx+1,picked+1);
		used[idx] = false;
		subset(idx+1,picked);
		
	}
}
