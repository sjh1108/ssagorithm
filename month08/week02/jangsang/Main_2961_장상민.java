package month08.week02.jangsang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_2961_���� {
	static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int N,S,B;
	static int[] sin;
	static int[] sseun;
	static boolean[] isSelected;
	static int[] input;
	static int sum;
	public static void main(String[] args) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(br.readLine());
		isSelected = new boolean[N];
		
		for (int i = 0; i < N; i++) {
			sin[i] = Integer.parseInt(st.nextToken());
			sseun[i] = Integer.parseInt(st.nextToken());
		}
		bt(0,0);
	}
	
	public static void bt(int cnt, int setCnt) {
		if(cnt == N) {
			for (int i = 0; i < N; i++) {
				System.out.print((isSelected[i]?input[i]:"X")+" ");
			}
			return;
		}
		
		isSelected[cnt] = true;
		bt(cnt+1, setCnt+1);
		
		isSelected[cnt] = false;
		bt(cnt+1, setCnt);
		
	}
}
