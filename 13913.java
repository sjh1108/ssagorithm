import java.io.*;
import java.util.*;

class Main {
    private static int N, K;

    private static int[] arr;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        if(N == K){
            System.out.println(0);
            System.out.println(N);
            return;
        }

        arr = new int[200_001];
        Arrays.fill(arr, -1);
        arr[N] = N;

        Queue<Integer> q = new ArrayDeque<>();
        q.add(N);

        while(!q.isEmpty()){
            int cur = q.poll();
            if(cur == K) break;

            Integer ans;

            ans = next(cur+1, cur);
            if(ans != null) q.add(ans);

            ans = next(cur-1, cur);
            if(ans != null) q.add(ans);
            
            ans = next(cur*2, cur);
            if(ans != null) q.add(ans);
        }

        ArrayDeque<Integer> stack = new ArrayDeque<>();
        int cur = K;

        while(cur != N){
            stack.push(cur);
            cur = arr[cur];
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append(stack.size() + "\n");
        sb.append(N + " ");
        while(!stack.isEmpty()){
            sb.append(stack.pop() + " ");
        }

        System.out.println(sb);
    }

    private static Integer next(int next, int cur){
        if(!checkMap(next)) return null;

        if(arr[next] != -1) return null;
        arr[next] = cur;

        return next;
    }

    private static boolean checkMap(int p){
        return p >= 0 && p < 200_001;
    }
}