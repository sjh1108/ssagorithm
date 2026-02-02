package Test

import java.io.*;
import java.util.*;

public class BOJ_13119_Mountains_Beyond_Mountains {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int X = Integer.parseInt(st.nextToken());

        int[] H = new int[M+1];

        st = new StringTokenizer(br.readLine());
        
        
        for (int i = 1; i <= M; i++) {
            H[i] = Integer.parseInt(st.nextToken());
        }

        StringBuilder out = new StringBuilder();

        for (int r=0; r <N; r++) {
            int height = N - r;

            for (int i=1; i<=M; i++) {
                
                int hi = H[i];
                char ch = '.';

                // 고속도로
                if (height == X) {
                    if (hi >=X) ch = '*';
                    else ch = '-';
                }
                // 산
                else if (height<=hi) {
                    ch = '#';
                }
                // 교각
                else if (hi < X && (i%3 == 0) && (hi<height) && (height<X)) {
                    ch = '|';
                }

                out.append(ch);
            }
            out.append('\n');
        }

        System.out.print(out.toString());
    }
}
