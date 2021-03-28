package march;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class boj20055_�����̾Ʈ���Ƿκ� {

	// N ������ �����̾� ��Ʈ. ��Ʈ�� 2N
	// 1 �ä��󰡴� ��ġ. N��ĭ �������� ��ġ
	// �κ��� �ö󰡰ų� �̵��ϸ� �� ĭ�� ������ 1��ŭ ����.
	// ������ 0���� �κ��� �ö� �� X
	// ��Ʈ �� ĭ ȸ��
	// �κ��� ���� �ö� �������� ȸ�� ������ 1ĭ�� �̵�
	// �̵��Ϸ��� ĭ�� �κ� X
	// �� ĭ ������ 1 �̻�
	// �ö󰡴� ��ġ�� �κ� ������ ���� �ø���
	// ������ 0�� ĭ ������ K�� �̻��̸� ���� ����
	
	static class Belt {
		int dura;  //�ش� ĭ�� ������
		boolean robot;  //�κ��� ���� �ִ���
		
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
		int step;  //�������� �ܰ�
		int zeroD;  //������ 0�� ĭ�� ����
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
			
			//1. ��Ʈ�� �� ĭ ȸ���Ѵ�.
			Belt temp = c.belt[2*c.n -1];
			for (int i = 2 * c.n - 1; i > 0; i--)
				c.belt[i] = c.belt[i-1];
			c.belt[0] = temp;

			//2. ���� ���� ��Ʈ�� �ö� �κ�����, ��Ʈ�� ȸ���ϴ� �������� �� ĭ �̵��� �� �ִٸ� �̵��Ѵ�. ���� �̵��� �� ���ٸ� ������ �ִ´�.
			//�κ��� �̵��ϱ� ���ؼ��� �̵��Ϸ��� ĭ�� �κ��� ������, �� ĭ�� �������� 1 �̻� ���� �־�� �Ѵ�.
			if(c.belt[c.n-1].robot) {  //�������� ��ġ�� �κ� ���� ��
				c.belt[c.n-1].robot = false;  //�κ� ������
			} //�������� ��ġ
			
			for (int i = c.n-1; i > 0; i--) {
				if(c.belt[i-1].robot && !c.belt[i].robot && c.belt[i].dura > 0) { //�ش� ĭ�� �κ� ��ġ, �̵�����
					c.belt[i-1].robot = false;
					c.belt[i].robot = true;
					c.belt[i].dura--;
					if(c.belt[i].dura == 0) {
						c.zeroD++;
						if(c.zeroD >= K) break;
					}
				}
			}
			
			//3. �ö󰡴� ��ġ�� �κ��� ���ٸ� �κ��� �ϳ� �ø���.
			if(!c.belt[0].robot && c.belt[0].dura > 0) {
				c.belt[0].robot = true;
				c.belt[0].dura--;
				if(c.belt[0].dura == 0) {
					c.zeroD++;
				}
			}
			
			//4. �������� 0�� ĭ�� ������ K�� �̻��̶�� ������ �����Ѵ�. �׷��� �ʴٸ� 1������ ���ư���.
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
