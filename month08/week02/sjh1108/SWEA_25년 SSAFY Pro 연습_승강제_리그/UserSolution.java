import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.TreeSet;

class UserSolution {
    private int L;
    private static int leagueSize;

    private static League[] leagues;

    void init(int N, int L, int mAbility[]) {
        leagues = new League[L];
        leagueSize = N / L;
        this.L = L;

        for(int i = 0; i < L; i++){
            League league = new League(leagueSize);
            leagues[i] = league;

            PriorityQueue<Player> players = new PriorityQueue<>();
            for(int j = i*leagueSize; j < (i+1) * leagueSize; j++){
                int playerIdx = j;

                Player player = new Player(playerIdx, mAbility[playerIdx]);
                players.add(player);
            }
            TreeSet<Player> upper = new TreeSet<>();
            TreeSet<Player> lower = new TreeSet<>();

            for(int j = 0; j < leagueSize / 2; j++){
                Player player = players.poll();
                lower.add(player);
            }
            while(!players.isEmpty()){
                Player player = players.poll();
                upper.add(player);
            }

            league.init(upper, lower);
        }
    }

    int move() {
        int sum = 0;

        for(int i = 0; i < this.L-1; i++){
            League upperLeague = leagues[i];
            League lowerLeague = leagues[i+1];

            sum += League.move(upperLeague, lowerLeague);
        }

        for(League league : leagues){
            league.reOrder();
        }

        return sum;
    }

    int trade() {
        int sum = League.trade(leagues);

        for(League league : leagues){
            league.reOrder();
        }

        return sum;
    }

}

class League{
    private int leagueSize;

    private TreeSet<Player> upper;
    private TreeSet<Player> lower;

    public League(int leagueSize){
        this.leagueSize = leagueSize;
    }

    public void init(TreeSet<Player> upper, TreeSet<Player> lower) {
        this.upper = upper;
        this.lower = lower;
    }

    public static int move(League upperLeague, League lowerLeague){
        Player upperPlayer = upperLeague.lower.pollFirst();
        Player lowerPlayer = lowerLeague.upper.pollLast();

        upperLeague.lower.add(lowerPlayer);
        lowerLeague.upper.add(upperPlayer);

        // System.out.println("Upper : " + upperPlayer);
        // System.out.println("Lower : " + lowerPlayer);

        return upperPlayer.id + lowerPlayer.id;
    }

    public static int trade(League[] leagues){
        int sum = 0;

        ArrayDeque<Player> uppperPlayers = new ArrayDeque<>(leagues.length - 1);
        ArrayDeque<Player> lowerPlayers = new ArrayDeque<>(leagues.length - 1);

        for(int i = 0; i < leagues.length - 1; i++){
            League upperLeague = leagues[i];
            League lowerLeague = leagues[i+1];

            Player upperPlayer = upperLeague.upper.pollFirst();
            Player lowerPlayer = lowerLeague.upper.pollLast();

            uppperPlayers.add(upperPlayer);
            lowerPlayers.add(lowerPlayer);
        }

        for(int i = 0; i < leagues.length - 1; i++){
            League upperLeague = leagues[i];
            League lowerLeague = leagues[i+1];

            Player upperPlayer = uppperPlayers.poll();
            Player lowerPlayer = lowerPlayers.poll();

            upperLeague.upper.add(lowerPlayer);
            lowerLeague.upper.add(upperPlayer);

            // System.out.println("Upper : " + upperPlayer);
            // System.out.println("Lower : " + lowerPlayer);

            sum += upperPlayer.id + lowerPlayer.id;
        }

        return sum;
    }

    public void reOrder(){
        if(upper.size() == leagueSize / 2){
            upper.add(lower.pollLast());
        }

        boolean flag = true;
        while(flag){
            flag = false;

            if(upper.first().compareTo(lower.last()) < 0){
                Player upperPlayer = upper.pollFirst();
                Player lowerPlayer = lower.pollLast();

                upper.add(lowerPlayer);
                lower.add(upperPlayer);

                flag = true;
            }
        }
    }
}

class Player implements Comparable<Player> {
    int id;
    int ability;

    Player(int id, int ability) {
        this.id = id;
        this.ability = ability;
    }

    @Override
    public int compareTo(Player o) {
        if(this.ability != o.ability) {
            return this.ability - o.ability;
        } else {
            return o.id - this.id;
        }
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", ability=" + ability +
                '}';
    }
}