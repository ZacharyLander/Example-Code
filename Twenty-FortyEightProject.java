//2048 Console I/O Game
//EECS 1510
//Zachary Lander
//4-6-2016

/*
 * This is a game of numbers, multiples of two that is. In this game, one individual moves left, right, up, 
 * and down, thus moving the available number tiles within a 4x4 board. The goal of the game to combine these numbers 
 * until they reach 2048. For each move, the tiles will scrunch together in the direction specified by the user.
 * If two of the tiles are the same and are combined into each other, they will be added together, so for example,
 * if the move was up, and a 2 tile combined with another 2 tile, the resulting single tile would be a 4. Ultimately
 * the user should attempt to combine as many tiles as possible to reach 2048, but be careful, if the board fills up
 * and no moves are available, you lose. Good luck!
 */

import java.util.Scanner;
import java.util.Arrays;

public class TwentyFourGame
{
	public static int[][] board = new int[5][4]; 	//The board is 5 by 4 to store the score and moves in the 
													// 5th row
	
	public static int[][] undoBoard = new int[5][4]; 	// This board will store the board from the last turn
														// for the undo function
	
	public static boolean win = false;				//This will tell main whether the user has already won or not
	
	/*
	 * Main acts as the overall controller of the moves. It utilizes the compact and move methods to perform
	 * a move up, down, left, or right on the board, uses displayBoard to print the board out all nice and 
	 * neat, and uses the check win/lose methods to find out if the user has won or lost the game at any point
	 */
	public static void main(String[] args)
	{
		
		System.out.println("Welcome to the game! For controls, press h or H!");
		
		//Initialize a 4x4 board
		for (int row = 0; row < 5; row++)
		{
			for(int column = 0; column < 4; column++)
			{
				board [row][column] = 0;
			}
		}
		
		randomInt(board);			// Randomly assign two of the empty cells to have
		randomInt(board);			// either a 2 or a 4 for the beginning of the game
		displayBoard(board);
		
		Scanner input = new Scanner(System.in);
		
		for(;;)						// This for loop will cycle infinitely to prompt the user for their next
		{							// move and will send the user's input to the correct method
			
		
		char userInput = input.next().charAt(0); // Prompt user for next move

			
			if(userInput == 'u'|| userInput == 'U' || userInput == '8') // If move is up, compact the array up, combine like terms,
			{															//	compact again, and then display the board, also incrementing
																		// the moves	
				{
					
					for (int column = 0; column <= 3; column++)   // Save an instance of the current board in case of needed UNDO
					{	
						for (int row = 0; row <= 4; row++)
						{
							undoBoard[row][column] = board[row][column]; 
						}
					}
					
					compactUp(board);
					moveUp(board);
					compactUp(board);
					

					if(Arrays.deepEquals(undoBoard, board))							//If the new board is equal to the undoBoard
					{																// that means that no move has been made, and thus
						System.out.println("That is not a valid move, try again");	// the move selected is invalid
						
					}
					else
					{
						board[4][1]++;										// Increment the moves
						randomInt(board);									// Introduce a new number
						if(checkLose(board))								// Check to see if that move created a board that no turn can work on
						{													// 		in which case then the user loses and the program tells the user their 
							displayBoard(board);							//		score and the number of moves that they made
							System.out.println("Sorry, you lose, good game! Your score was: "
												+ board[4][0] + " and you moved " + board[4][1] + " times");
							System.exit(0);
						}
					}
					
					displayBoard(board);
					
					if(win == false)	//Checks to see if the user has already achieved a 2048 tile, in which case all of the following steps are unnecessary
					{
						if(checkWin(board)) // If they win, let them know and let them know their score and moves
						{
							System.out.println("Congrats, you've won! Your score is "+board[4][0]+" and you achieved it in "+board[4][1]+" moves");
							System.out.println("If you would like to continue, press c. If you would like to quit, press q");
							char continueInput = input.next().charAt(0);
							
							if (continueInput == 'c' || continueInput == 'C') 	//If the user wants to continue, make the variable win true
							{													//	so that they don't see these prompts again, and display 
								win = true;										//	the board for their next move
								displayBoard(board);
							}
							
							if (continueInput == 'q' || continueInput == 'Q')
							{
								System.out.println("Alright, good game, thanks for playing!");
								
								System.exit(0);		//If user presses enters q, quit program
							}
						}
					}
					
				}
			}
			
			if(userInput == 'd' || userInput == 'D' || userInput == '2') //If the move is down, compact down, 
			{															//combine like terms, and compact down again
				{
					
					for (int column = 0; column <= 3; column++)   // Save an instance of the current board in case of needed UNDO
					{	
						for (int row = 0; row <= 4; row++)
						{
							undoBoard[row][column] = board[row][column]; 
						}
					}
					
					compactDown(board);
					moveDown(board);
					compactDown(board);
					

					if(Arrays.deepEquals(undoBoard, board))							//If the new board is equal to the undoBoard
					{																// that means that no move has been made, and thus
						System.out.println("That is not a valid move, try again");	// the move selected is invalid
					}
					else
					{
						board[4][1]++;										// Increment the moves
						randomInt(board);									// Introduce a new number
						if(checkLose(board))								// Check to see if that move created a board that no turn can work on
						{													// 		in which case then the user loses and the program tells the user their
							displayBoard(board);							//		score and the number of moves that they made
							System.out.println("Sorry, you lose, good game! Your score was: "
												+ board[4][0] + " and you moved " + board[4][1] + " times");
							
							System.exit(0);
						}
					}
					
					displayBoard(board); 
					
					if(win == false)	//Checks to see if the user has already achieved a 2048 tile, in which case all of the following steps are unnecessary
					{
						if(checkWin(board)) // If they win, let them know and let them know their score and moves
						{
							System.out.println("Congrats, you've won! Your score is "+board[4][0]+" and you achieved it in "+board[4][1]+" moves");
							System.out.println("If you would like to continue, press c. If you would like to quit, press q");
							char continueInput = input.next().charAt(0);
							
							if (continueInput == 'c' || continueInput == 'C')	//If the user wants to continue, make the variable win true
							{													//	so that they don't see these prompts again, and display 
								win = true;										//	the board for their next move
								displayBoard(board);
							}
							
							if (continueInput == 'q' || continueInput == 'Q')
							{
								System.out.println("Alright, good game, thanks for playing!");
								
								System.exit(0);		//If user presses enters q, quit program
							}
						}
					}
				}
			}
			
			if(userInput == 'r' || userInput == 'R' || userInput == '6')	//If the move is right, compact to the right
			{																//combine like terms to the right, and then
				{															//compact to the right again
					
					for (int column = 0; column <= 3; column++)   // Save an instance of the current board in case of needed UNDO
					{	
						for (int row = 0; row <= 4; row++)
						{
							undoBoard[row][column] = board[row][column]; 
						}
					}
					
					compactRight(board);
					moveRight(board);
					compactRight(board);
					

					if(Arrays.deepEquals(undoBoard, board))							//If the new board is equal to the undoBoard
					{																// that means that no move has been made, and thus
						System.out.println("That is not a valid move, try again");	// the move selected is invalid
					}																
					else															
					{
						board[4][1]++;										// Increment the moves
						randomInt(board);									// Introduce a new number
						if(checkLose(board))								// Check to see if that move created a board that no turn can work on
						{													// 		in which case then the user loses and the program tells the user their 
							displayBoard(board);							//		score and the number of moves that they made
							System.out.println("Sorry, you lose, good game! Your score was: "
												+ board[4][0] + " and you moved " + board[4][1] + " times");
							System.exit(0);
						}
					}
					
					displayBoard(board);
					
					if(win == false)	//Checks to see if the user has already achieved a 2048 tile, in which case all of the following steps are unnecessary
					{
						if(checkWin(board)) // If they win, let them know and let them know their score and moves
						{
							System.out.println("Congrats, you've won! Your score is "+board[4][0]+" and you achieved it in "+board[4][1]+" moves");
							System.out.println("If you would like to continue, press c. If you would like to quit, press q");
							
							char continueInput = input.next().charAt(0);
							
							if (continueInput == 'c' || continueInput == 'C')	//If the user wants to continue, make the variable win true
							{													//	so that they don't see these prompts again, and display 
								win = true;										//	the board for their next move
								displayBoard(board);
							}
							
							if (continueInput == 'q' || continueInput == 'Q')
							{
								System.out.println("Alright, good game, thanks for playing!");
								
								System.exit(0);		//If user presses enters q, quit program
							}
						}
					}
				}
			}
			
			if(userInput == 'l' || userInput == 'L' || userInput == '4')	//If the move is left, compact to the left, 
			{																//combine any like terms to the left, and then
				{															//compact to the left again
					
					for (int column = 0; column <= 3; column++)   // Save an instance of the current board in case of needed UNDO
					{	
						for (int row = 0; row <= 4; row++)
						{
							undoBoard[row][column] = board[row][column]; 
						}
					}
					
					compactLeft(board);
					moveLeft(board);
					compactLeft(board);
					
					if(Arrays.deepEquals(undoBoard, board))							//If the new board is equal to the undoBoard
					{																// that means that no move has been made, and thus
						System.out.println("That is not a valid move, try again");	// the move selected is invalid
					}
					else
					{
						board[4][1]++;										// Increment the moves
						randomInt(board);									// Introduce a new number
						if(checkLose(board))								// Check to see if that move created a board that no turn can work on
						{													// 		in which case then the user loses and the program tells the user their 
							displayBoard(board);							//		score and the number of moves that they made
							System.out.println("Sorry, you lose, good game! Your score was: "
												+ board[4][0] + " and you moved " + board[4][1] + " times");
							System.exit(0);
						}
					}
					
					displayBoard(board);
					
					if(win == false)	//Checks to see if the user has already achieved a 2048 tile, in which case all of the following steps are unnecessary
					{
						if(checkWin(board)) // If they win, let them know and let them know their score and moves
						{
							System.out.println("Congrats, you've won! Your score is "+board[4][0]+" and you achieved it in "+board[4][1]+" moves");
							System.out.println("If you would like to continue, press c. If you would like to quit, press q");
							char continueInput = input.next().charAt(0);
							
							if (continueInput == 'c' || continueInput == 'C')	//If the user wants to continue, make the variable win true
							{													//	so that they don't see these prompts again, and display 
								win = true;										//	the board for their next move
								displayBoard(board);
							}
							
							if (continueInput == 'q' || continueInput == 'Q')
							{
								System.out.println("Alright, good game, thanks for playing!");
								
								System.exit(0);		//If user presses enters q, quit program
							}
						}
					}
				}
			}
			
			
			if (userInput == 'z' || userInput == 'Z')			 // If they want to undo, copy the cells of the last saved board  
			{													// into the main board array, and continue from that point on
				for (int column = 0; column <= 3; column++)		//	Note: This will only undo once
				{	
					for (int row = 0; row <= 4; row++)
					{
						board[row][column] = undoBoard[row][column]; 
					}
				}
				displayBoard(board);
			}
			
			
			if(userInput == 'q' || userInput == 'Q')
			{
				System.out.println("Alright, good game, thanks for playing!");
				
				System.exit(0);		//If user presses enters q, quit program
			}
			
			if(userInput == 'h' || userInput == 'H' || userInput == '?')  //Help tells the user the controls at any time during the game
			{
				System.out.println("Controls: Move Up (u,U,8); Move Down (d,D,2)");
				System.out.println("       Move Right (r,R,4); Move Left (l,L,6)");	
				System.out.println("       Undo (u,U); Quit (q,Q); ");
			}
					
		}
		
		
			
		
	}
	/*
	 * This method takes each cell and displays it within a board that it creates out of Unicode values.
	 * If the cell value is zero, the method displays it as an empty space, but the entire board is formatted
	 * to be able to accept up to 4-digit integers within each cell. You'll notice the the loop used only goes 
	 * from row 0 to row 2. This intentionally allows the last row's borders to merge nicely with the bottom outline
	 * border
	 */
	public static void displayBoard (int[][] board)
	{
		
		System.out.println("\u2554\u2550\u2550\u2550\u2550\u2566\u2550\u2550\u2550\u2550\u2566"	
							+ "\u2550\u2550\u2550\u2550\u2566\u2550\u2550\u2550\u2550\u2557");		//Create a top border
		for (int row = 0; row < 3; row++)															//Create a border on the left, then print the cell value
		{
			System.out.print("\u2551");
			for(int column = 0; column < 4; column++)
			{
				if(board[row][column]==0)						// In case the cell value is zero, make the cell full of 4 empty spaces
				{
					System.out.printf("%4s", "    ");
					System.out.print("\u2551");
				}
				
				else
				{
					System.out.printf("%4d", board[row][column]);		// When the cell is a number other than zero, print it formated to a 4 space cell
					System.out.print("\u2551");
				}
			}
			System.out.println();
			System.out.println("\u2560\u2550\u2550\u2550\u2550\u256C\u2550\u2550\u2550\u2550\u256c\u2550\u2550\u2550\u2550\u256c\u2550\u2550\u2550\u2550"
								+"\u2563");		//Print out a middle borderline to establish the "square cell" appearance
		}
		
		System.out.print("\u2551");
		
		for(int column = 0; column < 4; column++)	//This is one final pass through on the last (3rd) row in order to merge 
		{											// neatly with the bottom borderline
			if(board[3][column]==0)						// In case the cell value is zero, make the cell full of 4 empty spaces
			{
				System.out.printf("%4s", "    ");
				System.out.print("\u2551");
			}
			
			else
			{
				System.out.printf("%4d", board[3][column]);		// When the cell is a number other than zero, print it formated to a 4 space cell
				System.out.print("\u2551");
			}
		}

		System.out.println();
		
		System.out.println("\u255A\u2550\u2550\u2550\u2550\u2569\u2550\u2550\u2550\u2550"
							+ "\u2569\u2550\u2550\u2550\u2550\u2569\u2550\u2550\u2550\u2550\u255d"); // Print the bottom border
		
		System.out.println("Score: " + board[4][0] + "\nMoves: " + board[4][1]); 					 // Print the score and the moves thus far
		
	}
	
