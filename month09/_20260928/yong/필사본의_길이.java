import java.util.*;
import java.io.*;

public class 필사본의_길이 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Deque<Long> lengthStack = new ArrayDeque<>();
        Deque<Long> multipleStack = new ArrayDeque<>();
        String line = br.readLine();
        
        long length = 0;
        long k = 0;

        for(char c : line.toCharArray()){
            if(Character.isDigit(c)){
                k = k * 10 + (c - '0'); // 괄호 들어갈때 초기화 해줘야함
            }
            else if(c == '['){
                // [ 바깥 문자열 길이 저장
                lengthStack.push(length);
                multipleStack.push(k);

                // 현재 괄호안 문자열만 다시 카운트
                length = 0;
                // k 초기화
                k = 0;
            }
            else if(c == ']'){
                long before = lengthStack.pop();
                long mul = multipleStack.pop();
                // 괄호 바깥 + 현재 압축된 문자열 길이
                length = before + length * mul;
            }
            // 일반 문자
            else{
                length++;
            }
        }
        
        System.out.println(length);
    }
}
