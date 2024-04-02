package rgou.model.dice;

public class DiceRollResult {
	private boolean isWin;
	private String textureName;
	private int id;

	public DiceRollResult(boolean isWin, String textureName, int id) {
		this.isWin = isWin;
		this.textureName = textureName;
		this.id = id;
	}

	public boolean isWin() {
		return isWin;
	}

	public String getTextureName() {
		return textureName;
	}

	public void setWin(boolean isWin) {
		this.isWin = isWin;
	}

	public void setTextureName(String texture) {
		this.textureName = texture;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