	/*
	 * This long line of code acts to compact all of the numbers within the array down by basically forcing out
	 * any zeros below any of the numbers to "bubble" up to the top. It works incrementally, using a switch statement
	 * to check which row is currently being monitored, and then checks back up the chain until it reaches the array limit.
	 * This works from the bottom up, so first it checks the lowest number and checks if that can be moved down, then it checks to see
	 * if the number in the row above that can be moved down, so on and so forth until it finally checks the top row to see if that number
	 * can be moved down. 
	 */
	public static void compactDown(int[][] board)
	{
		for (int column = 0; column < 4; column++)
		{
			for (int row = 2; row >= 0; row--) //Start at the bottom row and work its way up
			{
				switch (row)							// Each row needs a specific check, or else it will go out of the array's bounds
				{										// In essence, each check sees if the row below it is zero, and if so, it replaces that 
				case 2:									// that next row with its own value and then changes its own value to zero, thus swapping
						if (board[row+1][column]==0)	// places with the zero
						{
							board[row+1][column] = board[row][column]; //Start at the second to last row and work your way backwards through the rows
							board[row][column] = 0;
						}
						
						break;
				
				case 1:	if	(board[row+1][column]==0)		//Check the 2nd row and work your way down through the rows
						{
							if(board[row+2][column]==0)
							{
								board[row+2][column] = board[row][column];
								board[row+1][column] = 0;
								board[row][column] = 0;
							}
							else
							{
								board[row+1][column] = board[row][column];
								board[row][column] = 0;
							}
						}
						
						break;
				
				case 0:	if	(board[row+1][column]==0)		//Check the first (highest) row and work your way down
						{
							if(board[row+2][column]==0)
							{	
								if (board[row+3][column]==0)
								{
									board[row+3][column] = board[row][column];
									board[row+2][column] = 0;
									board[row+1][column] = 0;
									board[row][column] = 0;
								}
								else
								{
									board[row+2][column] = board[row][column];
									board[row+1][column] = 0;
									board[row][column] = 0;
								}
							}
							else
							{
								board[row+1][column] = board[row][column];
								board[row][column] = 0;
							}
						}
					
						break;
				}
			}
		}
	}
	
