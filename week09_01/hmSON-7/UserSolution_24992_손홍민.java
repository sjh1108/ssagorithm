import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

public class UserSolution_24992 {
	
	// 영화 업로드 순서를 기록하기 위한 카운터
		private static int movieCnt;
		
		// 유저 배열. 인덱스는 uID
		User[] users;
		// 영화 리스트. key는 mID
		HashMap<Integer, Movie> movieList;
		// 장르별 영화 리스트. 배열 인덱스는 영화 장르(1~5)
		TreeSet<Movie>[] genreList;
		
		// 영화 객체
		static class Movie {
			// mID, 장르 번호, 총점(사용자의 평점 총합), 업로드 순서
			int id, genre, totalScore, orderIdx;
			// 영화 삭제 여부 판별 플래그. 삭제 메서드 호출시 객체를 삭제하지 않고 erased 플래그만 확인
			boolean erased;
			// 해당 영화를 시청한 사용자 uID 리스트
			HashSet<Integer> viewer;
			
			public Movie(int id, int genre, int totalScore) {
				this.id = id;
				this.genre = genre;
				this.totalScore = totalScore;
				orderIdx = movieCnt++;
				erased = false;
				viewer = new HashSet<>();
			}
		}
		
		// 사용자 객체
		static class User {
			// 시청 기록 카운터
			int watchCnt;
			// 사용자가 시청한 영화, 시청 기록 및 평점 리스트
			// 최근 시청한 5개 영화의 기록을 찾기 위함
			List<UserRate> watchList;
			
			public User() {
				watchCnt = 0;
				watchList = new ArrayList<>();
			}
		}
		
		// 유저 평점 객체
		static class UserRate {
			// 영화 객체, 평점, 시청 기록
			Movie movie;
			int rate;
			int orderIdx;
			
			public UserRate(Movie movie, int rate, int orderIdx) {
				this.movie = movie;
				this.rate = rate;
				this.orderIdx = orderIdx;
			}
		}
		
	    void init(int N)
	    {
	    	// 유저 수를 매개변수로 받아 인원수만큼 배열 크기 등록 및 유저 객체 생성
	    	users = new User[N+1];
	    	for(int i=1; i<=N; i++) {
	    		users[i] = new User();
	    	}
	    	
	    	// 영화 리스트 및 장르별 영화 리스트 생성
	    	movieList = new HashMap<>();
	    	genreList = new TreeSet[6];
	    	// 영화의 정렬 순서
	    	// 1. 총점 내림차순
	    	// 2. 최근 업로드순
	    	for(int i=1; i<=5; i++) {
	    		genreList[i] = new TreeSet<>((a, b) -> a.totalScore != b.totalScore ? 
	    				Integer.compare(b.totalScore,  a.totalScore) : Integer.compare(b.orderIdx, a.orderIdx)
	    		);
	    	}
	    	movieCnt = 0;
	    	
	        return;
	    }
	    
	    int add(int mID, int mGenre, int mTotal)
	    {
	    	// 호출 횟수 10_000번
	    	// 영화 정보를 받아서 등록해야 함
	    	// 등록 성공 : 0, 등록 실패 : 1
	    	
	    	// 이미 존재하는 영화 mID를 등록한 경우 리턴
	    	if(movieList.containsKey(mID)) return 0;
	    	
	    	// 영화 객체를 리스트에 등록
	    	Movie movie = new Movie(mID, mGenre, mTotal);
	    	movieList.put(mID, movie);
	    	genreList[mGenre].add(movie);
	    	
	        return 1;
	    }
	    
	    int erase(int mID)
	    {
	    	// 호출 횟수 1_000번
	    	// 지정된 영화를 삭제. 단, 해당 프로그램에서는 객체 삭제 대신 삭제 플러그로 표기함
	    	
	    	// 찾는 영화가 리스트에 없는 경우 리턴
	    	if(!movieList.containsKey(mID)) return 0;
	    	
	    	// 찾는 영화가 이미 삭제된 경우 리턴
	    	// 그렇지 않으면 삭제 후 1 반환 리턴
	    	Movie target = movieList.get(mID);
	    	if(target.erased) return 0;
	    	
	    	target.erased = true;
	    	
	        return 1;
	    }

	    int watch(int uID, int mID, int mRating)
	    {
	    	// 호출 횟수 30_000번
	    	// 영화 mID를 받아 영화가 존재하는지, 삭제된 영화거나 이미 시청했던 영화인지 확인
	    	// 유저 mID를 받아 영화에 대한 유저 평점 객체를 리스트에 추가
	    	
	    	// 영화가 존재하지 않으면 리턴
	    	if(!movieList.containsKey(mID)) return 0;
	    	
	    	// 이미 삭제된 영화거나, 이미 해당 유저가 시청했던 영화라면 리턴
	    	Movie watched = movieList.get(mID);
	    	if(watched.erased || watched.viewer.contains(uID)) return 0;
	    	
	    	// 해당 영화에 대한 유저 평점 객체를 만들어 리스트에 추가
	    	// 해당 영화의 시청자 리스트에 사용자 uID 추가
	    	User curUser = users[uID];
	    	UserRate rate = new UserRate(watched, mRating, curUser.watchCnt);
	    	curUser.watchCnt++;
	    	curUser.watchList.add(rate);
	    	watched.viewer.add(uID);
	    	
	    	// TreeSet은 정렬에 사용되는 값이 변경되어도 재정렬을 지원하지 않음
	    	// 따라서 장르별 영화 리스트에서 해당 영화를 잠시 지우고 값 변경 후 재삽입
	    	genreList[watched.genre].remove(watched);
	    	watched.totalScore += mRating;
	    	genreList[watched.genre].add(watched);
	    	
	        return 1;
	    }
	    
