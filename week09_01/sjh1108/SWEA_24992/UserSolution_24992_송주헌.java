package week09_01.sjh1108.SWEA_24992;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

class UserSolution_24992_송주헌
{
    private int index;

    // 유저 시청목록
    private List<LinkedList<WatchLog>> userLog;
    
    // key idx, value id;
    private HashMap<Integer, Integer> idxToIDMap;
    // key id, value Movie
    private HashMap<Integer, Movie> idToMovieMap;

    private TreeSet<Movie>[] genreSet;

    // User의 시청 기록
    static class WatchLog{
        int mID;
        int score;

        public WatchLog(int mID, int score){
            this.mID = mID;
            this.score = score;
        }
    }

    static class Movie implements Comparable<Movie>{
        int idx;

        // genre
        int mGenre;

        // score
        int mTotal;

        // soft delete flag
        boolean deleteFlag;

        // 시청한 유저 set
        HashSet<Integer> watchedUser;

        public Movie(int idx, int mGenre, int mTotal){
            this.idx = idx;
            this.mGenre = mGenre;
            this.mTotal = mTotal;

            watchedUser = new HashSet<>();
        }

        public void delete(){
            this.deleteFlag = true;
        }

        @Override
        public int compareTo(Movie o) {
            if(this.mTotal != o.mTotal){
                // score 내림차순
                return Integer.compare(o.mTotal, this.mTotal);
            }
            // 최신 추가 순서대로 정렬
            return Integer.compare(o.idx, this.idx);
        }
    }

    @SuppressWarnings("unchecked")
    void init(int N)
    {
        index = 0;

        userLog = new ArrayList<>();
        for(int i = 0; i <= N; i++){
            userLog.add(new LinkedList<>());
        }

        idToMovieMap = new HashMap<>();
        idxToIDMap = new HashMap<>();

        genreSet = new TreeSet[6];
        for(int i = 0; i < 6; i++){
            genreSet[i] = new TreeSet<>();
        }

        return;
    }
    
    int add(int mID, int mGenre, int mTotal)
    {
        Movie newMovie = idToMovieMap.get(mID);
        // 원래 있는 영화거나 지워지지 않은 영화인 경우 return 0
        if(newMovie != null && !newMovie.deleteFlag) return 0;

        newMovie = new Movie(index, mGenre, mTotal);
        
        genreSet[0].add(newMovie);
        genreSet[mGenre].add(newMovie);

        idToMovieMap.put(mID, newMovie);
        idxToIDMap.put(index++, mID);

        return 1;
    }
    
    int erase(int mID)
    {
        Movie deletMovie = idToMovieMap.get(mID);
        // 원래 없는 영화거나 이미 지워진 영화인 경우 return 0
        if(deletMovie == null || deletMovie.deleteFlag) return 0;

        // deleteFlag 설정
        deletMovie.delete();

        // genreMap에서 삭제
        genreSet[0].remove(deletMovie);
        genreSet[deletMovie.mGenre].remove(deletMovie);

        return 1;
    }

    int watch(int uID, int mID, int mRating)
    {
        Movie m = idToMovieMap.get(mID);
        // 원래 없는 영화거나 이미 지워진 영화인 경우 return 0
        if(m == null || m.deleteFlag) return 0;
        // 이미 시청한 영화일 경우 return 0
        if(m.watchedUser.contains(Integer.valueOf(uID))) return 0;

        // 유저 시청 목록 업데이트
        WatchLog log = new WatchLog(mID, mRating);
        userLog.get(uID).addFirst(log);
        m.watchedUser.add(uID);

        // 영화 total Score update
        /// genreSet에서 제거
        /// mTotal 업데이트
        /// genreSet에 다시 추가
        genreSet[0].remove(m);
        genreSet[m.mGenre].remove(m);

        m.mTotal += mRating;

        genreSet[0].add(m);
        genreSet[m.mGenre].add(m);

        return 1;
    }
    
    Solution_24992_송주헌.RESULT suggest(int uID)
    {
        Solution_24992_송주헌.RESULT res = new Solution_24992_송주헌.RESULT();
        
        res.cnt = 0;
        LinkedList<WatchLog> logs = userLog.get(uID);
        Iterator<WatchLog> iter = logs.iterator();

        // 추천 장르 찾기
        int cnt = 0;
        int maxScore = 0;
        int genre = 0;
        while(iter.hasNext() && cnt < 5){
            WatchLog log = iter.next();
            
            Movie watched = idToMovieMap.get(log.mID);
            if(watched.deleteFlag) continue;

            if(maxScore < log.score){
                genre = watched.mGenre;
                maxScore = log.score;
            }

            cnt++;
        }

        // 해당 장르에서 영화 5개 추천
        for(Movie m: genreSet[genre]){
            if(res.cnt == 5) break;

            // 봤거나 삭제된 영화 제외
            if(m.watchedUser.contains(uID) || m.deleteFlag) continue;

            res.IDs[res.cnt] = idxToIDMap.get(m.idx);

            res.cnt++;
        }
        
        return res;
    }
}