	/*
	 * This long line of code acts to compact all of the numbers within the array up by basically forcing out
	 * any zeros below any of the numbers to "sink" down to the bottom. It works incrementally, using a switch statement
	 * to check which row is currently being monitored, and then checks down the chain until it reaches the array limit.
	 * This works from the top down, so first it checks the first row and checks if that value can be moved up, then it checks to see
	 * if the number in the row below that can be moved up, so on and so forth until it finally checks the bottom row to see if that number
	 * can be moved up. 
	 */
	public static void compactUp(int[][] board)
	{
		for (int column = 0; column < 4; column++)
		{
			for (int row = 1; row <= 3; row++) 		// Start at the top row and work down
			{
				switch (row)						// Each case will change depending on the row so it doesn't go out of bounds
				{									// Each case however essentially goes to its respective row, and checks if the row above it is a zero.
				case 1:								// If it is, then it swaps values with that row, and then moves up a row and repeats, thus compacting upwards
						if (board[row-1][column]==0)	
						{												//Start your check from the 2nd row, because "below" the last row is out of the array's bounds
							board[row-1][column] = board[row][column];
							board[row][column] = 0;
						}
						
						break;
				
				case 2:	if	(board[row-1][column]==0)					//Check the 2nd row and work your way up the rows
						{
							if(board[row-2][column]==0)
							{
								board[row-2][column] = board[row][column];
								board[row-1][column] = 0;
								board[row][column] = 0;
							}
							else
							{
								board[row-1][column] = board[row][column];
								board[row][column] = 0;
							}
						}
						
						break;
				
				case 3:	if	(board[row-1][column]==0)					//Check the 
						{
							if(board[row-2][column]==0)
							{	
								if (board[row-3][column]==0)
								{
									board[row-3][column] = board[row][column];
									board[row-2][column] = 0;
									board[row-1][column] = 0;
									board[row][column] = 0;
								}
								else
								{
									board[row-2][column] = board[row][column];
									board[row-1][column] = 0;
									board[row][column] = 0;
								}
							}
							else
							{
								board[row-1][column] = board[row][column];
								board[row][column] = 0;
							}
						}
					
						break;
				}
			}
		}
	}
	
