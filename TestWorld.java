/**This program is an implementation of a cellular automata simulation.
 * It simulates 2 species living in a world represented by a 2-dimensional grid.
 * Each species periodically reproduces and propagates throughout the world.
 * The species have different attributes and the one with the higher fitness level
 * has the potential to murder the weaker one and reproduce in its place.
 * Each generated creature runs on it's own thread. There is a flat world and 
 * a torus (wrap-around) world.
 * 
 * @author Khari Outram
 *
 */
public class TestWorld {

	public static void main(String[] args) {
		/**
		 * comment out either of the following worlds 
		 * to run the program with the other 
		 */
		//FlatWorld world = new FlatWorld();
		TorusWorld world = new TorusWorld();
				
		try
		{
			//infinitely print the grid every half-second
			for(;;)
			{
				world.printGrid();
				Thread.sleep(500);
			}
		}
		catch(InterruptedException ie)
		{
			// do nothing
		}
	}

}
