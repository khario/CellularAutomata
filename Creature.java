/**This is an abstract class that represents the creature and contains most
 * of the state and behaviour used by its concrete subclasses.
 * 
 * @author Khari
 *
 */
public abstract class Creature implements Runnable{
	private int maxLifeSpan; // maximum time(in milliseconds) the creature may live
	private double fitness; // the creature's strength between 0.0 and 1.0
	private int rowPos, colPos; // the creatures coordinates on the grid
	private World world; // reference to the world object
	private int speciesType; // the type of species, specified as a digit
	private Thread creatureThread; // the thread on which this creature runs
	
	public Creature(World theWorld, int row, int col)
	{
		world = theWorld;
		setRowPos(row);
		setColPos(col);
		
		//place this object on a thread and keep a reference to that thread
		creatureThread = new Thread(this);		
	}
	
	
	/**
	 * Abstract methods to be implemented by
	 * classes that extend Creature	 * 
	 */
	public abstract String toString();	
	
	public abstract void setRowPos(int row);
	
	public abstract void setColPos(int col);	
	
	public abstract int getSpeciesType(); 

	public abstract int getMaxLifeSpan(); 

	public abstract double getFitness();
	
	public abstract int getRowPos();
	
	public abstract int getColPos();

	
	/**
	 * determines the type of world and chooses the 
	 * appropriate reproduction method
	 */
	public void reproduce()
	{				
		if(world.getWorldType().equals("torus"))
		{
			torusReproduce();
		}
		else
		{
			flatReproduce();
		}		
	}
	
	/**
	 * used to reproduce either species in the flat world
	 */
	public void flatReproduce()
	{
		synchronized(world)
		{
			//create a reference to the world's grid
			Creature[][] grid = world.getGrid();
			
			//check all 9 squares in the vicinity of the creature
			for(int i = this.getRowPos()-1; i <= this.getRowPos()+1; i++)
			{
				for(int j = this.getColPos()-1; j <= this.getColPos()+1; j++)
				{	
					//only proceed if the square being checked it not out of bounds
					if(i >= 0 && i < world.getnumRows() && j >= 0 && j < world.getnumColumns())
					{
						//create child
						Creature newCreature;
						
						//initialise child, matching the species of the parent
						switch(this.getSpeciesType())
						{
							case 1: newCreature = new Species1(world, i, j);
							break;
							case 2: newCreature = new Species2(world, i, j);
							break;
							default: newCreature = null;;
							break;							
						}
						
						//if the square is unoccupied or occupied by the parent, 
						//attempt to add a creature based on its probability
						if(grid[i][j] == null || grid[i][j] == this)
						{
							if(Math.random() <= this.getFitness())
							{						
								world.addCreature(newCreature, i, j);						
								grid[i][j].live();
							}
						}
						//if the square is occupied, attempt to murder it and reproduce in its
						//place based on probability and the relative fitness levels of both species
						else
						{
							if(Math.random() <= (this.getFitness()-grid[i][j].getFitness()))
							{
								grid[i][j].murder();							
								world.addCreature(newCreature, i, j);							
								grid[i][j].live();
							}
						}
					}						
				}
			}
		}
	}
	
	
	/**
	 * used to reproduce either species in the torus world
	 */
	public void torusReproduce()
	{
		synchronized(world)
		{
			//create a reference to the world's grid
			Creature[][] grid = world.getGrid();			
			
			//check all 9 squares in the vicinity of the creature
			for(int i = this.getRowPos()-1; i <= this.getRowPos()+1; i++)
			{
				for(int j = this.getColPos()-1; j <= this.getColPos()+1; j++)
				{
					//if either index goes out of range, adjust
					//it to wrap around to the opposite side
					int rowIndex = wrapRow(i);
					int colIndex = wrapCol(j);			
					
					//create child
					Creature newCreature;
					
					//initialise child, matching the species of the parent
					switch(this.getSpeciesType())
					{
						case 1: newCreature = new Species1(world, rowIndex, colIndex);
						break;
						case 2: newCreature = new Species2(world, rowIndex, colIndex);
						break;
						default: newCreature = null;;
						break;							
					}
					//if the square is unoccupied or occupied by the parent, 
					//attempt to add a creature based on its probability
					if(grid[rowIndex][colIndex] == null || grid[rowIndex][colIndex] == this)
					{
						if(Math.random() <= this.getFitness())
						{
							world.addCreature(newCreature, rowIndex, colIndex);
							grid[rowIndex][colIndex].live();						
						}
					}
					//if the square is occupied, attempt to murder it and reproduce in its
					//place based on probability and relative fitness levels of both species
					else
					{
						if(Math.random() <= (this.getFitness()-grid[rowIndex][colIndex].getFitness()))
						{
							grid[rowIndex][colIndex].murder();
							world.addCreature(newCreature, rowIndex, colIndex);
							grid[rowIndex][colIndex].live();
						}
					}						
				}
			}			
		}
	}
	
	
	/**
	 * resets the current square to null if the creature 
	 * reaches its end of life without producing a child
	 * in the same square.
	 */
	public void die()
	{				
		synchronized(world)
		{
			Creature[][] grid = world.getGrid();
			if(grid[getRowPos()][getColPos()] == this)
			{
				world.setGridNull(this.getRowPos(), this.getColPos());
			}			
		}
	}
	
	
	/**
	 * allows this Creature to be murdered via an interrupt
	 */
	public void murder()
	{
		this.creatureThread.interrupt();
	}
	
	/**
	 * starts a creature's thread
	 */
	public void live()
	{
		this.creatureThread.start();
	}
	
	
	/**
	 * Runnable object's 'run' method.
	 * It put's the thread to sleep for a randomised time with 
	 * a cap of the the species' maximum lifetime.
	 * After this, the creature reproduces and then dies. 
	 */
	public void run()
	{		
		try
		{	
			this.creatureThread.sleep((int)(Math.random() *this.getMaxLifeSpan()));				
			reproduce();
			die();			
		}
		catch(InterruptedException e)
		{			
			// do nothing
		}
	}
	
	/**
	 * adjust the current index in the reproduce method if it goes 
	 * out of bounds on the Y-axis so that it wraps around
	 * to the other side
	 * @param rowIndex
	 * @return rowIndex 
	 */
	public int wrapRow(int rowIndex)
	{            
       if(rowIndex < 0)
       {
           return world.getnumRows()-1;
       }
       if(rowIndex >= world.getnumRows())
       {
           return 0;
       }
       return rowIndex;
	}
   
	/**
	 * adjust the current index in the reproduce method if it goes 
	 * out of bounds on the X-axis so that it wraps around
	 * to the other side
	 * @param colIndex
	 * @return colIndex
	 */
	public int wrapCol(int colIndex)
	{            
       if(colIndex < 0)
       {
           return world.getnumColumns()-1;
       }
       if(colIndex >= world.getnumColumns())
       {
           return 0;
       }
       return colIndex;
	}	
}