	/*
	 * This code compacts all the numbers in an array to the right. It does so by essentially checking to see
	 * if the column to its right is empty, if so then it swaps values with that column and then does the same process for that
	 * new column, thereby slowly moving to the right one cell at a time until it can no longer move to the right. After this method
	 * finishes, none of the numbers within the array should have a zero on their right.
	 */
	public static void compactRight(int[][] board)
	{
		for (int row = 0; row < 4; row++)
		{
			for (int column = 2; column >= 0; column--)
			{
				switch (column)
				{
				case 2:										//Start your check from the 3rd column, because "to the right" of
						if (board[row][column+1]==0)		// column 4 is outside of the array's bounds
						{
							board[row][column+1] = board[row][column];
							board[row][column] = 0;
						}
						
						break;
				
				case 1:	if	(board[row][column+1]==0)		//Check the 2nd column and work your way up the columns
						{
							if(board[row][column+2]==0)
							{
								board[row][column+2] = board[row][column];
								board[row][column+1] = 0;
								board[row][column] = 0;
							}
							else
							{
								board[row][column+1] = board[row][column];
								board[row][column] = 0;
							}
						}
						
						break;
				
				case 0:	if	(board[row][column+1]==0)		//Check the 1st column and work your way up the columns
						{
							if(board[row][column+2]==0)
							{	
								if (board[row][column+3]==0)
								{
									board[row][column+3] = board[row][column];
									board[row][column+2] = 0;
									board[row][column+1] = 0;
									board[row][column] = 0;
								}
								else
								{
									board[row][column+2] = board[row][column];
									board[row][column+1] = 0;
									board[row][column] = 0;
								}
							}
							else
							{
								board[row][column+1] = board[row][column];
								board[row][column] = 0;
							}
						}
					
						break;
				}
			}
		}
	}
	
