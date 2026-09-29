import java.io.*;
import java.util.*;

public class Main {

    static int N;
    static long[] arr;
    static long[] tree;

    static long init(int node, int start, int end) {
        if (start == end) return tree[node] = arr[start];

        int mid = (start + end) / 2;

        return tree[node] = Math.max(init(node * 2, start, mid), init(node * 2 + 1, mid + 1, end));
    }

    static long query(int node, int start, int end, int left, int right) {
        if (right < start || end < left) return 0;

        if (left <= start && end <= right) return tree[node];

        int mid = (start + end) / 2;

        long l = query(node * 2, start, mid, left, right);
        long r = query(node * 2 + 1, mid + 1, end, left, right);
        return Math.max(l, r);
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        arr = new long[n];
        tree = new long[n * 4];

        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(br.readLine());
        }

        init(1, 0, n - 1);

        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            bw.write((int) query(1, 0, n - 1, a - 1, b - 1) + "\n");
        }

        bw.flush();
        bw.close();
    }
}
