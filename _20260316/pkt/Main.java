import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        HashMap<String, String> map = new HashMap<>();

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            String id = st.nextToken();
            String pw = st.nextToken();
            map.put(id,pw);
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < m; i++) {
            String id = br.readLine();
            sb.append(map.get(id)).append("\n");
        }
        System.out.println(sb);
    }
}