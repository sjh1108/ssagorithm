package week03.sjh1108.BOJ_2294;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

public class Main_2294_송주헌 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        
        // DP 배열 초기화
        // dp[i]는 i원을 만들기 위한 최소 동전 개수
        int[] dp = new int[100001];
        Arrays.fill(dp, Integer.MAX_VALUE);
        Queue<Integer> q = new ArrayDeque<>();
        // 동전의 중복을 피하기 위해 Set 사용
        // 이제 보니 사용하지 않아도 되는 부분이라고 생각됨
        Set<Integer> set = new HashSet<>();

        // 동전 입력
        while(N-- > 0){
            int tmp = Integer.parseInt(br.readLine());
            q.offer(tmp);
            dp[tmp] = 1;
            set.add(tmp);
        }

        // BFS를 이용하여 최소 동전 개수 구하기
        Integer[] list = set.toArray(new Integer[set.size()]);

        while(!q.isEmpty()){
            int cur = q.poll();

            for(Integer i: list){
                int tmp = cur + i;

                if(tmp > K) continue;
                if(dp[tmp] > dp[cur] + 1){
                    q.offer(tmp);
                    dp[tmp] = dp[cur]+1;
                }
            }
        }

        // 결과 출력
        if(dp[K] == Integer.MAX_VALUE) System.out.println(-1);
        else{
            System.out.println(dp[K]);
        }
    }
}