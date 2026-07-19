import java.util.*;
class Solution {
    public int solution(String s) {
        int result = 0;
        
        int slen = s.length();
        
        for(int i = 0; i < slen; i++){
            // 검사
            if(isCorrect(s)){
                result++;
            }
            // 회전
            s = rotate(s);
            
        }
        return result;
        
    }
    public String rotate(String s) {
        ArrayDeque<Character> q = new ArrayDeque<>();
        for(char i : s.toCharArray()){
            // System.out.println(i);
            q.addLast(i);
        }
        // 마지막 문자 -> 맨 앞으로
        q.addFirst(q.removeLast());
        
        StringBuilder sb = new StringBuilder();

        while (!q.isEmpty()) {
            sb.append(q.removeFirst());
        }
        return sb.toString();
    }
    // 문자열이 올바른지 체크
    public boolean isCorrect(String s) {
        Stack<Character> stack = new Stack<>();
        for(char c : s.toCharArray()){
            if(c == '[' || c == '{' || c == '('){
                stack.push(c);
            }
            // 스택이 비어있으면 false
            else if (stack.isEmpty()){
                return false;
            }
            
            // 이전 괄호가 짝이 안맞으면 false
            else if(c ==']'){
                if(stack.pop() != '['){
                    return false;
                }
            }
            else if(c == '}'){
                if(stack.pop() != '{'){
                    return false;
                }
            }
            else if(c == ')'){
                if(stack.pop() != '('){
                    return false;
                }
            }
            
        }
        return stack.isEmpty();
    }
}