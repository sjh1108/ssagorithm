package Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BOJ_1193_분수찾기 {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int X = Integer.parseInt(br.readLine());

        int line = 1;
        int count = 0;

        while (true) {
            if (count + line >= X) {
                break;
            }
            count += line;
            line++;
        }

        int idx = X - count;

        int ja;
        int mo;

        if (line % 2 == 1) {
            ja = line - idx + 1;
            mo = idx;
        } else {
            ja = idx;
            mo = line - idx + 1;
        }

        System.out.println(ja + "/" + mo);
    }
}
