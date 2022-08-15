#Makefile
#Muzerengwa Vincent MZRVIN001
#10/08/2022

CC = javac
CLAGS = -g -O1

SRC_DIR = src
BIN_DIR = bin

.SUFFIXES: .java .class

$(BIN_DIR)/%.class : $(SRC_DIR)/%.java
	$(JC) -d $(BIN_DIR)/.$(JFLAGS) $(SRC_DIR)/*.java

CLASSES: bin/MeanFilterSerial.class bin/MedianFilterSerial.class \
		bin/MedianFilterParallel.class bin/MeanFilterParallel.class 
		

default: $(CLASSES)

clean:
	rm bin/*.class
	rm filtered_images/*

