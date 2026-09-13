// 문제: 검사창에 잡힌 최저 높이 (GOLD)
// 접근: 값이 증가하도록 유지되는 인덱스 덱을 굴려 각 창의 최솟값을 O(1) 에 얻고 더한다 (슬라이딩 윈도우 최솟값, O(N))
import java.io.*;

public class B2_Solution {
    public static void main(String[] args) throws IOException {
        DataInputStream in = new DataInputStream(new BufferedInputStream(System.in, 1 << 16));
        StringBuilder sb = new StringBuilder();

        // ---- 입력 읽기 ----
        int n = nextInt(in);
        int k = nextInt(in);
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = nextInt(in);

        // ---- 풀이 ----
        // 덱을 배열로 쓴다. 인덱스는 한 번 들어가고 한 번만 나오므로 크기 n 이면 충분하다.
        int[] dq = new int[n];
        int head = 0, tail = 0;   // [head, tail) 구간이 덱의 내용
        long total = 0;
        for (int i = 0; i < n; i++) {
            int v = a[i];
            // 뒤에 더 작거나 같은 v 가 들어오므로, 그보다 큰 값들은 앞으로 어떤 창에서도 최솟값이 될 수 없다
            while (head < tail && a[dq[tail - 1]] >= v) tail--;
            dq[tail++] = i;
            // 창이 한 칸 움직이면 밖으로 나가는 인덱스는 많아야 하나뿐이라 if 로 충분하다
            if (dq[head] <= i - k) head++;
            // 오른쪽 끝이 i 인 창은 i >= k-1 일 때부터 완성된다
            if (i >= k - 1) total += a[dq[head]];   // 합이 3e14 규모라 long 필수
        }

        // ---- 출력 ----
        sb.append(total).append('\n');
        System.out.print(sb);
    }

    // 음수 부호를 포함한 정수 하나를 읽는 빠른 입력
    private static int nextInt(DataInputStream in) throws IOException {
        int ret = 0;
        int b = in.read();
        while (b < '0' && b != '-') b = in.read();
        boolean neg = false;
        if (b == '-') {
            neg = true;
            b = in.read();
        }
        while (b >= '0' && b <= '9') {
            ret = ret * 10 + (b - '0');
            b = in.read();
        }
        return neg ? -ret : ret;
    }
}
