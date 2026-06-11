package _20260526.thdwngjs;

import java.util.*;
import java.io.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        String cmd = br.readLine().trim();
        for (char ch : cmd.toCharArray()) {
            int nr = r, nc = c;
            if (ch == 'U') nr--;
            else if (ch == 'D') nr++;
            else if (ch == 'L') nc--;
            else if (ch == 'R') nc++;
            if (nr >= 1 && nr <= N && nc >= 1 && nc <= M) {
                r = nr;
                c = nc;
            }
        }
        System.out.println(r + " " + c);
    }
}