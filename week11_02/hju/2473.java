import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        long[] arr = new long[N];

        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) 
            arr[i] = Long.parseLong(st.nextToken());

        Arrays.sort(arr);

        long minSum = Long.MAX_VALUE;
        long ans1 = 0, ans2 = 0, ans3 = 0;

        // 첫 번째 용액(i)을 0부터 N-3까지 고정
        for (int i = 0; i < N - 2; i++) {
            int l = i + 1;
            int r = N - 1;

            while (l < r) {
                long sum = arr[i] + arr[l] + arr[r];
                long absSum = Math.abs(sum);

                // 0에 더 가까운 합을 찾으면 갱신
                if (absSum < minSum) {
                    minSum = absSum;
                    ans1 = arr[i];
                    ans2 = arr[l];
                    ans3 = arr[r];
                }

                if (sum < 0)        l++; // 합을 키워야 함
                else if (sum > 0)   r--; // 합을 줄여야 함
                else {
                    // 합이 0이면 완벽한 답이므로 즉시 종료
                    i = N; // 바깥쪽 for문까지 종료
                    break;
                }
            }
        }

        System.out.println(ans1 + " " + ans2 + " " + ans3);
    }
}