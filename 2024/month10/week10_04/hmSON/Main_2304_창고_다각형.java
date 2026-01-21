import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main_2304_창고_다각형 {
	
	// BOJ_2304 : 창고 다각형(Silver_1)
	// 자료구조 및 알고리즘 : 구현, 브루트 포스, 스택
	// 주어진 기둥들을 이용하여 창고의 지붕을 오목하게 들어간 부분 없이 만들어야 한다고 할 때, 가장 작은 창고 다각형 면적을 구해야 한다.
	
	public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 기둥 수
        int n = Integer.parseInt(br.readLine());
        // 각 기둥의 위치와 높이
        int[][] pillars = new int[n][2];
        for(int i=0; i<n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            pillars[i][0] = Integer.parseInt(st.nextToken());
            pillars[i][1] = Integer.parseInt(st.nextToken());
        }
        
        // 기둥 배열을 위치 순으로 정렬
        Arrays.sort(pillars, Comparator.comparingInt(a -> a[0]));
        
        // 창고 다각형의 총 면적
        int area = 0;
        // 가장 높은 기둥의 위치
        int maxHeightIndex = 0;
        for(int i=0; i<n; i++) {
            if(pillars[maxHeightIndex][1] < pillars[i][1]) {
                maxHeightIndex = i;
            }
        }
        
        Deque<int[]> stack = new ArrayDeque<>();
        
        // 가장 높은 기둥의 위치 기준 왼쪽 기둥 탐색
        stack.push(pillars[0]);
        for(int i = 1; i <= maxHeightIndex; i++) {
            int[] current = pillars[i];
            int[] prev = stack.peek();
            
            if(current[1] >= prev[1]) {
                area += (current[0] - prev[0]) * prev[1];
                stack.pop();
                stack.push(current);
            }
        }
        
        stack.clear();
        
        // 가장 높은 기둥의 위치 기준 오른쪽 기둥 탐색
        stack.push(pillars[n - 1]);
        for(int i = n - 2; i >= maxHeightIndex; i--) {
            int[] current = pillars[i];
            int[] prev = stack.peek();
            if(current[1] >= prev[1]) {
                area += (prev[0] - current[0]) * prev[1];
                stack.pop();
                stack.push(current);
            }
        }
        
        // 마지막으로 가장 높은 기둥 자체가 차지하는 넓이 추가
        area += pillars[maxHeightIndex][1];
        
        System.out.println(area);
    }

}
