package minmax;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Minimax_3 {
	public static int walknode=0;
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("test123.txt"));
		int branchnumber = scanner.nextInt();
		int treeheight = scanner.nextInt();
		scanner.nextLine();
		String input = scanner.nextLine();
		scanner.close();
		int[][] tree = new int[treeheight + 1][(int) Math.pow(branchnumber,
				treeheight)];
		int[][] alpha = new int[treeheight + 1][(int) Math.pow(branchnumber,
				treeheight)];
		int[][] beta = new int[treeheight + 1][(int) Math.pow(branchnumber,
				treeheight)];
		int[][] checked = new int[treeheight + 1][(int) Math.pow(branchnumber,
				treeheight)];
		int[][] fatherposition = new int[treeheight + 1][(int) Math.pow(
				branchnumber, treeheight)];
		int[][] childrencounter = new int[treeheight + 1][(int) Math.pow(
				branchnumber, treeheight)];
		int[][] gotvalue = new int[treeheight + 1][(int) Math.pow(
				branchnumber, treeheight)];
		int leafnum = 0,Node=0;
		for (int i = 0; i <= treeheight - 1; i++) {// �x�s�C�Ӹ`�I��l�ƶq
			for (int j = 0; j < (int) Math.pow(branchnumber, i); j++) {
				childrencounter[i][j] = branchnumber;
			}
		}
		for (int i = treeheight; i > 0; i--) {// �x�s�C�Ӹ`�I���˦�m
			for (int j = 0; j < (int) Math.pow(branchnumber, i); j++) {
				int fathernumber = (j + 1) / branchnumber;
				if ((j + 1) % branchnumber == 0) {
					fathernumber--;
				}
				fatherposition[i][j] = fathernumber;
			}
		}
		for (int i = treeheight; i >= 0; i--) {// ����k���𳣶�J�̤j�̤p��
			for (int j = 0; j < (int) Math.pow(branchnumber, treeheight); j++) {
				alpha[i][j] =  -2147483648;
				beta[i][j] = 2147483647;
				if(i%2==0){
					tree[i][j] = alpha[i][j];
				}
				else{
					tree[i][j] = beta[i][j];}
			}
		}
		String[] splitString;
		splitString = input.split(",");
		for (String s : splitString) {// �N�𸭶�J���
			tree[treeheight][leafnum] = Integer.valueOf(s);
			leafnum++;
		}
		minimax(tree, childrencounter, alpha, beta, fatherposition, checked,
				branchnumber, treeheight, gotvalue);
		for (int i = 0; i <=treeheight; i++) {// �`�@��node��
			Node = Node + (int) Math.pow(branchnumber, i);
		}
		walknode++;//�[�Jroot
		System.out.println("�`node�� = " + Node);
		System.out.println("walknode: "+walknode+" �`�� : "+(Node-walknode));
		System.out.println("result: "+tree[0][0]);
	}
	public static void treeup(int[] i,int[] j,int branchnumber,int treeheight,int[][] tree,int[][]childrencounter,int[][]alpha,int beta[][],int[][]fatherposition,int[][]checked){
 		int child=j[0];
		while(childrencounter[i[0]][j[0]]==0){//�Y�O���`�I�w�g�S����l�h���W��
			boolean gotcut =false;
			child=j[0];
			j[0]=fatherposition[i[0]][j[0]];
			i[0]--;
			if(childrencounter[0][0]==0)
				break;
		/*	if(tree[i[0]][j[0]]==2147483647||tree[i[0]][j[0]]==-2147483648){
				tree[i[0]][j[0]]=tree[i[0]+1][child];
			}*/
			//���ˬdA>B
			if(alpha[i[0]][j[0]]>beta[i[0]][j[0]]){
				childrencounter[i[0]][j[0]]=0;
				continue;
			}
			//���W�ɵ��Ӽhi�ȵ�A��B
			if(i[0]%2==0){
				if(tree[i[0]+1][child]>alpha[i[0]][j[0]]){
					tree[i[0]][j[0]]=tree[i[0]+1][child];
					alpha[i[0]][j[0]]=tree[i[0]+1][child];
				}
			}else{
				if(tree[i[0]+1][child]<beta[i[0]][j[0]]){
					tree[i[0]][j[0]]=tree[i[0]+1][child];
					beta[i[0]][j[0]]=tree[i[0]+1][child];
				}
			}
			if(alpha[i[0]][j[0]]>beta[i[0]][j[0]]){
				childrencounter[i[0]][j[0]]=0;
				walknode++;
				continue;
			}
		}
		
		if(i[0]>=0&&childrencounter[i[0]][j[0]]!=0)
			treedown(i, j, branchnumber, treeheight, tree, childrencounter, alpha, beta, fatherposition, checked);
	}
	public static void treedown(int[] i,int[] j,int branchnumber,int treeheight,int[][] tree,int[][]childrencounter,int[][]alpha,int beta[][],int[][]fatherposition,int[][]checked){
		int father=j[0];
		if(childrencounter[0][0]!=0){
		while(childrencounter[i[0]][j[0]]!=0){//�Y���`�I�٦��D���`�I���l��h���U
			if(alpha[i[0]][j[0]]>beta[i[0]][j[0]]){
				childrencounter[i[0]][j[0]]=0;
				treeup(i, j, branchnumber, treeheight, tree, childrencounter, alpha, beta, fatherposition, checked);
				continue;
			}
			father=j[0];
			j[0]=findchildren(i, father, fatherposition, childrencounter, checked, branchnumber, treeheight);
			i[0]++;
			if((i[0]+1)>treeheight||childrencounter[i[0]][j[0]]==0){
				i[0]--;
				j[0]=father;
				break;
			}
			//�ˬdA>B
			//���U��A��B�ҭn����
			if(alpha[i[0]-1][father]>alpha[i[0]][j[0]]){
				alpha[i[0]][j[0]]=alpha[i[0]-1][father];
			}
			if(beta[i[0]-1][father]<beta[i[0]][j[0]]){
				beta[i[0]][j[0]]=beta[i[0]-1][father];
			}
		
		}
		}
		//if(childrencounter[i[0]][j[0]]==0)
			//treeup(i, j, branchnumber, treeheight, tree, childrencounter, alpha, beta, fatherposition, checked);
	}
	public static void minimax(int[][] tree,int[][]childrencounter,int[][]alpha,int beta[][],int[][]fatherposition,int[][] checked,int branchnumber,int treeheight, int[][] gotvalue){
		int[] i={0},j={0};
		int child=0;
		while(childrencounter[0][0]!=0){//�Y�ڸ`�I���������X���l��h�~��
			boolean gotcut =false;
			treeup(i, j, branchnumber, treeheight, tree, childrencounter, alpha, beta, fatherposition, checked);
			treedown(i, j, branchnumber, treeheight, tree, childrencounter, alpha, beta, fatherposition, checked);
			if(childrencounter[0][0]==0)
				break;
			//����ij������l�� �n��Xi j����l
			if(alpha[i[0]][j[0]]>beta[i[0]][j[0]]){
				childrencounter[i[0]][j[0]]=0;
				continue;
			}
			System.out.println("i "+i[0]+" j "+j[0]);
			walknode++;			child=findchildren(i, j[0], fatherposition, childrencounter, checked, branchnumber, treeheight);
			//��l��������
		/*	if(tree[i[0]][j[0]]==2147483647||tree[i[0]][j[0]]==-2147483648){
				tree[i[0]][j[0]]=tree[i[0]+1][child];
			}*/
			
			if(i[0]%2==0){
				if(tree[i[0]+1][child]>alpha[i[0]][j[0]]){
					tree[i[0]][j[0]]=tree[i[0]+1][child];
					alpha[i[0]][j[0]]=tree[i[0]+1][child];
				}
			}else{
				
				if(tree[i[0]+1][child]<beta[i[0]][j[0]]){
					tree[i[0]][j[0]]=tree[i[0]+1][child];
					beta[i[0]][j[0]]=tree[i[0]+1][child];
				}
			}
			if(tree[i[0]][j[0]]==2147483647||tree[i[0]][j[0]]==-2147483648){
				tree[i[0]][j[0]]=tree[i[0]+1][child];
			}
			if(childrencounter[i[0]][j[0]]>0)
				childrencounter[i[0]][j[0]]--;//������l�ƶq-1
			checked[i[0]+1][child]=1;//�аO��l�w�Q���L
			if(childrencounter[0][0]==0)//�Y�ڸ`�I��l�ƶq��0�h�פ�
				break;
		}
	
	}
	public static int findchildren(int i[], int father, int[][] fatherposition,
			int[][] childrencounter, int[][] checked, int branchnumber, int treeheight) {
		if(i[0]+1==treeheight){//�q�𸭸̧��l
			for (int j = 0; j < Math.pow(branchnumber, treeheight); j++) {
				if (fatherposition[i[0] + 1][j] == father && checked[i[0] + 1][j] == 0) {
					return j;
				}
			}
		}
		else{//��K���l
			for (int j = 0; j < Math.pow(branchnumber, treeheight); j++) {
				if (fatherposition[i[0] + 1][j] == father &&childrencounter[i[0]+1][j]!=0) {
					return j;
				}
			}
		}
		for (int j = 0; j < Math.pow(branchnumber, treeheight); j++) {
			if (fatherposition[i[0] + 1][j] == father && checked[i[0] + 1][j] == 0) {
				return j;
			}
		}
		return 2147483647;
	}
}
