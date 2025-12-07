import java.util.*;
import java.io.*;

class Main {

    static int idx = 0;
    static int[] inOrder, postOrder;
    static List<Integer> preOrder = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        inOrder = new int[N];
        postOrder = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            inOrder[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) {
            postOrder[i] = Integer.parseInt(st.nextToken());
        }

        getPreOrder(0, N-1, 0, N-1);

        StringBuilder sb = new StringBuilder();
        for(int n : preOrder) {
            sb.append(n + " ");
        }
        System.out.println(sb);
    }

    static void getPreOrder(int is, int ie, int ps, int pe) {
        if(is > ie || ps > pe) return;

        preOrder.add(postOrder[pe]);

        int root = 0;
        for(int i = is; i <= ie; i++) {
            if(inOrder[i] == postOrder[pe]) {
                root = i;
                break;
            }
        }

        getPreOrder(is, root-1, ps, ps+root-is-1);
        getPreOrder(root+1, ie, ps+root-is, pe-1);
    }
}