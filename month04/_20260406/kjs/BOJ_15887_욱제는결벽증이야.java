import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        int[] arr = new int[N + 1];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        StringBuilder sb = new StringBuilder();
        int count = 0;

        for (int i = 1; i <= N; i++) {
            if (arr[i] == i) continue;

            int pos = i;
            while (pos <= N && arr[pos] != i) {
                pos++;
            }

            int left = i;
            int right = pos;
            while (left < right) {
                int temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
                left++;
                right--;
            }

            count++;
            sb.append(i).append(" ").append(pos).append("\n");
        }

        System.out.println(count);
        System.out.print(sb.toString());
    }
}
