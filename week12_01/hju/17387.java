import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 오버플로우 방지를 위해 long 사용
        long x1 = Long.parseLong(st.nextToken());
        long y1 = Long.parseLong(st.nextToken());
        long x2 = Long.parseLong(st.nextToken());
        long y2 = Long.parseLong(st.nextToken());

        st = new StringTokenizer(br.readLine());
        long x3 = Long.parseLong(st.nextToken());
        long y3 = Long.parseLong(st.nextToken());
        long x4 = Long.parseLong(st.nextToken());
        long y4 = Long.parseLong(st.nextToken());

        System.out.println(isIntersect(x1, y1, x2, y2, x3, y3, x4, y4));
    }

    // CCW: 세 점의 방향성을 판별 (-1, 0, 1)
    // (x2-x1)(y3-y1) - (x3-x1)(y2-y1) 공식을 사용 (벡터의 외적)
    static int ccw(long x1, long y1, long x2, long y2, long x3, long y3) {
        long result = (x1 * y2 + x2 * y3 + x3 * y1) - (y1 * x2 + y2 * x3 + y3 * x1);
        if (result > 0) return 1;
        else if (result < 0) return -1;
        else return 0;
    }

    static int isIntersect(long x1, long y1, long x2, long y2, long x3, long y3, long x4, long y4) {
        int p123 = ccw(x1, y1, x2, y2, x3, y3);
        int p124 = ccw(x1, y1, x2, y2, x4, y4);
        int p341 = ccw(x3, y3, x4, y4, x1, y1);
        int p342 = ccw(x3, y3, x4, y4, x2, y2);

        // 두 선분이 일직선 상에 있는 경우 (모든 CCW가 0)
        if (p123 * p124 == 0 && p341 * p342 == 0) {
            // 비교를 위해 좌표 정렬 (p1이 p2보다 작게, p3가 p4보다 작게)
            if (Math.min(x1, x2) <= Math.max(x3, x4) && Math.min(x3, x4) <= Math.max(x1, x2) &&
                Math.min(y1, y2) <= Math.max(y3, y4) && Math.min(y3, y4) <= Math.max(y1, y2)) {
                return 1;
            }
            return 0;
        }

        // 일반적인 교차 상황
        // 각 선분을 기준으로 다른 선분의 두 점이 서로 반대 방향(혹은 선 위)에 있어야 함
        if (p123 * p124 <= 0 && p341 * p342 <= 0) {
            return 1;
        }

        return 0;
    }
}