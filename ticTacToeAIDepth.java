
import java.util.Scanner;
public class ticTacToeAIDepth
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner (System.in);
		System.out.println("Enter your player (X/O):");
		char a = in.next().charAt(0);
		while (!(a == 'X' || a == 'x' || a == 'O' || a == 'o'))
		{
			System.out.println(a + " is not X or O");
			System.out.println("Enter your player (X/O):");
			a = in.next().charAt(0);
		}
		char[] arr = {' ',' ',' ',' ',' ',' ',' ',' ',' '};
		int spot = 0;
		printBoard(arr);
		if (a == 'O' || a == 'o')
		{
			a = 'O';
			int bestIndex = 0;
			for (int k = 1; k < 9; k++)
			{
				double best = checkValueDepth(addMark(arr, bestIndex+1, switchMark(a)), a, switchMark(a), 0);
				arr = addMark(arr,bestIndex+1,' ');
				double temp = checkValueDepth(addMark(arr, k+1, switchMark(a)), a, switchMark(a), 0);
				arr = addMark(arr, k+1, ' ');
				if (temp > best)
					bestIndex = k;
			}
			arr = addMark(arr, bestIndex+1, switchMark(a));
			printBoard(arr);
		}
		if (a == 'x')
			a = 'X';
		while (!isOver(arr) && !isPWon(arr, 'X') && !isPWon(arr,'O'))
		{
			while (spot < 1 || spot > 9 || arr[spot-1] != ' ')
			{
				System.out.println("Enter spot to mark " + a + " (1-9 only)");
				spot = in.nextInt();
			}
			arr = addMark(arr, spot, a);
			printBoard(arr);
			int i = 0;
			while(i < 9 && arr[i] != ' ')
			{
				i++;
			}
			for (int j = i; j < 9; j++)
			{
				if (arr[j] == ' ')
				{
					double temp = checkValueDepth(addMark(arr, j+1, switchMark(a)), a, switchMark(a), 0);
					arr = addMark(arr,j+1,' ');
					double best = checkValueDepth(addMark(arr, i+1, switchMark(a)), a, switchMark(a), 0);
					arr = addMark(arr, i+1, ' ');
					if (temp > best)
						i = j;
				}
			}
			if (i<9)
			{
				arr = addMark(arr, i+1, switchMark(a));
				printBoard(arr);
			}
		}
		if (isPWon(arr,'X'))
			System.out.println("Player X won!");
		if (isPWon(arr,'O'))
			System.out.println("Player O won!");
		if (isDraw(arr))
			System.out.println("Draw");
	}
	
	public static void printBoard(char[] arr)
	{
		int i = 0;
		System.out.println(" -----------");
		System.out.print("| ");
		while (i<3)
		{
			System.out.print(arr[i] + " | ");
			i++;
		}
		System.out.println();
		System.out.println("|-----------|");
		System.out.print("| ");
		while (i<6)
		{
			System.out.print(arr[i] + " | ");
			i++;
		}
		System.out.println();
		System.out.println("|-----------|");
		System.out.print("| ");
		while (i<9)
		{
			System.out.print(arr[i] + " | ");
			i++;
		}
		System.out.println();
		System.out.print(" ----------- ");
		System.out.println();
	}
	
	public static boolean isOver(char[] arr)
	{
		for (int i = 0; i < arr.length; i++)
			if (arr[i] == ' ')
				return false;
		return true;
	}
	
	public static boolean isPWon(char[] arr, char a)
	{
		boolean ok = false;
		for (int i = 0; i < 9 && !ok ; i+= 3)
			if (arr[i] == a && arr[i+1] == a && arr[i+2] == a)
				ok = true;
		for (int j = 0; j < 3 && !ok; j++)
			if (arr[j] == a && arr[j+3] == a && arr[j+6] == a)
				ok = true;
		if ((arr[0] == a && arr[4] == a && arr[8] == a) || (arr[2] == a && arr[4] == a && arr[6] == a))
			ok = true;
		return ok;
	}
	
	public static boolean isDraw(char[] arr)
	{
		return (isOver(arr) && !(isPWon(arr, 'X') || isPWon(arr, 'O')));
	}
	
	public static char[] addMark(char[] arr, int spot, char a)
	{
		arr[spot-1] = a;
		return arr;
	}
	
	public static char switchMark(char a)
	{
		if (a == 'X')
			return 'O';
		else
			return 'X';
	}
	
	public static double checkValueDepth(char[] board, char a, char c, double total)
	{
		if (isDraw(board))
			return 0;
		if (isPWon(board, c))
			return 10.0;
		if (isPWon(board, switchMark(c)))
			return -10.0;
		for (int i = 0; i < 9; i++)
		{
			if (board[i] == ' ')
			{
				total += checkValueDepth(addMark(board, i+1, a), switchMark(a), c, 0)/10.0;
				board = addMark(board,i+1,' ');
				//System.out.println(total);
			}
		}
		return total;
	}

}
