package algo2026_02_03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_28356_부정행위멈춰 {
	    public static void main(String[] args) throws IOException {
	        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	        StringTokenizer st = new StringTokenizer(br.readLine());
	        int N = Integer.parseInt(st.nextToken());
	        int M = Integer.parseInt(st.nextToken());

	        StringBuilder sb = new StringBuilder();

	        if (N == 1 && M == 1) {
	            sb.append(1).append('\n');
	            sb.append(1).append('\n');
	        } else if (N == 1) {
	            sb.append(2).append('\n');
	            for (int x = 0; x < M; x++) {
	                if (x > 0) sb.append(' ');
	                sb.append((x % 2) + 1);
	            }
	            sb.append('\n');
	        } else if (M == 1) {
	            sb.append(2).append('\n');
	            for (int y = 0; y < N; y++) {
	                sb.append((y % 2) + 1).append('\n');
	            }
	        } else {
	            // (y%2==0, x%2==0) -> 1
	            // (y%2==0, x%2==1) -> 2
	            // (y%2==1, x%2==0) -> 3
	            // (y%2==1, x%2==1) -> 4
	            sb.append(4).append('\n');
	            for (int y = 0; y < N; y++) {
	                for (int x = 0; x < M; x++) {
	                    if (x > 0) sb.append(' ');
	                    int val = (y % 2) * 2 + (x % 2) + 1;
	                    sb.append(val);
	                }
	                sb.append('\n');
	            }
	        }

	        System.out.print(sb);
	    }
	}
