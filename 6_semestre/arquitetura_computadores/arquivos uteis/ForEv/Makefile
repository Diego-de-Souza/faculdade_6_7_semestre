LOGISIM_JAR_FILE = c:\logisim\logisim-evolution-cornell.jar
#LOGISIM_JAR_FILE = c:\logisim\logisim-evolution.jar
#LOGISIM_JAR_FILE = ..\logisim-generic-2.7.1.jar
MANIFEST_FILE = MANIFEST.MF
JAR_FILE = ForEv.jar
BIN_DIR = .\bin
SRC = src\ForEv.java \
	src\SimpleStringGetter.java \
	src\Strings.java \
	src\Video2.java \
	src\Video3.java \
	src\Video4.java \
	src\OldAbstractFlipFlop.java \
	src\OldDFlipFlop.java \
	src\OldJKFlipFlop.java \
	src\OldClockState.java \
	src\OldCounter.java \
	src\OldCounterAttributes.java \
	src\OldRegister.java \
	src\OldRegisterData.java \
	src\OldRegisterLogger.java \
	src\OldRegisterPoker.java \
	src\OldMem.java \
	src\OldMemContents.java \
	src\OldMemContentsSub.java \
	src\OldMemMenu.java \
	src\OldMemPoker.java \
	src\OldMemState.java \
	src\OldRam.java \
	src\OldRom.java \
	src\OldRomAttributes.java \
	src\OldRomContentsListener.java \
	src\oldrandom.java \
	src\oldshiftregister.java \
	src\oldshiftregisterdata.java \
	src\oldshiftregisterlogger.java \
	src\oldshiftregisterpoker.java \
	src\oldsrflipflop.java \
	src\oldtflipflop.java

jar: classes
	jar cmf $(MANIFEST_FILE) $(JAR_FILE) -C $(BIN_DIR) . src resources Makefile MANIFEST.MF README

classes:
	javac -nowarn -d $(BIN_DIR) -classpath $(LOGISIM_JAR_FILE) $(SRC)

clean:
	rm -rf $(BIN_DIR)

