import java.io.*;
import java.util.*;

public class oj_3_이용호 {

    static int N, M, R;
    static int[][] arr;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());

        arr = new int[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int layerCount = Math.min(N, M) / 2;

        for (int layer = 0; layer < layerCount; layer++) {
            rotateLayer(layer);
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                sb.append(arr[i][j]).append(' ');
            }
            sb.append('\n');
        }

        System.out.print(sb);
    }

    static void rotateLayer(int layer) {
        List<Integer> list = new ArrayList<>();

        int top = layer;
        int left = layer;
        int bottom = N - 1 - layer;
        int right = M - 1 - layer;

        // 위쪽: 왼쪽 -> 오른쪽
        for (int j = left; j <= right; j++) {
            list.add(arr[top][j]);
        }

        // 오른쪽: 위 -> 아래
        for (int i = top + 1; i <= bottom; i++) {
            list.add(arr[i][right]);
        }

        // 아래쪽: 오른쪽 -> 왼쪽
        for (int j = right - 1; j >= left; j--) {
            list.add(arr[bottom][j]);
        }

        // 왼쪽: 아래 -> 위
        for (int i = bottom - 1; i > top; i--) {
            list.add(arr[i][left]);
        }

        int size = list.size();
        int move = R % size;

        // 반시계 방향 회전
        Collections.rotate(list, -move);

        int idx = 0;

        // 다시 배열에 넣기
        for (int j = left; j <= right; j++) {
            arr[top][j] = list.get(idx++);
        }

        for (int i = top + 1; i <= bottom; i++) {
            arr[i][right] = list.get(idx++);
        }

        for (int j = right - 1; j >= left; j--) {
            arr[bottom][j] = list.get(idx++);
        }

        for (int i = bottom - 1; i > top; i--) {
            arr[i][left] = list.get(idx++);
        }
    }
}