import java.io.*;
import java.util.*;

public class Main_2983_개구리_공주 {

    /*
     * BOJ_2983 : 개구리 공주 (Platinum_4)
     * 자료구조 및 알고리즘 : 연결 리스트, 트리를 사용한 집합과 맵
     *
     * [문제 요약]
     * - (x, y) 좌표에 식물이 N개 존재하고, 개구리는 처음 주어진 식물에서 시작한다.
     * - 명령 문자열에 따라 대각선 방향으로 "같은 대각선 위의 다음 식물"로 이동한다.
     * - 이동 후에는 원래 밟고 있던 식물은 사라져(제거되어) 이후 이동에 사용될 수 없다.
     * - 모든 명령을 수행한 뒤 개구리의 최종 (x, y)를 출력한다.
     *
     * [핵심 아이디어]
     * - 대각선 이동은 좌표 성질로 그룹화할 수 있다.
     *   1) 우하향 대각선(↘): (y - x)가 같은 점들끼리 같은 대각선
     *   2) 좌하향 대각선(↙): (y + x)가 같은 점들끼리 같은 대각선
     * - 현재 위치에서 "같은 대각선 위의 다음/이전 점"을 빠르게 찾기 위해
     *   대각선 그룹별로 y 좌표를 TreeSet에 저장해두고 higher/lower로 이웃을 찾는다.
     * - 이동할 때마다 현재 점은 삭제되므로, 두 대각선 자료구조에서 동시에 제거해야 한다.
     *
     * [구현 메모]
     * - rightDown: key = (y - x), value = 해당 대각선 위 점들의 y를 오름차순 TreeSet 저장
     *   - higher(y): y보다 큰 다음 점(한 방향), lower(y): y보다 작은 이전 점(반대 방향)
     * - leftDown:  key = (y + x), value = 해당 대각선 위 점들의 y를 내림차순 TreeSet 저장
     *   - 내림차순으로 만든 이유는 higher/lower 호출이 명령 방향과 자연스럽게 맞도록 하기 위함
     *     (정렬 방향이 바뀌면 higher/lower의 의미가 "대각선 진행 방향"에 대응됨)
     * - 현재 위치 (sx, sy)에서
     *   diff = sy - sx, sum = sy + sx 로 소속 대각선을 구한다.
     * - 이동 성공 시:
     *   - rightDown.get(diff).remove(sy)
     *   - leftDown.get(sum).remove(sy)
     *   로 현재 식물을 제거하고,
     *   새 좌표는 diff/sum으로 복원한다.
     *   - ↘ 대각선: sx = sy - diff
     *   - ↙ 대각선: sx = sum - sy
     *
     * [시간 복잡도]
     * - 각 명령마다 TreeSet 탐색/삭제: O(log N)
     * - 총: O((N + |cmd|) log N)
     */

    // 점프 명령어 횟수, 초기 y, x 좌표
    static int jump, sy = -1, sx = -1;
    // 명령어 순서
    static char[] cmd;
    // 우상향, 우하향 대각선별 식물 목록
    static HashMap<Integer, TreeSet<Integer>> leftDown, rightDown;

    static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        jump = Integer.parseInt(st.nextToken());
        cmd = br.readLine().toCharArray();

        leftDown = new HashMap<>();
        rightDown = new HashMap<>();

        for(int i=0; i<n; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            // 우하향 대각선에 속한 좌표들은 y, x값의 차가 동일함
            // 우상향 대각선에 속한 좌표들은 y, x값의 합이 동일함
            int diff = y - x, sum = y + x;
            if(sy == -1) { sy = y; sx = x; }

            // 각 대각선에 해당하는 트리셋이 없으면 새로 만든다
            // y값을 입력할 예정이므로 우성향인 leftDown 트리셋은 역순 정렬한다.
            if(!rightDown.containsKey(diff)) rightDown.put(diff, new TreeSet<>());
            rightDown.get(diff).add(y);

            if(!leftDown.containsKey(sum)) leftDown.put(sum, new TreeSet<>(Collections.reverseOrder()));
            leftDown.get(sum).add(y);
        }
    }

    public static void main(String[] args) throws Exception {
        init();

        for(char c : cmd) {
            // 현재 위치의 y, x값의 합과 차를 미리 구함
            // 이동 후 현재 위치의 식물을 제거하기 위함
            int diff = sy - sx, sum = sy + sx;

            // 'A','D'는 (y-x)가 같은 ↘ 대각선에서 이동 (rightDown 사용)
            if(c == 'A' || c == 'D') {
                Integer ny = c == 'A' ? rightDown.get(diff).higher(sy) : rightDown.get(diff).lower(sy);
                if(ny == null) continue;

                // 현재 식물 제거 (두 대각선 자료구조에서 동시에 삭제)
                rightDown.get(diff).remove(sy);
                leftDown.get(sum).remove(sy);

                // 새 좌표 복원: diff = y - x => x = y - diff
                sy = ny; sx = sy - diff;
            } else {
                // 'B','C'는 (y+x)가 같은 ↙ 대각선에서 이동 (leftDown 사용)
                Integer ny = c == 'B' ? leftDown.get(sum).higher(sy) : leftDown.get(sum).lower(sy);
                if(ny == null) continue;

                // 현재 식물 제거
                rightDown.get(diff).remove(sy);
                leftDown.get(sum).remove(sy);

                // 새 좌표 복원: sum = y + x => x = sum - y
                sy = ny; sx = sum - sy;
            }
        }

        System.out.print(sx + " " + sy);
    }

}