	/*
	 * This code compacts all the numbers to the left in the given array. It essentially does so by checking each column
	 * to see if the column to its left is empty, in which case if it is the method then swaps the value of the current column
	 * with the column to the left, thus making its own value zero and giving the column to the left its old value. Then it does the 
	 * same process to that new column, thereby forcing out any zeroes to the right. Once this method is completed, none of the cells
	 * with values other than zero should have a zero on their left.
	 */
	public static void compactLeft(int[][] board)
	{
		for (int row = 0; row < 4; row++)
		{
			for (int column = 1; column <= 3; column++)
			{
				switch (column)
				{
				case 1:									//Check from the 2nd column, because "to the left" of the first 
						if (board[row][column-1]==0)	// column is out of the array's bounds
						{
							board[row][column-1] = board[row][column];
							board[row][column] = 0;
						}
						
						break;
				
				case 2:	if	(board[row][column-1]==0)	//Check from the 3rd column, and work your way down the columns
						{
							if(board[row][column-2]==0)
							{
								board[row][column-2] = board[row][column];
								board[row][column-1] = 0;
								board[row][column] = 0;
							}
							else
							{
								board[row][column-1] = board[row][column];
								board[row][column] = 0;
							}
						}
						
						break;
				
				case 3:	if	(board[row][column-1]==0)		//Check from the 4th column and work your way down the columns
						{
							if(board[row][column-2]==0)
							{	
								if (board[row][column-3]==0)
								{
									board[row][column-3] = board[row][column];
									board[row][column-2] = 0;
									board[row][column-1] = 0;
									board[row][column] = 0;
								}
								else
								{
									board[row][column-2] = board[row][column];
									board[row][column-1] = 0;
									board[row][column] = 0;
								}
							}
							else
							{
								board[row][column-1] = board[row][column];
								board[row][column] = 0;
							}
						}
					
						break;
				}
			}
		}
	}
	
	
	/*
	 * This method combines downward any cells that are sitting on top of each other that have the same
	 * value, for instance, a 2 and a 2 become a 4 if the 2 is on top of the other 2. 
	 */
	public static void moveDown (int[][] board)
	{
		//check array for empty space where move is being attempted
		for (int column = 0; column < 4; column++)
		{
			for (int row = 3; row > 0; row--)			// Start at the bottom and work your way up
			{
				if(board[row][column]==board[row-1][column] && board[row][column]!=0) //If the value in the cell above is equal to the value in your cell
				{																		// add them, put that value in your cell, and make the row above you zero
					board[row][column] = 2*board[row][column];
					board[row-1][column] = 0;
					
					board[4][0] = board[row][column]+ board[4][0];
				}
			}	
		}
	}
	
