package Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BOJ_1337_올바른배열 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n];
        
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(br.readLine());
        }
        
        Arrays.sort(arr);
        
        int block = 0;

        for (int i = 0; i < n; i++) {
            int count = 0;
            for (int j = i; j < n; j++) {
                if (arr[j] - arr[i] <= 4) {
                    count++;
                } else {
                    break;
                }
            }
            if (count > block) {
            	block = count;
            }
        }
        
        int howMuch = 5 - block;
        if (howMuch < 0) howMuch = 0;
        System.out.println(howMuch);
    }
}
