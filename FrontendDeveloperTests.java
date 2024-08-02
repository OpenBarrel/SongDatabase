import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

public class FrontendDeveloperTests {
  
  //test readFile method when user enter valid input
  @Test
  void testReadFile() {
    TextUITester tester = new TextUITester("songs.csv\n");
    IterableSortedCollection<SongInterface> tree = new ISCPlaceholder<>(); 
    BackendInterface backend = new BackendPlaceholder(tree);
    Frontend front = new Frontend(new Scanner(System.in),backend);
    front.readFile();
    String output = tester.checkOutput();
    assertTrue(output.startsWith("Please enter the name of the file:") && output.contains("songs.csv"));
  }
  
  //test getValues method when user enter valid input
  @Test
  void testGetValues() {
    TextUITester tester = new TextUITester("70 - 80\n");
    IterableSortedCollection<SongInterface> tree = new ISCPlaceholder<>(); 
    BackendInterface backend = new BackendPlaceholder(tree);
    Frontend front = new Frontend(new Scanner(System.in),backend);
    front.getValues();
    String output = tester.checkOutput();
    assertTrue(output.contains("songs found between 70 - 80: ")&&output.contains("Just the Way You Are"));
  }
  
  //test setFilter method when user enter valid input
  @Test
  void testSetFilter() {
    TextUITester tester = new TextUITester("100\n");
    IterableSortedCollection<SongInterface> tree = new ISCPlaceholder<>(); 
    BackendInterface backend = new BackendPlaceholder(tree);
    Frontend front = new Frontend(new Scanner(System.in),backend);
    front.setFilter();
    String output = tester.checkOutput();
    assertTrue(output.startsWith("Please enter min speed: ") && output.contains("2 songs found between min - max at 100 BPM or faster: "));
    assertTrue(output.contains("Love The Way You Lie"));
  }
  
  //test topFive method when user enter valid input
  @Test
  void testTopFive() {
    TextUITester tester = new TextUITester("");
    IterableSortedCollection<SongInterface> tree = new ISCPlaceholder<>(); 
    BackendInterface backend = new BackendPlaceholder(tree);
    Frontend front = new Frontend(new Scanner(System.in),backend);
    front.topFive();
    String output = tester.checkOutput();
    assertTrue(output.contains("-5: Love The Way You Lie"));
  }
  
  //test setFilter method when user enter invalid input
  @Test
  void testSetFilterError() {
    TextUITester tester = new TextUITester("");
    IterableSortedCollection<SongInterface> tree = new ISCPlaceholder<>(); 
    BackendInterface backend = new BackendPlaceholder(tree);
    Frontend front = new Frontend(new Scanner(System.in),backend);
    front.setFilter();
    String output = tester.checkOutput();
    assertTrue(output.contains("Invalid speed"));
  }
  
  //test getValues method when user enter invalid input
  @Test
  void testGetValuesError() {
    TextUITester tester = new TextUITester("x - y\n");
    IterableSortedCollection<SongInterface> tree = new ISCPlaceholder<>(); 
    BackendInterface backend = new BackendPlaceholder(tree);
    Frontend front = new Frontend(new Scanner(System.in),backend);
    front.getValues();
    String output = tester.checkOutput();
    assertTrue(output.contains("Invalid range"));
  }
  
}
