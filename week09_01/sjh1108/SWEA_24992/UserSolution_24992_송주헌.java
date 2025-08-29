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

    /*
     * 테스트 케이스에 대한 초기화 함수. 각 테스트 케이스의 맨 처음 1회 호출된다.
     * 
     * OTT에 가입한 사용자는 N명이 있다. 각 사용자는 1부터 N까지의 고유 번호로 구분된다.
     * 
     * 초기에 시스템에 등록된 영화는 없고 어떤 사용자도 영화를 본 적도 없다.
     * 
     * Parameter
     *  N : 서비스에 가입한 사용자의 수 (3 ≤ N ≤ 1,000)
     */
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

    /*
     * ID가 mID이고 장르가 mGenre이고 총점이 mTotal인 영화를 등록한다.
     * 
     * 만약 등록에 성공하면 1을 반환하고 실패하면 0을 반환한다.
     * 등록에 실패한 경우는 같은 ID를 가진 영화가 이미 등록된 경우이다.
     * 
     * 삭제된 영화의 ID로 다시 등록하는 경우는 없다.
     * 
     * 해당 함수가 더 나중에 호출될 수록 더 최신에 등록된 영화이다.
     * 
     * Parameters
     *   mID : 등록할 영화의 ID (1 ≤ mID ≤ 1,000,000,000)
     *   mGenre : 등록할 영화의 장르 (1 ≤ mGenre ≤ 5)
     *   mTotal : 등록할 영화의 총점 (0 ≤ mTotal ≤ 1,000)
     * 
     * Return  영화 등록 성공 여부. 성공할 경우 1, 실패할 경우 0.
     */
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
    
    /*
     * ID가 mID인 영화를 삭제한다.
     * 삭제에 성공하면 1을 반환하고 실패하면 0을 반환한다.
     * 삭제에 실패한 경우는 ID가 mID인 영화가 등록된 경우가 없거나 이미 삭제된 경우이다.
     * 
     * Parameter
     *   mID : 삭제할 영화의 ID (1 ≤ mID ≤ 1,000,000,000)
     * 
     * Return
     *   영화 삭제 성공 여부. 성공할 경우 1, 실패할 경우 0.
     */
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

    /*
     * 고유 번호가 uID인 사용자가 ID가 mID인 영화를 시청하고 그 영화에 대한 평점을 mRating로 준다.
     * 
     * 영화의 총점은 mRating만큼 증가한다.
     * 
     * 시청에 성공하면 1을 반환하고 실패하면 0을 반환한다.
     * 시청에 실패한 경우는 ID가 mID인 영화가 등록된 경우가 없거나 삭제되었거나 사용자 uID가 이미 시청한 영화인 경우이다.
     * 
     * 시청에 실패할 경우 해당 영화의 총점 변화는 없다.
     * 
     * 해당 함수가 더 나중에 호출될 수록 더 최근에 시청한 영화이다.
     * 
     * Parameters
     *   uID : 영화를 시청하는 사용자의 고유 번호 (1 ≤ uID ≤ N)
     *   mID : 시청할 영화의 ID (1 ≤ mID ≤ 1,000,000,000)
     *   mRating : 영화를 시청한 후 준 평점 (1 ≤ mRating ≤ 10)
     * 
     * Return
     *   영화 시청 성공 여부. 성공할 경우 1, 실패할 경우 0.
     */
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
    
    /*
     * 고유 번호가 uID인 사용자에게 최대 5개의 영화를 추천한다.
     * 
     * 영화를 추천하는 과정은 본문 설명을 참조하라.
     * 
     * 추천되는 영화의 수를 RESULT.cnt에 저장하고 추천 순위가 i번째인 영화의 ID를 RESULT.IDs[i – 1]에 저장하고 반환한다. (1 ≤ i ≤ RESULT.cnt)
     * 
     * 만약, 추천할 수 있는 영화가 없는 경우 RESULT.cnt에 0을 저장한다.
     * 
     * Parameter
     *   uID : 영화를 추천할 사용자의 고유 번호 (1 ≤ uID ≤ N)
     * 
     * Return
     *   추천되는 영화들. 개수는 RESULT.cnt에 저장하고 추천 순위 순으로 RESULT.IDs에 영화의 ID를 저장한다.
     */
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