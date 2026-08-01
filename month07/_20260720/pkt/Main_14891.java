import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

//문제 링크/제목: 백준 14891
//
//내가 이해한 문제:
//
//        - 톱니바퀴 4개 바퀴당 톱니 8개
//        - 톱니가 맞닿아서 같
//
//        상태:
//
//        - 현재 날짜
//
//        정리

//
//전이:
//

//
//제약:
//
//
//예:
//
//
//목표:
//
//
//
//예:
//
//
//
//내 풀이 방향:
//

//
//막힌 부분:
//




// 톱니바퀴
public class Main_14891 {

    static int[][] gear = new int[4][8];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 톱니바퀴 상태 입력
        for (int i = 0; i < 4; i++) {
            String line = br.readLine();

            for (int j = 0; j < 8; j++) {
                gear[i][j] = line.charAt(j) -'0';
            }
        }

        // 회전 수 입력
        int K = Integer.parseInt(br.readLine());

        for (int count = 0; count < K; count++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            // 입력은 1번부터지만, 배열은 0번부터 시작
            int gearNumber = Integer.parseInt(st.nextToken()) - 1;

            // 1: 시계방향, -1: 반시계방향
            int direction = Integer.parseInt(st.nextToken());

            // 각 톱니바퀴들이 회전할 방향 저장
            int[] directions = new int[4];
            directions[gearNumber] = direction;

            // 왼쪽 톱니바퀴 확인
            for (int i = gearNumber; i > 0; i--) {
                // 현재 톱니바퀴의 왼쪽 접점: 6번
                // 왼쪽 톱니바퀴의 오른쪽 접점: 2번
                if(gear[i][6] != gear[i-1][2]) {
                    directions[i-1] = -directions[i];
                } else {
                    break;
                }
            }
        }

    }
}
