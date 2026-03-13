import java.io.*;
import java.util.*;

public class Main_17130_토끼가_정보섬에_올라온_이유 {

    /*
     * BOJ_17130 : 토끼가 정보섬에 올라온 이유 (Gold_4)
     * 자료구조 및 알고리즘 : DP
     *
     * [문제 요약]
     * - 토끼는 r×c 격자의 시작점 'R'에서 출발한다.
     * - 이동은 항상 오른쪽으로만 진행하며, 한 번 이동할 때 (오른쪽 위 / 오른쪽 / 오른쪽 아래)로만 갈 수 있다.
     * - 벽('#')은 이동 불가, 당근('C')은 지나가면 1개 획득, 도착지('O')에 도달하면 종료 가능.
     * - 도착지에 도달할 수 있는 경우 중, 획득 가능한 당근의 최댓값을 출력한다.
     * - 도달할 수 있는 도착지가 없으면 -1을 출력한다.
     *
     * [핵심 아이디어]
     * - 이동이 항상 "열이 1씩 증가"하는 방향(오른쪽)으로만 이루어지므로,
     *   순환이 없고 열 기준으로 DP를 자연스럽게 전개할 수 있다.
     * - dp[y][x] = (y, x)에 도달했을 때 얻을 수 있는 당근 최대 개수
     * - (y, x)는 이전 열(x-1)의 (y-1, x-1), (y, x-1), (y+1, x-1)에서만 올 수 있다.
     * - 도달 불가능한 칸은 dp를 -1로 유지하여, 이후 전이에서 자동으로 제외되도록 한다.
     *
     * [구현 메모]
     * - dp를 전부 -1로 초기화하고, 시작점(R)만 0으로 설정한다.
     * - 열 i = 1..C-1 순서로 진행:
     *   - 현재 칸이 벽이면 skip
     *   - 이전 열(i-1)에서 올 수 있는 3칸 중 dp != -1 인 것이 하나라도 있으면 전이 가능
     *   - 그 중 최대값을 dp[j][i]로 가져오고,
     *     현재 칸이 'C'이면 +1 한다.
     * - 도착지 'O'는 당근을 추가로 주지 않으므로,
     *   'O'에 도달 가능한 경우 dp값으로 max를 갱신한다.
     * - flag는 "이 칸이 실제로 전이 가능한 칸인지"를 나타내며,
     *   전이 가능한 이전 상태가 하나도 없으면 dp를 건드리지 않는다.
     *
     * [시간 복잡도]
     * - 각 칸에서 최대 3방향만 확인 -> O(R * C)
     */

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        char[][] map = new char[r][c];

        int sy = -1, sx = -1;
        for(int i=0; i<r; i++) {
            String line = br.readLine();
            for(int j=0; j<c; j++) {
                char ch = line.charAt(j);
                map[i][j] = ch;

                if(ch == 'R') { sy = i; sx = j; }
            }
        }

        int[][] dp = new int[r][c];
        for(int i=0; i<r; i++) Arrays.fill(dp[i], -1);
        dp[sy][sx] = 0; // 시작점

        int max = -1;
        // 오른쪽으로만 진행하므로 열 단위로 DP 전개
        for(int i=1; i<c; i++) {
            for(int j=0; j<r; j++) {
                if(map[j][i] == '#') continue; // 벽은 불가

                boolean flag = false; // 이전 열에서 전이 가능 여부
                for(int k=j-1; k<=j+1; k++) {
                    if(k < 0 || k >= r || dp[k][i-1] == -1) continue;
                    dp[j][i] = Math.max(dp[j][i], dp[k][i-1]);
                    flag = true;
                }

                // 전이 가능한 이전 상태가 없으면 도달 불가
                if(!flag) continue;

                // 당근 칸이면 1개 추가
                if(map[j][i] == 'C') dp[j][i]++;
                    // 도착지에 도달 가능하면 최대 당근 수 갱신
                else if(map[j][i] == 'O') max = Math.max(max, dp[j][i]);
            }
        }

        System.out.println(max);
    }

}