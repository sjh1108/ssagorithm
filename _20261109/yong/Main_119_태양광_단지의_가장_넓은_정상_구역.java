import java.util.*;
import java.io.*;
public class Main_119_태양광_단지의_가장_넓은_정상_구역 {
  static int N, M;
  static int[][] panels;
  static int[] heights;

  // 막대에서 오른쪽으로 선을 긋는다고 생각
  // 다음 막대보다 높은 막대들은 다음 막대 이전(오른쪽 경계)까지가 최대 직사각형
  public static int findMaxSquare(){
    int maxSquare = 0;
    Deque<Integer> stack = new ArrayDeque<>();
    stack.push(-1); // 왼쪽 경계용 센티널

    for(int i = 0; i <= M; i++){
      int curHeight;
      if(i == M){
        curHeight = 0; // 마지막엔 높이 0 막대를 넣어서 스택에 남은 막대 처리
      }
      else{
        curHeight = heights[i];
      }
      
      // 최대 사각형 계산 로직
      // 막대 하나씩 push 하면서 최대 사각형 체크
      // 스택 비면 안됨 & 현재 막대보다 크면 pop(while true)
      while(stack.peek() != -1 && curHeight < heights[stack.peek()]){
        int h = heights[stack.pop()];
        int w = i - stack.peek() - 1;
        maxSquare = Math.max(maxSquare, h * w);
      }
      if(i != M){
        stack.push(i);
      }
    }
    return maxSquare;
  }
  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    N = Integer.parseInt(st.nextToken());
    M = Integer.parseInt(st.nextToken());

    heights = new int[M];
    int maxSquare = 0;

    // 한 행씩 누적하면서 최대 직사각형 구역 찾기
    for(int r = 0; r < N; r++){
      String line = br.readLine();
      for(int c = 0; c < M; c++){
        char panel = line.charAt(c);
        if(panel == '1'){
          heights[c] += 1;
        }
        else{
          heights[c] = 0;
        }
      }
      maxSquare = Math.max(maxSquare, findMaxSquare());
    }
    System.out.println(maxSquare);
  }
}
