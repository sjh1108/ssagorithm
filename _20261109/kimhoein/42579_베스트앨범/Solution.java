import java.util.*;

class Solution {
    // 장르 객체
    // 장르
    // 장르 재생 회수
    // 장르 크기별? 2개 까지 저장 리스트 이건 pq 사용인데 사이즈 2 이상이면서 더 크면 교체
    // 장르는 hashmap으로 먼저 생성하고
    // 장르 크기 자체를 pq에 넣거나 array 해서 섞기
    // 장르 객체를 2가지로 이용 하나는 hashmap으로 하나는 pq 이용할 용도로 미리 세팅

    static class Genre implements Comparable<Genre> {
        String genre;
        int play;
        PriorityQueue<music> pq;

        Genre(String genre, int play) {
            this.genre = genre;
            this.play = play;
            pq = new PriorityQueue<music>();
        }

        void AddMusic(music m) {
            play += m.play;
            if (pq.size() < 2) {
                pq.add(m);
            } else if (pq.size() >= 2) {
                music m1 = pq.poll(); // 가장 큰거

                System.out.println(m1.id + " " + m1.play);

                if (pq.peek().play < m.play) {
                    System.out.println("pq.peek().play: " + pq.peek().play);
                    pq.poll();
                    pq.add(m);
                }

                pq.add(m1);
            }
        }

        @Override
        public int compareTo(Genre o) {
            return Integer.compare(o.play, this.play);
        }
    }

    static class music implements Comparable<music> {
        int play;
        int id;

        music(int id, int play) {
            this.play = play;
            this.id = id;
        }

        @Override
        public int compareTo(music o) {
            if (this.play == o.play) {
                return Integer.compare(this.id, o.id);
            }
            return Integer.compare(o.play, this.play);
        }
    }

    public int[] solution(String[] genres, int[] plays) {
        int[] answer = {};

        ArrayList<Integer> arr = new ArrayList<Integer>();

        HashMap<String, Genre> hash = new HashMap<>();

        for (int i = 0; i < plays.length; i++) {
            if (hash.containsKey(genres[i])) {
                Genre g = hash.get(genres[i]);

                music m = new music(i, plays[i]);

                g.AddMusic(m);
            } else {
                Genre g = new Genre(genres[i], 0);

                music m = new music(i, plays[i]);

                g.AddMusic(m);

                hash.put(genres[i], g);
            }
        }

        PriorityQueue<Genre> pq = new PriorityQueue<>();

        for (String s : hash.keySet()) {
            System.out.println(s);
            pq.add(hash.get(s));
        }

        int count = 0;

        while (!pq.isEmpty()) {
            Genre g = pq.poll();

            while (!g.pq.isEmpty()) {
                arr.add(g.pq.poll().id);
                //answer[count] = g.pq.poll().play;
                count++;
            }
        }
        answer = new int[arr.size()];

        for (int i = 0; i < arr.size(); i++) {
            answer[i] = arr.get(i);
        }

        return answer;
    }
}
