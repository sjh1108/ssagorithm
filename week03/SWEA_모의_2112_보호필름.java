import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.StringTokenizer;

public class SWEA_모의_2112_보호필름 {

    static StringBuilder output = new StringBuilder();
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer tokens;

    static int T, D, W, K;
    static int[][] map;
    static int MIN;

    public static void main(String[] args) throws NumberFormatException, IOException {
        input = new BufferedReader(new StringReader(instr));
        T = Integer.parseInt(input.readLine());
        for (int t = 1; t <= T; t++) {
            tokens = new StringTokenizer(input.readLine());
            D = Integer.parseInt(tokens.nextToken());
            W = Integer.parseInt(tokens.nextToken());
            K = Integer.parseInt(tokens.nextToken());
            map = new int[D][W];
            for (int d = 0; d < D; d++) {
                tokens = new StringTokenizer(input.readLine());
                for (int w = 0; w < W; w++) {
                    map[d][w] = Integer.parseInt(tokens.nextToken());
                }
            } // 입력 완료

            // for(int [] row: map) {
            // System.out.println(Arrays.toString(row));
            // }

            MIN = Integer.MAX_VALUE;
            // 문제 풀어보자.
            dfs(0, new int [D], 0 );
            output.append("#").append(t).append(" ").append(MIN).append("\n");
        }
        System.out.println(output);
    }

    /**
     * 
     * @param row 문제의 크기 -> 기저조건으로 가기위해 필
     * @param status 약물을 투입한 상태
     * @param inject 약물을 투입한 회수
     */
    private static void dfs(int row, int[] status, int inject) {
        
        // 3. 가지치기
       if(inject  > MIN) {
           return;
       }

        // 2. 기저 케이스
        if (row == D) {
            // 상태 점검 필요!!!
            if(check(status)) {
                MIN = Math.min(MIN, inject);
            }
            return;
        }


        // 1. 재귀 케이스 : 시도:X(-1), A(0), B(1)
        for (int i = -1; i < 2; i++) {
            status[row] = i;
            if (i == -1) {
                dfs(row + 1, status, inject);
            } else {
                dfs(row + 1, status, inject + 1);
            }
        }
    }

    // status[0] = -1 : 약을 안친거 --> map, 0, 1 --> status의 값 활용
    private static boolean check(int[] status) {
        for (int w = 0; w < W; w++) {
            int base = -1;
            int k = 0; // 연속된값이 나온 개수
            for (int d = 0; d < D; d++) {
                int target = status[d] == -1 ? map[d][w] : status[d];
                if (d == 0) { // 첫번째 행 - 기준!!!!!!!
                    base = target;
                    k = 1;
                } else {
                    if (target == base) {
                        k++;
                        if (k == K) {
                            break;
                        }
                    } else {
                        base = target;
                        k = 1;
                    }
                }
            } // column 점검 끝
            if (k < K) {
                return false;
            }
        }
        return true;
    }



