import java.io.*;
import java.util.*;

/*
 * 숫자 카드 N장, i 번째 카드 정수 Ai 적혀있음
 * 서로 다른 두 장 골라 두 카드에 적힌 수의 합이 X 이하가 되는 카드 조합의 개수
 * x가 21억 초과 가능
 * 카드 쌍의 합 최대일 때 원소 다발로 구하기. -> 투포인터
 * 정렬후 접근해야 빠짐없이 잡을 수 있음
 * 
 * 
 */

public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    int n = Integer.parseInt(st.nextToken()); // 카드의 갯수
    long x = Long.parseLong(st.nextToken()); // 합, 21억 초과가능성ㅇ

    int[] a = new int[n]; // 투포인터의 합으로 효율적인 합 구하기
    st = new StringTokenizer(br.readLine());
    for (int i = 0; i < n; i++) {
      a[i] = Integer.parseInt(st.nextToken()); // 원소 입력
    }

    Arrays.sort(a); // 경우의 수를 구하기 위해 정렬

    long answer = 0;
    int l = 0, r = n - 1; // 투포인터 이용
    while (l < r) {
      if ((long) a[l] + a[r] <= x) { // x보다 작거나, 같은 쌍의 갯수
        answer += r - l; // 정렬했기 때문에 이 구간이 가장 큰값임. 
        l++;
        }
      else r--;
    }
    System.out.println(answer);
  }
}

// 글 잘읽기 -> 최대합일 때 원소의 갯수를 구하라는줄. 

// if (a[l] + a[r] <= x) {
//     answer += r - l;   // a[l]에 대한 판정 완결
//     l++;               // → a[l]을 구간에서 제거
// } else {
//     r--;               // a[r]에 대한 판정 완결 → 제거
// }

// l과 r이 번갈아 움직이는 게 무작위처럼 보이지만, 규칙은 단순합니다.
// 조건을 만족하면 → 더 큰 값을 시도해보기 위해 l++ (왼쪽을 키움)
// 조건을 위반하면 → 합을 줄이기 위해 r-- (오른쪽을 줄임)