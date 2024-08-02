import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;



/**
 * Class to test the functionality of the methods implemented from the Backend and Song Interfaces
 */
public class BackendDeveloperTests {
// TODO implement 5 JUnit test methods
// For backend developers: each of the 4 methods of the backend should be called at least once 
// across your set of test methods.
  
  private List<SongInterface> getSongs(String filename){
    
 // creates a file object, file reader, buffered reader, and arraylist to read and stores csv data
    //file path - TODO: may need to be changed when added to the Version Control!!!!!!
    String file = "C:\\Users\\Noahb\\eclipse-workspace\\CS400_P105_RoleCode\\src\\" + filename;
    //String file = filename; // for the VM
    List<SongInterface> toReturn = new ArrayList<>();
    try {
    FileReader fr = new FileReader(file);
    BufferedReader br = new BufferedReader(fr); // buffered reader for the csv file
    List<String> songData = null; // the current song data
    
    // current line in csv file
    String song = br.readLine();  
    
    while (song != null) {
      if (song != null)
        // uses a regex to ignore all commands between quotes
        songData = Arrays.asList(song.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1));
        // replaces the leftover quotes in song titles with blanks
        songData.set(0, songData.get(0).replace("\"", ""));
        
        // prevents adding the column headers as a song 
        if (songData.get(0).equals("title") == false)
          // adds the song's data to the List as an object type Song
          toReturn.add(new Song(songData));
        
      // moves on to the next line
      song = br.readLine();
    }
    br.close();
    } catch (IOException e) {}
    
    
    return toReturn;
  }
 
  /**
   * Verifies the functionality of the readData() method 
   * @return true if all tests are passed
   */
  @Test
  public void TestReadData() {
    // creates BackendImplementation object  
    // creates a tree object
    IterableRedBlackTree<SongInterface> tree = new IterableRedBlackTree<SongInterface>();
    BackendImplementation tester = new BackendImplementation(tree);
    // verifies that calling readData with a valid file does not throw an exception 
    try {    
      tester.readData("songs.csv");
    } catch (IOException e) {
      Assertions.fail("exception was thrown");
    }
    // verifies that data is no longer empty
    Assertions.assertEquals(false, tester.data.isEmpty(), "tree is empty");
    
    // verifies that the size of the tree is corect
    Assertions.assertEquals(600, tester.data.size(), "tree is the wrong size");
    
    // Verifies that the data in the tree is sorted by energy
    int prevEnergy = 0;
    for (SongInterface song:tester.data) {
      Assertions.assertEquals(true, song.getEnergy() >= prevEnergy, "not in sorted order");
      prevEnergy = song.getEnergy();
    } 
    
    // verifies that the method throws an IOException when there is trouble reading/finding the file
    try {
      tester.readData("random.csv");
      Assertions.fail("exception was not thrown");
    } catch (IOException e) { }
    // creates a Song object for "Hey, Soul Sister" to check if the data tree contains it
    String[] info = {"Hey, Soul Sister", "Train", "neo mellow","2010","97","89","67","-4","8",
        "80","217","19","4","83"};
    List<String> songInfo = Arrays.asList(info);
    SongInterface toCheck = new Song(songInfo);
    
    // checks if this song is contained in the tree
    Assertions.assertEquals(true, tester.data.contains(toCheck), "song is not in tree");
    
    // confirms that the tree contains all songs from the csv file
    List<SongInterface> toTest = getSongs("songs.csv");
    for (SongInterface song:toTest) 
      Assertions.assertEquals(true, tester.data.contains(song), "does not contain all songs");
  }
  
  /**
   * Verifies the functionality of the getRange() method
   * @return true if all tests pass
   */
  @Test
  public void TestGetRange() {
    
    // creates BackendImplementation object and calls readData()
    IterableRedBlackTree<SongInterface> tree = new IterableRedBlackTree<SongInterface>();
    BackendImplementation tester = new BackendImplementation(tree);
    try {
      tester.readData("songs.csv");
    } catch (IOException e) { }
    
    // saves the songs that have an energy level between 90 and 100
    List<String> results = tester.getRange(44, 100);
    tester.filterFastSongs(150);
    // confirms that the first song in the List is the first song with an energy of 89
    
    // Maybe use iterator and contains??????
    Assertions.assertEquals(true, results.contains("Kills You Slowly"), "wrong song");
    
    /*
    // verifies that the method works when low == high
    results = tester.getRange(94, 94);
    if (results.get(0).equals("I Like It") == false)
      return false;
    */
    // verifies that the method returns an empty list if low > high
    results = tester.getRange(95, 94);
    Assertions.assertEquals(true, results.isEmpty(), "list is not empty");
    
    /*
    // Tests with filter
    
    // tests that the first song with at least 129 BPM an 94 energy is 'I Like It'
    tester.getRange(94, 100);
    results = tester.filterFastSongs(129);
    if (results.get(0).equals("I Like It") == false)
      return false;
    
    // tests that getRange with a filter too high returns an empty list
    tester.getRange(94, 100);
    results = tester.filterFastSongs(300);
    if (results.isEmpty() == false)
      return false;
    
    // verifies that the method returns an empty list if low > high and there is a range set
    results = tester.getRange(95, 94);
    results = tester.filterFastSongs(100);
    if (results.isEmpty() == false)
      return false;
      */
  }
  
  /**
   * Verifies the functionality of the filterFastSongs() method
   * @return true if all tests pass
   */
  @Test
  public void TestFilterFastSongs() {
    // creates BackendImplementation object and loads data
    IterableRedBlackTree<SongInterface> tree = new IterableRedBlackTree<SongInterface>();
    BackendImplementation tester = new BackendImplementation(tree);
    try {
      tester.readData("songs.csv");
    } catch (IOException e) { }

    List<String> results = tester.filterFastSongs(100);
    // ensures that if GetRange() has not been called that this method returns an empty List
    Assertions.assertEquals(true, results.isEmpty(), "list is not empty");
    
    // calls the get range method so that results will not be null
    tester.getRange(44, 100);
    results = tester.filterFastSongs(150);
    
    // verifies that the first song is results is within the energy range and minimum BPLM
    Assertions.assertEquals(true, results.get(0).equals("Kills You Slowly"), "wrong first song");
    /* 
    // verifies that the last song in the list has the minSpeed and the highest energy
    if (results.get(results.size()-1).equals("Pom Poms") == false)
      return false;
   */
  }
  
  /**
   * Verifies the functionality of the fiveMostDanceable() method
   * @return true if all tests pass
   */
  @Test
  public void TestFiveMostDanceable() {
    IterableRedBlackTree<SongInterface> tree = new IterableRedBlackTree<SongInterface>();
    BackendImplementation tester = new BackendImplementation(tree);
    try {
      tester.readData("songs.csv");
    } catch (IOException e) { }
    
    tester.getRange(44, 100);
    tester.filterFastSongs(150);
    
    List<String> results = tester.fiveMostDanceable();
    /*
    if (results.get(0).equals("41: Bad Liar") == false)
      return false;
    else if (results.get(1).equals("59: Drip (feat. Migos)") == false)
      return false;
    */
    
    Assertions.assertEquals(true, results.contains("75: Stitches"), "wrong song");
    /*
    if (results.get(2).equals("44: Kills You Slowly") == false)
      return false;
    else if (results.get(3).equals("44: Come Get It Bae") == false)
      return false;
    else if (results.get(4).equals("82: WTF (Where They From)") == false)
      return false;
    */
  }
  
  /**
   * Verifies the functionality of the Song class
   * @return true if all tests pass
   */
  @Test
  public void TestSong() {
    IterableRedBlackTree<SongInterface> tree = new IterableRedBlackTree<SongInterface>();
    BackendImplementation tester = new BackendImplementation(tree);
    
    try {
      tester.readData("songs.csv");
    } catch (IOException e) { }
    // gets song objects to test with
    
    // isn't gonna work with the real implementation
    SongInterface toTest = tester.data.iterator().next();
    for (SongInterface song: tester.data) {
      toTest = song;
    }
        
    // test getTitle()
    Assertions.assertEquals(true, toTest.getTitle().equals("Hello"), "wrong song");
    // test getArtist()
    Assertions.assertEquals(true, toTest.getArtist().equals("Martin Solveig"), "wrong artist");
    // test gitGenres()
    Assertions.assertEquals(true, toTest.getGenres().equals("big room"), "wrong genres");
    // test getYear()
    Assertions.assertEquals(true, toTest.getYear() == 2010, "wrong year");
    // test getBPM()
    Assertions.assertEquals(true, toTest.getBPM() == 128, "wrong speed");
    // test getEnergy()
    Assertions.assertEquals(true, toTest.getEnergy() == 98, "wrong energy");
    // test getDanceability()
    Assertions.assertEquals(true, toTest.getDanceability() == 67,"wrong danceability");
    // test getLoudness()
    Assertions.assertEquals(true, toTest.getLoudness() == -3, "wrong loudness");
    // test getLiveness()
    Assertions.assertEquals(true, toTest.getLiveness() == 10, "wrong liveness");   
    /*
    // Tests the functionality of the compareTo method 
    if (toTest.compareTo(toCompare) >= 0)
      return false;
    
    if (toCompare.compareTo(toTest) <= 0)
      return false;
    
    if (toCompare.compareTo(toCompare) != 0)
      return false;
    */
  }
    
  /**
   * Tester method to test the readFile and getData frontend methods with backend implementation
   */
  @Test
  public void IntegrationTest1() {
    // creates the proper objects for a tester frontend test
    IterableRedBlackTree<SongInterface> temp = new IterableRedBlackTree<SongInterface>(); // creates a PC object
    BackendImplementation backend = new BackendImplementation(temp);
    // creates a TextUITester object to tests the readFile and getData methods
    TextUITester texter = new TextUITester("R\nsongs.csv\nG\n44-100");
    // creates the frontend object
    FrontendInterface tester = new Frontend(new Scanner(System.in),backend);

    // runs the command loop
    tester.runCommandLoop();
    // stores the output of the method
    String output = texter.checkOutput();
    // verifies that the output is what was expected (for readFile)
    Assertions.assertEquals(true,output.contains("Done reading file."), "bad output"); 
    // Tests that the getData method works 
    Assertions.assertEquals(true, output.contains("Kills You Slowly"), "bad song");
  }
  
  
  /**
   * Tester method to test the setFilter and topFive frontend methods with backend implementation
   */
  @Test
  public void IntegrationTest2() {
    // creates the proper objects for a tester frontend test
    IterableRedBlackTree<SongInterface> temp = new IterableRedBlackTree<SongInterface>(); // creates a PC object
    BackendImplementation backend = new BackendImplementation(temp);
    // creates a new TextUITester Object to hold output text
    TextUITester texter = new TextUITester("R\nsongs.csv\nG\n44-100\nF\n150\nD");
    // creates the frontend object to test with
    FrontendInterface tester = new Frontend(new Scanner(System.in),backend);
    
    // runs the command loop
    tester.runCommandLoop();
    // stores the output of the method
    String output = texter.checkOutput();
    // verifies that the output is what was expected
    Assertions.assertEquals(true,output.contains("Kills You Slowly"), "bad output");
    // tests that the output is what was expected
    Assertions.assertEquals(true, output.contains("75: Stitches"), "bad song");
  }

  /**
   * Tester method to test the readFile and getData frontend methods with backend placeholder
   */
  @Test
  public void PartnerTest1() {
    // creates the proper objects for a tester frontend objects wtih placeholders
    ISCPlaceholder<SongInterface> temp = new ISCPlaceholder<SongInterface>(); // creates a PC object
    BackendInterface backend = new BackendPlaceholder(temp);
    // creates a TextUITester object to tests the readFile and getData methods
    TextUITester texter = new TextUITester("R\nsongs.csv\nG\n44-100");
    // creates the frontend object
    FrontendInterface tester = new Frontend(new Scanner(System.in),backend);

    // runs the command loop
    tester.runCommandLoop();
    // stores the output of the method
    String output = texter.checkOutput();
    // verifies that the output is what was expected (for readFile)
    Assertions.assertEquals(true,output.contains("Done reading file."), "bad output"); 
    // Tests that the getData method works 
    Assertions.assertEquals(true, output.contains("Hey, Soul Sister"), "bad song");
  }
  
  /**
   * Tester method to test the setFilter and topFive frontend methods with backend placeholder
   */
  @Test
  public void PartnerTest2() {
    // creates the proper objects for a tester frontend object with placeholders
    ISCPlaceholder<SongInterface> temp = new ISCPlaceholder<SongInterface>(); // creates a PC object
    BackendInterface backend = new BackendPlaceholder(temp);
    // creates a new TextUITester Object to hold output text
    TextUITester texter = new TextUITester("R\nsongs.csv\nG\n44-100\nF\n150\nD");
    // creates the frontend object to test with
    FrontendInterface tester = new Frontend(new Scanner(System.in),backend);
    
    // runs the command loop
    tester.runCommandLoop();
    // stores the output of the method
    String output = texter.checkOutput();
    // verifies that the output is what was expected
    Assertions.assertEquals(true,output.contains("Hey, Soul Sister"), "bad output");
    // tests that the output is what was expected
    Assertions.assertEquals(true, output.contains("-4: Hey, Soul Sister"), "bad song");
  }
}
