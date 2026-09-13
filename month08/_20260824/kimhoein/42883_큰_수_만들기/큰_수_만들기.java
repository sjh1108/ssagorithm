import java.io.*;
import java.util.*;

class Solution {
    public String solution(String number, int k){
        StringBuilder answer = new StringBuilder();

        Deque<Character> dq = new ArrayDeque<>();

        int string_index = 0;

        while(string_index < number.length())
        {
            while(!dq.isEmpty() && k > 0 && dq.peekLast() < number.charAt(string_index)){
                dq.pollLast();
                k--;
            }

            dq.add(number.charAt(string_index++));
        }

        while(k > 0)
        {
            dq.pollLast();
            k--;
        }

        while(!dq.isEmpty())
        {
            answer.append(dq.pollFirst());
        }

        return answer.toString();
    }
}
