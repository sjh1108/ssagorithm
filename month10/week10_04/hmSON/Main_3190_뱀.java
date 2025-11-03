import java.io.*;
import java.util.*;

public class Main_3190_뱀 {
	
	// BOJ_3190 : 뱀(Gold_4)
	// 자료구조 및 알고리즘 : 구현, 시뮬레이션, 큐
	// 매 초마다 뱀의 머리를 정해진 방향으로 1씩 이동시킨다. 사과를 먹으면 몸의 길이가 1 증가한다.
	// 뱀의 머리가 벽 또는 자기자신의 몸과 부딪히면 게임은 종료된다. 명령대로 뱀이 이동한다고 할 때, 몇 초만에 게임이 종료되는지 구해야 한다.
	// 뱀의 이동 방식을 큐로 정의한다. 뱀의 머리 이동 -> 큐에 새로운 좌표 데이터 삽입, 뱀의 꼬리 이동 -> 큐의 peek 데이터 제거
	
	// 맵의 크기, 사과의 개수, 방향 전환 횟수
    static int n, appleCnt, cmdCnt;
    // 4방향 이동 배열. 편의를 위해 방향은 12시부터 시계방향 순서로 정렬된 상태
    static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};
    // 맵 정보. 빈 공간, 사과, 또는 뱀이 차지한 공간을 표시한다.
    static char[][] map;
    // 각 명령어의 정보를 저장한다. {실행시간, 방향}
    static int[][] cmds;
    public static void main(String[] args) throws Exception {
        init(); move();
    }

    private static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        map = new char[n][n];
        
        // 사과의 좌표를 받아서 맵에 'a'로 등록
        appleCnt = Integer.parseInt(br.readLine());
        StringTokenizer st;
        for(int i=0; i<appleCnt; i++) {
            st = new StringTokenizer(br.readLine());
            int appleY = Integer.parseInt(st.nextToken()) - 1;
            int appleX = Integer.parseInt(st.nextToken()) - 1;
            map[appleY][appleX] = 'a';
        }
        
        // 명령어 정보를 받아서 cmds 배열에 등록
        // 단, 방향 전환의 편의를 위해 미리 'L(왼쪽)'은 -1로, 'D(오른쪽)'은 1로 변환
        cmdCnt = Integer.parseInt(br.readLine());
        cmds = new int[cmdCnt][2];
        for(int i=0; i<cmdCnt; i++) {
            st = new StringTokenizer(br.readLine());
            cmds[i][0] = Integer.parseInt(st.nextToken());
            cmds[i][1] = st.nextToken().charAt(0) == 'D' ? 1 : -1;
        }
    }

    private static void move() {
    	// 현재 경과시간, 현재 좌표, 실행된 명령어 번호, 현재 방향(초기값은 1 -> 3시방향)
        int timeCnt = 0, currY = 0, currX = 0, cmdIdx = 0, currDir = 1;
        
        // 뱀의 초기 위치는 (0, 0). 맵 상에는 뱀의 몸 정보를 's'로 저장
        map[0][0] = 's';
        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{0, 0});
        
        while(true) {
        	// 시간 경과
            timeCnt++;
            
            // 이동
            int newY = currY + dy[currDir];
            int newX = currX + dx[currDir];
            
            // 벽 또는 자기 자신의 몸에 부딪힌 경우 루프 종료
            if(newY < 0 || newX < 0 || newY >= n || newX >= n) break;
            char next = map[newY][newX];
            if(next == 's') break;
            
            // 뱀 이동 과정
            // 만약 이동한 자리에 사과가 있으면 뱀의 꼬리는 제자리에 위치
            q.add(new int[]{newY, newX});
            map[newY][newX] = 's';
            currY = newY; currX = newX;
            if(next != 'a') {
            	// 뱀의 꼬리가 지나간 자리를 다시 빈 공간으로 변경
                int[] tail = q.poll();
                map[tail[0]][tail[1]] = '0';
            }

            // 새 명령어가 주어질 시간이 된 경우
            // 미리 입력된 방향값에 따라 현재 뱀의 이동 방향을 전환, 다음 명령어 대기
            if(cmdIdx < cmdCnt && cmds[cmdIdx][0] == timeCnt) {
                currDir = (currDir + cmds[cmdIdx][1] + 4) % 4;
                cmdIdx++;
            }
        }
        
        // 루프 종료 -> 경과 시간 출력
        System.out.println(timeCnt);
    }
}
