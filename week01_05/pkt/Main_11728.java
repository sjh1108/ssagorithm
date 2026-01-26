import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

// 배열 합치기 실버5
public class Main_11728 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[] A = new int[N];
        int[] B = new int[M];

        st =  new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            A[i] = Integer.parseInt(st.nextToken());
        }

        st =  new StringTokenizer(br.readLine());
        for (int i = 0; i < M; i++) {
            B[i] = Integer.parseInt(st.nextToken());
        }


        int total = N + M;
        int[] sum = new int[total];

        for (int i = 0; i < N; i++) {
            sum[i] = A[i];
        }

        for (int i = 0; i < M; i++) {
            sum[N+i] = B[i];
        }

        Arrays.sort(sum);

        StringBuilder sb =  new StringBuilder();

        for (int i = 0; i < total; i++) {
            sb.append(sum[i]).append(" ");
        }

        System.out.println(sb);
    }
}
