import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    int N = Integer.parseInt(br.readLine());
    StringTokenizer st = new StringTokenizer(br.readLine());
    int[] arr = new int[N];
    for(int i = 0; i < N; i++){
      arr[i] = Integer.parseInt(st.nextToken());
    }

    int ans = 0;
    for (int b = 29; b >= 0; b--) {
      int cand = ans | (1 << b);

      Set<Integer> set = new HashSet<>();
      for (int x : arr) {
        set.add(x >> b);
      }

      int c = cand >> b;

      for (int x : set) {
        if (set.contains(x ^ c)) {
          ans = cand;
          break;
        }
      }
    }
    System.out.print(ans);
  }
}
