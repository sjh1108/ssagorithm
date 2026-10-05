import java.io.*;
import java.util.*;

public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    int N = Integer.parseInt(br.readLine());

    int[] arr = new int[N], cnt = new int[N];
    StringTokenizer st = new StringTokenizer(br.readLine());
    for(int i = 0; i < N; i++){
      arr[i] = Integer.parseInt(st.nextToken());
    }
    
    for(int i = 0; i < N-1; i++){
      for(int j = i+1; j < N; j++){
        if(arr[j] < arr[i]){
          cnt[i]++;
          cnt[j]++;
        }
      }
    }

    int max = 0;
    int idx = 1;
    for(int i = 0; i < N; i++){
      if(max < cnt[i]) {
        max = cnt[i];
        idx = i+1;
      }
    }

    System.out.println(max + " " + idx);
  }
}
