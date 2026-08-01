package _20260720.thdwngjs.B2;

import java.io.*;
import java.util.*;

class B2 {
    // ===== 상태이상 컴포넌트 =====
    static class Effects {
        int shield = 0, poisonTurns = 0, poisonDmg = 0, stunTurns = 0;
        // 보호막으로 먼저 흡수 후, 체력에 반영할 잔여 피해 반환
        int takeDamage(int amount) {
            if (shield > 0) {
                int absorbed = Math.min(shield, amount);
                shield -= absorbed;
                amount -= absorbed;
            }
            return amount;
        }
    }

    static abstract class Unit {
        int gid, team, maxhp, hp, atk, dfn, spd;
        Effects eff = new Effects();
        Unit(int gid, int team, int hp, int atk, int dfn, int spd) {
            this.gid = gid; this.team = team;
            this.maxhp = hp; this.hp = hp;
            this.atk = atk; this.dfn = dfn; this.spd = spd;
        }
        void hit(int amount) { hp -= eff.takeDamage(amount); }
        abstract void act(Battle b);
    }

    static class Warrior extends Unit {
        Warrior(int g, int t, int hp, int atk, int dfn, int spd) { super(g, t, hp, atk, dfn, spd); }
        void act(Battle b) {
            Unit t = b.enemyMinHp(team);
            int dmg = atk - t.dfn;
            if (dmg < 1) dmg = 1;
            t.hit(dmg);
            t.eff.stunTurns = 1; // 기절 부여(갱신, 미중첩)
        }
    }

    static class Archer extends Unit {
        Archer(int g, int t, int hp, int atk, int dfn, int spd) { super(g, t, hp, atk, dfn, spd); }
        void act(Battle b) {
            Unit t = b.enemyMaxAtk(team);
            t.hit(atk); // 방어 무시
            t.eff.poisonTurns = 3;
            t.eff.poisonDmg = (atk + 1) / 2; // ceil(atk/2)
        }
    }

    static class Priest extends Unit {
        Priest(int g, int t, int hp, int atk, int dfn, int spd) { super(g, t, hp, atk, dfn, spd); }
        void act(Battle b) {
            Unit target = b.allyMinRatio(team, true); // 보호막 없는 아군 우선
            if (target != null) {
                target.eff.shield = atk;
            } else {
                Unit heal = b.allyMinRatio(team, false);
                int v = heal.hp + atk;
                heal.hp = Math.min(v, heal.maxhp);
            }
        }
    }

    static class Battle {
        int rounds;
        Unit[][] teams;
        Unit[] units; // gid 순 (팀0 먼저)
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
            for (Unit u : teams[1 - myTeam])
                if (u.hp > 0 && (best == null || u.hp < best.hp || (u.hp == best.hp && u.gid < best.gid)))
                    best = u;
            return best;
        }
        Unit enemyMaxAtk(int myTeam) {
            Unit best = null;
            for (Unit u : teams[1 - myTeam])
                if (u.hp > 0 && (best == null || u.atk > best.atk || (u.atk == best.atk && u.gid < best.gid)))
                    best = u;
            return best;
        }
        Unit allyMinRatio(int myTeam, boolean needNoShield) {
            Unit best = null;
            for (Unit u : teams[myTeam]) {
                if (u.hp <= 0 || (needNoShield && u.eff.shield != 0)) continue;
                if (best == null) { best = u; continue; }
                long lhs = (long) u.hp * best.maxhp;
                long rhs = (long) best.hp * u.maxhp;
                if (lhs < rhs || (lhs == rhs && u.gid < best.gid)) best = u;
            }
            return best;
        }
        int finish() { // -1 계속, 0/1 승리, 2 무승부
            boolean a0 = teamAlive(0), a1 = teamAlive(1);
            if (a0 && a1) return -1;
            if (!a0 && !a1) return 2;
            return a0 ? 0 : 1;
        }
        int[] simulate() { // {code, elapsed}
            for (int rnd = 1; rnd <= rounds; rnd++) {
                Unit[] order = liveOrder();
                for (Unit u : order) {
                    if (u.hp <= 0) continue;
                    if (u.eff.stunTurns > 0) { u.eff.stunTurns--; continue; }
                    u.act(this);
                    int r = finish();
                    if (r != -1) return new int[]{ r, rnd };
                }
                // 라운드 종료: 독 피해 (번호 순)
                for (Unit u : units) {
                    if (u.hp > 0 && u.eff.poisonTurns > 0) {
                        u.hit(u.eff.poisonDmg);
                        u.eff.poisonTurns--;
                    }
                }
                int r = finish();
                if (r != -1) return new int[]{ r, rnd };
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
        int[] res = new Battle(R, teams).simulate();
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