import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_2491_장상민 {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer tokens;

    public static void main(String[] args) throws NumberFormatException, IOException {
        
        int N = Integer.parseInt(input.readLine());
        int[] arr = new int[N];
        
        tokens = new StringTokenizer(input.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(tokens.nextToken());
        }
        
        int up = 1;      // 오름차순 길이
        int down = 1;    // 내림차순 길이
        int max = 1;     // max 길이

        for (int i = 1; i < N; i++) {
            if (arr[i-1] < arr[i]) {         // 오름차순
                up++;
                down = 1;
            } else if (arr[i-1] > arr[i]) {  // 내림차순
                down++;
                up = 1;
            } else {                         // 같을때는 양쪽 다 증가
                up++;
                down++;
            }
            max = Math.max(max, Math.max(up, down));
        }
        
        System.out.println(max);
    }
}
