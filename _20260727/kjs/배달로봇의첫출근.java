import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in)
        );

        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());

        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        String command = br.readLine();

        for (int i = 0; i < command.length(); i++) {
            char ch = command.charAt(i);

            if (ch == 'U') {
                if (r - 1 >= 1) {
                    r--;
                }
            } else if (ch == 'D') {
                if (r + 1 <= N) {
                    r++;
                }
            } else if (ch == 'L') {
                if (c - 1 >= 1) {
                    c--;
                }
            } else if (ch == 'R') {
                if (c + 1 <= M) {
                    c++;
                }
            }
        }

        System.out.println(r + " " + c);
    }
}
