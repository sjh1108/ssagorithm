import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : DFS(스택), Flood Fill
     *
     * 섬과 수역의 포함 관계가 트리를 이룰 때, 가장 깊은 섬의 깊이와 깊이별 섬 개수를 구해야 함
     *
     * 육지는 8방향, 물은 4방향으로만 인접한다는 비대칭이 핵심
     * -> DFS 진행시 탐색 대상에 따라 방향 개수를 다르게 적용
     *
     * 깊이는 바깥에서 안쪽으로 한 겹씩 증가하므로, 바깥 바다부터 시작해 안쪽으로 번갈아 확장
     * 1. 해도 테두리의 물 칸에서 시작해 바깥 바다를 깊이 1로 채움
     * 2. 아직 채워지지 않은 칸을 발견하면 인접한 기존 영역의 깊이를 참조해 자신의 깊이를 결정
     *    - 육지라면 인접 수역과 같은 깊이 (바깥 바다와 맞닿은 섬이 깊이 1)
     *    - 물이라면 인접 섬보다 한 단계 깊음 (호수는 자신을 감싼 섬의 안쪽)
     * 3. 결정된 깊이로 해당 영역 전체를 Flood Fill
     *
     * 격자를 좌상단부터 순회하면 바깥쪽 영역이 항상 먼저 처리되므로,
     * 어떤 칸에 도달했을 때 그 칸과 맞닿은 영역 중 최소 하나는 이미 깊이가 확정되어 있음
     */

    static int r, c;
    static boolean[][] isLand; // true: 육지, false: 물
    static int[][] contourMap; // 각 칸이 속한 영역의 깊이. 0은 미확인 위치
    static int[] stack, dy = {-1, -1, 0, 1, 1, 1, 0, -1}, dx = {0, 1, 1, 1, 0, -1, -1, -1};
    // 방향 배열은 8방향을 시계 순으로 배치
    // 짝수 인덱스(0, 2, 4, 6)만 취하면 상우하좌 4방향이 되어 하나의 배열로 두 경우를 처리

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        isLand = new boolean[r][c];
        for(int i=0; i<r; i++) {
            char[] line = br.readLine().toCharArray();
            for(int j=0; j<c; j++) {
                isLand[i][j] = line[j] == '#';
            }
        }

        stack = new int[r*c]; // DFS 스택(2차원 좌표를 1차원 형태로 관리)

        // 1단계 : 테두리의 물 칸에서 출발해 바깥 바다를 깊이 1로 채움
        // 바깥 바다는 하나뿐이지만 테두리의 여러 지점에서 접근할 수 있으므로 미확정 여부를 확인
        contourMap = new int[r][c];
        for(int i=0; i<r; i++) {
            if(!isLand[i][0] && contourMap[i][0] == 0) dfs(i, 0, false, 1);
            if(!isLand[i][c-1] && contourMap[i][c-1] == 0) dfs(i, c-1, false, 1);
        }
        for(int i=1; i<c-1; i++) {
            if(!isLand[0][i] && contourMap[0][i] == 0) dfs(0, i, false, 1);
            if(!isLand[r-1][i] && contourMap[r-1][i] == 0) dfs(r-1, i, false, 1);
        }

        // 깊이가 1 늘어나려면 사방으로 육지 한 겹과 물 한 겹이 추가로 필요
        // 따라서 가능한 최대 깊이는 짧은 변의 길이를 4로 나눈 값(나머지 올림)
        int min = Math.min(r, c);
        int maxPossibleDepth = min/4 + (min%4 == 0 ? 0 : 1); // 입력받은 맵 크기에서 나올 수 있는 최대 깊이(1000*1000 기준 최대 깊이는 250)
        int[] islandsByDepth = new int[maxPossibleDepth+1];

        // 2단계 : 좌상단부터 순회하며 미확정 영역을 바깥쪽부터 차례로 채움
        int maxDepth = 0;
        for(int i=0; i<r; i++) {
            for(int j=0; j<c; j++) {
                if(contourMap[i][j] != 0) continue; // 이미 처리된 영역

                // 인접한 영역의 깊이부터 확인
                int depth = getAdjacentDepth(i, j);
                if(isLand[i][j]) {
                    // 섬은 자신을 감싼 수역과 같은 깊이
                    islandsByDepth[depth]++; // 해당 깊이에 대응되는 섬 개수 카운트
                    maxDepth = Math.max(maxDepth, depth); // 최대 깊이 갱신
                } else {
                    // 호수는 자신을 감싼 섬보다 한 단계 깊음
                    depth++;
                }

                dfs(i, j, isLand[i][j], depth);
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(maxDepth).append("\n");

        for(int i=1; i<=maxDepth; i++) {
            sb.append(islandsByDepth[i]).append(" ");
        }
        System.out.println(sb);
    }

    // 섬 또는 수역 전체를 탐색하는 스택 DFS 메서드
    // 시작 칸과 같은 종류로 이어진 영역 전체를 주어진 깊이로 채움
    // flag가 true면 육지(8방향), false면 물(4방향)
    static void dfs(int y, int x, boolean flag, int depth) {
        int top = 1;
        stack[0] = y * c + x;
        contourMap[y][x] = depth; // 시작 칸도 반드시 표시
        int unit = flag ? 1 : 2; // 8방향이면 1칸씩, 4방향이면 2칸씩 건너뜀

        while(top > 0) {
            int cur = stack[--top];
            int cy = cur / c;
            int cx = cur % c;

            for(int i=0; i<8; i+=unit) {
                int ny = cy + dy[i];
                int nx = cx + dx[i];
                // 격자 밖이거나, 종류가 다르거나, 이미 채워진 칸은 건너뜀
                if(!isIn(ny, nx) || isLand[ny][nx] != flag || contourMap[ny][nx] != 0) continue;

                contourMap[ny][nx] = depth; // push 시점에 표시해 중복 진입 방지
                stack[top++] = ny * c + nx;
            }
        }
    }

    // 인접한 영역 중 이미 깊이가 확정된 곳의 값을 반환
    // 영역 간 이웃 관계는 4방향으로만 정의되므로 짝수 인덱스만 확인
    static int getAdjacentDepth(int y, int x) {
        for(int i=0; i<8; i+=2) {
            int ny = y + dy[i];
            int nx = x + dx[i];
            if(!isIn(ny, nx)) return 1; // 해도 바깥과 맞닿으면 바깥 바다에 접한 것

            if(contourMap[ny][nx] == 0) continue;
            return contourMap[ny][nx];
        }

        return -1; // 좌상단부터 순회하므로 확정된 이웃이 반드시 존재하여 여기까지 도달할 수는 없음
    }

    // 배열 범위 초과 방지
    static boolean isIn(int y, int x) {
        return y >= 0 && y < r && x >= 0 && x < c;
    }

}