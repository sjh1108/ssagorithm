package week_11_02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

// 암호 만들기
public class Main_1759 {

    static int L, C;
    static char[] ch;
    static StringBuilder sb = new StringBuilder();
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        

        // 입력받기
        L = Integer.parseInt(st.nextToken()); // 결과 알파벳 구성 갯수 6개 중 4개 만들라할 떄 4개 차후 dfs depth 될 놈. 
        C = Integer.parseInt(st.nextToken()); // 입력 알파벳 갯수 초기 6개

        
        String[] temp = br.readLine().split(" "); // " "가쥰 구분해서 담기
        ch = new char[C];
        for (int i = 0; i < C; i++) {
            ch[i] = temp[i].charAt(0); // 문자열을 문자로 받기
        }
        // a t c i s w        


        // 정렬하기
       Arrays.sort(ch);

       // dfs 돌리기.
       dfs(0, 0, 0, 0, new char[L]);      

       System.out.println(sb); 
    }

    private static void dfs(int start, int depth, int aeiou, int others, char[] frame){

        if(depth == L){
            // 최소 1개의 모음과 2개 이상의 자음으로 구성될 때만 추가. 
            if(aeiou >= 1 && others >= 2) {
                sb.append(frame).append("\n");
            }
            return;
        }

        for (int i = start; i < C; i++) {
            char c = ch[i];
            frame[depth] = c;

            if(isAeiou(c)){ // 아래 매서드: 알파벳 모음 맞는지 확인하고, 불린형으로 반환. 
                dfs(i + 1, depth + 1, aeiou + 1, others, frame);
            } else {
                dfs(i + 1, depth + 1, aeiou, others + 1, frame);
            }           
        }        
    } 

    private static boolean isAeiou(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }
}