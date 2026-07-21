// [#13] 자동 운반 로봇  —  GOLD (서브태스크)
// 접근: 다중 로봇 동시 이동 시뮬레이션. 매 초 모든 로봇의 다음 위치를 한꺼번에 계산한 뒤
//       충돌 판정: (1) 같은 칸에 둘 이상, (2) 두 로봇의 자리 교환(swap). 칸 기준 스탬프
//       배열로 매 초 O(N) 판정(매 초 재할당 없이 타임스탬프로 초기화를 대체).
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
            for (int j = 0; j < C; j++) {
                if (line.charAt(j) == '#') wall[i * C + j] = true;
            }
        }

        // clockwise: 0=U, 1=R, 2=D, 3=L
        int[] dr = {-1, 0, 1, 0};
        int[] dc = {0, 1, 0, -1};
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
        int[] oldStamp = new int[R * C], oldOwner = new int[R * C];

        int answer = 0;
        for (int t = 1; t <= M; t++) {
            // mark old positions
            for (int i = 0; i < N; i++) {
                int cell = r[i] * C + c[i];
                oldStamp[cell] = t; oldOwner[cell] = i;
            }
            // compute next
            for (int i = 0; i < N; i++) {
                int fr = r[i] + dr[d[i]], fc = c[i] + dc[d[i]];
                if (fr < 0 || fr >= R || fc < 0 || fc >= C || wall[fr * C + fc]) {
                    nr[i] = r[i]; nc[i] = c[i]; nd[i] = (d[i] + 1) & 3; // blocked: rotate CW
                } else {
                    nr[i] = fr; nc[i] = fc; nd[i] = d[i];             // advance
                }
            }
            boolean coll = false;
            // (1) same cell
            for (int i = 0; i < N; i++) {
                int cell = nr[i] * C + nc[i];
                if (occStamp[cell] == t) { coll = true; break; }
                occStamp[cell] = t; occOwner[cell] = i;
            }
            // (2) swap
            if (!coll) {
                for (int i = 0; i < N; i++) {
                    int newCell = nr[i] * C + nc[i];
                    if (oldStamp[newCell] == t) {
                        int j = oldOwner[newCell];
                        if (j != i && nr[j] == r[i] && nc[j] == c[i]) { coll = true; break; }
                    }
                }
            }
            int[] tr = r; r = nr; nr = tr;
            int[] tc = c; c = nc; nc = tc;
            int[] td = d; d = nd; nd = td;
            if (coll) { answer = t; break; }
        }
        System.out.println(answer);
    }
}