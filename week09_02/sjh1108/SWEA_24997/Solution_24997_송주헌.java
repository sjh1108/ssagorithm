package week09_02.sjh1108.SWEA_24997;

import java.util.Scanner;

class Solution_24997_송주헌
{	
	private static final int CMD_INIT				= 100;
	private static final int CMD_PICKUP				= 200;
	private static final int CMD_RESET				= 300;
	private static final int CMD_GET_BEST			= 400;

	private static final int MAX_M					= 2000;

	private static UserSolution_24997_송주헌 usersolution = new UserSolution_24997_송주헌();

	static class Result
	{
		int mX, mY;
		int mMoveDistance;
		int mRideDistance;
		
		Result()
		{
			mX = mY = mMoveDistance = mRideDistance = -1;
		}
	}

	private static long mSeed;
	private static int pseudo_rand()
	{
		mSeed = (mSeed * 1103515245 + 12345) % 2147483647;
		return (int)(mSeed >> 16);
	}
	
	private static int[] mXs = new int[MAX_M];
	private static int[] mYs = new int[MAX_M];
	
	private static boolean run(Scanner sc) throws Exception 
	{
		int Q;
		int N = 0, M, L, mNo;
		int mSX, mSY, mEX, mEY;
		int ret = -1, ans;
		
		Result res;
		int x, y, mdist, rdist;

		int[] mNos = new int[5];

		Q = sc.nextInt();
		mSeed = sc.nextLong();

		boolean okay = false;

		for (int q = 0; q < Q; ++q)
		{
			int cmd = sc.nextInt();

			switch(cmd)
			{
			case CMD_INIT:
				N = sc.nextInt();
				M = sc.nextInt();
				L = N / 10;
				for (int i = 0; i < M; ++i)
				{
					mXs[i] = pseudo_rand() % N;
					mYs[i] = pseudo_rand() % N;
				}
				usersolution.init(N, M, L, mXs, mYs);
				okay = true;
				break;
			case CMD_PICKUP:
				do
				{
					mSX = pseudo_rand() % N;
					mSY = pseudo_rand() % N;
					mEX = pseudo_rand() % N;
					mEY = pseudo_rand() % N;
				} while (mSX == mEX && mSY == mEY);
				ret = usersolution.pickup(mSX, mSY, mEX, mEY);
				ans = sc.nextInt();
				if (ret != ans){
					System.out.println(ret + " : " + ans);
					// System.out.println("NO");
					okay = false;
				}
				break;
			case CMD_RESET:
				mNo = sc.nextInt();
				res = usersolution.reset(mNo);
				x = sc.nextInt();
				y = sc.nextInt();
				mdist = sc.nextInt();
				rdist = sc.nextInt();
				if (res.mX != x || res.mY != y || res.mMoveDistance != mdist || res.mRideDistance != rdist)
					okay = false;
				break;
			case CMD_GET_BEST:
				usersolution.getBest(mNos);
				for (int i = 0; i < 5; ++i)
				{
					ans = sc.nextInt();
					if (mNos[i] != ans){
						System.out.print("BEST ");
						System.out.println(mNos[i] + " : " + ans);
						okay = false;
					}
				}
				break;
			default:
				okay = false;
				break;
			}
		}

		return okay;
	}
	
	public static void main(String[] args) throws Exception
	{
		System.setIn(new java.io.FileInputStream("res/sample_input.txt"));

		Scanner sc = new Scanner(System.in);
		
		int TC = sc.nextInt();
		int MARK = sc.nextInt();
		
		for (int testcase = 1; testcase <= TC; ++testcase)
		{
			int score = run(sc) ? MARK : 0;
			System.out.println("#" + testcase + " " + score);
		}

		sc.close();
		
	}
}