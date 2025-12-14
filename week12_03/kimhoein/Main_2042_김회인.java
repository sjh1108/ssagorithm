package study;
import java.io.*;
import java.util.*;

public class Main_2042_김회인 {

	/*
	 * 특정 범위에서 무언가를 빠르게 찾기 위해서 세그트리를 보통 사용한다.
	 * 트리 노드에 각 범위 내에서 특정 값 가장 작은 값 혹은 가장 큰 값 혹은 구간의 합 등의 내용을 저장하여
	 * 특정 구간을 모두 보지 않더라도 빠르게 값을 구하는 알고리즘이다
	 * 여기에서는 구간에서 구간 부모에 구간의 합을 저장해서
	 * 구간 합을 빠르게 구한다.
	 */
	
    static int n;
    static long[] tree;
    
    // 세그먼트 트리 빌드
    static long build(int node, int start, int end, long[] arr) {
        if (start == end) {
            return tree[node] = arr[start];
        }
        int mid = (start + end) / 2;
        long leftSum = build(node * 2, start, mid, arr);
        long rightSum = build(node * 2 + 1, mid + 1, end, arr);
        tree[node] = leftSum + rightSum;
        return tree[node];
    }

    // 구간 합 쿼리
    static long query(int node, int start, int end, int left, int right) {
        if (right < start || end < left) return 0;
        if (left <= start && end <= right) return tree[node];
        int mid = (start + end) / 2;
        long leftSum = query(node * 2, start, mid, left, right);
        long rightSum = query(node * 2 + 1, mid + 1, end, left, right);
        return leftSum + rightSum;
    }

    // 값 업데이트
    static void update(int node, int start, int end, int idx, long val) {
        if (idx < start || idx > end) return;
        if (start == end) {
            tree[node] = val;
            return;
        }
        int mid = (start + end) / 2;
        update(node * 2, start, mid, idx, val);
        update(node * 2 + 1, mid + 1, end, idx, val);
        tree[node] = tree[node * 2] + tree[node * 2 + 1];
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        long[] array = new long[n];
        tree = new long[n * 4];

        for (int i = 0; i < n; i++) {
            array[i] = Long.parseLong(br.readLine()); // 안전하게 long으로 입력
        }

        build(1, 0, n - 1, array);

        for (int i = 0; i < m + k; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            long c = Long.parseLong(st.nextToken());

            if (a == 1) { // 업데이트
                update(1, 0, n - 1, b - 1, c);
            } else { // 구간 합 쿼리
                long result = query(1, 0, n - 1, b - 1, (int) (c - 1)); // c는 범위 끝
                bw.write(result + "\n");
            }
        }

        bw.flush();
        bw.close();
        br.close();
    }
}
