import java.io.BufferedReader;
import java.io.InputStreamReader;
class Solution
{
	public static void printMatrix(int[][] matrix)
	{
		for(int i[] : matrix)
		{
			for(int j : i)
			{
				System.out.print(j+" ");
			}
			System.out.println();
		}
	}
	public static void destroyIslands(int[][] matrix,int i,int j, boolean isDiagonalConsidered)
	{
		if(i<0 || j<0 || i>=matrix.length || j>=matrix.length || matrix[i][j]==0) return;
		matrix[i][j]=0;
		destroyIslands(matrix,i-1,j, isDiagonalConsidered);
		destroyIslands(matrix,i,j+1, isDiagonalConsidered);
		destroyIslands(matrix,i+1,j, isDiagonalConsidered);
		destroyIslands(matrix,i,j-1, isDiagonalConsidered);

		/*
		To consider even the diagonal land
        */
        if(isDiagonalConsidered){
            destroyIslands(matrix, i-1, j-1, isDiagonalConsidered);
            destroyIslands(matrix, i-1, j+1, isDiagonalConsidered);
            destroyIslands(matrix, i+1, j+1, isDiagonalConsidered);
            destroyIslands(matrix, i+1, j-1, isDiagonalConsidered);
        }
		

		
	}
	public static int noOfIslands(int[][] matrix, boolean isDiagonalConsidered)
	{
		int count=0;
		for(int i=0;i<matrix.length;i++)
		{
			for(int j=0;j<matrix.length;j++)
			{
				if(matrix[i][j]==1)
				{
					count++;
					destroyIslands(matrix,i,j, isDiagonalConsidered);
				}
			}
		}
		return count;
	}
	public static void main(String args[])throws java.io.IOException
	{
		int matrix[][] = {
				{0, 1, 1, 1, 0, 1, 0},
				{0, 1, 1, 1, 1, 1, 0},
				{0, 1, 0, 0, 0, 0, 0},
				{0, 0, 0, 1, 1, 0, 1},
				{1, 1, 1, 0, 0, 1, 1},
				{1, 1, 0, 0, 0, 0, 1},
				{0, 1, 1, 0, 1, 0, 1}
		};
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Consider the diagonal land? (y/n)");
        boolean isDiagonalConsidered = false;
        if(br.readLine().trim().equalsIgnoreCase("y")){
            isDiagonalConsidered = true;
        }
		System.out.print("No. of Islands : "+noOfIslands(matrix, isDiagonalConsidered));
	}
}