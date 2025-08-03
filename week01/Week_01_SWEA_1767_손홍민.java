import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Week01_SWEA_1767_손홍민 {
    /**
     * SWEA 1767 : 프로세서 연결하기
     * 이미 설치된 각 프로세서 코어와 멕시노스 외부를 잇는 전선을 직선으로 설치. 단, 이미 가장자리에 있는 코어는 연결된 것으로 간주.
     * 단, 4방향으로만 설치 가능하며, 다른 직선과의 교차 설치는 불가.
     * 최대한 많은 코어를 멕시노스 외부와 연결하고 그 때 사용한 전선의 길이 최소값을 구할 것.

     * N*N 크기의 멕시노스가 존재(7 <= N < 12). 1 <= 코어의 개수 <= 12. 전원에 연결되지 못하는 코어가 존재할 가능성도 있다.
     * 문제의 제 1조건은 코어를 최대한 많이 연결. 제 2조건은 사용된 전선 길이의 최소화
     * 입력값에서 0은 빈 칸, 1은 코어. 그 외의 입력은 주어지지 않음.

     * 0. 맵의 최대 크기와 코어의 최대 개수가 각각 12로, DFS를 사용하여 외부에 가까운 코어부터 탐색하면 제한 실행 시간을 준수할 수 있을 것으로 예상
     * 1. 리스트로 각 코어의 위치를 저장. 가장자리에 있는 코어는 이미 연결된 상태이므로 취급하지 않음
     * 2. 각 코어로부터 외부와 가장 가까운 방향의 거리를 구하고, 이를 기준으로 리스트를 정렬하여 가장자리에 가까운 코어부터 탐색하도록 유도
     * 3. DFS를 이용한 코어 완전 탐색 시작
     * 3-0. 코어 기준 4방향을 탐색하여 다른 코어 및 전선과의 충돌 없이 외부와 직선으로 연결할 수 있다면 해당 경로에 전선 설치
     * 3-1. 현재 전선 설치 횟수 및 사용된 전선 길이를 합산하고 다음 코어 탐색, 탐색 종료 후 전선 회수
     * 3-2. 만약 충돌이 발생하면 다른 방향 탐색
     * 3-3. 전선을 4방향 모두 설치할 수 없다면 전선 설치 과정을 무시하고 다음 코어 탐색
     * 4. 탐색 완료 또는 탐색 중지 후 복귀
     * 4-0. 만약 남은 코어에 전부 전선을 연결해도 현재 기록된 최대 연결 코어 수보다 작은 상황이라면 더 탐색하는 의미가 없으므로 이전 코어로 복귀
     * 4-1. 만약 같은 수의 코어를 연결하더라도 사용된 전선의 길이가 현재 기록된 최소 길이보다 길 수밖에 없는 상황이라면 더 탐색하는 의미가 없으므로 이전 코어로 복귀
     * 4-2. 탐색 완료 : 모든 코어를 탐색한 뒤 외부 전원과 연결된 코어의 개수를 갱신하거나, 사용된 전선의 길이를 갱신한 후 이전 코어로 복귀
     * 5. DFS 탐색 종료 후 사용된 최소 전선 길이 반환
     * */

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st;
    // 맵의 크기, 코어 카운트, 전선 최대 개수 카운트, 전선 최소 길이
    static int n, coreCnt, maxWireCnt, minWireLength;
    // 방향 설정 델타 배열
    static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};
    // 맵 현황. 코어나 전선이 설치되면 true, 빈 칸은 0
    static boolean[][] visited;
    // 코어 좌표 리스트
    static List<int[]> coreList = new ArrayList<>();
    public static void main(String[] args) throws Exception {
        int t = Integer.parseInt(br.readLine());
        for(int i=1; i<=t; i++) {
            init(); solve(i);
            coreList.clear();
        }
        System.out.println(sb);
    }

    public static void init() throws Exception {
        n = Integer.parseInt(br.readLine());
        // 맵 등록
        visited = new boolean[n][n];
        for(int i=0; i<n; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<n; j++) {
                String s = st.nextToken();
                // 코어인 경우
                if(s.equals("1")) {
                    // 맵에 코어 등록 및 가장자리에 있지 않은 코어들은 백트래킹 탐색을 위해 리스트 등록
                    visited[i][j] = true;
                    if(i >= 1 && j >= 1 && i < n-1 && j < n-1) coreList.add(new int[]{i,j});
                }
            }
        }
        coreCnt = coreList.size();
        maxWireCnt = 0;
        minWireLength = n;
        // 탐색 횟수를 최소화하기 위해 가장자리와 가까운 코어부터 탐색하고자 함
        // 따라서 각 코어 별로 외부와 가장 가까운 방향의 거리를 계산하고, 이를 기준으로 리스트 정렬
        coreList.sort((a, b) -> {
            int distA = Math.min(Math.min(a[0], n-1 - a[0]), Math.min(a[1], n-1 - a[1]));
            int distB = Math.min(Math.min(b[0], n-1 - b[0]), Math.min(b[1], n-1 - b[1]));
            return distA - distB;
        });
    }

    public static void solve(int idx) {
        sb.append("#").append(idx).append(" ");
        // 백트래킹. 전부 초기값을 제공한 뒤 시작
        dfs(0, 0, 0);
        // 백트래킹 종료 후 저장된 최소 전선 길이 출력
        sb.append(minWireLength).append("\n");
    }

    // 코어 완전탐색용 dfs 메서드
    // 매개변수는 각각 탐색한 코어 수, 연결한 전선의 수, 현재까지 연결한 전선 길이의 합
    public static void dfs(int searchIdx, int connectedCnt, int wireLength) {
        // 아직 탐색하지 않은 코어 수
        int remain = coreCnt - searchIdx;
        // 이미 연결된 전선의 수와 남은 코어의 합이 현재까지 기록된 최대 전선 수보다 낮다면 굳이 탐색을 이어갈 필요가 없음
        if(connectedCnt + remain < maxWireCnt) return;
        // 이미 연결된 전선의 수와 남은 코어의 합이 최대 전선수와 동일한데, 이미 기록된 최대 전선 길이를 넘었다면 더 탐색할 필요가 없음
        if(connectedCnt + remain == maxWireCnt && wireLength >= minWireLength) return;

        // 모든 코어를 탐색한 뒤
        if(remain == 0) {
            // 연결된 전선 수 갱신
            if(connectedCnt > maxWireCnt) {
                maxWireCnt = connectedCnt;
            }
            minWireLength = wireLength;
            return;
        }

        // 외부에 가까운 코어부터 탐색 시작
        int[] core = coreList.get(searchIdx);
        for(int i=0; i<4; i++) {
            int y = core[0], x = core[1];
            // 다른 개체와의 충돌 여부를 확인할 플래그 변수
            boolean crossedFlag = true;
            int setLen = 0;
            while(true) {
                y += dy[i]; x += dx[i];
                // 전선이 외부까지 도달한 경우
                if(!isIn(y, x)) {
                    crossedFlag = false;
                    break;
                }
                // 전선 설치 도중 다른 개체와 충돌한 경우
                if(visited[y][x]) break;

                // 문제가 없으면 해당 위치에 전설 설치 후 방문 여부 체크
                visited[y][x] = true;
                setLen++;
            }
            // 전선을 설치하는 동안 다른 개체와 충돌하지 않았다면
            if(!crossedFlag) {
                dfs(searchIdx+1, connectedCnt+1, wireLength + setLen);
            }

            // 설치 이전 상태로 롤백
            y = core[0]; x = core[1];
            for(int j=0; j<setLen; j++) {
                y += dy[i]; x += dx[i];
                visited[y][x] = false;
            }
        }

        // 어떤 방향으로도 전선 설치가 불가능한 경우 그 상태로 다음 코어 탐색
        dfs(searchIdx + 1, connectedCnt, wireLength);
    }

    public static boolean isIn(int y, int x) {
        return y >= 0 && x >= 0 && y < n && x < n;
    }
}