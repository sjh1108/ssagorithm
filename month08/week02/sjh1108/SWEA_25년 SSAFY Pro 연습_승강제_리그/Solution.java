import java.util.Scanner;

class Solution {
    private static Scanner sc;
    private static UserSolution usersolution = new UserSolution();

    private final static int CMD_INIT = 100;
    private final static int CMD_MOVE = 200;
    private final static int CMD_TRADE = 300;

    private static boolean run() throws Exception {

        int query_num = sc.nextInt();
		int ans;
        boolean ok = false;

        for (int q = 0; q < query_num; q++) {
            int query = sc.nextInt();

            if (query == CMD_INIT) {
                int N = sc.nextInt();
                int L = sc.nextInt();
                int mAbility[] = new int[N];
                for (int i = 0; i < N; i++){
                    mAbility[i] = sc.nextInt();
                }
                usersolution.init(N, L, mAbility);
                ok = true;
            } else if (query == CMD_MOVE) {
                int ret = usersolution.move();
                ans = sc.nextInt();
                if (ans != ret) {
                    ok = false;
                }
            } else if (query == CMD_TRADE) {
                int ret = usersolution.trade();
                ans = sc.nextInt();
                if (ans != ret) {
                    ok = false;
                }
            }
        }
        return ok;
    }

    public static void main(String[] args) throws Exception {
        int T, MARK;

        // System.setIn(new java.io.FileInputStream("res/sample_input.txt"));
        sc = new Scanner(System.in);
        T = sc.nextInt();
        MARK = sc.nextInt();

        for (int tc = 1; tc <= T; tc++) {
            int score = run() ? MARK : 0;
            System.out.println("#" + tc + " " + score);
        }
        sc.close();
    }
}