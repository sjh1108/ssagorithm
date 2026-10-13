import java.io.*;
import java.util.*;

class Main {
  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int N = Integer.parseInt(br.readLine());

    int[] arr = new int[N];
    StringTokenizer st = new StringTokenizer(br.readLine());
    for(int i = 0; i < N; i++){
      arr[i] = Integer.parseInt(st.nextToken());
    }

    long cnt = 0L;
    for(int i = 0; i < N; i++){
      // 중심이 1
      int l = i-1, r = i+1;
      while(l >= 0 && r < N && arr[l] == arr[r]){
        cnt++;
        l--;
        r++;
      }

      // 중심이 2
      l = i-1; r = i;
      while(l >= 0 && r < N && arr[l] == arr[r]){
        cnt++;
        l--;
        r++;
      }
    }

    System.out.println(cnt);
  }
}
