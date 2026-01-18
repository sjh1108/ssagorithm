import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        // 주어지는 원소는 1,000,000,000보다 작거나 같은 음이 아닌 정수네요.
        // 배열로 만들면 메모리가 터지니까 다른 작은 값을 찾아봅시다.
        // N은 50보다 작거나 같은 자연수네요??
        // 그럼 N을 이용해봅시다.

        HashMap<Integer, Integer> hmLeft = new HashMap<>();
        HashMap<Integer, Integer> hmRight = new HashMap<>();

        for(int i = 0; i < N; i++) {
            int k = Integer.parseInt(br.readLine());
            hmLeft.put(k, 1);
            hmRight.put(k, 1);
        }

        for(int k : hmLeft.keySet()) {

            // 왼쪽으로 올바른 배열을 이루는 원소 개수
            for(int i = k - 4; i < k; i++) 
                if(hmLeft.containsKey(i))
                    hmLeft.put(k, hmLeft.get(k) + 1);
            
            // 오른쪽으로 올바른 배열을 이루는 원소 개수
            for(int i = k + 4; i > k; i--) 
                if(hmRight.containsKey(i))
                    hmRight.put(k, hmRight.get(k) + 1);
            

        }

        int maxValue = 0;
        for(int i : hmLeft.values()) {
            maxValue = Math.max(maxValue, i);
            if(maxValue >= 5) {
                maxValue = 5;
                System.out.println(5 - maxValue);
                return;
            }
        }

        for(int i : hmRight.values()) {
            maxValue = Math.max(maxValue, i);
            if(maxValue >= 5) {
                maxValue = 5;
                System.out.println(5 - maxValue);
                return;
            }
        }

        System.out.println(5 - maxValue);
    }
}