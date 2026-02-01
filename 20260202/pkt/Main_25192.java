import java.io.*;
import java.util.*;


// 인사성 밝은 곰곰이 실버4
public class Main_25192 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        HashSet<String> set = new HashSet<>();
        int ans = 0;

        for (int i = 0; i < N; i++) {
            String s = br.readLine();
            if (s.equals("ENTER")) {
                set.clear();
            } else {
                if (set.add(s)) ans++; // 처음 등장한 닉이면 카운트
            }
        }

        System.out.println(ans);
    }
}