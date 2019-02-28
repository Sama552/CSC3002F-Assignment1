# makefile author: Joseph Anderson <jtanderson@ratiocaeli.com>

# This is where you specify the necessary source files

# Program packages and files
#   - The packages should be the path inside your src directory. eg: package1 package2/package3
PACKAGES =

# Java compiler
JAVAC = javac
JVM =

# Directory for compiled binaries
# - trailing slash is important!
BIN = ./bin/

# Directory of source files
# - trailing slash is important!
SRC = ./src/

# Directory of doc files
# - trailing slash is important!
DOC = ./doc/

# Java compiler flags
JAVAFLAGS = -g -d $(BIN) -cp $(SRC)

# Creating a .class file
COMPILE = $(JAVAC) $(JAVAFLAGS)

EMPTY =

JAVA_FILES = $(subst $(SRC), $(EMPTY), $(wildcard $(SRC)*.java))

ifdef PACKAGES
PACKAGEDIRS = $(addprefix $(SRC), $(PACKAGES))
PACKAGEFILES = $(subst $(SRC), $(EMPTY), $(foreach DIR, $(PACKAGEDIRS), $(wildcard $(DIR)/*.java)))
ALL_FILES = $(PACKAGEFILES) $(JAVA_FILES)
else
#ALL_FILES = $(wildcard $(SRC).java)
ALL_FILES = $(JAVA_FILES)
endif

# One of these should be the "main" class listed in Runfile
# CLASS_FILES = $(subst $(SRC), $(BIN), $(ALL_FILES:.java=.class))
CLASS_FILES = $(ALL_FILES:.java=.class)

# The first target is the one that is executed when you invoke
# "make".

all : $(addprefix $(BIN), $(CLASS_FILES))

bin/Client_1.class: bin/Client.class
	$(COMPILE) src/Client_1.java

bin/Client_2.class: bin/Client.class
	$(COMPILE) src/Client_1.java

bin/Client_3.class: bin/Client.class
	$(COMPILE) src/Client_1.java

bin/Client.class:
	$(COMPILE) src/Client.java

run : all
	java -cp ./bin/ Client_1

clean :
	rm -rf $(BIN)*.class $(DOC)*
