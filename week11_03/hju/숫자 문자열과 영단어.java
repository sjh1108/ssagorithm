import java.util.*;

class Solution {
    
    public int solution(String s) {
        int answer = 0;
        
        HashMap<String, String> map = new HashMap<>();
        
        map.put("zero", "0");
        map.put("one", "1");
        map.put("two", "2");
        map.put("three", "3");
        map.put("four", "4");
        map.put("five", "5");
        map.put("six", "6");
        map.put("seven", "7");
        map.put("eight", "8");
        map.put("nine", "9");
        
        for(String key : map.keySet()) {
            s = s.replaceAll(key, map.get(key));
        }
        
        answer = Integer.parseInt(s);
        return answer;
    }
}