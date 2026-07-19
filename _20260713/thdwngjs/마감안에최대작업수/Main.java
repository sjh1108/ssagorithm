// [#16] 마감 안에 최대 작업 수  —  SILVER
// 접근: 마감 오름차순 정렬 + 그리디. 지금까지 고른 수가 마감 d보다 작으면(빈 슬롯이
//       있으면) 채택하고 개수를 늘린다. 라인이 1개일 때 최대 개수를 준다.
// 복잡도: O(N log N)
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine().trim());
        long[] d = new long[N];
        for (int i = 0; i < N; i++) d[i] = Long.parseLong(br.readLine().trim());

        Arrays.sort(d);
        // 마감 오름차순으로 보며, 지금까지 고른 수가 마감보다 작으면(빈 슬롯이 있으면) 채택
        long cnt = 0;
        for (int i = 0; i < N; i++) {
            if (cnt < d[i]) cnt++;
        }
        System.out.println(cnt);
    }
}