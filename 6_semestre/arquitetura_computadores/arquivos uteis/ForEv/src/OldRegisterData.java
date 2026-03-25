/* Copyright (c) 2010, Carl Burch. License information is located in the
 * com.cburch.logisim.Main source code and at www.cburch.com/logisim/. */

package com.mcsoftware.logisim.forev;

import com.cburch.logisim.instance.InstanceData;

class OldRegisterData extends OldClockState implements InstanceData {
	int value;

	public OldRegisterData() {
		value = 0;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