	/*
	 * This method combines upward any cells that are sitting on top of each other that have the same
	 * value, for instance, a 2 and a 2 become a 4 if the 2 is on top of the other two
	 */
	public static void moveUp (int[][] board)
	{
		//check array for empty space where move is being attempted
		for (int column = 0; column < 4; column++)
		{
			for (int row = 0; row < 3; row++)		//Start at the top and work your way down
			{
				if(board[row][column]==board[row+1][column] && board[row][column]!=0) //If the value in the cell below is equal to the value in your cell
				{																		// add them, set your cell equal to that value, and set the cell below you to zero
					board[row][column] = 2*board[row][column];
					board[row+1][column] = 0;
					
					board[4][0] = board[row][column]+ board[4][0];
				}
			}	
		}
	}
	
	/*
	 * This method combines to the right any cells that are sitting next to each other that have the same
	 * value, for instance, a 2 and a 2 become a 4 if the 2 is next to the other 2
	 */
	public static void moveRight(int[][] board)
	{
		//check array for empty space where move is being attempted
		for (int row = 0; row < 4; row++)
		{
			for (int column = 3; column > 0; column--)		// Start from the right and work left
			{
				if(board[row][column]==board[row][column-1] && board[row][column]!=0) //If the value in the cell to your left is equal to the value in your cell
				{																	//add them, set your cell equal to that value, and set the cell to your left equal to zero
					board[row][column] = 2*board[row][column];	
					board[row][column-1] = 0;
					
					board[4][0] = board[row][column]+ board[4][0];
				}
			}	
		}
	}
	
	/*
	 * This method combines to the left any cells that are sitting next to each other that have the same
	 * value, for instance, a 2 and a 2 become a 4 if the 2 is next to the other 2
	 */
	public static void moveLeft(int[][] board)
	{
		//check array for empty space where move is being attempted
		for (int row = 0; row < 4; row++)
		{
			for (int column = 0; column < 3; column++)		//Start at the left, work your way right
			{
				if(board[row][column]==board[row][column+1] && board[row][column]!=0) //If the value in the cell to your left is equal to the value in your cell
				{																	//add them, set your cell equal to that value, and set the cell to your left equal to zero
					board[row][column] = 2*board[row][column];
					board[row][column+1] = 0;
					
					board[4][0] = board[row][column]+ board[4][0];
				}
			}	
		}
	}
	
	

