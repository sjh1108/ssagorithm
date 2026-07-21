/*
공백문자가 연속해서 나올수 있음

*/
class Solution {
    public String solution(String s) {
        String answer = "";
        for(int i = 0; i < s.length(); i++){
            char now = s.charAt(i);
            // 처음이랑 공백 다음은 대문자
            if(i == 0 || s.charAt(i-1) == ' '){
                now = Character.toUpperCase(now);   // toUpper
            }
            else{
                now = Character.toLowerCase(now);
            }
            answer += now;
        }
        return answer;
    }
}