op.class: op.java Subset.class
	javac op.java

Subset.class: Subset.java
	javac Subset.java

.PHONY: clean
clean:
	rm -f *.class

.PHONY: all
all: clean op.class
