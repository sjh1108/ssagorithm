package Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Arrays;

public class BOJ_2108_통계학 {

	static int forRange = 4000;
	static int size = 8001;
		
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		
		int[] array = new int[N];
		int[] freq = new int[size];
		
		long sum = 0L;
		int min = 4001;
		int max = -4001;
		
		for(int i =0; i<N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());

            array[i] = x;
            sum += x;
			
            freq[x+forRange]++;
            
            // 최대값 최소값 갱신하기
            if (x < min) min = x;
            if (x > max) max = x;
			
		}
		
		// 평균 구하기
		int mean = (int) Math.round((double) sum / N);
		
		// 중앙 값 구하기
        Arrays.sort(array);
        int median = array[N / 2];
		
        
        // 최빈값 구하기
        int maxFreq = 0;
        for (int i = 0; i < size; i++) {
            if (freq[i] > maxFreq) {
                maxFreq = freq[i];
            }
        }
		
        // mode 
        int mode = 0;
        int countAtMax = 0; // 최대 빈도 값 순서
        for (int i = 0; i < size; i++) {
            if (freq[i] == maxFreq) {
                countAtMax++;

                if (countAtMax == 2) {// 두 번째로 작은 값을 만나면
                    mode = i - forRange;
                    break;
                }
                mode = i - forRange; // 첫 번째일 경우 담기
            }
        }
        
        int range = max - min;
        StringBuilder sb = new StringBuilder();
        sb.append(mean).append('\n');
        sb.append(median).append('\n');
        sb.append(mode).append('\n');
        sb.append(range);
        System.out.println(sb.toString());
        
	}

}
