package week09_01.sjh1108;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution_19340_송주헌 {
	private final static int CMD_INIT = 1;
	private final static int CMD_ADD = 2;
	private final static int CMD_REMOVE = 3;
	private final static int CMD_DISTRIBUTE = 4;

	private final static UserSolution_19340_송주헌 usersolution = new UserSolution_19340_송주헌();

	private static boolean run(BufferedReader br) throws Exception {
		int q = Integer.parseInt(br.readLine());

		int[] midArr = new int[1000];
		int[] mnumArr = new int[1000];
		int mid, mnum, mparent, n, k;
		int cmd, ans, ret = 0;
		boolean okay = false;

		for (int i = 0; i < q; ++i) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			cmd = Integer.parseInt(st.nextToken());
			switch (cmd) {
				case CMD_INIT:
					n = Integer.parseInt(st.nextToken());
					for (int j = 0; j < n; ++j) {
						StringTokenizer dep = new StringTokenizer(br.readLine(), " ");
						midArr[j] = Integer.parseInt(dep.nextToken());
						mnumArr[j] = Integer.parseInt(dep.nextToken());
					}
					usersolution.init(n, midArr, mnumArr);
					okay = true;
					break;
				case CMD_ADD:
					mid = Integer.parseInt(st.nextToken());
					mnum = Integer.parseInt(st.nextToken());
					mparent = Integer.parseInt(st.nextToken());
					ans = Integer.parseInt(st.nextToken());
					ret = usersolution.add(mid, mnum, mparent);
					if (ret != ans)
						okay = false;
					break;
				case CMD_REMOVE:
					mid = Integer.parseInt(st.nextToken());
					ans = Integer.parseInt(st.nextToken());
					ret = usersolution.remove(mid);
					if (ret != ans)
						okay = false;
					break;
				case CMD_DISTRIBUTE:
					k = Integer.parseInt(st.nextToken());
					ans = Integer.parseInt(st.nextToken());
					ret = usersolution.distribute(k);
					if (ret != ans)
						okay = false;
					break;
				default:
					okay = false;
					break;
			}
		}
		return okay;
	}

	public static void main(String[] args) throws Exception {
		int TC, MARK;

		System.setIn(new java.io.FileInputStream("res/sample_input.txt"));

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		TC = Integer.parseInt(st.nextToken());
		MARK = Integer.parseInt(st.nextToken());

		for (int testcase = 1; testcase <= TC; ++testcase) {
			int score = run(br) ? MARK : 0;
			System.out.println("#" + testcase + " " + score);
		}

		br.close();
	}
}