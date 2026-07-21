import java.util.*;

class Solution {

    /**
     * 자료구조 및 알고리즘 : 해시맵, 정렬
     * 장르 목록은 문자열로 주어지므로 이를 key로 사용하는 해시맵 필요
     * 각 장르별 재생 수 카운트를 합산하여 재생 수 내림차순으로 정렬
     * 각 장르 내 곡 번호 목록을 단일 곡 재생 수 내림차순으로 정렬
     * 장르별 재생 수가 높은 장르부터 최다 재생 곡 최대 2개씩 반환
     */

    // 장르 클래스
    // 각 장르별 재생 수 및 곡 번호 리스트 관리
    static class Genre {
        int cnt = 0;
        List<Integer> list = new ArrayList<>();

        // 장르별 곡 추가 기능
        // 리스트에 곡 번호 추가 및 재생 수 합산
        public void update(int idx, int val) {
            list.add(idx);
            cnt += val;
        }
    }

    public int[] solution(String[] genres, int[] plays) {
        int len = plays.length;
        // 문자열을 key값으로 하는 해시맵 생성 및 장르 객체 저장
        HashMap<String, Genre> genreMap = new HashMap<>();
        for(int i=0; i<len; i++) {
            // 이미 등록한 적 있는 장르라면 해시맵에서 꺼내서 업데이트
            // 아직 등록한 적 없는 장르라면 객체 생성해서 업데이트한 뒤 해시맵에 등록
            if(genreMap.containsKey(genres[i])) {
                Genre genre = genreMap.get(genres[i]);
                genre.update(i, plays[i]);
            } else {
                Genre genre = new Genre();
                genre.update(i, plays[i]);
                genreMap.put(genres[i], genre);
            }
        }

        // 장르별 총 재생 수 내림차순 장르 정렬
        List<String> albums = new ArrayList<>(genreMap.keySet());
        albums.sort((a, b) -> Integer.compare(genreMap.get(b).cnt, genreMap.get(a).cnt));

        List<Integer> res = new ArrayList<>();
        for(int i=0; i<albums.size(); i++) {
            // 각 장르별 곡 재생 수 내림차순 정렬
            Genre genre = genreMap.get(albums.get(i));
            genre.list.sort((a, b) -> Integer.compare(plays[b], plays[a]));
            // 장르별 곡이 2개 이상이면 2개만, 그 미만이면 전체 리스트에 추가
            for(int j=0; j<Math.min(2, genre.list.size()); j++) {
                res.add(genre.list.get(j));
            }
        }

        // 반환 : List<Integer> -> int[] 과정시 stream().mapToInt(Integer::intValue)로 전환
        return res.stream().mapToInt(Integer::intValue).toArray();
    }

}