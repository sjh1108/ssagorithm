import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main {
    public static int N;
    public static int[] board;
    public static int count = 0;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        // board[행] = 열
        board = new int[N];

        // 0번째 행부터 퀸 배치를 시작
        solve(0);

        System.out.println(count);
    }

    public static void solve(int r) {

        if(r == N) {
            count++;
            return;
        }

        for (int c = 0; c < N; c++) {

            board[r] = c;

            if(isSafe(r)){

                solve(r+1);

            }
        }
    }

    public static boolean isSafe(int currentRow) {
        for (int i = 0; i < currentRow; i++) {
            if (board[currentRow] == board[i]) {
                return false; // 같은 열에 다른 퀸이 있는지 확인
            }

            if (Math.abs(currentRow - i) == Math.abs(board[currentRow] - board[i])) {
                return false; // 대각선에 다른 퀸이 있는지 확인(좌표간 차이 절대값으로 확인 가능)
            }
        }
        return  true; // 퀸이 서로 먹을 수 없는 경우
    }
}