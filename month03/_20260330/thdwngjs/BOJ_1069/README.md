import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int X = Integer.parseInt(st.nextToken());
        int Y = Integer.parseInt(st.nextToken());
        int D = Integer.parseInt(st.nextToken());
        int T = Integer.parseInt(st.nextToken());

        double dist = Math.sqrt(Math.pow(X, 2) + Math.pow(Y, 2));

        int jump = (int)dist / D;

        double case1, case2, case3;

        if(dist >= D){
            case1 = T * jump + (dist - (D * jump));
            case2 = dist;
            case3 = T * (jump + 1);
        } else{
            case1 = T + (D - dist);
            case2 = dist;
            case3 = T * 2;
        }

        System.out.println(Math.min(case1, Math.min(case2, case3)));
    }
}