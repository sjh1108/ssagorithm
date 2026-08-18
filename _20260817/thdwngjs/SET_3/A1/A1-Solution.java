// 문제: 가장 조용한 통신 경로 (GOLD)
// 접근: 케이블을 잡음 오름차순으로 하나씩 합치면서, 각 컴포넌트에 그 컴포넌트에 걸린 질의 id 집합을
//       매달아 두고 small-to-large 로 병합한다. 합칠 때 양쪽 집합에 같은 질의 id 가 있으면
//       그 질의의 두 끝점이 지금 이 케이블로 처음 이어진 것이므로 답이 현재 잡음 w 로 확정된다.
import java.io.*;
import java.util.*;

public class Solution {

    static int[] par;

    // 재귀 없이 경로 압축 (사슬 모양에서 깊이가 20만까지 갈 수 있어 재귀는 스택 오버플로 위험)
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

    static BufferedReader br;
    static StringTokenizer st;

    static String next() throws IOException {
        while (st == null || !st.hasMoreTokens()) {
            st = new StringTokenizer(br.readLine());
        }
        return st.nextToken();
    }

    static int nextInt() throws IOException { return Integer.parseInt(next()); }

    static long nextLong() throws IOException { return Long.parseLong(next()); }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        StringBuilder sb = new StringBuilder();

        // ---- 입력 읽기 ----
        int n = nextInt();
        int m = nextInt();
        int q = nextInt();

        int[] eu = new int[m];
        int[] ev = new int[m];
        // w <= 10^12 < 2^40, m <= 300000 < 2^19 이므로 (w << 19 | 간선번호) 를 long 하나에 담을 수 있다.
        // 이렇게 하면 비교자 없이 Arrays.sort(long[]) 만으로 잡음 오름차순 정렬이 끝난다.
        final int SHIFT = 19;
        final int MASK = (1 << SHIFT) - 1;
        long[] key = new long[m];
        for (int i = 0; i < m; i++) {
            eu[i] = nextInt();
            ev[i] = nextInt();
            long w = nextLong();          // 잡음은 32비트를 넘으므로 반드시 long
            key[i] = (w << SHIFT) | i;
        }
        Arrays.sort(key);

        par = new int[n + 1];
        int[] sz = new int[n + 1];
        for (int i = 1; i <= n; i++) { par[i] = i; sz[i] = 1; }

        HashSet<Integer>[] qs = new HashSet[n + 1];   // 대표 정점 -> 그 컴포넌트에 걸린 질의 id 집합
        long[] ans = new long[q];
        Arrays.fill(ans, -1L);                        // 끝까지 안 이어지면 -1

        for (int i = 0; i < q; i++) {
            int u = nextInt();
            int v = nextInt();
            if (u == v) {                             // 케이블을 하나도 지나지 않아도 되므로 0
                ans[i] = 0L;
                continue;
            }
            if (qs[u] == null) qs[u] = new HashSet<Integer>();
            qs[u].add(i);
            if (qs[v] == null) qs[v] = new HashSet<Integer>();
            qs[v].add(i);
        }

        // ---- 풀이: 잡음 오름차순 유니온 파인드 + 질의 집합 small-to-large ----
        for (int t = 0; t < m; t++) {
            long k = key[t];
            int idx = (int) (k & MASK);
            long w = k >>> SHIFT;
            int ru = find(eu[idx]);
            int rv = find(ev[idx]);
            if (ru == rv) continue;                   // 다중 간선/이미 연결된 경우

            HashSet<Integer> a = qs[ru];
            HashSet<Integer> b = qs[rv];
            if (a == null) a = new HashSet<Integer>();
            if (b == null) b = new HashSet<Integer>();
            if (a.size() < b.size()) {                // 작은 집합을 큰 집합에 부어 넣어야 총 이동이 O(Q log Q)
                HashSet<Integer> tmp = a; a = b; b = tmp;
            }
            if (!b.isEmpty()) {
                for (Integer qid : b) {
                    if (a.contains(qid)) {
                        ans[qid] = w;                 // 지금 이 케이블로 두 끝점이 처음 이어졌다
                        a.remove(qid);
                    } else {
                        a.add(qid);
                    }
                }
            }

            if (sz[ru] < sz[rv]) {                    // 컴포넌트 합치기는 union by size
                int tmp = ru; ru = rv; rv = tmp;
            }
            par[rv] = ru;
            sz[ru] += sz[rv];
            qs[ru] = a;
            qs[rv] = null;
        }

        // ---- 출력 ----
        for (int i = 0; i < q; i++) {
            sb.append(ans[i]).append('\n');
        }
        System.out.print(sb);
    }
}
