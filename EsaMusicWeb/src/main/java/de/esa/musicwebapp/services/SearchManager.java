package de.esa.musicwebapp.services;

import java.util.List;

import de.esa.musicwebapp.entity.Track;
import de.esa.musicwebapp.services.chartlyrics.ChartLyricsApi;
import de.esa.musicwebapp.services.lastfm.LastFMApi;

/**
 *
 * @author erik
 */
public class SearchManager {

    private static SearchManager instance;

    /**
     *
     * @return
     */
    public static SearchManager getInstance() {
        if (instance == null) {
            instance = new SearchManager();
        }
        return instance;
    }

    private SearchManager() {
    }

    
	
    /**
     *
     * @param songTitle
     * @param artist
     * @return
     */
    public List<Track> searchTracks(final String songTitle, final String artist) {

        List<Track> trackList = LastFMApi.getInstance().searchTrack(songTitle, artist);
        if (trackList.isEmpty()) {
            trackList.add(new Track("leer", "leer", "leer", "http://kwinofnothing.files.wordpress.com/2012/09/linkinpark-hybridtheory.jpg"));
        }
        for (Track track : trackList) {
            ChartLyricsApi.getInstance().addLyric(track);
        }
        return trackList;
    }
}