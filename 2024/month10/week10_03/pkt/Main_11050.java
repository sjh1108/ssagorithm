package practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main_11050 {

    static int N, K;

    
    public static void main(String[] args) throws IOException {
        
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());    
    
    N = Integer.parseInt(st.nextToken());
    K = Integer.parseInt(st.nextToken());

    int res = combinattion(N,K);    
    System.out.println(res);
    }

    private static int combinattion(int N, int K){

        int resCon = factorial(N)/(factorial(K) * factorial(N-K));
        return resCon;
        
    }

    private static int factorial(int Num){
        if(Num == 0) {
            int resFac = 1;
            return 1;
        }
        // 수정 차후 확인하기.
        int resFac = 1;
        for (int i = 1; i <= Num; i++) {
             resFac = resFac * i;   
        }
        return resFac;
    }
}