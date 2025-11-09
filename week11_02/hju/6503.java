import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;

class Main {
    public static void main(String[] args) throws IOException {

        // 아스키 코드 ' ' = 32, 'A' = 65, 'a' = 97, '.' = 46
        // int 배열로 쓸라니까 귀찮다.
        // Map 쓰자
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int m = -1;
        while ((m = Integer.parseInt(br.readLine())) != 0) {
            char[] str = br.readLine().toCharArray();

            HashMap<Character, Integer> used = new HashMap<>();

            int l = 0, maxLen = 0;

            for (int r = 0; r < str.length; r++) {
                used.put(str[r], used.getOrDefault(str[r], 0) + 1);

                while (used.size() > m) {
                    used.put(str[l], used.get(str[l]) - 1);

                    if (used.get(str[l]) == 0)
                        used.remove(str[l]);

                    l++;
                }

                maxLen = Math.max(maxLen, r - l + 1);
            }

            System.out.println(maxLen);
        }

    }
}