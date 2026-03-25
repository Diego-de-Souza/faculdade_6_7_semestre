package com.mcsoftware.logisim.forev;


import java.util.Arrays;
import java.util.List;

import com.cburch.logisim.tools.Tool;
import com.cburch.logisim.tools.AddTool;
import com.cburch.logisim.tools.Library;
import com.cburch.logisim.tools.FactoryDescription;

/** The library of components that the user can access. */
public class ForEv extends Library {
    /** The list of all tools contained in this library. Technically,
     * libraries contain tools, which is a slightly more general concept
     * than components; practically speaking, though, you'll most often want
     * to create AddTools for new components that can be added into the circuit.
     */
	protected static final int DELAY = 5;
	
	private static FactoryDescription[] DESCRIPTIONS = {
		new FactoryDescription("Old D Flip-Flop", Strings.getter("dFlipFlopComponent"),
				"dFlipFlop.gif", "OldDFlipFlop"),
		new FactoryDescription("Old J-K Flip-Flop", Strings.getter("jkFlipFlopComponent"),
				"jkFlipFlop.gif", "OldJKFlipFlop"),
/*
		new FactoryDescription("Old Register", Strings.getter("registerComponent"),
				"register.gif", "OldRegister"),
		new FactoryDescription("Old Counter", Strings.getter("counterComponent"),
				"counter.gif", "OldCounter"),
		new FactoryDescription("Old RAM", Strings.getter("ramComponent"),
				"ram.gif", "OldRam"),
		new FactoryDescription("Old ROM", Strings.getter("romComponent"),
				"rom.gif", "OldRom"),
*/
	};

	private List<Tool> tools = null;
    
    /** Constructs an instance of this library. This constructor is how
     * Logisim accesses first when it opens the JAR file: It looks for
     * a no-arguments constructor method of the user-designated class.
     */
    public ForEv() {
        /* tools = Arrays.asList(new AddTool[] {
                new AddTool(Video2.factory),
                new AddTool(Video3.factory),
                new AddTool(Video4.factory),
        }); */
    }
    
    /** Returns the name of the library that the user will see. */ 
    public String getDisplayName() {
        return "ForEv";
    }
    
    /** Returns a list of all the tools available in this library. */
    public List<Tool> getTools() {
		if (tools == null) {
			tools = FactoryDescription.getTools(
ForEv.class, DESCRIPTIONS);
		}
		return tools;
    }
}
