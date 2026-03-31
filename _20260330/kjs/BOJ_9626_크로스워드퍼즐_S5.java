import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            StringTokenizer st = new StringTokenizer(br.readLine());
            int M = Integer.parseInt(st.nextToken());
            int N = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());
            int U = Integer.parseInt(st.nextToken());
            int L = Integer.parseInt(st.nextToken());
            int R = Integer.parseInt(st.nextToken());
            int D = Integer.parseInt(st.nextToken());

            String[] arr = new String[M];
            for (int i = 0; i < M; i++) {
                arr[i] = br.readLine();
            }

            int row = U + M + D;
            int col = L + N + R;

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (i >= U && i < U + M && j >= L && j < L + N) {
                        sb.append(arr[i - U].charAt(j - L));
                    } else {
                        if ((i + j) % 2 == 0) {
                            sb.append('#');
                        } else {
                            sb.append('.');
                        }
                    }
                }
                sb.append('\n');
            }

            System.out.print(sb);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
