package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/* 풀이 순서
 * 1. 아기 상어의 위치 저장, 사이즈 기록
 * 2. 아기 상어 위치에서 BFS로 먹이 탐색
 * 	2-1. 먹이가 없다면, 이동가능 여부 판단(이동 가능하면 Q추가)
 *  2-2. 같은 시간초 내에서 먹을 수 있는 먹이들을 먹이 리스트에 추가
 * 3. 먹이 리스트 안에서 우선 순위 판단(맨 위 -> 좌측)
 * 4. 해당 위치 이동, 사이즈 증가, 초 증가 
 */

/* 틀린 이유
 * - BFS를 수행할 때, time+1로 시간값이 증가하므로 4방 탐색을 하는 단계마다 먹이 리스트에 넣으면 거리(시간)계산을 안해도 되겠다는 생각으로 접근
 *   BFS(같은 거리가 나올 때마다, 식사)로 생각
 * - 하지만, 몇몇 값에선, time값이 늦게 반영되어 동일한 시간값이지만 체킹을 못하고 넘어가는 경우가 발생..
 * - 로직 자체를 while(BFS, 식사) 의 형태로 변경
 * - BFS는 BFS로만 두고, 로직을 끼우지 말자...
 */

public class BOJ16236_아기상어_스터디_이주상 {

	// 먹이가 될 물고기
	private static class Fish {
		int x, y, time;

		public Fish(int x, int y, int time) {
			super();
			this.x = x;
			this.y = y;
			this.time = time;
		}
	}
	
	static int N;
	static int[][] map;
	static boolean[][] isVisit;
	
	static Fish shark;		// 상어도 물고기! 상어의 위치를 기록
	static int size = 2;
	static int eaten = 0;
	
	static ArrayList<Fish> feedList = new ArrayList<>();
	static int answer;
	
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String[] input = br.readLine().split(" ");
		N = Integer.parseInt(input[0]);
		
		map = new int[N][N];
		isVisit = new boolean[N][N];
		
		for (int i = 0; i < N; i++) {
			input = br.readLine().split(" ");
			
			for (int j = 0; j < N; j++) {	
				map[i][j] = Integer.parseInt(input[j]);
				
				if(map[i][j] == 9) {
					shark = new Fish(i, j, 0);
					map[i][j] = 0;
				}
			}
		}
		
//		for (int i = 0; i < N; i++) {
//			for (int j = 0; j < N; j++) {
//				System.out.print(map[i][j]);
//			}
//			System.out.println();
//		}
		
		simulation();
		System.out.println(answer);
	}

	// 
	private static void simulation() {
		
		Queue<Fish> q = new LinkedList<>();
		q.add(shark);
		isVisit[shark.x][shark.y] = true;
		
		while(true) {
			while(!q.isEmpty()) {
				Fish now = q.poll();
				int time = now.time;
//			System.out.println("현재 탐색 위치 : " + now.x + "," + now.y + "/" + now.time);
				
				
				for (int k = 0; k < 4; k++) {
					int nx = now.x + dx[k];
					int ny = now.y + dy[k];
					
					// 범위 밖 -> 아웃
					if(!(0 <= nx && nx < N && 0 <= ny && ny < N) || isVisit[nx][ny]) continue;
					
					// [식사 가능] 먹이 리스트에 추가
					if(map[nx][ny] < size && map[nx][ny] != 0) {
						q.add(new Fish(nx, ny, time+1));
						isVisit[nx][ny] = true;
						feedList.add(new Fish(nx, ny, time+1));
					}
					
					// [이동만 가능] 사이즈랑 동일 OR 비어있음(0)
					if(map[nx][ny] == size || map[nx][ny] == 0) {
						q.add(new Fish(nx, ny, time+1));
						isVisit[nx][ny] = true;
					}
				}
			}
			
			if(!feedList.isEmpty()) {
				eat();
				// 식사가 끝났으면, 방문 배열 초기화
				
				q.clear();
				isVisit = new boolean[N][N];
				
	//			System.out.println("===============");
	//			System.out.println("현재 상어 위치 : " + shark.x + "," + shark.y);
	//			System.out.println("현재 상어 사이즈 : " + size);
	//			System.out.println("현재 걸린 시간 : " + answer);
	//			
	//			System.out.println("식사를 마친 후의 모습");
	//			for (int i = 0; i < N; i++) {
	//				for (int j = 0; j < N; j++) {
	//					System.out.print(map[i][j]);
	//				}
	//				System.out.println();
	//			}
	//			System.out.println();
				
				
				// 다시 이동하는 큐(q)에다가 현재 상어 추가
				q.add(shark);
				isVisit[shark.x][shark.y] = true;
			}else {
				return;
			}
		}
		
	}

	// 먹이 리스트가 값이 존재하면, 식사 시작
	private static void eat() {
		
		// 정렬을 통해 우선순위별로 먹이 리스트 재정렬
		Collections.sort(feedList, new Comparator<Fish>() {
			@Override
			public int compare(Fish o1, Fish o2) {
				if(o1.time == o2.time) {
					if(o1.x == o2.x) {
						if(o1.y > o2.y) {
							return 1;
						}else {
							return -1;
						}
					}else {
						if(o1.x > o2.x) {
							return 1;
						}else {
							return -1;
						}				
					}
				}else if(o1.time > o2.time){
					return 1;
				}else {
					return -1;
				}
			};
		});
		
//		for (Fish f : feedList) {
//			System.out.print(f.x + "," + f.y + "(" + f.time + ")    ");
//		}
		
		
		Fish now = feedList.get(0);
//		System.out.println("정렬의 선택 : " + now.x + "," + now.y);
		
		// 상어 위치 이동, 상어가 먹은 횟수 증가, 상어 걸린 시간 증가
		shark.x = now.x;
		shark.y = now.y;
		
		if(++eaten == size) {
			size++;
			eaten = 0;
		}
		
		answer += now.time;
		
		// 지도 위에 해당 위치 0 처리
		map[now.x][now.y] = 0;
		
		// 먹이 리스트 초기화
		feedList.clear();
	}

}
