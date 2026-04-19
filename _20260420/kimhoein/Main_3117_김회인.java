package study;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Main_3117_김회인 {
    static final int MAX_LOG = 30;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        // 핵심 수정 포인트: 첫 동영상 시청에 1분을 쓰므로, 실제 점프 횟수는 M - 1번
        int jumps = M - 1;

        int[] startNode = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            startNode[i] = Integer.parseInt(st.nextToken());
        }

        int[][] up = new int[MAX_LOG][K + 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= K; i++) {
            up[0][i] = Integer.parseInt(st.nextToken());
        }

        // 1. 희소 배열(Sparse Table) 채우기
        for (int i = 1; i < MAX_LOG; i++) {
            for (int j = 1; j <= K; j++) {
                up[i][j] = up[i - 1][up[i - 1][j]];
            }
        }

        StringBuilder sb = new StringBuilder();

        // 2. 각 학생별로 jumps(M - 1)번 후의 위치 계산
        for (int i = 0; i < N; i++) {
            int curr = startNode[i];
            
            // M이 아닌 jumps 변수를 사용하여 비트 연산 수행
            for (int j = 0; j < MAX_LOG; j++) {
                if (((jumps >> j) & 1) == 1) {
                    curr = up[j][curr];
                }
            }
            
            // 결과를 StringBuilder에 공백과 함께 추가
            sb.append(curr).append(" ");
        }

        // 마지막에 추가된 불필요한 공백을 trim()으로 제거 후 출력
        System.out.println(sb.toString().trim());
    }
}