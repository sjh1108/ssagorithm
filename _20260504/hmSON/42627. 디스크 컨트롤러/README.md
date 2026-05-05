# [level 3] 디스크 컨트롤러 - 42627 

[문제 링크](https://school.programmers.co.kr/learn/courses/30/lessons/42627) 

### 성능 요약

메모리: 79.2 MB, 시간: 5.97 ms

### 구분

코딩테스트 연습 > 힙（Heap）

### 채점결과

정확성: 100.0<br/>합계: 100.0 / 100.0

### 제출 일자

2026년 05월 02일 16:39:14

### 문제 설명

<p>하드디스크는 한 번에 하나의 작업만 수행할 수 있습니다. 디스크 컨트롤러를 구현하는 방법은 여러 가지가 있습니다. 이 문제에서는 <strong>우선순위 디스크 컨트롤러</strong>라는 가상의 장치를 이용한다고 가정합니다. <strong>우선순위 디스크 컨트롤러</strong>는 다음과 같이 동작합니다.</p>

<ol>
<li>어떤 작업 요청이 들어왔을 때 작업의 번호, 작업의 요청 시각, 작업의 소요 시간을 저장해 두는 <strong>대기 큐</strong>가 있습니다. 처음에 이 큐는 비어있습니다.</li>
<li>디스크 컨트롤러는 하드디스크가 작업을 하고 있지 않고 <strong>대기 큐</strong>가 비어있지 않다면 가장 우선순위가 높은 작업을 <strong>대기 큐</strong>에서 꺼내서 하드디스크에 그 작업을 시킵니다. 이때, 작업의 소요시간이 짧은 것, 작업의 요청 시각이 빠른 것, 작업의 번호가 작은 것 순으로 우선순위가 높습니다.</li>
<li>하드디스크는 작업을 한 번 시작하면 작업을 마칠 때까지 그 작업만 수행합니다.</li>
<li>하드디스크가 어떤 작업을 마치는 시점과 다른 작업 요청이 들어오는 시점이 겹친다면 하드디스크가 작업을 마치자마자 디스크 컨트롤러는 요청이 들어온 작업을 <strong>대기 큐</strong>에 저장한 뒤 우선순위가 높은 작업을 <strong>대기 큐</strong>에서 꺼내서 하드디스크에 그 작업을 시킵니다. 또, 하드디스크가 어떤 작업을 마치는 시점에 다른 작업이 들어오지 않더라도 그 작업을 마치자마자 또 다른 작업을 시작할 수 있습니다. 이 과정에서 걸리는 시간은 없다고 가정합니다.</li>
</ol>

<p>예를 들어</p>
<div class="highlight"><pre class="codehilite"><code>- 0ms 시점에 3ms가 소요되는 0번 작업 요청
- 1ms 시점에 9ms가 소요되는 1번 작업 요청
- 3ms 시점에 5ms가 소요되는 2번 작업 요청
</code></pre></div>
<p>와 같은 요청이 들어왔습니다. 이를 그림으로 표현하면 다음과 같습니다.</p>

<p><img src="https://asset.programmers.co.kr/files/production/02c70993-ca43-4130-ac63-0dec59e091c6/image.001.jpeg" title="" alt="image.001.jpeg"></p>

<p>이 요청을 <strong>우선순위 디스크 컨트롤러</strong>가 처리하는 과정은 다음 표와 같습니다.</p>
<table class="table">
        <thead><tr>
<th>시점</th>
<th>하드디스크</th>
<th>대기 큐</th>
<th>디스크 컨트롤러</th>
</tr>
</thead>
        <tbody><tr>
<td>0ms</td>
<td></td>
<td>[]</td>
<td></td>
</tr>
<tr>
<td>0ms</td>
<td></td>
<td>[[0번, 0ms, 3ms]]</td>
<td>0번 작업 요청을 대기 큐에 저장</td>
</tr>
<tr>
<td>0ms</td>
<td>0번 작업 시작</td>
<td>[]</td>
<td>대기 큐에서 우선순위가 높은 0번 작업을 꺼내서 작업을 시킴</td>
</tr>
<tr>
<td>1ms</td>
<td>0번 작업 중</td>
<td>[[1번, 1ms, 9ms]]</td>
<td>1번 작업 요청을 대기 큐에 저장</td>
</tr>
<tr>
<td>3ms</td>
<td>0번 작업 완료</td>
<td>[[1번, 1ms, 9ms]]</td>
<td></td>
</tr>
<tr>
<td>3ms</td>
<td></td>
<td>[[1번, 1ms, 9ms], [2번, 3ms, 5ms]]</td>
<td>2번 작업 요청을 대기 큐에 저장</td>
</tr>
<tr>
<td>3ms</td>
<td>2번 작업 시작</td>
<td>[[1번, 1ms, 9ms]]</td>
<td>대기 큐에서 우선순위가 높은 2번 작업을 꺼내서 작업을 시킴</td>
</tr>
<tr>
<td>8ms</td>
<td>2번 작업 완료</td>
<td>[[1번, 1ms, 9ms]]</td>
<td></td>
</tr>
<tr>
<td>8ms</td>
<td>1번 작업 시작</td>
<td>[]</td>
<td>대기 큐에서 우선순위가 높은 1번 작업을 꺼내서 작업을 시킴</td>
</tr>
<tr>
<td>17ms</td>
<td>1번 작업 완료</td>
<td>[]</td>
<td></td>
</tr>
</tbody>
      </table>
<p>모든 요청 작업을 마쳤을 때 각 작업에 대한 <strong>반환 시간(turnaround time)</strong>은 작업 요청부터 종료까지 걸린 시간으로 정의합니다. 위의 <strong>우선순위 디스크 컨트롤러</strong>가 처리한 각 작업의 <strong>반환 시간</strong>은 다음 그림, 표와 같습니다.</p>

<p><img src="https://asset.programmers.co.kr/files/production/fdfb33a3-1ad4-443a-a5d0-09b3dc548ece/image.002.jpeg" title="" alt="image.002.jpeg"></p>
<table class="table">
        <thead><tr>
<th>작업 번호</th>
<th>요청 시각</th>
<th>작업 종료 시각</th>
<th>반환 시간</th>
</tr>
</thead>
        <tbody><tr>
<td>0번</td>
<td>0ms</td>
<td>3ms</td>
<td>3ms(= 3ms - 0ms)</td>
</tr>
<tr>
<td>1번</td>
<td>1ms</td>
<td>17ms</td>
<td>16ms(= 17ms - 1ms)</td>
</tr>
<tr>
<td>2번</td>
<td>3ms</td>
<td>8ms</td>
<td>5ms(= 8ms - 3ms)</td>
</tr>
</tbody>
      </table>
<p><strong>우선순위 디스크 컨트롤러</strong>에서 모든 요청 작업의 반환 시간의 평균은 8ms(= (3ms + 16ms + 5ms) / 3)가 됩니다.</p>

<p>각 작업에 대해 [작업이 요청되는 시점, 작업의 소요시간]을 담은 2차원 정수 배열 <code>jobs</code>가 매개변수로 주어질 때, <strong>우선순위 디스크 컨트롤러</strong>가 이 작업을 처리했을 때 모든 요청 작업의 반환 시간의 평균의 정수부분을 return 하는 solution 함수를 작성해 주세요.</p>

<hr>

<h5>제한 사항</h5>

<ul>
<li>1 ≤ <code>jobs</code>의 길이 ≤ 500 </li>
<li><code>jobs[i]</code>는 <code>i</code>번 작업에 대한 정보이고 [s, l] 형태입니다.

<ul>
<li>s는 작업이 요청되는 시점이며 0 ≤ s ≤ 1,000입니다.</li>
<li>l은 작업의 소요시간이며 1 ≤ l ≤ 1,000입니다.</li>
</ul></li>
</ul>

<hr>

<h5>입출력 예</h5>
<table class="table">
        <thead><tr>
<th>jobs</th>
<th>return</th>
</tr>
</thead>
        <tbody><tr>
<td>[[0, 3], [1, 9], [3, 5]]</td>
<td>8</td>
</tr>
</tbody>
      </table>
<hr>

<h5>입출력 예 설명</h5>

<p>입출력 예 #1</p>

<ul>
<li>문제에 주어진 예와 같습니다.</li>
</ul>

<hr>

<h5>문제가 잘 안풀린다면😢</h5>

<p>힌트가 필요한가요? [코딩테스트 연습 힌트 모음집]으로 오세요! → <a href="https://school.programmers.co.kr/learn/courses/14743?itm_content=lesson42627" target="_blank" rel="noopener">클릭</a></p>

<hr>

<p>※ 공지 - 2024년 11월 14일 문제 지문이 리뉴얼되었습니다. 기존에 제출한 코드가 통과하지 못할 수도 있습니다.</p>


> 출처: 프로그래머스 코딩 테스트 연습, https://school.programmers.co.kr/learn/challenges