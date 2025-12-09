package study1207;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


// 나이순 정렬_실버5
public class Main_10814 {
	
    // 회원정보를 담을 클래스
    public static class Member {
        int age;
        String name;

        public Member(int age, String name) {
            this.age = age;
            this.name = name;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int N = Integer.parseInt(br.readLine());
        Member[] members = new Member[N];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            int age = Integer.parseInt(st.nextToken());
            String name = st.nextToken();
            
            members[i] = new Member(age, name);
        }

        // 나이순만 처리하면, 가입순은 가입된 순서로 처리되어 있음. 
        Arrays.sort(members, (m1, m2) -> m1.age - m2.age);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            sb.append(members[i].age).append(" ").append(members[i].name).append("\n");
        }
        
        System.out.print(sb);
    }
}
