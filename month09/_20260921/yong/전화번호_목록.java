import java.util.*;

public class 전화번호_목록 {
    public static boolean solution(String[] phone_book){
        boolean answer = true;
        HashSet<String> hash = new HashSet<>();
        // 번호들 해시에 등록
        for(String num : phone_book){
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < num.length() - 1; i++){
                sb.append(num.charAt(i));
                hash.add(sb.toString());
            }
        }
         for(String num : phone_book){
            if(hash.contains(num)){
                return false;
            }
         }

        return answer;
    }
    public static void main(String[] args){
        String[] phone_book = {"119", "97674223", "1195524421"};
        if(solution(phone_book)){
            System.out.println("true");
        }
        else{
            System.out.println("false");
        }
    }
}
