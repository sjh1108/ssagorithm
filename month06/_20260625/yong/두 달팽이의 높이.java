import java.util.*;
import java.io.*;
public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

      StringTokenizer st = new StringTokenizer(br.readLine());
      int n = Integer.parseInt(st.nextToken());
      int m = Integer.parseInt(st.nextToken());
      int k = Integer.parseInt(st.nextToken());
      // System.out.println(n + " " + m +" " + k);
      int[] arr = new int[k];

      st = new StringTokenizer(br.readLine());
      for(int i = 0; i < k; i++){
        arr[i] = Integer.parseInt(st.nextToken());
      }

      if(n == m){
        System.out.println(0);
      }
      else{
        int heightA = n;
        int idx = 0;
        int day = 0;

        while(true){
          day++;

          // A 이동 처리(기어오르는 날은 기어오르도록 처리)
          if(idx < k && arr[idx] == day){
            heightA++;
            idx++;

          }else{
            // 높이는 1아래로 못내려감
            if(heightA > 1){
              heightA--;
            }
          }
          // B가 day일 후 heightA에 도달 가능한지 확인
          // 1. 필요한 거리 diff가 day 이하(B도 day일 동안 최대 day칸 이동가능함)
          // 2. 남는 날짜는 왕복 이동으로 처리해야 하므로 짝수여야 함
          int diff =  Math.abs(heightA - m);
          if (diff <= day && (day - diff) % 2 == 0){
            System.out.println(day);
            return;
          }
        }
      }
  }
}
