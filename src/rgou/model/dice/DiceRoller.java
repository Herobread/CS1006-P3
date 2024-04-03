package rgou.model.dice;

import java.util.Random;

public class DiceRoller {
	// there are 6 dice states
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

	public static DiceRollsResult roll() {
		DiceRollResult[] results = new DiceRollResult[DICES_AMOUNT];

		for (int i = 0; i < DICES_AMOUNT; i++) {
			results[i] = rollOne();
		}

		return new DiceRollsResult(results);
	}

	public static DiceRollResult rollOne() {
		int roll = MIN_DICE_STATE + random.nextInt(MAX_DICE_STATE - MIN_DICE_STATE + 1);
		boolean isSuccessfullRoll = winningRolls[roll];
		String texture = diceRollTextures[roll];

		return new DiceRollResult(isSuccessfullRoll, texture, roll);
	}

	public static DiceRollsResult createCustomResult (String[] inputResults){
		DiceRollResult[] results = new DiceRollResult[DICES_AMOUNT];

		for (int i = 0; i < DICES_AMOUNT; i++){
			int roll = Integer.parseInt(inputResults[i]);
			results[i] = new DiceRollResult(winningRolls[roll], diceRollTextures[roll], roll);
		}
		return new DiceRollsResult(results);
	}
}
