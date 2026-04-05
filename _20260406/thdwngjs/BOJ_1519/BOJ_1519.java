import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        if(N < 10){
            System.out.println(-1);
            return;
        }

        int[] dp = new int[N + 1];

        for (int n = 10; n <= N; n++) {
            String str = String.valueOf(n);
            Set<String> set = new HashSet<>();

            for (int start = 0; start < str.length(); start++) {
                if (str.charAt(start) == '0') {
                    continue;
                }

                String res = "";
                for (int i = start; i < str.length(); i++) {
                    res += str.charAt(i);

                    if (!res.equals(str)) {
                        set.add(res);
                    }
                }
            }

            Iterator<String> it = set.iterator();

            int min = Integer.MAX_VALUE;
            while (it.hasNext()) {
                int num = Integer.parseInt(str);
                int tmp = Integer.parseInt(it.next());

                if (dp[num - tmp] == 0) {
                    min = Math.min(min, tmp);
                }
            }

            if (min != Integer.MAX_VALUE) {
                dp[n] = min;
            }
        }
        
        System.out.println(dp[N] == 0 ? -1 : dp[N]);
    }
}