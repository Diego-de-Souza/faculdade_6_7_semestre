/* Copyright (c) 2010, Carl Burch. License information is located in the
 * com.cburch.logisim.Main source code and at www.cburch.com/logisim/. */

package com.mcsoftware.logisim.forev;

import com.cburch.logisim.data.Value;

public class OldDFlipFlop extends OldAbstractFlipFlop {
	public OldDFlipFlop() {
		super("Old D Flip-Flop", "dFlipFlop.gif",
				Strings.getter("dFlipFlopComponent"), 1, true);
	}

	@Override
	protected String getInputName(int index) {
		return "D";
	}

	@Override
	protected Value computeValue(Value[] inputs, Value curValue) {
		return inputs[0];
	}
}
