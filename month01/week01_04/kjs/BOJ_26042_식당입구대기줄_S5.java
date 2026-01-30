package Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.ArrayDeque;

public class BOJ_26042_식당입구대기줄_S5 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        ArrayDeque<Integer> q = new ArrayDeque<>();

        int bSize = 0;
        int bBack = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int type = Integer.parseInt(st.nextToken());

            if (type == 1) {
                int a = Integer.parseInt(st.nextToken());
                q.addLast(a);
            } else { 
                q.pollFirst(); 
            }

            int curSize = q.size();

            // curSize == 0 뒤가 없음, 비교 X
            if (curSize > 0) {
                int curBack = q.peekLast();

                if (curSize > bSize) {
                    bSize = curSize;
                    bBack = curBack;
                } else if (curSize == bSize) {
                    if (curBack < bBack) {
                        bBack = curBack;
                    }
                }
            }
        }

        System.out.println(bSize + " " + bBack);
    }
}