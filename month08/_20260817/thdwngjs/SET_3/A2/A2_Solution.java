// 문제: 관측소 케이블 부설 (GOLD)
// 접근: 후보 케이블을 비용 오름차순으로 훑으면서 아직 이어지지 않은 두 덩어리를 잇는 것만 채택한다(크루스칼 + 유니온 파인드).
import java.io.*;
import java.util.*;

public class A2_Solution {

    static int[] par;
    static int[] sz;

    // 경로 압축: 루트를 한 번 찾은 뒤 지나온 노드를 전부 루트에 직접 매단다
    static int find(int x) {
        int r = x;
        while (par[r] != r) r = par[r];
        while (par[x] != r) {
            int nx = par[x];
            par[x] = r;
            x = nx;
        }
        return r;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        // ---- 입력 읽기 ----
        st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[] eu = new int[m];
        int[] ev = new int[m];
        // 비용(최대 10^12 < 2^40)을 상위 비트에, 간선 번호(최대 3*10^5 < 2^19)를 하위 비트에 담아
        // long 하나로 정렬한다. 객체 배열 정렬보다 빠르면서 정렬 기준은 비용 오름차순 그대로다.
        long[] key = new long[m];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            eu[i] = Integer.parseInt(st.nextToken());
            ev[i] = Integer.parseInt(st.nextToken());
            long w = Long.parseLong(st.nextToken());
            key[i] = (w << 19) | i;
        }
        Arrays.sort(key);

        // ---- 풀이 ----
        par = new int[n + 1];
        sz = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            par[i] = i;
            sz[i] = 1;
        }

        long total = 0;   // 비용 합은 32비트를 넘으므로 long
        int used = 0;
        int need = n - 1;
        for (int t = 0; t < m; t++) {
            int idx = (int) (key[t] & ((1L << 19) - 1));
            long w = key[t] >>> 19;
            int ru = find(eu[idx]);
            int rv = find(ev[idx]);
            if (ru == rv) continue;      // 이미 같은 덩어리면 건너뛴다
            if (sz[ru] < sz[rv]) {       // union by size
                int tmp = ru; ru = rv; rv = tmp;
            }
            par[rv] = ru;
            sz[ru] += sz[rv];
            total += w;
            used++;
            if (used == need) break;
        }

        // ---- 출력 ----
        sb.append(used == need ? total : -1L).append('\n');
        System.out.print(sb);
    }
}
