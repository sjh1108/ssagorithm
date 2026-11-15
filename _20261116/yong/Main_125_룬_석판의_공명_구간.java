import java.util.*;
import java.io.*;
public class Main {
  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    int N = Integer.parseInt(st.nextToken());
    int K = Integer.parseInt(st.nextToken());

    Map<Integer, Integer> map = new HashMap<>();
    int xor = 0;
    int answer = 0;
    map.put(0,1);
    st = new StringTokenizer(br.readLine());
    for(int i = 0; i < N; i++){
      int num = Integer.parseInt(st.nextToken());
      // 현재 위치까지 누적 xor
      xor ^= num;

      // xor이 K인 구간의 개수 누적
      answer += map.getOrDefault(xor ^ K, 0);

      // 현재 누적 xor 등장 횟수 누적
      map.put(xor, map.getOrDefault(xor, 0) + 1);
    }
    System.out.println(answer);
  }
}
