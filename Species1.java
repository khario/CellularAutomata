/**Concrete implementation of the Creature class that specifies its own 
 * values for maxLifeSpan, fitness and speciesType
 * 
 * @author Khari
 *
 */
public class Species1 extends Creature{
	private final int maxLifeSpan = 10000;
	private final double fitness = 0.8;
	private final int speciesType = 1;
	private int rowPos, colPos;	
	private World world;
	private Thread creatureThread;
	
	/**
	 * Constructor
	 * @param w
	 * @param row
	 * @param col
	 */
	public Species1(World theWorld, int row, int col)
	{		
		super(theWorld, row, col);					
	}
	
	/**
	 * @return the string to be printed to represent this object
	 */
	public String toString()
	{
		return "[1]";
	}
	/**
	 * @return the species type of this object
	 */
	public int getSpeciesType()
	{
		return speciesType;
	}

	/**
	 * @return the maxLifeSpan attribute of this object
	 */
	public int getMaxLifeSpan()
	{
		return maxLifeSpan;
	}

	/**
	 * @return the fitness attribute of this object
	 */
	public double getFitness() 
	{
		return fitness;
	}

	/**
	 * @return the row coordinate of this object
	 */
	public int getRowPos() 
	{
		return rowPos;
	}
	
	/**
	 * @return the column coordinate of this object
	 */
	public int getColPos() 
	{
		return colPos;
	}	
	
	/**
	 * set the row coordinate of this object
	 */
	public void setRowPos(int row)
	{
		rowPos = row;
	}
	/**
	 * Set the column coordinate of this object
	 */
	public void setColPos(int col)
	{
		colPos = col;
	}
	
}