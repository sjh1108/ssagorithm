import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main {

    static char[] option;
    static boolean[] isUsed = new boolean[26];

    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        while(N-- > 0) {
            option = br.readLine().toCharArray();
            // 우선순위가 있는 조건을 차례대로 검사해, 만족하면 return 하기 위해 함수로 뺐음
            selectKey();
        }
        
    }

    static void selectKey() {

        // if(!isUsed[charToInt(option[0])]) {
        //     isUsed[charToInt(option[0])] = true;
        //     printAns(0);
        //     return;
        // }
        // 다음과 같은 코드가 자주 등장하는데 이걸 함수화 했으면 더 좋았을듯 (귀찮음 이슈)
        // 3번 이상 반복되는 코드는 함수화 하는게 좋습니다.

        // 1. 옵션의 첫글자
        if(!isUsed[charToInt(option[0])]) {
            isUsed[charToInt(option[0])] = true;
            printAns(0);
            return;
        }

        // 2. 옵션 단어들의 첫글자
        for(int i = 0; i < option.length; i++) {
            // 공백이 있고 다음 문자가 있을 경우 단어의 첫글자 이다.
            if(option[i] == ' ' && i + 1 < option.length) 
                if(!isUsed[charToInt(option[i + 1])]) {
                    isUsed[charToInt(option[i + 1])] = true;
                    printAns(i + 1);
                    return;
                }
        }

        // 3. 왼쪽에서 부터 차례대로
        for(int i = 0; i < option.length; i++) {
            // 공백은 패스 : isUsed 배열에 접근하면 터짐
            if(option[i] == ' ')    continue;
            
            if(!isUsed[charToInt(option[i])]) {
                isUsed[charToInt(option[i])] = true;
                printAns(i);
                return;
            }
        }

        // 4. 단축키 지정이 안되면 그냥 출력
        printAns(-1);
    }

    static int charToInt(char c) {
        if(c >= 'A' && c <= 'Z')    return c - 'A';
        return c - 'a';
    }

    static void printAns(int t) {
        for(int i = 0; i < option.length; i++) {
            if(i == t) {
                System.out.print('[');
                System.out.print(option[i]);
                System.out.print(']');
                continue;
            }
            System.out.print(option[i]);
        }
        System.out.println();
    }
}