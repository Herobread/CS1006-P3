package rgou.view.components.ui;

import javax.swing.JPanel;

import rgou.view.components.primitives.ImageBox;
import rgou.view.components.primitives.ImageButton;
import rgou.view.components.primitives.RenderScaleContext;

public class DicePanel extends JPanel {
	public DicePanel(RenderScaleContext renderScaleContext) {
		setLayout(null);
		setOpaque(false);

		ImageBox number = new ImageBox("dice/numbers/1.png");
		number.setBounds(renderScaleContext.scaleRectangle(39, 0, 32, 42));
		add(number);

		ImageBox dice1 = new ImageBox("dice/variants/dice-notop-left-up.png");
		dice1.setBounds(renderScaleContext.scaleRectangle(0, 57, 20, 19));
		add(dice1);

		ImageBox dice2 = new ImageBox("dice/variants/dice-notop-left-up.png");
		dice2.setBounds(renderScaleContext.scaleRectangle(30, 57, 20, 19));
		add(dice2);

		ImageBox dice3 = new ImageBox("dice/variants/dice-notop-left-up.png");
		dice3.setBounds(renderScaleContext.scaleRectangle(60, 57, 20, 19));
		add(dice3);

		ImageBox dice4 = new ImageBox("dice/variants/dice-notop-left-up.png");
		dice4.setBounds(renderScaleContext.scaleRectangle(90, 57, 20, 19));
		add(dice4);

		ImageButton rollButton = new ImageButton("buttons/roll.png");
		rollButton.setBounds(renderScaleContext.scaleRectangle(34, 91, 42, 20));
		add(rollButton);
	}
}
