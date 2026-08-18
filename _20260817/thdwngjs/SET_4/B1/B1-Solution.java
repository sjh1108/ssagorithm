// 문제: 컨베이어 벨트 부품 검수 (GOLD)
// 접근: 오른쪽 끝을 하나씩 늘리는 투 포인터 + 최댓값/최솟값 단조 덱 2개로 O(N)
import java.io.*;

public class Solution {
    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader(System.in);
        StringBuilder sb = new StringBuilder();

        // ---- 입력 읽기 ----
        int n = (int) in.nextLong();
        long d = in.nextLong();           // D 는 최대 2*10^9 라 int 로 담으면 넘친다
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = (int) in.nextLong();

        // ---- 풀이 ----
        // maxq: 값이 감소하는 인덱스 덱(앞이 구간 최댓값), minq: 값이 증가하는 인덱스 덱(앞이 구간 최솟값)
        int[] maxq = new int[n];
        int[] minq = new int[n];
        int maxHead = 0, maxTail = 0;     // [head, tail) 이 유효 구간
        int minHead = 0, minTail = 0;
        int l = 0;
        long ans = 0;                     // 답은 최대 N(N+1)/2 ≈ 4.5*10^10 이라 long 필수

        for (int r = 0; r < n; r++) {
            int v = a[r];
            // 뒤에서 자기보다 작거나 같은(최댓값 덱) / 크거나 같은(최솟값 덱) 후보는 앞으로 쓸모가 없다
            while (maxTail > maxHead && a[maxq[maxTail - 1]] <= v) maxTail--;
            maxq[maxTail++] = r;
            while (minTail > minHead && a[minq[minTail - 1]] >= v) minTail--;
            minq[minTail++] = r;

            // 최소 l 은 r 이 커질 때 절대 되돌아가지 않으므로 전체 이동 횟수가 O(N)
            while ((long) a[maxq[maxHead]] - (long) a[minq[minHead]] > d) {
                if (maxq[maxHead] == l) maxHead++;
                if (minq[minHead] == l) minHead++;
                l++;
            }
            ans += (long) (r - l + 1);    // 오른쪽 끝이 r 인 유효 구간의 개수
        }

        // ---- 출력 ----
        sb.append(ans).append('\n');
        System.out.print(sb);
    }

    // 3MB 를 넘는 입력이라 토큰 단위 수동 파서를 쓴다
    static class FastReader {
        private final InputStream is;
        private final byte[] buf = new byte[1 << 16];
        private int len = 0, ptr = 0;

        FastReader(InputStream is) { this.is = is; }

        private int read() throws IOException {
            if (ptr == len) {
                len = is.read(buf, 0, buf.length);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buf[ptr++];
        }

        long nextLong() throws IOException {
            int c = read();
            while (c != -1 && c != '-' && (c < '0' || c > '9')) c = read();
            boolean neg = false;
            if (c == '-') { neg = true; c = read(); }
            long x = 0;
            while (c >= '0' && c <= '9') { x = x * 10 + (c - '0'); c = read(); }
            return neg ? -x : x;
        }
    }
}
