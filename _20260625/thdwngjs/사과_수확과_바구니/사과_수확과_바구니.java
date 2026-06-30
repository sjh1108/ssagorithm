package _20260625.thdwngjs.사과_수확과_바구니;

import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        // [전략] First-Fit 그리디:
        //  사과를 입력 순서대로 보면서, 이미 만들어진 카트들을 "생성된 순서"로 훑어
        //  가장 먼저 조건을 만족하는 카트에 싣는다. 어디에도 못 실으면 새 카트를 만든다.
        //  카트에 실으려면 두 조건을 동시에 만족해야 함:
        //    (1) 무게 합이 limit 이하       (2) (담긴 사과 중 최대-최소) 차이가 diff 이하
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long limit = Long.parseLong(st.nextToken()); // 카트 한 대의 무게 합 상한
        long diff  = Long.parseLong(st.nextToken()); // 한 카트 안 사과들의 무게 편차(max-min) 상한
        int k = Integer.parseInt(st.nextToken());     // 사과 개수
        int[] w = new int[k];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < k; i++) w[i] = Integer.parseInt(st.nextToken()); // 각 사과 무게

        // 각 카트의 상태를 long[4]로 관리: [0]=무게합, [1]=최소무게, [2]=최대무게, [3]=담긴 개수
        List<long[]> carts = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            int x = w[i];
            boolean placed = false;
            for (long[] c : carts) {              // 먼저 만들어진 카트부터 차례로 시도
                // 이 카트에 x를 넣었다고 가정했을 때의 합/최소/최대를 미리 계산
                long ns = c[0] + x, nmin = Math.min(c[1], x), nmax = Math.max(c[2], x);
                if (ns <= limit && nmax - nmin <= diff) { // 두 제약을 모두 통과하면
                    c[0] = ns; c[1] = nmin; c[2] = nmax; c[3]++; // 실제로 싣고 상태 갱신
                    placed = true;
                    break;                        // 실을 수 있으면 반드시 싣고 다음 사과로
                }
            }
            if (!placed) carts.add(new long[]{x, x, x, 1});  // 모든 카트가 거부 -> 새 카트 생성
        }

        // 카트들이 생성된 순서대로 "담긴 사과 개수"를 공백으로 구분해 출력
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < carts.size(); i++) {
            if (i > 0) sb.append(' ');
            sb.append(carts.get(i)[3]);
        }
        System.out.println(sb.toString());
    }
}