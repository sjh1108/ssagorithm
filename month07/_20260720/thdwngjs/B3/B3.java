package _20260720.thdwngjs.B3;

import java.io.*;
import java.util.*;

class B3 {
    static final int[] DR = {-1, 1, 0, 0}; // 상, 하, 좌, 우
    static final int[] DC = {0, 0, -1, 1};
    static final int WARRIOR_RANGE = 1, ARCHER_RANGE = 3, PRIEST_RANGE = 2;

    static int H, W;
    static char[][] grid;
    static Unit[][] occ;      // 점유 (죽은 유닛도 라운드 끝 정리 전까지 남음)
    static int[][] dist;      // BFS 작업 배열

    static abstract class Unit {
        int gid, team, maxhp, hp, atk, dfn, spd, r, c;
        Unit(int gid, int team, int hp, int atk, int dfn, int spd, int r, int c) {
            this.gid = gid; this.team = team; this.maxhp = hp; this.hp = hp;
            this.atk = atk; this.dfn = dfn; this.spd = spd; this.r = r; this.c = c;
        }
        abstract void act(Battle b);
    }

    static int manhattan(Unit a, Unit b) {
        return Math.abs(a.r - b.r) + Math.abs(a.c - b.c);
    }

    static class Warrior extends Unit {
        Warrior(int g, int t, int hp, int a, int d, int s, int r, int c) { super(g, t, hp, a, d, s, r, c); }
        void act(Battle b) {
            Unit target = null;
            for (Unit e : b.teams[1 - team]) {
                if (e.hp > 0 && manhattan(this, e) == WARRIOR_RANGE)
                    if (target == null || e.hp < target.hp || (e.hp == target.hp && e.gid < target.gid))
                        target = e;
            }
            if (target != null) {
                int dmg = atk - target.dfn;
                if (dmg < 1) dmg = 1;
                target.hp -= dmg;
            } else {
                b.approach(this, b.enemyStandCells(team));
            }
        }
    }

    static class Archer extends Unit {
        Archer(int g, int t, int hp, int a, int d, int s, int r, int c) { super(g, t, hp, a, d, s, r, c); }
        void act(Battle b) {
            Unit target = null;
            for (Unit e : b.teams[1 - team]) {
                if (e.hp > 0 && manhattan(this, e) <= ARCHER_RANGE)
                    if (target == null || e.atk > target.atk || (e.atk == target.atk && e.gid < target.gid))
                        target = e;
            }
            if (target != null) {
                target.hp -= atk; // 방어 무시
            } else {
                b.approach(this, b.enemyStandCells(team));
            }
        }
    }

    static class Priest extends Unit {
        Priest(int g, int t, int hp, int a, int d, int s, int r, int c) { super(g, t, hp, a, d, s, r, c); }
        void act(Battle b) {
            // 사거리 내 다친 아군 중 비율 최저
            Unit best = null;
            for (Unit a : b.teams[team]) {
                if (a.hp > 0 && a.hp < a.maxhp && manhattan(this, a) <= PRIEST_RANGE) {
                    if (best == null) best = a;
                    else {
                        long lhs = (long) a.hp * best.maxhp, rhs = (long) best.hp * a.maxhp;
                        if (lhs < rhs || (lhs == rhs && a.gid < best.gid)) best = a;
                    }
                }
            }
            if (best != null) {
                int v = best.hp + atk;
                best.hp = Math.min(v, best.maxhp);
            } else {
                // 격자 어딘가에 다친 아군이 있으면 접근
                ArrayList<Unit> injured = new ArrayList<>();
                for (Unit a : b.teams[team]) if (a.hp > 0 && a.hp < a.maxhp) injured.add(a);
                if (!injured.isEmpty()) b.approach(this, b.allyStandCells(injured));
            }
        }
    }

