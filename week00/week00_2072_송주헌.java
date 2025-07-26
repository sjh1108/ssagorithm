package week00;

import java.util.Scanner;
import java.io.FileInputStream;

public class week00_2072_송주헌 {
	public static void main(String args[]) throws Exception
	{
		// System.setIn(new FileInputStream("res/input.txt"));

		Scanner sc = new Scanner(System.in);
		int T;
		T=sc.nextInt();
		for(int test_case = 1; test_case <= T; test_case++)
		{
            int sum = 0;
            for(int i = 0; i < 10; i++){
                int tmp = sc.nextInt();

                if(tmp % 2 == 1){
                    sum += tmp;
                }
            }

            System.out.println("#" + test_case + " " + sum);
		}

        sc.close();
	}
}