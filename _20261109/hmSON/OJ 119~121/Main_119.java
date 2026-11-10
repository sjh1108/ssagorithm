import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : 구현, 누적, 브루트포스
     *
     * 정상(1) 또는 고장(0) 패널로 이루어진 격자 형태의 태양광 발전 단지
     * 정상 패널로만 이루어진 가장 큰 직사각형 구역의 넓이를 구한다
     *
     * 세로 방향으로 정상 패널이 몇 번 연속되는지 누적값을 기록
     * 각 패널이 우측 하단 모서리인 직사각형의 최대 크기를 확인
     */

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        int[][] panel = new int[r+1][c];

        for(int i=1; i<=r; i++) {
            char[] line = br.readLine().toCharArray();
            for(int j=0; j<c; j++) {
                char ch = line[j];

                // 정상 패널이면 위쪽 연속 패널 개수에 1 추가
                // 고장 패널이면 0으로 초기화
                if(ch == '1') panel[i][j] = panel[i-1][j] + 1;
            }
        }

        int max = 0; // 직사각형 최대 크기
        for(int i=1; i<=r; i++) {
            for(int j=0; j<c; j++) {
                // 각 패널을 우측 하단 모서리로 두는 것이 전제이므로 0인 패널은 무시
                if(panel[i][j] == 0) continue;

                // 모든 경우를 확인하기 위해 현재 다루는 패널부터 왼쪽으로 한 칸씩 이동하며 직사각형의 높이를 갱신
                // leftEdge는 직사각형의 왼쪽 모서리
                int leftEdge = j, width = 1, minHeight = panel[i][j];
                while(leftEdge >= 0) {
                    if(panel[i][leftEdge] < minHeight) minHeight = panel[i][leftEdge];
                    if(minHeight == 0) break; // 높이가 0이므로 직사각형 성립 X

                    int area = minHeight * width;
                    if(area > max) max = area;

                    leftEdge--;
                    width++;
                }
            }
        }

        System.out.println(max);
    }

}