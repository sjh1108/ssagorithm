package week_11;

import java.io.*;
import java.util.*;

public class Main_24313 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int a1 = Integer.parseInt(st.nextToken());
        int a0 = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(br.readLine().trim());
        int n0 = Integer.parseInt(br.readLine().trim());

        boolean ok = true;
        for (int n = n0; n <= 100; n++) {
            if (a1 * n + a0 > c * n) {
                ok = false;
                break;
            }
        }
        System.out.println(ok ? 1 : 0);
    }
}