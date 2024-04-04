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

	///////////////////// networking

	public String toActionString() {
		StringBuilder result = new StringBuilder("roll,");

		String[] numbers = new String[diceRollResults.length];
		for (int i = 0; i < diceRollResults.length; i++) {
			numbers[i] = "" + diceRollResults[i].getId();
		}
		result.append(String.join("|", numbers));
		return result.toString();
	}

	// Constructor to deconstruct action string
	public DiceRollsResult(String actionString) {
		if (actionString.startsWith("roll,")) {
			String[] parts = actionString.substring(5).split("\\|");
			this.diceRollResults = new DiceRollResult[parts.length];
			for (int i = 0; i < parts.length; i++) {
				int id = Integer.parseInt(parts[i]);
				this.diceRollResults[i] = DiceRoller.customRollOne(id);
				this.total += this.diceRollResults[i].isWin() ? 1 : 0;
			}
		} else {
			throw new IllegalArgumentException("Invalid action string format");
		}
	}
}