    private static String instr = "10\r\n" +
                                  "6 8 3\r\n" +
                                  "0 0 1 0 1 0 0 1\r\n" +
                                  "0 1 0 0 0 1 1 1\r\n" +
                                  "0 1 1 1 0 0 0 0\r\n" +
                                  "1 1 1 1 0 0 0 1\r\n" +
                                  "0 1 1 0 1 0 0 1\r\n" +
                                  "1 0 1 0 1 1 0 1\r\n" +
                                  "6 8 3\r\n" +
                                  "1 1 1 1 0 0 1 0\r\n" +
                                  "0 0 1 1 0 1 0 1\r\n" +
                                  "1 1 1 1 0 0 1 0\r\n" +
                                  "1 1 1 0 0 1 1 0\r\n" +
                                  "1 1 0 1 1 1 1 0\r\n" +
                                  "1 1 1 0 0 1 1 0\r\n" +
                                  "6 8 4\r\n" +
                                  "1 1 0 0 0 1 1 0\r\n" +
                                  "1 0 1 0 0 1 1 1\r\n" +
                                  "0 1 0 0 1 1 0 0\r\n" +
                                  "1 0 1 0 0 0 0 0\r\n" +
                                  "1 1 0 0 0 0 0 0\r\n" +
                                  "1 0 0 0 1 1 1 1\r\n" +
                                  "6 4 4\r\n" +
                                  "1 1 0 0\r\n" +
                                  "0 1 0 1\r\n" +
                                  "0 0 0 1\r\n" +
                                  "1 1 1 1\r\n" +
                                  "1 1 0 1\r\n" +
                                  "1 0 1 0\r\n" +
                                  "6 10 3\r\n" +
                                  "0 1 0 0 0 1 0 0 1 1\r\n" +
                                  "0 1 1 0 0 1 0 0 1 0\r\n" +
                                  "0 1 0 0 1 0 1 1 1 1\r\n" +
                                  "0 0 0 0 0 1 1 1 1 0\r\n" +
                                  "0 1 0 0 1 1 1 1 1 1\r\n" +
                                  "1 0 0 0 1 1 0 0 1 1\r\n" +
                                  "6 6 5\r\n" +
                                  "0 0 0 0 0 0\r\n" +
                                  "0 0 0 0 0 0\r\n" +
                                  "0 0 0 0 0 0\r\n" +
                                  "0 0 0 0 0 0\r\n" +
                                  "0 0 0 0 0 0\r\n" +
                                  "0 0 0 0 0 0\r\n" +
                                  "6 6 4\r\n" +
                                  "1 1 1 1 1 1\r\n" +
                                  "0 0 0 0 0 1\r\n" +
                                  "0 1 1 1 0 1\r\n" +
                                  "0 1 0 1 0 1\r\n" +
                                  "0 1 0 0 0 1\r\n" +
                                  "0 1 1 1 1 1\r\n" +
                                  "8 15 3\r\n" +
                                  "0 1 1 0 0 1 1 0 1 1 0 0 0 0 0\r\n" +
                                  "1 0 0 0 1 1 0 0 0 0 0 1 0 1 1\r\n" +
                                  "1 1 0 1 0 1 0 1 0 1 0 1 0 0 0\r\n" +
                                  "0 1 1 1 0 0 1 0 0 0 0 1 0 0 0\r\n" +
                                  "0 0 0 0 0 0 1 0 0 0 1 1 0 0 1\r\n" +
                                  "1 0 1 0 0 1 0 1 1 1 1 0 1 1 1\r\n" +
                                  "0 0 0 0 0 1 1 1 0 0 0 0 0 1 0\r\n" +
                                  "0 0 1 0 1 1 0 1 1 0 0 0 1 0 0\r\n" +
                                  "10 20 4\r\n" +
                                  "1 0 1 1 1 1 1 1 1 1 0 0 1 1 1 0 1 1 0 1\r\n" +
                                  "1 1 0 1 1 1 0 0 1 0 0 0 1 1 1 1 0 0 1 0\r\n" +
                                  "1 1 0 1 1 0 0 0 1 1 1 1 1 0 0 1 1 0 1 0\r\n" +
                                  "0 0 0 1 1 0 0 0 0 1 0 0 1 0 1 1 1 0 1 0\r\n" +
                                  "0 1 1 0 1 0 1 0 1 0 0 1 0 0 0 0 1 1 1 1\r\n" +
                                  "1 0 1 0 1 0 1 1 0 0 0 0 1 1 1 0 0 0 0 0\r\n" +
                                  "0 1 0 0 1 1 0 0 0 0 0 1 1 0 0 1 1 0 1 1\r\n" +
                                  "1 0 0 0 0 1 0 1 1 0 1 1 0 1 0 0 1 1 1 0\r\n" +
                                  "0 1 1 0 0 1 0 1 0 0 0 0 0 0 0 1 1 1 0 1\r\n" +
                                  "0 0 0 0 0 0 1 1 0 0 1 1 0 0 0 0 0 0 1 0\r\n" +
                                  "13 20 5\r\n" +
                                  "1 1 0 1 0 0 0 1 1 1 1 0 0 0 1 1 1 0 0 0\r\n" +
                                  "1 1 1 1 0 1 0 1 0 0 0 0 1 0 0 0 0 1 0 0\r\n" +
                                  "1 0 1 0 1 1 0 1 0 1 1 0 0 0 0 1 1 0 1 0\r\n" +
                                  "0 0 1 1 0 1 1 0 1 0 0 1 1 0 0 0 1 1 1 1\r\n" +
                                  "0 0 1 0 0 1 0 0 1 0 0 0 0 1 0 0 0 0 1 1\r\n" +
                                  "0 0 1 0 0 0 0 0 0 0 0 0 1 1 1 0 0 1 0 1\r\n" +
                                  "0 0 0 1 0 0 0 0 0 0 1 1 0 0 0 1 0 0 1 0\r\n" +
                                  "1 1 1 0 0 0 1 0 0 1 1 1 0 1 0 1 0 0 1 1\r\n" +
                                  "0 1 1 1 1 0 0 0 1 1 0 1 0 0 0 0 1 0 0 1\r\n" +
                                  "0 0 0 0 1 0 1 0 0 0 1 0 0 0 0 1 1 1 1 1\r\n" +
                                  "0 1 0 0 1 1 0 0 1 0 0 0 0 1 0 1 0 0 1 0\r\n" +
                                  "0 0 1 1 0 0 1 0 0 0 1 0 1 1 0 1 1 1 0 0\r\n" +
                                  "0 0 0 1 0 0 1 0 0 0 1 0 1 1 0 0 1 0 1 0";;

}
