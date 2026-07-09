/*
로봇 노드 클래스로 움직임 관리
동시 이동 처리
같은 칸 충돌 + 엇갈림 충돌 체크
*/
import java.util.*;
import java.io.*;

public class Main {

  public static class Robot {
    int R;
    int C;
    int dir;

    Robot(int R, int C, int dir) {
      this.R = R;
      this.C = C;
      this.dir = dir;
    }

    void MoveRobot(int R, int C) {
      this.R = R;
      this.C = C;
    }

    void Rotate() {
      dir = (dir + 1) % 4;
    }
  }

  static int MapR, MapC, RobotN, M;
  static char[][] map;
  static Robot[] Robots;

  // U -> R -> D -> L
  static int[][] dt = {
      {-1, 0},
      {0, 1},
      {1, 0},
      {0, -1}
  };

  public static int simulate() {
    for (int time = 1; time <= M; time++) {
      if (moveAndCheckCollision()) {
        return time;
      }
    }

    return 0;
  }

  // 충돌이나 엇갈림 없으면 움직이고 false, 있으면 true 반환
  public static boolean moveAndCheckCollision() {
    int[] prevR = new int[RobotN];
    int[] prevC = new int[RobotN];

    int[] nextR = new int[RobotN];
    int[] nextC = new int[RobotN];
    int[] nextDir = new int[RobotN];

    boolean[] moved = new boolean[RobotN];

    // 1. 현재 위치 저장
    for (int i = 0; i < RobotN; i++) {
      Robot now = Robots[i];

      prevR[i] = now.R;
      prevC[i] = now.C;

      nextR[i] = now.R;
      nextC[i] = now.C;
      nextDir[i] = now.dir;
    }

    // 2. 모든 로봇의 다음 위치 계산
    for (int i = 0; i < RobotN; i++) {
      Robot now = Robots[i];

      int nr = now.R + dt[now.dir][0];
      int nc = now.C + dt[now.dir][1];

      // 범위를 벗어나면 회전만 하고 제자리
      if (nr < 1 || nr > MapR || nc < 1 || nc > MapC) {
        nextDir[i] = (now.dir + 1) % 4;
        continue;
      }

      // 벽이면 회전만 하고 제자리
      if (map[nr][nc] == '#') {
        nextDir[i] = (now.dir + 1) % 4;
        continue;
      }

      // 이동 가능하면 다음 위치 갱신
      nextR[i] = nr;
      nextC[i] = nc;
      moved[i] = true;
    }

    // 3. 같은 칸 충돌 체크
    for (int i = 0; i < RobotN; i++) {
      for (int j = i + 1; j < RobotN; j++) {
        if (nextR[i] == nextR[j] && nextC[i] == nextC[j]) {
          return true;
        }
      }
    }

    // 4. 엇갈림 충돌 체크
    for (int i = 0; i < RobotN; i++) {
      for (int j = i + 1; j < RobotN; j++) {
        // 둘다 움직였고, 위치 서로 바뀐건지 확인
        if (moved[i] && moved[j]
            && prevR[i] == nextR[j]
            && prevC[i] == nextC[j]
            && prevR[j] == nextR[i]
            && prevC[j] == nextC[i]) {
          return true;
        }
      }
    }

    // 5. 충돌이 없으면 실제 로봇 위치와 방향 반영
    for (int i = 0; i < RobotN; i++) {
      Robots[i].MoveRobot(nextR[i], nextC[i]);
      Robots[i].dir = nextDir[i];
    }

    return false;
  }

  public static int getDir(char dirChar) {
    switch (dirChar) {
      case 'U':
        return 0;
      case 'R':
        return 1;
      case 'D':
        return 2;
      case 'L':
        return 3;
    }

    return 0;
  }

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());

    MapR = Integer.parseInt(st.nextToken());
    MapC = Integer.parseInt(st.nextToken());
    RobotN = Integer.parseInt(st.nextToken());
    M = Integer.parseInt(st.nextToken());

    map = new char[MapR + 1][MapC + 1];

    for (int i = 1; i <= MapR; i++) {
      String line = br.readLine();

      for (int j = 1; j <= MapC; j++) {
        map[i][j] = line.charAt(j - 1);
      }
    }

    Robots = new Robot[RobotN];

    for (int i = 0; i < RobotN; i++) {
      st = new StringTokenizer(br.readLine());

      int RobotR = Integer.parseInt(st.nextToken());
      int RobotC = Integer.parseInt(st.nextToken());
      char dirChar = st.nextToken().charAt(0);

      int dir = getDir(dirChar);

      Robots[i] = new Robot(RobotR, RobotC, dir);
    }

    int result = simulate();
    System.out.println(result);
  }
}