package com.mcsoftware.logisim.forev;


import java.util.Arrays;
import java.util.List;

import com.cburch.logisim.tools.AddTool;
import com.cburch.logisim.tools.Library;

/** The library of components that the user can access. */
public class ForEv extends Library {
    /** The list of all tools contained in this library. Technically,
     * libraries contain tools, which is a slightly more general concept
     * than components; practically speaking, though, you'll most often want
     * to create AddTools for new components that can be added into the circuit.
     */
	protected static final int DELAY = 5;

    private List<AddTool> tools;
    
    /** Constructs an instance of this library. This constructor is how
     * Logisim accesses first when it opens the JAR file: It looks for
     * a no-arguments constructor method of the user-designated class.
     */
    public ForEv() {
        tools = Arrays.asList(new AddTool[] {
                new AddTool(Video2.factory),
                new AddTool(Video3.factory),
                new AddTool(Video4.factory),
                new AddTool(new OldDFlipFlop()),
                new AddTool(new OldJKFlipFlop()),
                new AddTool(new OldSRFlipFlop()),
                new AddTool(new OldTFlipFlop()),
                new AddTool(new OldCounter()),
                new AddTool(new OldRegister()),
                new AddTool(new OldRam()),
                new AddTool(new OldRom()),
                new AddTool(new OldRandom()),
                new AddTool(new OldShiftRegister()),
        });
    }
    
    /** Returns the name of the library that the user will see. */ 
    public String getDisplayName() {
        return "MCSoftware Logisim 2.7.1 Components";
    }
    
    /** Returns a list of all the tools available in this library. */
    public List<AddTool> getTools() {
        return tools;
    }

	@Override
	public boolean removeLibrary(String name) { return false; }
}
