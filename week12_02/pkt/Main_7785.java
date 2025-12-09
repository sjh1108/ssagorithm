package study1207;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

// 회사에 있는 사람_실버5
public class Main_7785 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine()); 
        
        Map<String, String> map = new HashMap<>();

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String name = st.nextToken();
            String log = st.nextToken();

            if (log.equals("enter")) {
                map.put(name, "enter"); // 들어오면 맵에 넣음
            } else {
                map.remove(name);       // 필요없으면 맵에서 삭제
            }
        }
        
        List<String> list = new ArrayList<>(map.keySet()); // 키들만 모아서 list에 저장.
        Collections.sort(list, Collections.reverseOrder());

        for (String name : list) {
            System.out.println(name);
        }
    }
}

