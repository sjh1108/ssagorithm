import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 부녀회장이 될테야 브론즈1
public class Main_2775 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb= new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            int k = Integer.parseInt(br.readLine());
            int n = Integer.parseInt(br.readLine());

            int[][] a = new int[15][15];

            // 0층 초기화 -> 1 2 3 4 5 6 7 ...
            for (int i = 1; i <= 14; i++) {
                a[0][i] = i;
            }

            // DP 채우기 - 누적합
            for (int i = 1; i <= 14 ; i++) {
                for (int j = 1; j <= 14 ; j++) {
                    a[i][j] = a[i][j-1] + a[i-1][j];
                }
            }

            sb.append(a[k][n]).append("\n");
        }
        System.out.println(sb);
    }
}