	/*
	 * This method sorts through the board and finds all of the empty spots
	 * and randomly chooses one of those spots to place either a 2 90% of the time
	 * or a 4 the other 10% of the time
	 */
	public static void	randomInt (int[][] board)
	{
		int empty = 0;
		
		for (int column = 0; column <= 3; column++) // Find out how many empty cells exist
		{	
			for (int row = 0; row <= 3; row++)
				{
					if (board[row][column] == 0) 
						{empty++;}
				}
		}
		
		int[][] findEmpty = new int[empty][2]; 	// Create a variable length array to store row and column
												// of the possible empty board cells
		
		int change = 1; // This variable will be used to move through the different rows of
						// the findEmpty array
		
		for (int column = 0; column <= 3; column++) // Fill the findEmpty array with the rows and columns
		{											// of possible empty cells
			for (int row = 0; row <= 3; row++)
				{
					if (board[row][column] == 0) 
					{
						findEmpty[empty-change][0] = row;
						findEmpty[empty-change][1] = column;
						
						change++;
					}
				}
		}
		
		int cell = (int)((empty)*Math.random()); // Choose a random row from findEmpty, then place either a 
												// 2 or 4 in that cell depending on whether Math.random is 
		if(Math.random() <= .9)					// is greater than or less than .9
		{
			board[findEmpty[cell][0]][findEmpty[cell][1]] = 2;
		}
		
		else
		{
			board[findEmpty[cell][0]][findEmpty[cell][1]] = 4;
		}
	}
	
	/* 
	 * This method takes on the task of sifting through the board array
	 * and attempts to spot a win, meaning it will search for any of the 
	 * contents of the array cells to be equal to 2048. It is a boolean method,
	 * which means it will return whether "win" is true or not. It does not handle
	 * the actual process of announcing the winner, it simply informs main whether
	 * a win has been achieved or not
	 */
	public static boolean checkWin(int[][] board)
	{	
		boolean win = false; 	// Create an boolean variable that will
								// be sent through the method's return
		
		for(int column = 0; column < 4; column++)	// Search through each individual
		{											// cell of the code to determine
			for (int row = 0; row < 4; row++)		// if the number 2048 has been created
			{										// in any of the cells
				if (board[row][column] == 2048)
				{
					win = true;						// If it finds a 2048 tile, 
				}									// set win to true
			}
		}
		
		return win;
	}
	
	/*
	 * This code pulls directly from moveUp, moveDown, moveLeft, moveRight, and the findEmpty portion of randomInt.
	 * Essentially, it works off of a "guilty unless proven innocent" system, where it is assumed that the player has 
	 * lost until it can find a possible move in the board that can be made. So, it checks for empty spaces, because it
	 * knows at least those could be filled in as a move, or it checks to see if any of the same number are right next to
	 * each other, in which case those could be combined. If neither one of these qualifications are met, the lose variable
	 * stays true, and then main informs the loser that they have lost. 
	 */
	public static boolean checkLose(int[][] board)
	{
		boolean lose = true;
		
		
		for (int column = 0; column < 4; column++)  //Check the vertical going up to see if any of them can be combined
		{
			for (int row = 0; row < 3; row++)		//Start at the top and work your way down
			{
				if(board[row][column]==board[row+1][column]) //If the value in the cell below is equal to the value in your cell
				{																		// add them, set your cell equal to that value, and set the cell below you to zero
					lose = false;
				}
			}	
		}
		
		
		for (int column = 0; column < 4; column++)	//Check the vertical going down to see if any of them can be combined
		{
			for (int row = 3; row > 0; row--)			// Start at the bottom and work your way up
			{
				if(board[row][column]==board[row-1][column]) //If the value in the cell above is equal to the value in your cell
				{											
					lose = false;							//Then the player has not lost 
				}
			}	
		}
		
		for (int row = 0; row < 4; row++) //Check the horizontal going left to see if any of them can be combined
		{
			for (int column = 3; column > 0; column--)		// Start from the right and work left
			{
				if(board[row][column]==board[row][column-1]) //If the value in the cell to your left is equal to the value in your cell
				{																	
					lose = false;							//Then the player has not lost 
				}
			}	
		}
		
		
		for (int row = 0; row < 4; row++)	//Check the horizontal going right to see if any of them can be combined
		{
			for (int column = 0; column < 3; column++)		//Start at the left, work your way right
			{
				if(board[row][column]==board[row][column+1]) //If the value in the cell to your left is equal to the value in your cell
				{																
					lose = false;							//Then the player has not lost 
				}
			}	
			
		}
	
		for (int column = 0; column <= 3; column++) //Search through every cell in the board and find out if 
		{											// any of the cells are zero
			for (int row = 0; row <= 3; row++)
				{
					if (board[row][column] == 0) 
					{
						lose = false;			//If there's a zero, the player hasn't lost
					}
				}
		}
		
		return lose; 
	}
}


	

