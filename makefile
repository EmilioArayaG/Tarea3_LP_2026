JAVAC = javac
SRC = $(shell find . -name "*.java")
OUT = out

all:
	mkdir -p $(OUT)
	$(JAVAC) -d $(OUT) $(SRC)

run: all
	java -cp $(OUT) Main

clean:
	rm -rf $(OUT)
