// 문제: 컨베이어 벨트의 다음 높은 부품 (SILVER)
// 접근: 높이가 단조 감소하도록 유지되는 인덱스 스택을 왼쪽부터 쌓고,
//       새 부품이 스택 top 보다 높으면 pop 하며 그 부품들의 답을 현재 위치로 확정한다. O(N)
import java.io.*;

public class Solution {
    private static InputStream is;
    private static final byte[] ibuf = new byte[1 << 16];
    private static int ipos = 0, ilen = 0;

    public static void main(String[] args) throws IOException {
        is = System.in;

        // ---- 입력 읽기 ----
        // N 이 30만이고 값이 최대 10자리라 입력이 3MB 가까이 된다.
        // StringTokenizer 를 쓰면 문자열 객체가 30만 개 생기므로 바이트 단위로 직접 파싱한다.
        int n = nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = nextInt();

        // ---- 풀이 ----
        int[] ans = new int[n];
        int[] stack = new int[n];   // 인덱스를 담는 스택. 각 인덱스는 최대 한 번 push/pop 되므로 전체 O(N)
        int top = 0;
        for (int i = 0; i < n; i++) {
            int cur = a[i];
            // 스택은 아래로 갈수록 높이가 큰 단조 감소 상태로 유지된다.
            // 현재 부품보다 낮은 부품들은 "오른쪽의 첫 더 높은 부품"이 바로 지금이므로 확정하고 꺼낸다.
            // 조건이 < 이므로 높이가 같은 부품은 pop 되지 않는다(같은 높이는 답이 될 수 없다).
            while (top > 0 && a[stack[top - 1]] < cur) {
                ans[stack[--top]] = i + 1;   // 위치는 1-인덱스
            }
            stack[top++] = i;
        }
        // 끝까지 스택에 남은 인덱스들은 오른쪽에 더 높은 부품이 없다는 뜻이다.
        while (top > 0) ans[stack[--top]] = -1;

        // ---- 출력 ----
        // 출력이 2MB 정도라 StringBuilder 에 모아 한 번에 내보낸다.
        StringBuilder sb = new StringBuilder(n * 8 + 16);
        for (int i = 0; i < n; i++) {
            if (i > 0) sb.append(' ');
            sb.append(ans[i]);
        }
        sb.append('\n');
        OutputStream out = new BufferedOutputStream(System.out, 1 << 16);
        out.write(sb.toString().getBytes("US-ASCII"));
        out.flush();
    }

    private static int read() throws IOException {
        if (ipos == ilen) {
            ilen = is.read(ibuf, 0, ibuf.length);
            ipos = 0;
            if (ilen <= 0) return -1;
        }
        return ibuf[ipos++];
    }

    private static int nextInt() throws IOException {
        int c = read();
        while (c != -1 && (c < '0' || c > '9')) c = read();
        int ret = 0;
        while (c >= '0' && c <= '9') {
            ret = ret * 10 + (c - '0');
            c = read();
        }
        return ret;
    }
}
