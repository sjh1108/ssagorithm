import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main_2829_김민상 {
    static int N;
    static int[][] map;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine().trim());
        map = new int[N+1][N+1];

        for(int i = 1; i <= N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 1; j <= N; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 대각선 누적합 배열
        int[][] mainCross = new int[N+2][N+2];
        int[][] subCross = new int[N+2][N+2];

        for(int i = 1;  i <= N; i++) {
            for(int j = 1; j <= N; j++) {
                // 현재 값 + 왼쪽 위
                mainCross[i][j] = map[i][j] + mainCross[i-1][j-1];
                subCross[i][j] = map[i][j] + subCross[i-1][j+1];
            }
        }
        int maxBeauty = Integer.MIN_VALUE;
        
        // k : 크기 값 결정
        for(int k = 0; k < N; k++) {
            for(int i = k + 1;  i <= N; i++) {
                for(int j = 1; j <= N - k; j++) {
                    // 주 대각선 범위 : (i-k, j) ~ (i, j+k)
                    int mainSum = mainCross[i][j + k] - mainCross[i - k - 1][j - 1];
                    // 부 대각선 (i-k, j+k) ~ (i, j)
                    int subSum = subCross[i][j] - subCross[i - k - 1][j + k + 1];

                    // 최댓값
                    maxBeauty = Math.max(maxBeauty, mainSum - subSum);
                }
            }
        }
        System.out.println(maxBeauty);
    }
}