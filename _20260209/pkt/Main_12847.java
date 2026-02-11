import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_12847 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken()); // 전체 일수
        int m = Integer.parseInt(st.nextToken()); // 일할 수 있는 일수

        long[] salary = new long[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            salary[i] = Long.parseLong(st.nextToken());
        }

        // 1. 초기 윈도우 설정 (첫 m일간의 합)
        long currentSum = 0;
        for (int i = 0; i < m; i++) {
            currentSum += salary[i];
        }

        long maxSum = currentSum;

        // 2. 슬라이딩 윈도우 진행
        // i는 새로 추가될 인덱스, (i - m)은 제거될 인덱스
        for (int i = m; i < n; i++) {
            currentSum += salary[i] - salary[i - m];
            maxSum = Math.max(maxSum, currentSum);
        }

        // 3. 결과 출력
        System.out.println(maxSum);
    }
}
