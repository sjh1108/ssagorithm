import java.util.*;
import java.io.*;

class Solution {
    public int solution(int[] people, int limit) {
        int answer = 0;

        int front_index = 0;
        int back_index = people.length - 1;
        Arrays.sort(people);
        int[] array = people;
        int count = 0;

        while (back_index >= front_index)
        {
            if (back_index == front_index) {
                //System.out.println("aaa");
                count++;
                break;
            }

            if (array[front_index] + array[back_index] <= limit)
            {
                front_index++;
                back_index--;
            }
            else
            {
                back_index--;
            }

            //System.out.println("back_index : " + back_index + " front_index : " + front_index);

            count++;
        }

        answer = count;

        return answer;
    }
}
