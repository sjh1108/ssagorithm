package algostudy.baek;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_2805_이용호 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		int[] trees = new int[N];
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++) {
			trees[i] = Integer.parseInt(st.nextToken());
		}
		
		int left = 0;
		int right = 1000000000;

		while(left <= right) {
			int H = (left + right) / 2;
			
			long sum = 0;//아... long gg;;
			//자르고 갖고갈수 있는나무 계산
			for(int treeH : trees) {
				if (treeH > H) sum += treeH - H;
			}
			//M미터 이상 못갖고 가면 H낮춰야함 -> right = mid - 1;
			if(sum < M) {
				right = H - 1;
			}
			//left = mid + 1
			else{
				left = H + 1;
			}
				
		}
		System.out.println(right);
	}

}
