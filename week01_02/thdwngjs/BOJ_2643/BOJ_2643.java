package week01_02.thdwngjs.BOJ_2643;

import java.io.*;
import java.util.*;
class BOJ_2643 {

    static class Shape implements Comparable<Shape>{
        int x, y;

        Shape(int x, int y){
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Shape o){
            if(this.x == o.x){
                return Integer.compare(o.y, this.y);
            }
            return Integer.compare(o.x, this.x);
        }
    }

    private static List<Shape> shapes;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N  = Integer.parseInt(br.readLine());
        int ans = -1;
        int[] dp = new int[N+1];

        shapes = new ArrayList<>();
        StringTokenizer st;
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());

            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            if(x > y) shapes.add(new Shape(x, y)); 
            else      shapes.add(new Shape(y, x));
        }

        Collections.sort(shapes);

        for(int i = 0; i < N; i++){
            dp[i] = 1;

            for(int j = 0; j < i; j++){
                if(shapes.get(i).x <= shapes.get(j).x
                && shapes.get(i).y <= shapes.get(j).y)
                dp[i] = Math.max(dp[i], dp[j] + 1);
            }

            ans = Math.max(ans, dp[i]);
        }

        System.out.println(ans);
    }
}
