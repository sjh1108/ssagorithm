package _20260625.thdwngjs.두_달팽이의_높이;

import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        // [핵심 아이디어]
        //  A의 레벨은 매일 결정적으로 변한다: 커밋한 날엔 +1, 아닌 날엔 -1(단 최소 1).
        //  B는 매일 자유롭게 +1 또는 -1(최소 1) 가능 -> "d일째에 특정 레벨 T에 도달 가능한가?"
        //  만 판별하면 된다. 따라서 하루씩 진행하며 A의 레벨을 갱신하고,
        //  그 레벨에 B가 같은 날 도달 가능한 첫 날 d를 찾는다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long n = Long.parseLong(st.nextToken()); // A의 시작 레벨
        long m = Long.parseLong(st.nextToken()); // B의 시작 레벨
        int k = Integer.parseInt(st.nextToken()); // A가 커밋(레벨업)하는 날의 수

        // commit[day] = true 이면 A가 그 날 +1. 가장 큰 커밋일(maxDay)까지만 배열로 관리.
        int maxDay = 0;
        int[] arr = new int[k];
        if (k > 0) {
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < k; i++) {
                arr[i] = Integer.parseInt(st.nextToken());
                if (arr[i] > maxDay) maxDay = arr[i];
            }
        }
        boolean[] commit = new boolean[maxDay + 2];
        for (int i = 0; i < k; i++) commit[arr[i]] = true;

        long A = n, d = 0;
        // d=0(시작 시점)에 이미 같은 레벨이 될 수 있는지 먼저 확인
        if (reachable(m, A, d)) { System.out.println(0); return; }
        while (true) {
            d++;
            // 그 날이 커밋일이면 A는 +1, 아니면 -1(최소 1에서 멈춤)
            boolean isCommit = (d <= maxDay) && commit[(int) d];
            A = isCommit ? A + 1 : Math.max(1, A - 1);
            // maxDay 이후엔 A가 계속 감소해 결국 1로 수렴 -> 언젠가 reachable이 참이 되어 종료 보장
            if (reachable(m, A, d)) { System.out.println(d); return; }
        }
    }

    // B(시작 레벨 m)가 "정확히 d일째"에 레벨 T가 될 수 있는가?
    static boolean reachable(long m, long T, long d) {
        long diff = Math.abs(m - T);
        // (1) 패리티 경로: 최소 diff번 이동이 필요하고, 남는 이동(d-diff)은 +/- 왕복으로 소모.
        //     단 왕복은 짝수여야 제자리로 돌아오므로 (d-diff)가 짝수여야 한다.
        if (d >= diff && ((d - diff) % 2 == 0)) return true;
        // (2) 바닥(레벨1) 대기 활용: 1까지 내려가면 -1을 해도 1에 머물러(헛걸음) 패리티를 깰 수 있다.
        //     m->1 (m-1번) 후 1->T (T-1번) = m+T-2번 이상이면 패리티 상관없이 도달 가능.
        if (d >= m + T - 2) return true;
        return false;
    }
}