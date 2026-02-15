package bk.silver; // 실버 5

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 이해
 * - 100x100의 그림
 * - N개의 종이로 그림을 가림
 * - 입력으로 가림 종이의 왼쪽아래 오른쪽위 좌표 들어옴
 * - M번 초과해서 그림을 덮어야 안보이는 걸로 침
 *
 * 입력
 * - N M (<= 50)
 * - 종이좌표 * N (자연수. 1~100)
 *
 * 출력
 * - 보이지 않는 그림 칸 개수
 *
 * 전략
 * - 모든 종이를 2차원배열에 배치한 후 탐색한다고하면
 * - 최대 100*100 연산을 50번해야함 + 100*100
 * - 10000*50 + 10000 = 510,000
 * - 걍 해도될듯?
 * - 일단 무식하게 다 계산해보고 하나하나 세보도록 하겠습니다
 * -
 */
public class _Main_1531_투명{

    static int N, M;
    static int[][] map;
    static int cnt;
    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[101][101]; // 자연수이므로 0 인덱스는 버림

        // 가림 종이 수만큼 반복
        for(int i = 0; i < N; i++){
            // 가림 종이 좌표를 입력받아서 map에 덮힐때마다 +1
            st = new StringTokenizer(br.readLine());
            int startX = Integer.parseInt(st.nextToken());
            int startY = Integer.parseInt(st.nextToken());

            int endX = Integer.parseInt(st.nextToken());
            int endY = Integer.parseInt(st.nextToken());

            for(int x = startX; x <= endX; x++){
                for(int y = startY; y <= endY; y++){
                    map[x][y] += 1;
                }
            }
        }

        // 1부터 100까지 그림을 탐색하며 보이지 않는 그림 카운팅
        // M보다 큰 수가 들어있어야 카운팅함
        for(int i = 1; i <=100; i++){
            for(int j = 1; j <=100; j++){
                if(map[i][j] > M){
                    cnt++;
                }
            }
        }

        System.out.println(cnt);
    }
}