package ch.epfl.cs107.play.game.superpacman.area;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.actor.SuperPacmanPlayer;
import ch.epfl.cs107.play.game.superpacman.actor.ghost.*;
import ch.epfl.cs107.play.game.superpacman.Collectables.Bonus;
import ch.epfl.cs107.play.game.superpacman.Collectables.Cherry;
import ch.epfl.cs107.play.game.superpacman.Collectables.Diamond;
import ch.epfl.cs107.play.game.superpacman.solidEntities.Wall;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;
import ch.epfl.cs107.play.game.areagame.AreaGraph;

public class SuperPacmanBehavior extends AreaBehavior{

	private AreaGraph graph;
	private List<Ghost> ghosts = new ArrayList<>();


	public SuperPacmanBehavior(Window window, String name) {
		super(window, name);
		int height = getHeight();
		int width = getWidth();
		this.graph = new AreaGraph();
		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				SuperPacmanCellType celltype = SuperPacmanCellType.toType(getRGB(height - 1 - y, x));
				setCell(x, y, new SuperPacmanCell(x, y, celltype));
			}
		}
		createNodes();

	}

	public AreaGraph getGraph() {
		return graph;
	}


	/**
	 * creates the nodes of the graph associcated to the grid
	 */
	public void createNodes() {
		for (int y = 0; y < getHeight(); ++y) {
			for (int x = 0; x < getWidth(); ++x) {
				if (((SuperPacmanCell) getCell(x, y)).type != SuperPacmanCellType.WALL) {
					graph.addNode(new DiscreteCoordinates(x, y),
							(x > 0 && ((SuperPacmanCell) getCell(x - 1, y)).type != SuperPacmanCellType.WALL),
							(y + 1 < getHeight() && ((SuperPacmanCell) getCell(x, y + 1)).type != SuperPacmanCellType.WALL),
							(x + 1 < getWidth() && ((SuperPacmanCell) getCell(x + 1, y)).type != SuperPacmanCellType.WALL),
							(y > 0 && ((SuperPacmanCell) getCell(x, y - 1)).type != SuperPacmanCellType.WALL));
				}
			}
		}
	}

	/**
	 *
	 * @param x (int): x coordinate of the cell
	 * @param y (int): y coordinate of the cell
	 * @return true if cell is contained in the grid, false if not
	 */
	private boolean cellExist(int x, int y) {
		return ((x < this.getWidth()) && (y < this.getHeight()) && (x >= 0) && (y >=0));
	}

	/**
	 *
	 * @param a (int): x coordinate of the cell
	 * @param b (int): y coordinate of the cell
	 * @param radius (int): radius around the given cell
	 * @return List of cells contained inside the radius with the given cell as the center
	 */
	public List<DiscreteCoordinates> createFieldArea(int a, int b, int radius) {
		List<DiscreteCoordinates> field = new ArrayList<>();

		for (int x = -radius; x < radius+1; ++x) {
			for (int y = -radius; y < radius+1; ++y) {
				if (cellExist(a+x, b+y))
					field.add(new DiscreteCoordinates(a+x, b+y));
			}
		}
		return field;
	}

	/**
	 *
	 * @param x (int): x coordinate
	 * @param y (int): y coordinate
	 * @return 2D-boolean-array of the walls surrounding the given cell
	 */
	public boolean[][] getNeighborhood(int x, int y) {
		boolean[][] neighborhood = new boolean[3][3];
		
		for (int i = 0; i < 3; ++i) {
			for (int j = 2; j >=0; --j) {				//on commence a compte d'en haut et on descend
				if (cellExist(x+i-1, y-j+1)) {
						neighborhood[i][j] = SuperPacmanCellType.toType(getRGB(getHeight()-1-(y-j+1), (x+i-1))) == SuperPacmanBehavior.SuperPacmanCellType.WALL;
				} else {
					neighborhood[i][j] = false;	
				}
				
				} 
			}
		
		return neighborhood;
	}

	/**
	 *
	 * @return List of Ghosts registered inside the current area
	 */
	public List<Ghost> accessGhosts() {
		return ghosts;
	}


	/**
	 *
	 * @param area (Area)
	 * @param ghost (Ghost)
	 * @param x (int) : x coordinate
	 * @param y (int) : y coordinate
	 * registers ghost into area, sets up its refugePosition and adds it to the ghost list
	 */
	protected void addGhost(Area area, Ghost ghost, int x, int y) {
		area.registerActor(ghost);
		ghost.setRefugePostion(new DiscreteCoordinates(x, y));
		ghosts.add(ghost);
	}

	/**
	 *
	 * @param x (int): x coordinate
	 * @param y (int): y coordinate
	 * adds a node on coordinate (x, y)
	 */
	public void addNode(int x, int y) {
		graph.addNode(new DiscreteCoordinates(x, y),
				(x > 0 && ((SuperPacmanCell) getCell(x - 1, y)).type != SuperPacmanCellType.WALL),
				(y + 1 < getHeight() && ((SuperPacmanCell) getCell(x, y + 1)).type != SuperPacmanCellType.WALL),
				(x + 1 < getWidth() && ((SuperPacmanCell) getCell(x + 1, y)).type != SuperPacmanCellType.WALL),
				(y > 0 && ((SuperPacmanCell) getCell(x, y - 1)).type != SuperPacmanCellType.WALL));
	}

	/**
	 *
	 * @param area (Area): owner area
	 * registers actors in consideration of the celltype
	 */
	protected void registerActors (Area area) {
			for (int y = 0; y < getHeight(); ++y) {
				for (int x = 0; x < getWidth(); ++x) {
					if (SuperPacmanCellType.toType(getRGB(getHeight() - 1 - y, x)) == SuperPacmanBehavior.SuperPacmanCellType.WALL) {
						area.registerActor(new Wall(area, new DiscreteCoordinates(x, y), getNeighborhood(x, y)));
					} else if (SuperPacmanCellType.toType(getRGB(getHeight() - 1 - y, x)) == SuperPacmanBehavior.SuperPacmanCellType.FREE_WITH_BONUS) {
						area.registerActor(new Bonus(area, new DiscreteCoordinates(x, y)));
					} else if (SuperPacmanCellType.toType(getRGB(getHeight() - 1 - y, x)) == SuperPacmanBehavior.SuperPacmanCellType.FREE_WITH_CHERRY) {
						area.registerActor(new Cherry(area, new DiscreteCoordinates(x, y)));
					} else if (SuperPacmanCellType.toType(getRGB(getHeight() - 1 - y, x)) == SuperPacmanBehavior.SuperPacmanCellType.FREE_WITH_DIAMOND) {
						area.registerActor(new Diamond(area, new DiscreteCoordinates(x, y)));
						((SuperPacmanArea) area).incrementCounter(1);                            //des qu'un diamant est cree, on appelle la methode pour augmenter le compteur de diamant
					} else if (SuperPacmanCellType.toType(getRGB(getHeight() - 1 - y, x)) == SuperPacmanBehavior.SuperPacmanCellType.FREE_WITH_BLINKY) {
						Blinky blinky = new Blinky(area, Orientation.DOWN, new DiscreteCoordinates(x, y), "superpacman/ghost.blinky");
						addGhost(area, blinky, x, y);
					} else if (SuperPacmanCellType.toType(getRGB(getHeight() - 1 - y, x)) == SuperPacmanBehavior.SuperPacmanCellType.FREE_WITH_INKY) {
						Inky inky = new Inky(area, Orientation.DOWN, new DiscreteCoordinates(x, y), "superpacman/ghost.inky");
						addGhost(area, inky, x, y);
						inky.setRefugeAreaAfraid(createFieldArea(x, y, 5));
						inky.setRefugeArea(createFieldArea(x, y, 10));
					} else if (SuperPacmanCellType.toType(getRGB(getHeight() - 1 - y, x)) == SuperPacmanBehavior.SuperPacmanCellType.FREE_WITH_PINKY) {
						Pinky pinky = new Pinky(area, Orientation.DOWN, new DiscreteCoordinates(x, y), "superpacman/ghost.pinky");
						addGhost(area, pinky, x, y);
					}
				}
			}

	}


	public enum SuperPacmanCellType {
		NONE(0), // never used as real content
		WALL ( -16777216), //black
		FREE_WITH_DIAMOND(-1), //white
		FREE_WITH_BLINKY (-65536), //red
		FREE_WITH_PINKY ( -157237), //pink
		FREE_WITH_INKY ( -16724737), //cyan
		FREE_WITH_CHERRY (-36752), //light red
		FREE_WITH_BONUS ( -16478723), //light blue
		FREE_EMPTY ( -6118750); // sort of gray

		final int type;

		SuperPacmanCellType (int type) {
			this.type=type;
		}

		public static SuperPacmanCellType toType(int type){
			for(SuperPacmanCellType ict : SuperPacmanCellType.values()){
				if(ict.type == type)
					return ict;
			}

			System.out.println(type);
			return null;
		}
	}

	public class SuperPacmanCell extends Cell {

		private SuperPacmanCellType type;

		public SuperPacmanCell(int x, int y, SuperPacmanCellType type) {
			super(x, y);
			this.type = type;
		}


		@Override
		public boolean isCellInteractable() {
			//TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isViewInteractable() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public void acceptInteraction(AreaInteractionVisitor v) {
			// TODO Auto-generated method stub

		}

		@Override
		protected boolean canLeave(Interactable entity) {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		protected boolean canEnter(Interactable entity) {
			return !hasNonTraversableContent();
		}



	}
	
	
}

