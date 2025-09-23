package week09_04.sjh1108.BOJ_11505;
import java.io.*;
import java.util.*;

class Main {
    private static final long MOD = 1_000_000_007;

    private static int N;
    
    private static long[] arr, tree;

    // 구간 곱 계산
    private static long mul(int start, int end, int node, int left, int right){
        // 범위 밖에만 존재하는 경우
        // 1을 리턴함
        if(end < left || start > right) return 1;

        // 범위 내에 있는 경우
        // tree[node] 리턴함
        if(left <= start && right >= end) return tree[node];

        // 범위에서 일부 벗어난 경우
        // 범위 내에 있는 값들만을 연산한 결과를 반환받고
        // 해당 결과를 곱연산 하여 리턴함
        int mid = (start + end) / 2;
        return (mul(start, mid, node*2, left, right) * mul(mid+1, end, node*2 + 1, left, right)) % MOD;
    }

    // 배열의 값 변경할 경우
    private static long update(int start, int end, int node, int index, long value){
        // 범위 밖을 벗어 날 경우 tree[node] 반환
        // 합에선 0이었지만, 곱에서는 tree[node]인 이유?
        //  범위 밖이면 값이 변경되진 않지만, 
        //  해당 노드의 루트 노드에선 이 값을 이용해서 곱을 해야하기 때문
        if(index < start || index > end) return tree[node];

        // start == end 인 경우 목적지 노드임
        if(start == end) return tree[node] = value;

        // 그 외의 경우
        // 업데이트가 된 값, 되지 않은 값들을 곱하여 루트 노드들의 값을 연산함
        int mid = (start + end) / 2;
        return tree[node] = (update(start, mid, node*2, index, value) * update(mid+1, end, node*2 + 1, index, value)) % MOD;
    }

    private static long initTree(int start, int end, int node){
        // start == end면 리프 노드임을 의미함
        // 리프 노드면 원본 배열에서 가져옴
        if(start == end) return tree[node] = arr[start];

        // 노드의 리프 노드들의 곱을 이용해서 값 계산
        int mid = (start + end) / 2;
        return tree[node] = (initTree(start, mid, node * 2) * initTree(mid+1, end, node*2 + 1)) % MOD;
    }

    // 2042 와 동일하게 tree 배열 초기화 후 initTree 메소드 호출
    private static void init(){
		int k = (int) Math.ceil(Math.log(N) / Math.log(2)) + 1;
		int size = (int) Math.pow(2, k);
		
		tree = new long[size];

        initTree(1, N, 1);
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        arr = new long[N+1];
        for(int i = 1; i <= N; i++){
            arr[i] = Integer.parseInt(br.readLine());
        }

        init();

        StringBuilder sb = new StringBuilder();
        while(M > 0 || K > 0){
            st = new StringTokenizer(br.readLine());

            int cmd = Integer.parseInt(st.nextToken());
            if(cmd == 1){
                M--;
                int b = Integer.parseInt(st.nextToken());
                long c = Long.parseLong(st.nextToken());

                update(1, N, 1, b, c);
            } else{
                K--;
                int b = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());

                sb.append(mul(1, N, 1, b, c) + "\n");
            }
        }

        System.out.print(sb);
    }
}