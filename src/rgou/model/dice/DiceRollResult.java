package rgou.model.dice;

/**
 * Represents the result of rolling a single die.
 */
public class DiceRollResult {
	private boolean isWin;
	private String textureName;
	private int id;

	/**
	 * Constructs a DiceRollResult with the specified attributes.
	 * 
	 * @param isWin       true if the roll is a winning roll, false otherwise
	 * @param textureName the name of the texture associated with the roll
	 * @param id          the identifier of the roll
	 */
	public DiceRollResult(boolean isWin, String textureName, int id) {
		this.isWin = isWin;
		this.textureName = textureName;
		this.id = id;
	}

	/**
	 * Checks if the roll is a winning roll.
	 * 
	 * @return true if the roll is a winning roll, false otherwise
	 */
	public boolean isWin() {
		return isWin;
	}

	/**
	 * Gets the name of the texture associated with the roll.
	 * 
	 * @return the name of the texture
	 */
	public String getTextureName() {
		return textureName;
	}

	/**
	 * Sets whether the roll is a winning roll or not.
	 * 
	 * @param isWin true if the roll is a winning roll, false otherwise
	 */
	public void setWin(boolean isWin) {
		this.isWin = isWin;
	}

	/**
	 * Sets the name of the texture associated with the roll.
	 * 
	 * @param textureName the name of the texture
	 */
	public void setTextureName(String texture) {
		this.textureName = texture;
	}

	/**
	 * Gets the identifier of the roll.
	 * 
	 * @return the identifier of the roll
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the identifier of the roll.
	 * 
	 * @param id the identifier of the roll
	 */
	public void setId(int id) {
		this.id = id;
	}
}