	    Solution_24992.RESULT suggest(int uID)
	    {

			// 호출 횟수 5_000번
	    	// 사용자가 최근 시청한 영화 5개 중 해당 사용자가 준 평점이 제일 높은(동일한 경우 가장 최근에 본) 영화의 장르를 추출
			// 이후 해당 장르에서 총점이 제일 높은(동일한 경우 가장 최근에 출시된) 영화 5개를 추천해야 함
	    	// 만약 아직 아무것도 안봤거나 시청한 영화들이 전부 삭제된 상태라면, 장르 상관없이 위와 동일한 조건의 영화 5개를 추천해야 함

			// 장르 무관하고 영화 5개를 추천해야 하는 경우 :
			// 각 장르별로 최고 총점 영화를 5개씩 뽑아 트리셋에 저장한 뒤, 우선순위 (최대)5개 영화만 반환한다.
			// 특정 장르의 영화 5개를 추천해야 하는 경우 :
			// 각 장르별 영화 리스트 역시 우선순위에 따라 정렬된 상태이므로 상위 (최대)5개 영화를 반환한다.

			// 위 과정에서 이미 삭제된(erased), 또는 사용자가 시청한(Movie.viewer) 영화는 무시한다.
			
	        Solution_24992.RESULT res = new Solution_24992.RESULT();
	        
	        // 초기 카운트는 0으로 통일
	        res.cnt = 0;
	        User target = users[uID];
	        
	        // 아무것도 시청하지 않은 사용자라면?
	        // 장르 무관 총점이 제일 높은 영화 5개를 선정하여 리스트 제공
	        // 먼저 각 장르별 영화 리스트에서 유효햔(삭제되지 않은) 영화를 5개씩 모아두고,
	        // 그 중에서 총점이 제일 높은(동일한 경우 최신에 가까운) 영화 5개를 추려 리스트 제공
	        if(target.watchList.isEmpty()) {
	        	TreeSet<Movie> best = getBestMovies(uID);
	        	
	        	for(Movie m : best) {
	        		res.IDs[res.cnt++] = m.id;
	        		
	        		if(res.cnt == 5) break;
	        	}
	        	
	        } 
	        
	        // 시청 기록이 존재하는 사용자라면?
	        // 최근 시청한 영화 최대 5개 중 평점을 가장 높게 준(동일한 경우 더 최근에 본) 영화의 장르를 확인
	        // 이후 해당 장르에서 총점이 가장 높은 영화 5개(삭제된 영화, 이미 본 영화 제외)의 mID를 리스트에 담아 반환
	        // 단, 시청한 영화가 전부 삭제된 영화인 경우 시청 기록이 없는 사용자와 동일하게 처리
	        else {
	        	// 유효한 최근 시청 영화 리스트 (최대)5개 확인
	        	List<UserRate> valid = new ArrayList<>();
	        	for(int i=target.watchList.size() - 1; i >= 0; i--) {
	        		UserRate ur = target.watchList.get(i);
	        		// 이미 삭제된 영화는 무시
	        		if(ur.movie.erased) continue;
	        		valid.add(ur);
	        		
	        		if(valid.size() == 5) break;
	        	}
	        	
	        	// 시청한 영화가 전부 삭제된 상태라면?
	        	// 시청 기록이 없는 사용자와 동일하게 장르 무관 영화 추천
	        	if(valid.size() == 0) {
	        		TreeSet<Movie> best = getBestMovies(uID);
	        		
	        		for(Movie m : best) {
	            		res.IDs[res.cnt++] = m.id;
	            		
	            		if(res.cnt == 5) break;
	            	}
	        		
	        	} 
	        	
	        	// 시청한 영화가 존재하는 상태라면?
	        	// 유효한 영화 평가 리스트를 평점 순으로(동일한 경우 최근 시청 순으로) 정렬하고, 우선순위가 제일 높은 영화의 장르를 확인
	        	// 해당 장르의 영화 리스트에서 아직 시청하지 않은 영화 (최대)5개의 mID를 리스트에 담아 반환
	        	else {
	        		valid.sort((a, b) -> a.rate != b.rate ?
	            			Integer.compare(b.rate, a.rate) : Integer.compare(b.orderIdx, a.orderIdx)
	            	);
	            	
	            	int suggestGenre = valid.get(0).movie.genre;
	            	for(Movie m : genreList[suggestGenre]) {
	            		if(m.erased || m.viewer.contains(uID)) continue;
	            		
	            		res.IDs[res.cnt++] = m.id;
	            		if(res.cnt == 5) break;
	            	}
	        	}
	        }
	        
	        return res;
	    }
	    
	    // 장르 무관 최고의 영화 리스트(최대 25개)를 제공하는 메서드
	    TreeSet<Movie> getBestMovies(int uID) {
	    	// 트리셋 생성. 정렬 조건 :
	    	// 1. 영화 총점 내림차순
	    	// 2. 영화 최신 업로드순
	    	TreeSet<Movie> best = new TreeSet<>((a, b) -> a.totalScore != b.totalScore ?
	    			Integer.compare(b.totalScore, a.totalScore) : Integer.compare(b.orderIdx, a.orderIdx)
	    	);
	    	for(int i=1; i<=5; i++) {
	    		int cnt = 0;
	    		for(Movie m : genreList[i]) {
	    			// 삭제된 영화나 사용자가 이미 본 영화는 무시
	    			if(m.erased || m.viewer.contains(uID)) continue;
	    			best.add(m);
	    			cnt++;
	    			
	    			if(cnt == 5) break;
	    		}
	    	}
	    	
	    	return best;
	    }
	
}
