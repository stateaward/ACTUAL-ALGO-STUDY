package march;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class boj20055_컨베이어벨트위의로봇 {

	// N 길이의 컨베이어 벨트. 벨트는 2N
	// 1 올ㄹ라가는 위치. N번칸 내려가는 위치
	// 로봇이 올라가거나 이동하면 그 칸의 내구도 1만큼 감소.
	// 내구도 0에는 로봇이 올라갈 수 X
	// 벨트 한 칸 회전
	// 로봇이 먼저 올라간 순서부터 회전 방향대로 1칸씩 이동
	// 이동하려는 칸에 로봇 X
	// 그 칸 내구도 1 이상
	// 올라가는 위치에 로봇 없으면 새로 올리기
	// 내구도 0인 칸 개수가 K개 이상이면 과정 종료
	
	static class Belt {
		int dura;  //해당 칸의 내구도
		boolean robot;  //로봇이 위에 있는지
		
		Belt(int dura){
			this.dura = dura;
			this.robot = false;
		}
		
		Belt(int dura, boolean robot){
			this.dura = dura;
			this.robot = robot;
		}
	}
	
	static class Conveyer{
		Belt[] belt;
		int step;  //진행중인 단계
		int zeroD;  //내구도 0인 칸의 개수
		int n;
		
		Conveyer(int N){
			this.belt = new Belt[2*N];
			this.step = 0;
			this.zeroD = 0;
			this.n = N;
		}
	}
	
	static int spin(Conveyer c, int K) {
		while(c.zeroD < K) {
			c.step++;
			
			//1. 벨트가 한 칸 회전한다.
			Belt temp = c.belt[2*c.n -1];
			for (int i = 2 * c.n - 1; i > 0; i--)
				c.belt[i] = c.belt[i-1];
			c.belt[0] = temp;

			//2. 가장 먼저 벨트에 올라간 로봇부터, 벨트가 회전하는 방향으로 한 칸 이동할 수 있다면 이동한다. 만약 이동할 수 없다면 가만히 있는다.
			//로봇이 이동하기 위해서는 이동하려는 칸에 로봇이 없으며, 그 칸의 내구도가 1 이상 남아 있어야 한다.
			if(c.belt[c.n-1].robot) {  //내려가는 위치에 로봇 있을 때
				c.belt[c.n-1].robot = false;  //로봇 내리고
			} //내려가는 위치
			
			for (int i = c.n-1; i > 0; i--) {
				if(c.belt[i-1].robot && !c.belt[i].robot && c.belt[i].dura > 0) { //해당 칸에 로봇 위치, 이동가능
					c.belt[i-1].robot = false;
					c.belt[i].robot = true;
					c.belt[i].dura--;
					if(c.belt[i].dura == 0) {
						c.zeroD++;
						if(c.zeroD >= K) break;
					}
				}
			}
			
			//3. 올라가는 위치에 로봇이 없다면 로봇을 하나 올린다.
			if(!c.belt[0].robot && c.belt[0].dura > 0) {
				c.belt[0].robot = true;
				c.belt[0].dura--;
				if(c.belt[0].dura == 0) {
					c.zeroD++;
				}
			}
			
			//4. 내구도가 0인 칸의 개수가 K개 이상이라면 과정을 종료한다. 그렇지 않다면 1번으로 돌아간다.
		}
		
		
		return c.step;
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String line[] = br.readLine().split(" ");
		int N = Integer.parseInt(line[0]);
		int K = Integer.parseInt(line[1]);
		
		Conveyer conv = new Conveyer(N);
		
		line = br.readLine().split(" ");
		for (int i = 0; i < N; i++) {
			conv.belt[i] = new Belt(Integer.parseInt(line[i]));
		}
		for (int i = 0; i < N; i++) {
			conv.belt[N+i] = new Belt(Integer.parseInt(line[2*N-1-i]));
		}
		
//		for (int i = 0; i < 2*N; i++) {
//			System.out.print(conv.belt[i].dura + " ");
//		};
//		System.out.println();

		int step = spin(conv, K);
		System.out.println(step);
				
	}

}
