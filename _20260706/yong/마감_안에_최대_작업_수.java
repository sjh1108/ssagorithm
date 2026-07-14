import java.util.*;
import java.io.*;
/*
마감시한 주어지면 그 시간부터 처리 해보면 될듯
*/
public class Main {
  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int N = Integer.parseInt(br.readLine());
    int[] deadLines = new int[N];

    for(int i = 0; i < N; i++){
      deadLines[i] = Integer.parseInt(br.readLine());
    }
    
    Arrays.sort(deadLines);
    // System.out.println(deadLines[0]);
    int cnt = 0;

    for(int i = 0; i < N; i++){
      if(deadLines[i] <= cnt) continue;
      cnt++;
    }
    System.out.println(cnt);
  }
}
