import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int[][] arr = new int[N][2];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            arr[i][0] = a;
            arr[i][1] = b;
        }

        long min = Long.MAX_VALUE;

        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                long l = uc(arr[i][0], arr[i][1], arr[j][0], arr[j][1]);
                min = Math.min(min, l);
            }
        }

        bw.write(Long.toString(min));
        bw.flush();
        bw.close();
    }

    static public long uc(int x1, int y1, int x2, int y2) {
        long x = Math.abs(x1 - x2);
        long y = Math.abs(y1 - y2);

        return x * x + y * y;
    }
}
