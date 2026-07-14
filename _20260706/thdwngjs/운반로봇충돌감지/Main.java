// [#14] 운반 로봇 충돌 감지  —  GOLD
// 접근: #13의 워밍업. 다중 로봇 동시 이동은 같지만 충돌은 '같은 칸에 둘 이상'만 판정하고
//       자리 교환은 충돌로 보지 않는다. 스탬프 배열로 매 초 O(N) 판정.
// 복잡도: O(M*N)
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int R = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        boolean[] wall = new boolean[R * C];
        for (int i = 0; i < R; i++) {
            String line = br.readLine();
            for (int j = 0; j < C; j++) if (line.charAt(j) == '#') wall[i * C + j] = true;
        }

        int[] dr = {-1, 0, 1, 0}, dc = {0, 1, 0, -1};   // clockwise U,R,D,L
        int[] di = new int[128];
        di['U'] = 0; di['R'] = 1; di['D'] = 2; di['L'] = 3;

        int[] r = new int[N], c = new int[N], d = new int[N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            r[i] = Integer.parseInt(st.nextToken()) - 1;
            c[i] = Integer.parseInt(st.nextToken()) - 1;
            d[i] = di[st.nextToken().charAt(0)];
        }

        int[] nr = new int[N], nc = new int[N], nd = new int[N];
        int[] occStamp = new int[R * C], occOwner = new int[R * C];

        int answer = 0;
        for (int t = 1; t <= M; t++) {
            for (int i = 0; i < N; i++) {
                int fr = r[i] + dr[d[i]], fc = c[i] + dc[d[i]];
                if (fr < 0 || fr >= R || fc < 0 || fc >= C || wall[fr * C + fc]) {
                    nr[i] = r[i]; nc[i] = c[i]; nd[i] = (d[i] + 1) & 3;
                } else {
                    nr[i] = fr; nc[i] = fc; nd[i] = d[i];
                }
            }
            boolean coll = false;
            // only same-cell collision (no swap rule in this warmup)
            for (int i = 0; i < N; i++) {
                int cell = nr[i] * C + nc[i];
                if (occStamp[cell] == t) { coll = true; break; }
                occStamp[cell] = t; occOwner[cell] = i;
            }
            int[] tr = r; r = nr; nr = tr;
            int[] tc = c; c = nc; nc = tc;
            int[] td = d; d = nd; nd = td;
            if (coll) { answer = t; break; }
        }
        System.out.println(answer);
    }
}