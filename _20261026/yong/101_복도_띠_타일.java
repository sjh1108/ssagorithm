import java.util.*;
import java.io.*;
public class Main {
  static int L;
  static int[] tiles = new int[6];
  static int result;
  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    L = Integer.parseInt(br.readLine());
    StringTokenizer st = new StringTokenizer(br.readLine());

    for(int i = 1; i <= 5; i++){
      tiles[i] = Integer.parseInt(st.nextToken());
    }

    back(0);

    System.out.println(result);
  }
  public static void back(int len){
    if(len == L){
      result++;
      return;
    }
    for(int k = 1; k <= 5; k++){
      if(tiles[k] == 0){ // 타일 없으면 다음 타일
        continue;
      }
      if(len + k > L){ // 복도길이 초과 x
        continue;
      }
      tiles[k]--; // 타일 사용
      back(len + k);
      tiles[k]++; // 다음 경우의 수를 위해 타일 복구

    }
  }
  
}
