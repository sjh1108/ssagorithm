package algostudy.baek;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_2512_이용호 {
/*
 * 모든요청 배정될수 있으면 그대로 배정
 * 배정될수 없으면 상한액 배정, 상한액 이하면 요청한대로 배정
 * left 1, right M(총예산)으로 두고 최대예산 찾기 
 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[] area = new int[N];
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int max = 0;
		for(int i = 0; i < N; i++) {
			area[i] = Integer.parseInt(st.nextToken());
			max = Math.max(max, area[i]);
		}
		//총 예산
		int M = Integer.parseInt(br.readLine());
		
		int left = 1;
		int right = M;
		
		while(left <= right) {
			//상한액
			int mid = (left + right) / 2;
			
			long totalNeed = 0; //이번엔 안당했죠?
			for(int need : area) {
				//상한액보다 예산요청이 작으면 요청한대로 더하기 
				if(need <= mid) {
					totalNeed += need;
					
				}
				//요청액이 더 크면 상한액더하기
				else {
					totalNeed += mid;
				}
					
			}
//			System.out.println(mid + " " + totalNeed);
			//예산 초과면 상한액 줄이기(right = mid - 1)
			if(totalNeed > M) {
				right = mid - 1;
			}
			else {
				left = mid + 1;
			}
		}
		System.out.println(right > max ? max : right);
	}

}
