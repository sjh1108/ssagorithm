package _20260323.thdwngjs.BOJ_27988;

import java.io.*;
import java.util.*;

/**
 * BOJ 27988 - 리본 (Hard)
 * 알고리즘: 정렬 + 스위핑
 *
 * 각 리본은 중심 x, 길이 l을 가지며 구간 [x-l, x+l]을 커버한다.
 * 서로 다른 색의 리본 중 겹치는 쌍이 있는지 찾는 문제.
 *
 * 핵심: 리본을 왼쪽 끝(x-l) 기준으로 정렬한 후 스위핑하면서,
 * 각 색상별로 가장 오른쪽까지 뻗은 리본의 right 값을 관리.
 * 현재 리본의 left가 다른 색상의 maxRight 이하이면 겹침 발생.
 */
class Main {
    /**
     * 리본 클래스: 입력 순서(num), 중심(x), 반지름(l), 색상(c)
     * 왼쪽 끝(x-l) 기준 오름차순 정렬
     */
    static class Ribon implements Comparable<Ribon>{
        static int CNT = 1;
        int num;    // 입력 순서 번호 (1-based)
        int x, l;   // 중심 좌표, 반지름(길이)
        char c;     // 색상 (R, Y, B)

        public Ribon(int x, int l, char c) {
            this.num = CNT++;
            this.x = x;
            this.l = l;
            this.c = c;
        }

        @Override
        public int compareTo(Main.Ribon o) {
            // 왼쪽 끝 (x - l) 기준 오름차순 정렬
            return Long.compare((long)this.x - this.l, (long)o.x - o.l);
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine().trim());

        StringTokenizer X = new StringTokenizer(br.readLine());
        StringTokenizer L = new StringTokenizer(br.readLine());
        StringTokenizer C = new StringTokenizer(br.readLine());

        List<Ribon> all = new ArrayList<>();

        while(N-- > 0){
            int x = Integer.parseInt(X.nextToken());
            int l = Integer.parseInt(L.nextToken());
            char c = C.nextToken().charAt(0);
            all.add(new Ribon(x, l, c));
        }

        // 왼쪽 끝 기준 정렬
        Collections.sort(all);

        // 각 색상(R=0, Y=1, B=2)별로 가장 오른쪽까지 뻗은 리본의 right 값
        long[] maxRight = {Long.MIN_VALUE, Long.MIN_VALUE, Long.MIN_VALUE};
        Ribon[] maxRibon = new Ribon[3]; // 해당 maxRight를 가진 리본 객체

        for(Ribon cur : all){
            long left = (long)cur.x - cur.l;   // 현재 리본의 왼쪽 끝
            long right = (long)cur.x + cur.l;   // 현재 리본의 오른쪽 끝
            int cc = cur.c == 'R' ? 0 : cur.c == 'Y' ? 1 : 2; // 현재 색상 인덱스

            // 다른 색상의 리본과 겹치는지 확인
            for(int oc = 0; oc < 3; oc++){
                if(oc == cc) continue; // 같은 색상은 스킵
                // 다른 색상의 maxRight가 현재 리본의 left 이상이면 겹침 발생
                if(maxRight[oc] >= left){
                    System.out.println("YES");
                    System.out.println(maxRibon[oc].num + " " + cur.num);
                    return;
                }
            }

            // 현재 색상의 maxRight 갱신
            if(right > maxRight[cc]){
                maxRight[cc] = right;
                maxRibon[cc] = cur;
            }
        }

        // 겹치는 쌍이 없는 경우
        System.out.println("NO");
    }
}
