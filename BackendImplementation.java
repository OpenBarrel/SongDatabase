import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Class to implement the functions of several backend functions for a song app
 */
public class BackendImplementation implements BackendInterface{

  public IterableSortedCollection<SongInterface> data;
  private int minSpeed = 0;
  private int[] energyRange;
  
  public BackendImplementation(IterableSortedCollection<SongInterface> tree) {
    // what do I do with this
    // storing the value of the tree passed in as an argument
    data = tree;
  }
  
  /**
   * Loads data from the .csv file referenced by filename.
   * @param filename is the name of the csv file to load data from
   * @throws IOException when there is trouble finding/reading file
   */
  @Override
  public void readData(String filename) throws IOException {
    
    // creates a file object, file reader, buffered reader, and arraylist to read and stores csv data
    //file path - TODO: may need to be changed when added to the Version Control!!!!!!
    //String file = "C:\\Users\\Noahb\\eclipse-workspace\\CS400_P105_RoleCode\\src\\" + filename;
    String file = filename; // for the VM
    FileReader fr = new FileReader(file);
    BufferedReader br = new BufferedReader(fr); // buffered reader for the csv file
    List<String> songData = null; // the current song data
    // current line in csv file
    String song = br.readLine();  
    
    while (song != null) {
        // uses a regex to ignore all commands between quotes
        songData = Arrays.asList(song.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1));
        // replaces the leftover quotes in song titles with blanks
        songData.set(0, songData.get(0).replace("\"", ""));
        
        // prevents adding the column headers as a song 
        if (songData.get(0).equals("title") == false) {
          // adds the song's data to the List as an object type Song
          data.insert(new Song(songData));
        }
        // moves on to the next line 
      song = br.readLine();

    }
    br.close();
  }

  /**
   * Retrieves a list of song titles for songs that have an Energy rating
   * within the specified range (sorted by Energy in ascending order).  If 
   * a minSpeed filter has been set using filterFastSongs(), then only songs
   * with speeds greater than or equal to minSpeed should be included in the 
   * list that is returned by this method.
   *
   * Note that either this energy range, or the resulting unfiltered list
   * of songs should be saved for later use by the other methods defined in 
   * this class.
   *
   * @param low is the minimum Energy rating of songs in the returned list
   * @param hight is the maximum Energy rating of songs in the returned list
   * @return List of titles for all songs in specified range 
   */
  @Override
  public List<String> getRange(int low, int high) {  // MAYBE UES ITERATOR AND STARTING POINT 
    // saves the range    
    if (energyRange != null) {
      energyRange[0] = low;
      energyRange[1] = high;
    } else
      energyRange = new int[]{low,high};
    
    // arrayList of sorted results
    List<String> results = new ArrayList<>();
    
    // returns empty list if the low is greater than high
    if (low > high)
      return results;
    
      // for loop through each song in csv file
      for (SongInterface song:data) { // changes the type here to SongInterface and data
        // if the song has the required energy level and is at or above minspeed, add it to list
        // the list should already be sorted in ascending order for energy!!
        if (song.getEnergy() >= low && song.getEnergy() <= high && song.getBPM() >= minSpeed) 
          results.add(song.getTitle());
      }   
    return results;
  }
  
  /**
   * Filters the list of songs returned by future calls of getRange() and 
   * fiveMostDanceable() to only include fast songs.  If getRange() was 
   * previously called, then this method will return a list of song titles
   * (sorted in ascending order by Energy) that only includes songs with
   * speeds greater than or equal to minSpeed.  If getRange() was not 
   * previously called, then this method should return an empty list.
   *
   * Note that this minSpeed threshold should be saved for later use by the 
   * other methods defined in this class.
   *
   * @param minSpeed is the minimum speed of a returned song
   * @return List of song titles, empty if getRange was not previously called
   */
  @Override
  public List<String> filterFastSongs(int minSpeed) {
    // update the minSpeed varaible
    this.minSpeed = minSpeed;
    // creates a List to store the results
    List<String> results = new ArrayList<>();
    // if getRange has not yet been called, returns an empty list
    if (energyRange == null)
      return results;
      
    // calls the get range method with a new minSpeed variable set 
    results = getRange(energyRange[0], energyRange[1]);
    
    // returns the songs that meet the criteria in ascending order
    return results;
  }

  /**
   * This method makes use of the attribute range specified by the most
   * recent call to getRange().  If a minSpeed threshold has been set by
   * filterFastSongs() then that will also be utilized by this method.
   * Of those songs that match these criteria, the five most danceable will
   * be returned by this method as a List of Strings in increasing order of 
   * energy.  Each string contains the energy rating followed by a colon,
   * a space, and then the song's title.
   * If fewer than five such songs exist, return all of them.
   *
   * @return List of five most danceable song titles and their energy
   * @throws IllegalStateException when getRange() was not previously called.
   */
  @Override
  public List<String> fiveMostDanceable() throws IllegalStateException{
    if (energyRange == null)
      throw new IllegalStateException();
    
    // arrayList of sorted results
    List<SongInterface> results = new ArrayList<>();
    List<String> mostDanceable = new ArrayList<>();
    
    // returns empty list if the low is greater than high
    if (energyRange[0] > energyRange[1])
      return mostDanceable;
    
      // for loop through each song in csv file
      for (SongInterface song:data) {
        // if the song has the required energy level and is at or above minspeed, add it to list
        // list should already be sorted in increasing order by energy !!!!
        if (song.getEnergy() >= energyRange[0] && song.getEnergy() <= energyRange[1] 
            && song.getBPM() >= minSpeed && results.contains(song) == false) 
          results.add(song);     
      }
      // filter out any repeats
      for (int i  = 0; i < results.size();i++) {
        for (int j = 1; j < results.size(); j++) {
          if (results.get(i).getTitle().equals(results.get(j).getTitle()) && j!=i)
            results.remove(results.get(j));
        }
      }
   
    // results is a list of the songs that meet the criteria
    // Now pick the 5 most danceable songs
    int smallestIndex;
    int lowestDance;
    while (results.size() > 5) {
      smallestIndex = 0;
      lowestDance = results.get(0).getDanceability();
      // remove the least danceable 
      for (int i = 1; i < results.size(); i++) {
        if (results.get(i).getDanceability() < lowestDance) {
          lowestDance = results.get(i).getDanceability();
          smallestIndex = i; 
        }
      }
      results.remove(smallestIndex);
    }
    
    // convert the 5 most danceable songs into the proper format and keep them stored
    for(SongInterface song:results) {
      mostDanceable.add(song.getEnergy() + ": " + song.getTitle());
    }
    
    // return list of the five most danceable songs
    return mostDanceable;
  }
}
