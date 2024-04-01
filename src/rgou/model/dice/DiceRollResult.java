package rgou.model.dice;

public class DiceRollResult {
	private boolean isWin;
	private String textureName;

	public DiceRollResult(boolean isWin, String textureName) {
		this.isWin = isWin;
		this.textureName = textureName;
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

}
