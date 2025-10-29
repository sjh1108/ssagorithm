import java.io.*;
import java.util.*;

class Main {

    static int H, W;    // (1 ≤ H, W ≤ 500)
    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());
        
        int[] map = new int[W];

        st = new StringTokenizer(br.readLine());
        for(int j = 0; j < W; j++)
            map[j] = Integer.parseInt(st.nextToken());

        // 시간 제한 1초, 2차원 배열 크기 최대 500 X 500 => 브루트포스
        // 그럼 어떻게 브루트포스를 할 지 생각해봅시다.
        // 먼저, 빗물이 고이는 조건을 생각해봅니다.
        // 양 옆으로 블록이 존재하면 빗물이 고이는 조건입니다.
        // 지붕이 있는 경우는 없는 것 같으니 
        // 행마다 양옆에 블록이 있는 비어있는 공간들을 찾으면 될 것 같습니다.

        int ans = 0;

        for(int i = 1; i <= H; i++) {
            int lastIndex = -1;
            
            for(int j = 0; j < W; j++) {
                if(map[j] >= i) {

                    // -1인 경우 첫 블록이 나오지 않은 경우라서 계산을 하지 않습니다.
                    if(lastIndex != -1)
                        ans += j - lastIndex - 1;

                    // 마지막 블록 위치를 갱신해줍니다.
                    lastIndex = j;
                }
            }
        }

        System.out.println(ans);
    }

}