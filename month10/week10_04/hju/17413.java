import java.io.IOException;

class Main {
    public static void main(String[] args) throws IOException {
        // 코드 작성
    }
}import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();
        
        StringBuilder result = new StringBuilder();
        StringBuilder currentWord = new StringBuilder();
        
        boolean inTag = false;
        
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (c == '<') {
                // 태그가 시작되면, 그 전까지 만들던 단어를 처리
                result.append(currentWord.reverse());
                currentWord.setLength(0); // 현재 단어 비우기
                
                inTag = true;
                result.append(c);
            } else if (c == '>') {
                inTag = false;
                result.append(c);
            } else if (inTag) {
                // 태그 안쪽일 경우, 그냥 그대로 추가
                result.append(c);
            } else { // 태그 바깥쪽일 경우
                if (c == ' ') {
                    // 공백을 만나면 단어가 끝난 것
                    result.append(currentWord.reverse());
                    currentWord.setLength(0);
                    result.append(c);
                } else 
                    // 단어의 일부인 문자
                    currentWord.append(c);
            }
        }
        
        // 반복문이 끝난 후, 마지막에 남아있는 단어 처리
        if (currentWord.length() > 0)
            result.append(currentWord.reverse());

        System.out.println(result.toString());
    }
}