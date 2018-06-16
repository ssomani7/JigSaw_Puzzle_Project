package ooad.finalVersion;

public class User {

	private String name;
	private int score;
	private int level;
	private String image;
	private int dimension;
	
	/**
	 * Constructor: Default
	 */
	public User() {
	}//end constructor
	
	/**
	 * Constructor: Parametrized
	 * @param name: Name of the User
	 */
	public User(String name) {// new User
		this.name = name;
	}//end constructor
	
	/**
	 * Constructor : Parametrized
	 * @param name : Name of the User
	 * @param score: Score registered by the User
	 * @param level: Level choosen by the User
	 */
	public User(String name, int score, int level) {
		this.name = name;
		this.score = score;
		this.level = level;
	}//end constructor

	public User setName(String name) {
		this.name = name;
		return this;
	}

	public String getName() {
		return name;
	}
	
	public User setScore(int score) {
		this.score = score;
		return this;
	}
	
	public int getScore() {
		return score;
	}
	
	public User setLevel(int level) {
		this.level = level;
		return this;
	}

	public int getLeveL() {
		return level;
	}

	public User setDimension(int dimension) {
		this.dimension = dimension;
		return this;
	}

	public int getDimension() {
		return dimension;
	}

	public User setImage(String image) {
		this.image = image;
		return this;
	}

	public String getImage() {
		return image;
	}
	@Override
	public String toString() {
		return name + "," + score + "," + level; 
	}
}//end class User