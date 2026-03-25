/* Copyright (c) 2010, Carl Burch. License information is located in the
 * com.cburch.logisim.Main source code and at www.cburch.com/logisim/. */

package com.mcsoftware.logisim.forev;

import java.util.Arrays;
import java.util.List;
import java.util.WeakHashMap;

import com.cburch.logisim.data.AbstractAttributeSet;
import com.cburch.logisim.data.Attribute;
import com.cburch.logisim.data.BitWidth;
import com.cburch.logisim.gui.hex.HexFrame;
import com.cburch.logisim.proj.Project;

class OldRomAttributes extends AbstractAttributeSet {
	private static List<Attribute<?>> ATTRIBUTES = Arrays.asList(new Attribute<?>[] {
			OldMem.ADDR_ATTR, OldMem.DATA_ATTR, OldRom.CONTENTS_ATTR
		});
	
	private static WeakHashMap<OldMemContents,OldRomContentsListener> listenerRegistry
		= new WeakHashMap<OldMemContents,OldRomContentsListener>();
	private static WeakHashMap<OldMemContents,HexFrame> windowRegistry
		= new WeakHashMap<OldMemContents,HexFrame>();

	static void register(OldMemContents value, Project proj) {
		if (proj == null || listenerRegistry.containsKey(value)) return;
		OldRomContentsListener l = new OldRomContentsListener(proj);
		value.addHexModelListener(l);
		listenerRegistry.put(value, l);
	}
	
	static HexFrame getHexFrame(OldMemContents value, Project proj) {
		synchronized(windowRegistry) {
			HexFrame ret = windowRegistry.get(value);
			if (ret == null) {
				ret = new HexFrame(proj, value);
				windowRegistry.put(value, ret);
			}
			return ret;
		}
	}

	private BitWidth addrBits = BitWidth.create(8);
	private BitWidth dataBits = BitWidth.create(8);
	private OldMemContents contents;
	
	OldRomAttributes() {
		contents = OldMemContents.create(addrBits.getWidth(), dataBits.getWidth());
	}
	
	void setProject(Project proj) {
		register(contents, proj);
	}
	
	@Override
	protected void copyInto(AbstractAttributeSet dest) {
		OldRomAttributes d = (OldRomAttributes) dest;
		d.addrBits = addrBits;
		d.dataBits = dataBits;
		d.contents = contents.clone();
	}
	
	@Override
	public List<Attribute<?>> getAttributes() {
		return ATTRIBUTES;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <V> V getValue(Attribute<V> attr) {
		if (attr == OldMem.ADDR_ATTR) return (V) addrBits;
		if (attr == OldMem.DATA_ATTR) return (V) dataBits;
		if (attr == OldRom.CONTENTS_ATTR) return (V) contents;
		return null;
	}
	
	@Override
	public <V> void setValue(Attribute<V> attr, V value) {
		if (attr == OldMem.ADDR_ATTR) {
			addrBits = (BitWidth) value;
			contents.setDimensions(addrBits.getWidth(), dataBits.getWidth());
		} else if (attr == OldMem.DATA_ATTR) {
			dataBits = (BitWidth) value;
			contents.setDimensions(addrBits.getWidth(), dataBits.getWidth());
		} else if (attr == OldRom.CONTENTS_ATTR) {
			contents = (OldMemContents) value;
		}
		fireAttributeValueChanged(attr, value,null);
	}
}
