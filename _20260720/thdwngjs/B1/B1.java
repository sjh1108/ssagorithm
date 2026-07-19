package _20260720.thdwngjs.B1;

import java.io.*;
import java.util.*;

class B1 {
    // ===== 유닛: 공통 뼈대 + 병종별 다형적 act() =====
    static abstract class Unit {
        int gid, team, maxhp, hp, atk, dfn, spd;
        Unit(int gid, int team, int hp, int atk, int dfn, int spd) {
            this.gid = gid; this.team = team;
            this.maxhp = hp; this.hp = hp;
            this.atk = atk; this.dfn = dfn; this.spd = spd;
        }
        abstract void act(Battle b);
    }

    static class Warrior extends Unit {
        Warrior(int g, int t, int hp, int atk, int dfn, int spd) { super(g, t, hp, atk, dfn, spd); }
        void act(Battle b) {
            Unit t = b.enemyMinHp(team);
            int dmg = atk - t.dfn;
            if (dmg < 1) dmg = 1;
            t.hp -= dmg;
        }
    }

    static class Archer extends Unit {
        Archer(int g, int t, int hp, int atk, int dfn, int spd) { super(g, t, hp, atk, dfn, spd); }
        void act(Battle b) {
            Unit t = b.enemyMaxAtk(team);
            t.hp -= atk; // 방어 무시
        }
    }

    static class Priest extends Unit {
        Priest(int g, int t, int hp, int atk, int dfn, int spd) { super(g, t, hp, atk, dfn, spd); }
        void act(Battle b) {
            Unit ally = b.allyMinRatio(team);
            if (ally.hp < ally.maxhp) {
                int v = ally.hp + atk;
                ally.hp = Math.min(v, ally.maxhp);
            } else {
                Unit t = b.enemyMinHp(team);
                int dmg = atk / 2;
                if (dmg < 1) dmg = 1;
                t.hp -= dmg;
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
        Unit enemyMinHp(int myTeam) {
            Unit best = null;
            for (Unit u : teams[1 - myTeam]) {
                if (u.hp > 0 && (best == null || u.hp < best.hp || (u.hp == best.hp && u.gid < best.gid)))
                    best = u;
            }
            return best;
        }
        Unit enemyMaxAtk(int myTeam) {
            Unit best = null;
            for (Unit u : teams[1 - myTeam]) {
                if (u.hp > 0 && (best == null || u.atk > best.atk || (u.atk == best.atk && u.gid < best.gid)))
                    best = u;
            }
            return best;
        }
        Unit allyMinRatio(int myTeam) {
            Unit best = null;
            for (Unit u : teams[myTeam]) {
                if (u.hp <= 0) continue;
                if (best == null) { best = u; continue; }
                long lhs = (long) u.hp * best.maxhp;
                long rhs = (long) best.hp * u.maxhp;
                if (lhs < rhs || (lhs == rhs && u.gid < best.gid)) best = u;
            }
            return best;
        }
        int[] simulate() { // {winnerCode, elapsed}, winnerCode: 0,1, or 2(DRAW)
            for (int rnd = 1; rnd <= rounds; rnd++) {
                Unit[] order = liveOrder();
                for (Unit u : order) {
                    if (u.hp <= 0) continue;
                    u.act(this);
                    if (!teamAlive(0) || !teamAlive(1)) {
                        return new int[]{ teamAlive(0) ? 0 : 1, rnd };
                    }
                }
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
        String[] tok = readAll();
        int p = 0;
        int R = Integer.parseInt(tok[p++]);
        int gid = 0;
        Unit[][] teams = new Unit[2][];
        for (int team = 0; team < 2; team++) {
            int c = Integer.parseInt(tok[p++]);
            Unit[] arr = new Unit[c];
            for (int i = 0; i < c; i++) {
                String typ = tok[p++];
                int hp = Integer.parseInt(tok[p++]);
                int atk = Integer.parseInt(tok[p++]);
                int dfn = Integer.parseInt(tok[p++]);
                int spd = Integer.parseInt(tok[p++]);
                arr[i] = make(typ, gid++, team, hp, atk, dfn, spd);
            }
            teams[team] = arr;
        }

        Battle battle = new Battle(R, teams);
        int[] res = battle.simulate();

        StringBuilder sb = new StringBuilder();
        sb.append(res[0] == 2 ? "DRAW" : String.valueOf(res[0])).append('\n');
        sb.append(res[1]).append('\n');
        appendHps(sb, teams[0]);
        appendHps(sb, teams[1]);
        System.out.print(sb);
    }

    static Unit make(String typ, int gid, int team, int hp, int atk, int dfn, int spd) {
        switch (typ) {
            case "WARRIOR": return new Warrior(gid, team, hp, atk, dfn, spd);
            case "ARCHER":  return new Archer(gid, team, hp, atk, dfn, spd);
            default:        return new Priest(gid, team, hp, atk, dfn, spd);
        }
    }

    static void appendHps(StringBuilder sb, Unit[] team) {
        for (int i = 0; i < team.length; i++) {
            if (i > 0) sb.append(' ');
            sb.append(team[i].hp > 0 ? team[i].hp : 0);
        }
        sb.append('\n');
    }

    static String[] readAll() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder all = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) all.append(line).append(' ');
        StringTokenizer stk = new StringTokenizer(all.toString());
        ArrayList<String> list = new ArrayList<>();
        while (stk.hasMoreTokens()) list.add(stk.nextToken());
        return list.toArray(new String[0]);
    }
}