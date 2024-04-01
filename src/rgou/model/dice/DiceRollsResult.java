package rgou.model.dice;

public class DiceRollsResult {
	private DiceRollResult[] diceRollResults;
	private int total = 0;

	public DiceRollsResult(DiceRollResult[] diceRollResults) {
		this.diceRollResults = diceRollResults;

		// calculate total dice score
		for (DiceRollResult diceRollResult : diceRollResults) {
			total += diceRollResult.isWin() ? 1 : 0;
		}
	}

	public DiceRollResult[] getDiceRollResults() {
		return diceRollResults;
	}

	public int getTotal() {
		return total;
	}
}
