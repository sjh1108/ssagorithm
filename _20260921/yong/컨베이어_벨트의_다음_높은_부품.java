import java.io.*;
import java.util.*;

public class 컨베이어_벨트의_다음_높은_부품 {
    public static class Component{
        int loc; int h;
        public Component(int loc, int h){
            this.loc = loc;
            this.h = h;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] results = new int[N+1];
        Arrays.fill(results, -1);

        Stack<Component> stack = new Stack<>();

        int[] nums = new int[N+1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 1; i <= N; i++){
            nums[i] = Integer.parseInt(st.nextToken());
        }

        // 가장 오른쪽 부품 푸시
        stack.push(new Component(N, nums[N]));

        for(int i = N-1; i > 0; i--){
            // 본인보다 높은 부품을 찾거나 본인보다 높은부품이 없다면 탈출
            while(!stack.isEmpty()){
                // 본인보다 낮거나 같으면 
                if(stack.peek().h <= nums[i]){
                    stack.pop();
                }
                // 본인보다 높으면 기록 후 탈출
                else{
                    results[i] = stack.peek().loc;
                    break;
                }
            }
            // 본인 스택에 추가
            stack.push(new Component(i, nums[i]));
        }

        for(int i = 1; i <= N; i++){
            System.out.print(results[i] + " ");
        }
    }
}
