/**This is an abstract class that represents the world and contains most
 * of the state and behaviour used by its concrete subclasses.
 * 
 * @author Khari
 *
 */
public abstract class World {
	private String worldType;
	//set number of rows in the grid
	private final int numRows = 20;
	
	//set number of columns in the grid
	private final int numColumns = 30;
	
	//create grid in which creatures will live
	private Creature[][] grid; 
	
	/**
	 * constructor for the world
	 */
	public World()
	{
		initWorld();	
	}
		
	/**
	 * loops through each element in the grid
	 * and prints it's contents. It prints "[ ]"
	 * in place of null values	
	 */
	public void printGrid()
	{
		for(int i = 0; i < numRows; i++)
		{
			for(int j = 0; j < numColumns; j++)
			{
				if(grid[i][j] == null)
				{
					System.out.print("[ ]");
				}
				else
				{
					System.out.print(grid[i][j]);
				}
			}
			System.out.println("\n");			
		}
		System.out.println("\n\n");
	}
	
	/**
	 * 
	 * @return the number of rows in the grid
	 */
	public int getnumRows() 
	{
		return numRows;
	}

	/**
	 * 
	 * @return the number of columns in the grid
	 */
	public int getnumColumns() 
	{
		return numColumns;
	}

	
	/**
	 * initialise the grid and place one of each species 
	 */
	public void initWorld()
	{
		grid = new Creature[numRows][numColumns];
		
		//place a Species1 object in the top half of the grid
		int topRowInit = (int)(Math.random()*(numRows/2));
		int topColInit = (int)(Math.random()*(numColumns));
		grid[topRowInit][topColInit] = new Species1(this, topRowInit, topColInit);
		grid[topRowInit][topColInit].live();
		
		//place a Species2 object in the bottom half of the grid
		int bottomRowInit = (int)(Math.random()*(numRows/2))+(numRows/2);		
		int bottomColInit = (int)(Math.random()*(numColumns));				
		grid[bottomRowInit][bottomColInit] = new Species2(this, bottomRowInit, bottomColInit);
		grid[bottomRowInit][bottomColInit].live();
		
	}
	
	/**
	 * sets the specified position in the grid to null
	 * @param row
	 * @param col
	 */
	public void setGridNull(int row, int col)
	{
		grid[row][col] = null;
	}
	
	/**
	 * adds an existing creature to the specified space on the grid
	 * @param child
	 * @param row
	 * @param col
	 */
	public void addCreature(Creature child, int row, int col)
	{
		grid[row][col] = child;
	}
	
	
	/**
	 * returns the grid object
	 */
	public Creature[][] getGrid()
	{
		return grid;
	}
	
	/**
	 * Returns the world type. This is overridden by its subclasses
	 * @return
	 */
	public abstract String getWorldType();
	
	
}