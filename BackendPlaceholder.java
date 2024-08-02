import java.util.List;
import java.io.IOException;

/**
 * BackendPlaceholder - CS400 Project 1: iSongify
 *
 * This class doesn't really work.  The methods are hardcoded to output values
 * as placeholders throughout development.  It demonstrates the architecture
 * of the Backend class that will be implemented in a later week.
 */
public class BackendPlaceholder implements BackendInterface {

    public BackendPlaceholder(IterableSortedCollection<SongInterface> tree) {}

    /**
     * Loads data from the .csv file referenced by filename.
     * @param filename is the name of the csv file to load data from
     * @throws IOException when there is trouble finding/reading file
     */
    public void readData(String filename) throws IOException {
	// Note: this placeholder doesn't need to output anything,
	// it will be implemented by the backend developer in P105.
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
    public List<String> getRange(int low, int high) {
	// placeholder just returns a hard coded list of songs
	return java.util.Arrays.asList(new String[]{
		"Hey, Soul Sister",
		"Love The Way You Lie",
		"TiK ToK",
		"Bad Romance",
		"Just the Way You Are"
	    });
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
    public List<String> filterFastSongs(int minSpeed) {
	return java.util.Arrays.asList(new String[]{
		"Hey, Soul Sister",
		"Love The Way You Lie"
	    });
    }

    /**
     * This method makes use of the attribute range specified by the most
     * recent call to getRange().  If a minSpeed threshold has been set by
     * filterFastSongs() then that will also be utilized by this method.
     * Of those songs that match these criteria, the five most danceable will
     * be returned by this method as a List of Strings in increasing order of 
     * energy.  Each string contains the danceability rating followed by a 
     * colon, a space, and then the song's title.
     * If fewer than five such songs exist, display all of them.
     *
     * @return List of five most danceable song titles and their energy
     * @throws IllegalStateException when getRange() was not previously called.
     */
    public List<String> fiveMostDanceable() {
	return java.util.Arrays.asList(new String[]{
		"-4: Hey, Soul Sister",
		"-5: Love The Way You Lie"
	    });	
    }    
}
