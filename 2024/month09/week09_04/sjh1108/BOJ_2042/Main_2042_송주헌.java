package week09_04.sjh1108.BOJ_2042;

import java.io.*;
import java.util.*;

class Main {
    private static long[] arr;
    private static long[] tree;

    // 세그먼트 트리 초기화
    private static long init(int start, int end, int node){
        // 리프 노드의 경우
        if(start == end) return tree[node] = arr[start];

        int mid = (start + end) / 2;

        // 재귀적으로 두 부분을 나눈 뒤에 그 합을 자기 자신으로 함
        return tree[node] = init(start, mid, node*2) + init(mid+1, end, node*2 + 1);
    }

    // 세그먼트 트리 업데이트
    private static void update(int start, int end, int node, int index, long dif){
        // 범위를 벗어나면 업데이트 불필요
        if(index < start || index > end) return;

        // 노드 업데이트
        tree[node] += dif;
        // 리프라면 재귀 탐색 불필요하기 때문에 return
        if(start == end) return;

        int mid = (start + end) / 2;

        // 리프 노드들을 탐색하며 업데이트
        update(start, mid, node * 2, index, dif);
        update(mid+1, end, node*2+1, index, dif);
    }

    // 세그먼트 트리에서 구간 합 구하기
    private static long sum(int start, int end, int node, int left, int right){
        // 범위에서 벗어나면 필요 없기 때문에 0 return
        // ** 탐색하고자 하는 값에서 벗어났다는 뜻 **
        if(left > end || right < start) return 0;

        // 범위 안에 있는 경우 현재 node 값 return
        // 범위 안에 있다는건 리프 노드들이 가리키고 있는 값들은
        // 현재 목표로 하고 있는 범위 내에 있다는 뜻
        if(left <= start && end <= right) return tree[node];

        int mid = (start + end) / 2;

        // 그 외의 경우 범위를 좁혀서 탐색
        return sum(start, mid, node*2, left, right) + sum(mid+1, end, node*2+1, left, right);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        arr = new long[N+1];

        // 일반적으로 Segment Tree에서는 4 * N을 통해서 초기화를 해주지만
        // (4를 곱해주면 필요한 사이즈가 보장이 됨)
        // 해당 풀이에서는 메모리를 최소한으로 사용할 수 있게끔 작성하였음

        // 메모리를 최소화 하기 위해서는
        // 2^k >= N인 최소의 k를 찾아야 함
        // k >= logN / log2
        // logN / log2의 값을 올림 한 후 +1을 해주면 k가 됨
        // 위에서 구한 k를 제곱하면 세그먼트 트리의 최소 size를 구할 수 있음
        int k = (int)Math.ceil(Math.log(N) / Math.log(2)) + 1;
        int size = (int)Math.pow(2, k);
        
        tree = new long[size];

        for(int i = 1; i <= N; i++){
            arr[i] = Long.parseLong(br.readLine());
        }

        init(1, N, 1);

        StringBuilder sb = new StringBuilder();
        while(M > 0 || K > 0){
            st = new StringTokenizer(br.readLine());

            int cmd = Integer.parseInt(st.nextToken());

            // cmd가 1인 경우 업데이트
            if(cmd == 1){
                M--;
                int index = Integer.parseInt(st.nextToken());
                long n = Long.parseLong(st.nextToken());

                long dif = n - arr[index];
                arr[index] = n;
                
                update(1, N, 1, index, dif);
            }
            // cmd가 2인 경우 구간 합을 계산
            else{
                K--;

                int left = Integer.parseInt(st.nextToken());
                int right = Integer.parseInt(st.nextToken());

                sb.append(sum(1, N, 1, left, right) + "\n");
            }
        }

        System.out.println(sb);
    }
}