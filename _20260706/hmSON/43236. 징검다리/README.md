# [level 4] 징검다리 - 43236 

[문제 링크](https://school.programmers.co.kr/learn/courses/30/lessons/43236?language=java) 

### 성능 요약

메모리: 81 MB, 시간: 15.64 ms

### 구분

코딩테스트 연습 > 이분탐색

### 채점결과

정확성: 100.0<br/>합계: 100.0 / 100.0

### 제출 일자

2026년 07월 02일 09:01:29

### 문제 설명

<p>출발지점부터 distance만큼 떨어진 곳에 도착지점이 있습니다. 그리고 그사이에는 바위들이 놓여있습니다. 바위 중 몇 개를 제거하려고 합니다.<br>
예를 들어, 도착지점이 25만큼 떨어져 있고, 바위가 [2, 14, 11, 21, 17] 지점에 놓여있을 때 바위 2개를 제거하면 출발지점, 도착지점, 바위 간의 거리가 아래와 같습니다.</p>
<table class="table">
        <thead><tr>
<th>제거한 바위의 위치</th>
<th>각 바위 사이의 거리</th>
<th>거리의 최솟값</th>
</tr>
</thead>
        <tbody><tr>
<td>[21, 17]</td>
<td>[2, 9, 3, 11]</td>
<td>2</td>
</tr>
<tr>
<td>[2, 21]</td>
<td>[11, 3, 3, 8]</td>
<td>3</td>
</tr>
<tr>
<td>[2, 11]</td>
<td>[14, 3, 4, 4]</td>
<td>3</td>
</tr>
<tr>
<td>[11, 21]</td>
<td>[2, 12, 3, 8]</td>
<td>2</td>
</tr>
<tr>
<td>[2, 14]</td>
<td>[11, 6, 4, 4]</td>
<td>4</td>
</tr>
</tbody>
      </table>
<p>위에서 구한 거리의 최솟값 중에 가장 큰 값은 4입니다.</p>

<p>출발지점부터 도착지점까지의 거리 distance, 바위들이 있는 위치를 담은 배열 rocks, 제거할 바위의 수 n이 매개변수로 주어질 때, 바위를 n개 제거한 뒤 각 지점 사이의 거리의 최솟값 중에 가장 큰 값을 return 하도록 solution 함수를 작성해주세요.</p>

<h5>제한사항</h5>

<ul>
<li>도착지점까지의 거리 distance는 1 이상 1,000,000,000 이하입니다.</li>
<li>바위는 1개 이상 50,000개 이하가 있습니다.</li>
<li>n 은 1 이상 <code>바위의 개수</code> 이하입니다.</li>
</ul>

<h5>입출력 예</h5>
<table class="table">
        <thead><tr>
<th>distance</th>
<th>rocks</th>
<th>n</th>
<th>return</th>
</tr>
</thead>
        <tbody><tr>
<td>25</td>
<td>[2, 14, 11, 21, 17]</td>
<td>2</td>
<td>4</td>
</tr>
</tbody>
      </table>
<h5>입출력 예 설명</h5>

<p>문제에 나온 예와 같습니다.</p>

<p><a href="http://contest.usaco.org/DEC06.htm" target="_blank" rel="noopener">출처</a></p>

<p>※ 공지 - 2020년 2월 17일 테스트케이스가 추가되었습니다.<br>
※ 공지 - 2023년 5월 15일 테스트케이스가 추가되었습니다. 기존에 제출한 코드가 통과하지 못할 수도 있습니다.</p>


> 출처: 프로그래머스 코딩 테스트 연습, https://school.programmers.co.kr/learn/challenges