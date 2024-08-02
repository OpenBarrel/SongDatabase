import java.util.List;

/**
 * Class to represent a song and store it's corresponding data
 */
public class Song implements SongInterface{
  
  // array to store data about the song
  List<String> data;
  
  // constructor to set the data array
  public Song(List<String> data) {
    this.data = data;
  }
  
  /**
   * Compares two song objects based on their energy values
   * @return -1 if this object is lower in energy than o, 0 if they are the same, and 1 if otherwise
   */
  @Override
  public int compareTo(SongInterface o) {
    
    // this is less than that
    if (this.getEnergy() < o.getEnergy())
      return -1;
    // this is greater than that
    else if (this.getEnergy() > o.getEnergy())
      return 1;
    // this is equal to that
    else 
      return 0;
  }

  /**
   * Method to get the title of a song
   * @return the title of the song
   */
  @Override
  public String getTitle() {
    // returns the title
    return data.get(0);
  }

  /**
   * Method to get the artist of the song
   * @return the artist of the song
   */
  @Override
  public String getArtist() {
    // returns the artist
    return data.get(1);
  }

  /**
   * Method to get the genres of the song
   * @return the genres of the song
   */
  @Override
  public String getGenres() {
    // returns the genres
    return data.get(2);
  }

  /**
   * Method to get the year the song was released
   * @return the year the song was released
   */
  @Override
  public int getYear() {
    // returns the year as an int
    return Integer.valueOf(data.get(3));
  }

  /**
   * Method to get the speed(BPM) of the song
   * @return the BPM of the song
   */
  @Override
  public int getBPM() {
    // returns the BPM
    return Integer.valueOf(data.get(4));
  }

  /**
   * Method to get the energy of the song
   * @return the energy of the song
   */
  @Override
  public int getEnergy() {
    // returns the energy
    return Integer.valueOf(data.get(5));
  }

  /**
   * Method to get the danceability of the song
   * @return the danceability of the song
   */
  @Override
  public int getDanceability() {
    // returns the danceability
    return Integer.valueOf(data.get(6));
  }

  /**
   * Method to get the loudness(dB) of the song
   * @return the dB of the song
   */
  @Override
  public int getLoudness() {
    // returns the loudness in dB
    return Integer.valueOf(data.get(7));
  }

  /**
   * Method to get the liveness of the song
   * @return the liveness of the song
   */
  @Override
  public int getLiveness() {
    // returns the liveness
    return Integer.valueOf(data.get(8));
  }

}
