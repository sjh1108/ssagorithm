// 문제: 관측소 통신망 개통 (SILVER)
// 접근: 케이블을 순서대로 유니온 파인드에 넣고, 실제로 합쳐질 때만 남은 무리 수를 1 줄인다.
//       무리 수가 1이 되는 순간의 케이블 번호가 답이고, 끝까지 1이 안 되면 -1.
import java.io.*;

public class A3_Solution {

    private static int[] parent;
    private static int[] size;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 줄 수가 30만이라 readLine + split 보다 StreamTokenizer 가 안전하다
        StreamTokenizer in = new StreamTokenizer(br);
        StringBuilder sb = new StringBuilder();

        in.nextToken(); int n = (int) in.nval;
        in.nextToken(); int m = (int) in.nval;

        parent = new int[n + 1];
        size = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            parent[i] = i;
            size[i] = 1;
        }

        int comp = n;   // 남은 무리 개수. 실제로 합쳐질 때만 줄어든다
        int ans = -1;
        for (int i = 1; i <= m; i++) {
            in.nextToken(); int u = (int) in.nval;
            in.nextToken(); int v = (int) in.nval;
            int ru = find(u);
            int rv = find(v);
            // u == v(자기 루프)이거나 이미 같은 무리면 ru == rv 라 아무 일도 일어나지 않는다
            if (ru != rv) {
                // union by size — 트리가 한쪽으로 길어지지 않게 큰 쪽에 작은 쪽을 붙인다
                if (size[ru] < size[rv]) {
                    int t = ru; ru = rv; rv = t;
                }
                parent[rv] = ru;
                size[ru] += size[rv];
                comp--;
                if (comp == 1) {
                    ans = i;
                    break;
                }
            }
        }

        sb.append(ans).append('\n');
        System.out.print(sb);
    }

    // 재귀 대신 두 번 훑는 반복문. 두 번째 훑기에서 경로 위 정점을 전부 루트에 직접 붙인다
    private static int find(int x) {
        int r = x;
        while (parent[r] != r) r = parent[r];
        while (parent[x] != r) {
            int nxt = parent[x];
            parent[x] = r;
            x = nxt;
        }
        return r;
    }
}
