import java.io.*;
import java.util.*;

public class Main_5585_거스름돈_박기택 {

	
	
	
	public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int cost = Integer.parseInt(br.readLine());

        // 일단 빼줌
        cost = 1000 - cost;

        // 세팅 
        int[] coinArr = {500, 100, 50, 10, 5, 1};

        int num = 0;
        for (int i = 0; i < 6; i++) {
            if (cost/coinArr[i] > 0) { // i로 돌리기 위해 여기에 조건문이 필요함.
                num += cost/coinArr[i]; // +연산 까먹지 말기.. 하하하
                cost = cost % coinArr[i];
            }
        }

        System.out.println(num);






        
	}
}