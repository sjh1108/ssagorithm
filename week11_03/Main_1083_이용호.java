package baek_week11_03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_1083_이용호 {
/*
 * 크기가 N인 배열 a
 * 소트할때 연속 두개 원소만 교환 가능
 * 최대 S번 겨환 가능
 * 소트한 결과가 사전순으로 가장 뒷서는 것을 출력
 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[] arr = new int[N];
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		
		int S = Integer.parseInt(br.readLine());
		
		// S > 0 교체횟수 안남아있으면 종료 
		for (int i = 0; i < N && S > 0; i++) {
		    int maxIdx = i;
		    // j - 1 <= S 당겨올수 있는지 확인
		    for (int j = i + 1; j < N && j - i <= S; j++) {
		        if (arr[j] > arr[maxIdx]) maxIdx = j;
		    }
		    for (int j = maxIdx; j > i; j--) {
		        int tmp = arr[j];
		        arr[j] = arr[j - 1];
		        arr[j - 1] = tmp;
		    }
		    S -= maxIdx - i;
		}
		StringBuilder sb = new StringBuilder();	
		for(int i = 0; i < N; i++) {
			sb.append(arr[i] + " ");
		}
		System.out.println(sb);
	}

}
