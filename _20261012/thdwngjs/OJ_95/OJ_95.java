package _20261012.thdwngjs.OJ_95;

import java.io.*;
import java.util.*;

// 오답 사유 : 비트 마스킹으로 풀어보겠다고 깝치다가 정렬 무시하기
// 비트마스킹 살려보겠다고 정렬 온몸비틀기 성공
class Main {
  private static int N;
  private static long L, R;
  private static int cnt;

  private static int[] arr;
  private static List<List<Integer>> answerList;
  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());

    N = Integer.parseInt(st.nextToken());
    L = Long.parseLong(st.nextToken());
    R = Long.parseLong(st.nextToken());

    arr = new int[N];
    st = new StringTokenizer(br.readLine());
    for(int i = 0; i < N; i++){
      arr[i] = Integer.parseInt(st.nextToken());
    }

    cnt = 0;
    answerList = new ArrayList<>();
    for(int i = 0; i < 1 << N; i++){
      combine(i);
    }

    if(cnt == 0){
      System.out.println(0);
      return;
    }

    answerList.sort((a, b) -> {
      int len = Math.min(a.size(), b.size());
      for(int k = 0; k < len; k++){
        int x = a.get(k), y = b.get(k);
        if(x != y) return Integer.compare(x, y);
      }
      return Integer.compare(a.size(), b.size());
    });

    StringBuilder sb = new StringBuilder();
    sb.append(cnt).append('\n');
    for(List<Integer> list: answerList){
      for(int x: list){
        sb.append(x).append(' ');
      }
      sb.append('\n');
    }
    System.out.print(sb);
  }

  private static void combine(int bit){
    long sum = 0L;

    List<Integer> list = new ArrayList<>();
    for(int i = 0; i < N; i++){
      if((bit & (1 << i)) > 0){
        sum += arr[i];
        list.add(i + 1);
      }
    }

    if(L <= sum &&sum <= R) {
      cnt++;
      answerList.add(list);
    }
  }
}
