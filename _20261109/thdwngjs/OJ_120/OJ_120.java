package _20261109.thdwngjs.OJ_120;

import java.io.*;
import java.util.*;

class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      int N = Integer.parseInt(br.readLine());

    long[] arr = new long[N];
    StringTokenizer st = new StringTokenizer(br.readLine());
    for(int i = 0; i < N; i++){
      arr[i] = Integer.parseInt(st.nextToken());
    }
    
    int top = 0;
    long total = 0;
    long[] sum = new long[N];
    int[] stack = new int[N];
    for(int i = 0; i < N; i++){
      // a[i] 이상인 값은 i보다 오른쪽 구간에서 최솟값이 될 수 없으므로 버린다
      while(top > 0 && arr[stack[top-1]] >= arr[i]) top--;
      
      long s;
      if(top > 0){
        int p = stack[top-1];
        s = sum[p] + (arr[i] * (i - p));
        // l <= p 는 sum[p] 그대로
        // p < l <= i는 최솟값이 a[i]
      } else{
        // arr[i] 가 현재까지 최소값이라면
        // arr[i]를 보장 출력으로 갖는 부분 집합의 개수 = 현재까지 부분집합 개수
        s = arr[i] * (i+1);
      }

      sum[i] = s;
      total += s;
      stack[top++] = i;
    }

    System.out.println(total);
  }
}
