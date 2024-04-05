package rgou.model.dice;

/**
 * Represents the result of rolling multiple dice.
 */
public class DiceRollsResult {
	private DiceRollResult[] diceRollResults;
	private int total = 0;

	/**
	 * Constructs a DiceRollsResult with the specified array of dice roll results.
	 * 
	 * @param diceRollResults the array of dice roll results
	 */
	public DiceRollsResult(DiceRollResult[] diceRollResults) {
		this.diceRollResults = diceRollResults;

		// Calculate total dice score
		for (DiceRollResult diceRollResult : diceRollResults) {
			total += diceRollResult.isWin() ? 1 : 0;
		}
	}

	/**
	 * Gets the array of dice roll results.
	 * 
	 * @return the array of dice roll results
	 */
	public DiceRollResult[] getDiceRollResults() {
		return diceRollResults;
	}

	/**
	 * Gets the total score of the dice rolls.
	 * 
	 * @return the total score
	 */
	public int getTotal() {
		return total;
	}

	///////////////////// Networking

	/**
	 * Converts the dice rolls result to an action string for networking.
	 * 
	 * @return the action string representation of the dice rolls result
	 */
	public String toActionString() {
		StringBuilder result = new StringBuilder("roll,");

		String[] numbers = new String[diceRollResults.length];
		for (int i = 0; i < diceRollResults.length; i++) {
			numbers[i] = "" + diceRollResults[i].getId();
		}
		result.append(String.join("|", numbers));
		return result.toString();
	}

	/**
	 * Constructs a DiceRollsResult from an action string received over the network.
	 * 
	 * @param actionString the action string received over the network
	 */
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