    static class Battle {
        int rounds;
        Unit[][] teams;
        Unit[] units;
        Battle(int rounds, Unit[][] teams) {
            this.rounds = rounds;
            this.teams = teams;
            units = new Unit[teams[0].length + teams[1].length];
            int i = 0;
            for (Unit u : teams[0]) units[i++] = u;
            for (Unit u : teams[1]) units[i++] = u;
        }
        boolean teamAlive(int team) {
            for (Unit u : teams[team]) if (u.hp > 0) return true;
            return false;
        }
        boolean free(int r, int c) {
            return r >= 0 && r < H && c >= 0 && c < W && grid[r][c] == '.' && occ[r][c] == null;
        }
        // 목표 칸: 살아있는 적에 인접한 빈칸(장애물·유닛 없음)
        Set<Integer> enemyStandCells(int team) {
            HashSet<Integer> cells = new HashSet<>();
            for (Unit e : teams[1 - team]) {
                if (e.hp <= 0) continue;
                for (int d = 0; d < 4; d++) {
                    int nr = e.r + DR[d], nc = e.c + DC[d];
                    if (free(nr, nc)) cells.add(nr * W + nc);
                }
            }
            return cells;
        }
        Set<Integer> allyStandCells(List<Unit> injured) {
            HashSet<Integer> cells = new HashSet<>();
            for (Unit a : injured) {
                for (int d = 0; d < 4; d++) {
                    int nr = a.r + DR[d], nc = a.c + DC[d];
                    if (free(nr, nc)) cells.add(nr * W + nc);
                }
            }
            return cells;
        }
        // 다중 소스 BFS로 목표 칸까지 최단거리 -> 한 칸 이동
        void approach(Unit mover, Set<Integer> targets) {
            if (targets.isEmpty()) return;
            for (int[] row : dist) Arrays.fill(row, Integer.MAX_VALUE);
            ArrayDeque<int[]> dq = new ArrayDeque<>();
            for (int key : targets) {
                int r = key / W, c = key % W;
                dist[r][c] = 0;
                dq.add(new int[]{r, c});
            }
            while (!dq.isEmpty()) {
                int[] cur = dq.poll();
                int r = cur[0], c = cur[1];
                for (int d = 0; d < 4; d++) {
                    int nr = r + DR[d], nc = c + DC[d];
                    if (passable(nr, nc, mover) && dist[nr][nc] == Integer.MAX_VALUE) {
                        dist[nr][nc] = dist[r][c] + 1;
                        dq.add(new int[]{nr, nc});
                    }
                }
            }
            int sr = mover.r, sc = mover.c, D = dist[sr][sc];
            if (D == Integer.MAX_VALUE || D == 0) return;
            for (int d = 0; d < 4; d++) {
                int nr = sr + DR[d], nc = sc + DC[d];
                if (passable(nr, nc, mover) && dist[nr][nc] == D - 1) {
                    occ[sr][sc] = null;
                    mover.r = nr; mover.c = nc;
                    occ[nr][nc] = mover;
                    return;
                }
            }
        }
        boolean passable(int r, int c, Unit mover) {
            if (r < 0 || r >= H || c < 0 || c >= W) return false;
            if (grid[r][c] == '#') return false;
            return occ[r][c] == null || occ[r][c] == mover;
        }
        int[] simulate() { // {code, elapsed}, code 0/1/2
            for (int rnd = 1; rnd <= rounds; rnd++) {
                Unit[] order = liveOrder();
                for (Unit u : order) {
                    if (u.hp <= 0) continue;
                    u.act(this);
                    if (!teamAlive(0) || !teamAlive(1))
                        return new int[]{ teamAlive(0) ? 0 : 1, rnd };
                }
                // 죽은 유닛 점유 정리 (라운드 끝)
                for (Unit u : units)
                    if (u.hp <= 0 && occ[u.r][u.c] == u) occ[u.r][u.c] = null;
            }
            return new int[]{ 2, rounds };
        }
        Unit[] liveOrder() {
            ArrayList<Unit> live = new ArrayList<>();
            for (Unit u : units) if (u.hp > 0) live.add(u);
            live.sort((a, b) -> a.spd != b.spd ? b.spd - a.spd : a.gid - b.gid);
            return live.toArray(new Unit[0]);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int R = Integer.parseInt(br.readLine().trim().split("\\s+")[0]);
        StringTokenizer hw = new StringTokenizer(br.readLine());
        H = Integer.parseInt(hw.nextToken());
        W = Integer.parseInt(hw.nextToken());
        grid = new char[H][];
        for (int i = 0; i < H; i++) {
            String row = br.readLine();
            if (row == null) row = "";
            char[] cs = new char[W];
            for (int j = 0; j < W; j++) cs[j] = (j < row.length()) ? row.charAt(j) : '.';
            grid[i] = cs;
        }
        occ = new Unit[H][W];
        dist = new int[H][W];

        int gid = 0;
        Unit[][] teams = new Unit[2][];
        for (int team = 0; team < 2; team++) {
            int c = Integer.parseInt(br.readLine().trim().split("\\s+")[0]);
            Unit[] arr = new Unit[c];
            for (int i = 0; i < c; i++) {
                StringTokenizer stk = new StringTokenizer(br.readLine());
                String typ = stk.nextToken();
                int hp = Integer.parseInt(stk.nextToken());
                int atk = Integer.parseInt(stk.nextToken());
                int dfn = Integer.parseInt(stk.nextToken());
                int spd = Integer.parseInt(stk.nextToken());
                int r = Integer.parseInt(stk.nextToken());
                int cc = Integer.parseInt(stk.nextToken());
                Unit u = make(typ, gid++, team, hp, atk, dfn, spd, r, cc);
                arr[i] = u;
                occ[r][cc] = u;
            }
            teams[team] = arr;
        }

        int[] res = new Battle(R, teams).simulate();
        StringBuilder sb = new StringBuilder();
        sb.append(res[0] == 2 ? "DRAW" : String.valueOf(res[0])).append('\n');
        sb.append(res[1]).append('\n');
        appendHps(sb, teams[0]);
        appendHps(sb, teams[1]);
        System.out.print(sb);
    }

    static Unit make(String typ, int g, int t, int hp, int a, int d, int s, int r, int c) {
        switch (typ) {
            case "WARRIOR": return new Warrior(g, t, hp, a, d, s, r, c);
            case "ARCHER":  return new Archer(g, t, hp, a, d, s, r, c);
            default:        return new Priest(g, t, hp, a, d, s, r, c);
        }
    }

    static void appendHps(StringBuilder sb, Unit[] team) {
        for (int i = 0; i < team.length; i++) {
            if (i > 0) sb.append(' ');
            sb.append(team[i].hp > 0 ? team[i].hp : 0);
        }
        sb.append('\n');
    }
}