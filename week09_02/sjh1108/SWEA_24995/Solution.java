package week09_02.sjh1108.SWEA_24995;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution
{
    private static final int CMD_INIT           = 100;
    private static final int CMD_SELL           = 200;
    private static final int CMD_CLOSE_SALE     = 300;
    private static final int CMD_DISCOUNT       = 400;
    private static final int CMD_SHOW           = 500;

    private static UserSolution usersolution = new UserSolution();

    public static class RESULT
    {
        int cnt;
        int[] IDs = new int[5];

        RESULT()
        {
            cnt = -1;
        }
    }

    private static boolean run(BufferedReader br) throws Exception
    {
        int Q;
        int mID, mCategory, mCompany, mPrice, mAmount;
        int mHow, mCode;

        int ret = -1, cnt, ans;

        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        Q = Integer.parseInt(st.nextToken());

        boolean okay = false;

        for (int q = 0; q < Q; ++q)
        {
            st = new StringTokenizer(br.readLine(), " ");
            int cmd = Integer.parseInt(st.nextToken());

            switch(cmd)
            {
            case CMD_INIT:
                usersolution.init();
                okay = true;
                break;
            case CMD_SELL:
                mID =  Integer.parseInt(st.nextToken());
                mCategory = Integer.parseInt(st.nextToken());
                mCompany = Integer.parseInt(st.nextToken());
                mPrice = Integer.parseInt(st.nextToken());
                ret = usersolution.sell(mID, mCategory, mCompany, mPrice);
                ans = Integer.parseInt(st.nextToken());
                if (ret != ans){
                    okay = false;
                    // System.out.println("NO");
                }
                break;
            case CMD_CLOSE_SALE:
                mID =  Integer.parseInt(st.nextToken());
                ret = usersolution.closeSale(mID);
                ans = Integer.parseInt(st.nextToken());
                if (ret != ans){
                    okay = false;
                    // System.out.println("NO");
                    System.out.println(ret + " : " + ans);
                }
                break;
            case CMD_DISCOUNT:
                mCategory = Integer.parseInt(st.nextToken());
                mCompany = Integer.parseInt(st.nextToken());
                mAmount = Integer.parseInt(st.nextToken());
                ret = usersolution.discount(mCategory, mCompany, mAmount);
                ans = Integer.parseInt(st.nextToken());
                if (ret != ans)
                    okay = false;
                break;
            case CMD_SHOW:
                mHow = Integer.parseInt(st.nextToken());
                mCode = Integer.parseInt(st.nextToken());
                RESULT res = usersolution.show(mHow, mCode);
                cnt = Integer.parseInt(st.nextToken());
                if (res.cnt != cnt){
                    System.out.println("NO");
                    okay = false;
                }
                for (int i = 0; i < cnt; ++i)
                {
                    ans = Integer.parseInt(st.nextToken());
                    if (res.IDs[i] != ans){
                        System.out.println("NO");
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

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        int TC = Integer.parseInt(st.nextToken());
        int MARK = Integer.parseInt(st.nextToken());

        for (int testcase = 1; testcase <= TC; ++testcase)
        {
            int score = run(br) ? MARK : 0;
            System.out.println("#" + testcase + " " + score);
        }

        br.close();
    }
}