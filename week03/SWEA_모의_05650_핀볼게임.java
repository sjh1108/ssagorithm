import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class SWEA_모의_05650_핀볼게임 {

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer tokens;

    static int T;
    static int N;

    static int[][] deltas = { { 1, 0 }, { 0, -1 }, { 0, 1 }, { -1, 0 } };
    static int[][] nextDirs = { {}, { 2, 3, 1, 0 }, { 3, 0, 1, 2 }, { 3, 2, 0, 1 }, { 1, 2, 3, 0 }, { 3, 2, 1, 0 } };

    static Block[][] map;
    static int MAX;

    public static void main(String[] args) throws NumberFormatException, IOException {
        input = new BufferedReader(new StringReader(instr));
        T = Integer.parseInt(input.readLine());
 
        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(input.readLine().trim());
            map = new Block[N + 2][N + 2]; // 맨 외곽은 ㅁ 블록

            Map<Integer, WormHale> whMap = new HashMap<>();
            for (int r = 1; r <= N; r++) {
                map[r][0] = map[r][N + 1] = new Block(5);
                tokens = new StringTokenizer(input.readLine().trim());
                for (int c = 1; c <= N; c++) {
                    map[0][c] = map[N + 1][c] = new Block(5);
                    int type = Integer.parseInt(tokens.nextToken());
                    if (type == 0) {
                        continue;
                    } else if (type == -1) {
                        map[r][c] = new BlackHole(-1);
                    } else if (type >= 1 && type <= 5) {
                        map[r][c] = new Block(type);
                    } else {
                        WormHale wh = new WormHale(type, r, c);
                        map[r][c] = wh;
                        if (whMap.get(type) == null) {
                            whMap.put(type, wh);
                        } else {
                            whMap.get(type).setPair(wh);
                        }
                    }
                }
            }
            // 입력 끝!!!
            // for(Block [] row : map) {
            // System.out.println(Arrays.toString(row));
            // }

            MAX = Integer.MIN_VALUE;
            for (int r = 1; r <= N; r++) {
                for (int c = 1; c <= N; c++) {
                    if (map[r][c] == null) {
                        for (int d = 0; d < deltas.length; d++) {
                            Pinball ball = new Pinball(r, c, d);
                            ball.move();// 추가 작업
                            MAX = Math.max(MAX, ball.score);
                        }
                    }
                }
            } // 처리 끝
            output.append("#").append(t).append(" ").append(MAX).append("\n");

        }
        System.out.println(output);

    }

    static class Pinball {
        int startR, startC, shootR, shootC, r, c, d, score;

        public Pinball(int r, int c, int d) {
            this.startR = this.shootR = this.r = r;
            this.startC = this.shootC = this.c = c;
            this.d = d; // 방향을 지정해주지 않았어요~~
        }

        public void move() {
            while (true) {
                // 1. 해당 방향으로 진행
                this.r += deltas[this.d][0];
                this.c += deltas[this.d][1];

                // 2. 한칸 갔더니..
                // 2-1 출발점이거나 지점의 타입이 blackhale - game over
                // 2-2 빈 공간 -continue
                // 2-3 warmhale - jump
                // 2-4 다른 블록 - 블록 타입에 따라 방향 전환

                if( ( this.r == this.startR && this.c==this.startC) || map[r][c] instanceof BlackHole ) {
                    break;
                }else if(map[r][c]==null) {
                    continue;
                }else if(map[r][c] instanceof WormHale){
                   WormHale wh = (WormHale)map[r][c];
                   wh.jump(this);
                }else {
                    map[r][c].changeDir(this);
                }
            }
        }
    }


    static class Block {
        int type;

        Block(int type) {
            this.type = type;
        }

        public void changeDir(Pinball ball) {
            int nextD = nextDirs[type][ball.d]; // ball이 d에서 왔을 때 다음 방향 결정
            // 충돌 지점이 이제 발사 지점이 된다.
            ball.shootR = ball.r;
            ball.shootC = ball.c;

            // 충돌: 점수 증가
            ball.score++;
            ball.d = nextD;
        }

        @Override
        public String toString() {
            return this.type + "";
        }
    }

    static class WormHale extends Block {
        WormHale pair;
        int r, c;

        public WormHale(int type, int r, int c) {
            super(type);
            this.r = r;
            this.c = c;
        }

        public void setPair(WormHale other) {
            this.pair = other;
            other.pair = this;
        }

        public void jump(Pinball ball) {
            ball.r = pair.r;
            ball.c = pair.c;
        }
    }



    static class BlackHole extends Block {
        public BlackHole(int type) {
            super(type);
        }

    }

    static String instr = "5\n"
                          + "10\n"
                          + "0 1 0 3 0 0 0 0 7 0 \n"
                          + "0 0 0 0 -1 0 5 0 0 0 \n"
                          + "0 4 0 0 0 3 0 0 2 2 \n"
                          + "1 0 0 0 1 0 0 3 0 0 \n"
                          + "0 0 3 0 0 0 0 0 6 0 \n"
                          + "3 0 0 0 2 0 0 1 0 0 \n"
                          + "0 0 0 0 0 1 0 0 4 0 \n"
                          + "0 5 0 4 1 0 7 0 0 5 \n"
                          + "0 0 0 0 0 1 0 0 0 0 \n"
                          + "2 0 6 0 0 4 0 0 0 4 \n"
                          + "6\n"
                          + "1 1 1 1 1 1 \n"
                          + "1 1 -1 1 1 1 \n"
                          + "1 -1 0 -1 1 1 \n"
                          + "1 1 -1 1 1 1 \n"
                          + "1 1 1 1 1 1 \n"
                          + "1 1 1 1 1 1 \n"
                          + "8\n"
                          + "0 0 0 3 0 0 0 0 \n"
                          + "0 0 2 0 0 5 0 0 \n"
                          + "0 0 5 0 3 0 0 0 \n"
                          + "0 0 1 1 0 0 0 4 \n"
                          + "0 0 0 0 0 0 0 0 \n"
                          + "0 0 0 0 0 0 5 0 \n"
                          + "0 0 4 0 0 3 1 0 \n"
                          + "2 0 0 4 3 4 0 0 \n"
                          + "10\n"
                          + "0 4 0 0 0 0 4 0 5 0 \n"
                          + "0 0 0 0 0 0 0 0 3 0 \n"
                          + "0 0 0 5 6 0 0 0 0 2 \n"
                          + "3 0 0 1 0 0 1 4 0 2 \n"
                          + "2 0 0 0 0 5 0 0 5 0 \n"
                          + "0 0 1 0 2 0 0 0 5 0 \n"
                          + "0 0 0 0 0 0 6 1 0 0 \n"
                          + "0 0 1 0 2 0 2 4 0 0 \n"
                          + "0 0 0 0 0 0 2 0 0 0 \n"
                          + "2 0 0 0 0 0 0 3 0 0 \n"
                          + "20\n"
                          + "0 0 1 0 0 0 0 3 0 3 0 0 0 4 0 0 1 0 4 0 \n"
                          + "0 1 2 3 3 0 0 0 0 0 0 0 0 5 0 0 0 0 5 0 \n"
                          + "0 0 0 0 0 0 0 0 0 5 0 0 0 5 0 4 0 0 0 0 \n"
                          + "4 0 0 0 0 0 0 4 5 0 0 0 3 0 0 0 3 0 0 0 \n"
                          + "0 0 0 3 0 4 1 0 3 0 0 0 0 4 0 0 0 2 0 3 \n"
                          + "2 2 0 0 0 0 0 0 0 0 0 0 4 0 0 0 0 0 0 0 \n"
                          + "0 0 0 0 0 0 0 0 0 0 0 0 5 0 5 0 0 0 3 4 \n"
                          + "0 0 5 0 -1 5 0 0 5 2 0 0 0 4 2 0 0 3 0 0 \n"
                          + "0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 \n"
                          + "2 0 0 0 0 3 0 0 3 3 3 0 0 1 0 0 2 0 0 0 \n"
                          + "1 5 0 5 0 0 0 0 5 4 5 0 0 0 0 4 2 4 0 0 \n"
                          + "0 4 0 0 0 1 3 0 0 0 0 0 1 0 3 0 0 2 0 0 \n"
                          + "0 0 0 0 0 0 3 0 1 0 0 1 0 0 0 0 0 3 4 0 \n"
                          + "0 4 0 4 0 0 0 0 0 0 0 2 0 0 0 3 0 0 0 2 \n"
                          + "0 5 0 0 0 4 1 5 0 0 0 2 0 0 0 0 0 0 0 0 \n"
                          + "0 0 0 5 0 0 1 2 0 0 0 3 1 2 5 0 0 0 0 0 \n"
                          + "0 4 2 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 \n"
                          + "0 0 0 4 0 0 0 0 4 0 0 0 0 0 0 1 4 0 2 0 \n"
                          + "0 0 1 0 0 0 0 0 3 0 0 0 0 0 0 0 5 0 0 0 \n"
                          + "0 0 0 0 0 0 0 5 0 4 0 0 0 0 0 2 0 0 2 0 \n"
                          + "";
}
