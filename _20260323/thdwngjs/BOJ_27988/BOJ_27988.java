package _20260323.thdwngjs.BOJ_27988;

import java.io.*;
import java.util.*;

class Main {
    static class Ribon implements Comparable<Ribon>{
        static int CNT = 1;
        int num;
        int x, l;
        char c;

        public Ribon(int x, int l, char c) {
            this.num = CNT++;
            this.x = x;
            this.l = l;
            this.c = c;
        }

        @Override
        public int compareTo(Main.Ribon o) {
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

        Collections.sort(all);

        long[] maxRight = {Long.MIN_VALUE, Long.MIN_VALUE, Long.MIN_VALUE};
        Ribon[] maxRibon = new Ribon[3];

        for(Ribon cur : all){
            long left = (long)cur.x - cur.l;
            long right = (long)cur.x + cur.l;
            int cc = cur.c == 'R' ? 0 : cur.c == 'Y' ? 1 : 2;

            for(int oc = 0; oc < 3; oc++){
                if(oc == cc) continue;
                if(maxRight[oc] >= left){
                    System.out.println("YES");
                    System.out.println(maxRibon[oc].num + " " + cur.num);
                    return;
                }
            }

            if(right > maxRight[cc]){
                maxRight[cc] = right;
                maxRibon[cc] = cur;
            }
        }

        System.out.println("NO");
    }
}
