package rgou.model;

/**
 * Represents a tile in the game board.
 */
public class Tile {
	private boolean isRosette;
	private boolean isVisible;
	private boolean isStart;
	private boolean isFinish;
	private String team;
	private String textureName;
	private String pawn;

	private Tile(Builder builder) {
		this.isRosette = builder.isRosette;
		this.isVisible = builder.isVisible;
		this.isStart = builder.isStart;
		this.isFinish = builder.isFinish;
		this.textureName = builder.textureName;
		this.team = builder.team;
	}

	/**
	 * Builder pattern for constructing Tile objects.
	 */
	public static class Builder {
		private boolean isRosette = false;
		private boolean isVisible = true;
		private boolean isStart = false;
		private boolean isFinish = false;
		private String textureName;
		private String team = "";

		/**
		 * Constructs a Builder with the specified texture name.
		 * 
		 * @param textureName the name of the texture associated with the tile
		 */
		public Builder(String textureName) {
			this.textureName = textureName;
		}

		/**
		 * Sets whether the tile is a rosette.
		 * 
		 * @param isRosette true if the tile is a rosette, false otherwise
		 * @return the Builder instance
		 */
		public Builder rosette(boolean isRosette) {
			this.isRosette = isRosette;
			return this;
		}

		/**
		 * Sets whether the tile is visible.
		 * 
		 * @param isVisible true if the tile is visible, false otherwise
		 * @return the Builder instance
		 */
		public Builder visible(boolean isVisible) {
			this.isVisible = isVisible;
			return this;
		}

		/**
		 * Sets whether the tile is a start tile.
		 * 
		 * @param isStart true if the tile is a start tile, false otherwise
		 * @return the Builder instance
		 */
		public Builder start(boolean isStart) {
			this.isStart = isStart;
			return this;
		}

		/**
		 * Sets whether the tile is a finish tile.
		 * 
		 * @param isFinish true if the tile is a finish tile, false otherwise
		 * @return the Builder instance
		 */
		public Builder finish(boolean isFinish) {
			this.isFinish = isFinish;
			return this;
		}

		/**
		 * Sets the team associated with the tile.
		 * 
		 * @param team the team associated with the tile
		 * @return the Builder instance
		 */
		public Builder team(String team) {
			this.team = team;
			return this;
		}

		/**
		 * Builds a Tile object based on the Builder's configuration.
		 * 
		 * @return the constructed Tile object
		 */
		public Tile build() {
			return new Tile(this);
		}
	}

	public boolean isRosette() {
		return isRosette;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public boolean isStart() {
		return isStart;
	}

	public boolean isFinish() {
		return isFinish;
	}

	public String getTextureName() {
		return textureName;
	}

	public String getTeam() {
		return team;
	}

	public String getPawn() {
		return pawn;
	}

	public void setPawn(String pawn) {
		this.pawn = pawn;
	}

	@Override
	public String toString() {
		return textureName;
	}
}
