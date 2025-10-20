import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
/*
 * 모든 조카에가 같은길이 막대과자(최대한 긴)
 * M명 조카 , N개 과자
 * 조카 1명에게 줄수 있는 막대 과자의 최대 길이
 * 막대 과자 나눠질수 있음
 */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int M = Integer.parseInt(st.nextToken()); // 조카 수
		int N = Integer.parseInt(st.nextToken()); // 과자 수
		
		int[] snack = new int[N];
		int maxLen = 0;
		st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            snack[i] = Integer.parseInt(st.nextToken());
            maxLen = Math.max(maxLen, snack[i]);
        }
		
		long left = 1; 
		long right = maxLen;
		long result = 0;
		while(left <= right) {
			long mid = (left + right) / 2; //나눠줄 과자 길이
			long count = 0; 
			
			for(int s : snack) {
				count += s / mid;
						
			}
			
			//다 나눠 줄수 있으면 길이 늘려보기
			if(count >= M) {
				result = mid;
				left = mid + 1;
			}else {
				right = mid - 1;
			}
			
			
		}
		System.out.println(result);
		
	}

}
