import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 알고리즘 재활소 단어공부 브1
public class Main_1157 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String word = br.readLine().toUpperCase();

        int[] alphaData = new int[26]; // 알파벳 인덱싱 자료구조
        // [알파벳 위치에 맞는 인덱싱에 +1 해줌]
        // 위에서 입력받은 문장을
        // 각각 자신의 알파벳 위치의 자료구조 위치 숫자를 더해줌 - 액션이 4개(배열, 인덱싱 문자하나 뽑아서, 인덱싱에, +1)
        for (int i = 0; i < word.length(); i++) {
            alphaData[word.charAt(i) - 'A']++;
        }

        int max = -1; // 최대값 카운팅 변수, 우선 설정(최소 0이니까, -1로 설정)
        boolean dup = false; // 중복시 ?울 출력하기 위함
        char maxChar = '?';

        for (int i = 0; i < 26; i++) {
            if (alphaData[i] > max) {
                max = alphaData[i];
                maxChar = (char) ('A' + i); // 인덱스 파악
                dup = false; // 최대값이 갱신되었으므로, 등호 성립 x
            } else if (alphaData[i] == max) {
                dup = true;
            }
        }

        if (!dup) { // dup == false
            System.out.println(maxChar);
        } else {
            System.out.println('?');
        }
    }
}

// [풀이전략]
// 대소문자 구분 x -> 같게 처리 요망
// 가장많이 사용된게 많으면 ? 출력
// 문장 입력 String 입력 받을 때 동일한 기준으로 대문자로 한 번에 다 바꿔도 됨.
// 알파벳 갯수 판단 -> 26개 알파벳 넣는 자료구조 만들어서 관리
// char maxChar = '?'; 중복있으면 걍 이걸로 나오게 하면 되니까,

// [구현 순서]
// 입력
// 알파벳 갯수를 세기 위한 자료구조 완성
// 최댓카운팅, 최대문자, 중복 확인
// 빈도수 가장 많은 알파벳 찾는 알고리즘
// 출력 - 조건문

// [실수]
// maxChar도 차후 출력을 위해 필요함.
//for (int i = 0; i < 26; i++) { word.len
// 위 아래 교체 ㄷㄷ word.len <-> 26