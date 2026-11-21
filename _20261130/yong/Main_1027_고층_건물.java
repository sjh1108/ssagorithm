import java.io.*;
import java.util.*;
/**
 * Main_1027_고층_건물
 */
public class Main_1027_고층_건물 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        int[] heights = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++){
            heights[i] = Integer.parseInt(st.nextToken());
        }

        int[] visible = new int[N];
        for (int i = 0; i < N - 1; i++) {
            // 바로 오른쪽 건물은 서로 보임 + 초기값을 오른쪽 건물과의 기울기
            double maxSlope = heights[i + 1] - heights[i];
            visible[i]++;
            visible[i + 1]++;

            for (int right = i + 2; right < N; right++) {
                // 두 건물의 옥상을 잇는 선의 기울기 (높이 차 / 가로 거리)
                double slope = (double) (heights[right] - heights[i]) / (right - i);

                if (slope > maxSlope) {
                    maxSlope = slope;
                    // 해당 i 건물에서 보이는 개수
                    visible[i]++;
                    // right에서도 i가 보이므로 증가
                    visible[right]++;
                }
            }
        }
        
        int answer = 0;
        for (int count : visible) {
            answer = Math.max(answer, count);
        }
        System.out.println(answer);
    }
    
}