import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_1475 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();

        int[] cnt = new int[10];

        // 숫자세기
        for (int i = 0; i < s.length(); i++) {
            int d = s.charAt(i) -'0';
            cnt[d]++;
        }

        // 6과 9는 한 세트에서 사용할 수 있기에 늘릴 필요없음.
        int sixNine = cnt[6] + cnt[9];
        cnt[6] = (sixNine + 1) / 2;
        cnt[9] = 0;

        int ans = 0;
        for (int i = 0; i < 10; i++) {
            ans = Math.max(ans, cnt[i]);
        }

        System.out.println(ans);
    }
}

// 다솜이의 옆집에서는
// 스티커 세트를 0~9가 한장씩 들어 있음
// 이때 최소 몇 개?
// 각 배열에서 나열된 값 중 최대값 찾기