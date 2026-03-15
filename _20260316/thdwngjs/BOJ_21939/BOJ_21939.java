package _20260316.thdwngjs.BOJ_21939;

import java.io.*;
import java.util.*;

class Main {
    static class Problem implements Comparable<Problem>{
        int num;
        int rate;
        boolean flag;

        Problem(int num, int rate){
            this.num = num;
            this.rate = rate;
            flag = false;
        }
        
        @Override
        public int compareTo(Problem p){
            if(this.rate == p.rate){
                return Integer.compare(this.num, p.num);
            }

            return Integer.compare(this.rate, p.rate);
        }
    }

    private static final String add = "add";
    private static final String solved = "solved";

    private static HashMap<Integer, Problem> idToProblem;

    private static Queue<Problem> hardQueue;
    private static Queue<Problem> easyQueue;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        idToProblem = new HashMap<>();
        hardQueue = new PriorityQueue<>(Collections.reverseOrder());
        easyQueue = new PriorityQueue<>();

        StringTokenizer st;
        while(N-- > 0){
            st = new StringTokenizer(br.readLine());
            int p = Integer.parseInt(st.nextToken());
            int l = Integer.parseInt(st.nextToken());

            add(p, l);
        }

        int M = Integer.parseInt(br.readLine());
        
        StringBuilder sb = new StringBuilder();
        while(M-- > 0){
            st = new StringTokenizer(br.readLine());

            String cmd = st.nextToken();

            if(cmd.equals(add)){
                add(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            }
            else if(cmd.equals(solved)){
                solved(Integer.parseInt(st.nextToken()));
            } else{
                int opt = Integer.parseInt(st.nextToken());

                sb.append(recommend(opt)).append('\n');
            }
        }

        System.out.print(sb);
    }

    private static void add(int num, int rate){
        Problem p = new Problem(num, rate);

        idToProblem.put(num, p);
        hardQueue.add(p);
        easyQueue.add(p);
    }

    private static void solved(int num){
        idToProblem.get(num).flag = true;
    }

    private static int recommend(int opt){
        Problem p;

        Queue<Problem> polled = new ArrayDeque<>();
        if(opt == 1){
            p = hardQueue.peek();

            while(p.flag){
                p = hardQueue.poll();
                polled.add(p);
            }

            while(!polled.isEmpty()){
                hardQueue.add(polled.poll());
            }
        } else{
            p = easyQueue.peek();

            while(p.flag){
                p = easyQueue.poll();
                polled.add(p);
            }

            while(!polled.isEmpty()){
                easyQueue.add(polled.poll());
            }
        }

        return p.num;
    }
}