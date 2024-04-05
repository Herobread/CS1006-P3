package rgou.model.dice;

import java.util.Random;

/**
 * Utility class for rolling dice.
 */
public class DiceRoller {
	// There are 6 dice states
	private static final int MIN_DICE_STATE = 0;
	private static final int MAX_DICE_STATE = 6 - 1;
	public static final int DICES_AMOUNT = 4;

	private static Random random = new Random();

	private static String[] diceRollTextures = {
			"dice/variants/dice-notop-left-up.png",
			"dice/variants/dice-notop-right-left.png",
			"dice/variants/dice-notop-up-right.png",
			"dice/variants/dice-top-left.png",
			"dice/variants/dice-top-right.png",
			"dice/variants/dice-top-up.png"
	};

	private static boolean[] winningRolls = {
			false,
			false,
			false,
			true,
			true,
			true
	};

	/**
	 * Rolls the specified amount of dice.
	 * 
	 * @return the result of rolling multiple dice
	 */
	public static DiceRollsResult roll() {
		DiceRollResult[] results = new DiceRollResult[DICES_AMOUNT];

		for (int i = 0; i < DICES_AMOUNT; i++) {
			results[i] = rollOne();
		}

		return new DiceRollsResult(results);
	}

	/**
	 * Rolls a single die.
	 * 
	 * @return the result of rolling one die
	 */
	public static DiceRollResult rollOne() {
		int roll = MIN_DICE_STATE + random.nextInt(MAX_DICE_STATE - MIN_DICE_STATE + 1);

		return customRollOne(roll);
	}

	/**
	 * Rolls a single die with a custom roll result.
	 * 
	 * @param roll the custom roll result
	 * @return the result of rolling one die with the custom roll result
	 */
	public static DiceRollResult customRollOne(int roll) {
		boolean isSuccessfullRoll = winningRolls[roll];
		String texture = diceRollTextures[roll];

		return new DiceRollResult(isSuccessfullRoll, texture, roll);
	}

	/**
	 * Creates a custom dice roll result based on input strings.
	 * 
	 * @param inputResults the input roll results as strings
	 * @return the custom dice roll result
	 */
	public static DiceRollsResult createCustomResult(String[] inputResults) {
		DiceRollResult[] results = new DiceRollResult[DICES_AMOUNT];

		for (int i = 0; i < DICES_AMOUNT; i++) {
			int roll = Integer.parseInt(inputResults[i]);
			results[i] = new DiceRollResult(winningRolls[roll], diceRollTextures[roll], roll);
		}
		return new DiceRollsResult(results);
	}
}
