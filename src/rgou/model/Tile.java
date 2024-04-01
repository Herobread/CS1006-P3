package rgou.model;

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

	public static class Builder {
		private boolean isRosette = false;
		private boolean isVisible = true;
		private boolean isStart = false;
		private boolean isFinish = false;
		private String textureName;
		private String team = "";

		public Builder(String textureName) {
			this.textureName = textureName;
		}

		public Builder rosette(boolean isRosette) {
			this.isRosette = isRosette;
			return this;
		}

		public Builder visible(boolean isVisible) {
			this.isVisible = isVisible;
			return this;
		}

		public Builder start(boolean isStart) {
			this.isStart = isStart;
			return this;
		}

		public Builder finish(boolean isFinish) {
			this.isFinish = isFinish;
			return this;
		}

		public Builder team(String team) {
			this.team = team;
			return this;
		}

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
