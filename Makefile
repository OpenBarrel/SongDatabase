runApp: App.class BackendImplementation.class Frontend.class
	java App
App.class: App.java
	javac App.java
runBDTests: BackendDeveloperTests.class
	java -jar ../junit5.jar -cp . -c BackendDeveloperTests
BackendDeveloperTests.class: BackendDeveloperTests.java BackendImplementation.class
	javac -cp .:../junit5.jar BackendDeveloperTests.java
BackendImplementation.class: BackendImplementation.java Song.class IterableRedBlackTree.class
	javac BackendImplementation.java
IterableRedBlackTree.class: IterableRedBlackTree.java
	javac BinarySearchTree.java
	javac RedBlackTree.java
	javac IterableRedBlackTree.java
Song.class: Song.java
	javac Song.java
Frontend.class: Frontend.java
	javac Frontend.java
runFDTests: FrontendDeveloperTests.java
	javac -cp .:../junit5.jar FrontendDeveloperTests.java
	java -jar ../junit5.jar -cp . -c FrontendDeveloperTests
clean: *.class
	rm *.class
