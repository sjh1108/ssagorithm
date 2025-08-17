package ssagorithm.week03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Boj2884_알람시계 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		// H 시간, M 분 입력
		int H = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		// K 45분 앞서는 시간으로 바꿀 목표 시간 (분단위)
		int K = (((H*60)+M)-45);
		int nH = 0;
		int nM = 0;
		// 0보다 크면 -> 00시 45분 이상일 경우 -> 시간+분의 합이 45 이상
		if(K>=0) {
			nH=K/60;
			nM=K%60;
		}else { // 시간 + 분의 합이 45 미만

			K=(24*60)+K; // 24시 + K(- 음수) , 23시 nn분 출력되게 만들기
			nH=K/60;
			nM=K%60;
		}

		System.out.println(nH + " " + nM);

	}

}
