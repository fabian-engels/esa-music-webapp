package de.esamusicwebapp.core;

import java.util.ArrayList;
import java.util.List;

import de.esamusicwebapp.core.entity.Track;
import de.esamusicwebapp.core.services.chartlyrics.ChartLyricsApi;
import de.esamusicwebapp.core.services.lastfm.LastFMApi;

public class SearchManager {
	
	private static SearchManager instance;
	
	public static SearchManager getInstance() {
		if (instance == null) {
			instance = new SearchManager();
		}
		return instance;
	}
	
	private SearchManager(){};
	
	public List<Track> searchTracks(final String songTitle, final String artist) {
		
		List<Track> trackList = LastFMApi.getInstance().searchTrack(songTitle, artist);
      if (trackList.isEmpty()) { trackList.add(new Track("leer", "leer", "leer", "http://kwinofnothing.files.wordpress.com/2012/09/linkinpark-hybridtheory.jpg")); }
      /*
      
        // dummy value creation
        trackList.add(new Track("Linkin Park", "Papercut", "Hybrid Theory",
                "http://kwinofnothing.files.wordpress.com/2012/09/linkinpark-hybridtheory.jpg", "2000",
                "Why does it feel like night today?\nSomething in here is not "
                + "right today\nWhy am I so uptight today?\nParanoia\'s all I got "
                + "left\nI don\'t know what stressed me first\nOr how the pressure was fed",
                "http://lyrics.wikia.com/Linkin_Park:Papercut",
                "http://www.amazon.de/gp/product/B003IIPI5I/ref=dm_mu_dp_trk1",
                "http://www.amazon.de/gp/dmusic/media/sample.m3u/ref=dm_sp_smpl?ie=UTF8&ASIN=B003IIPI5I&CustomerID=A3F1G1265D1P40&catalogItemType=track"));
        trackList.add(new Track("Linkin Park", "One Step Closer", "Hybrid Theory", "http://kwinofnothing.files.wordpress.com/2012/09/linkinpark-hybridtheory.jpg"));
        trackList.add(new Track("Linkin Park", "With You", "Hybrid Theory", "http://kwinofnothing.files.wordpress.com/2012/09/linkinpark-hybridtheory.jpg"));
        trackList.add(new Track("Linkin Park", "Points Of Authority", "Hybrid Theory", "http://kwinofnothing.files.wordpress.com/2012/09/linkinpark-hybridtheory.jpg"));
        trackList.add(new Track("Linkin Park", "Crawling", "Hybrid Theory", "http://kwinofnothing.files.wordpress.com/2012/09/linkinpark-hybridtheory.jpg"));
        trackList.add(new Track("Linkin Park", "Runaway", "Hybrid Theory", "http://kwinofnothing.files.wordpress.com/2012/09/linkinpark-hybridtheory.jpg"));
        trackList.add(new Track("Linkin Park", "By Myself", "Hybrid Theory", "http://kwinofnothing.files.wordpress.com/2012/09/linkinpark-hybridtheory.jpg"));
        trackList.add(new Track("Linkin Park", "In The End", "Hybrid Theory", "http://kwinofnothing.files.wordpress.com/2012/09/linkinpark-hybridtheory.jpg"));
        trackList.add(new Track("Linkin Park", "A Place For My Head", "Hybrid Theory", "http://kwinofnothing.files.wordpress.com/2012/09/linkinpark-hybridtheory.jpg"));
        trackList.add(new Track("Linkin Park", "Forgotten", "Hybrid Theory", "http://kwinofnothing.files.wordpress.com/2012/09/linkinpark-hybridtheory.jpg"));
        trackList.add(new Track("Linkin Park", "Cure For The Itch", "Hybrid Theory", "http://kwinofnothing.files.wordpress.com/2012/09/linkinpark-hybridtheory.jpg"));
        trackList.add(new Track("Linkin Park", "Pushing Me Away", "Hybrid Theory", "http://kwinofnothing.files.wordpress.com/2012/09/linkinpark-hybridtheory.jpg"));
        
        trackList.add(new Track("Cypress Hill", "Intro", "Stoned Raiders", "http://ecx.images-amazon.com/images/I/61vw7Ah7rzL._SL500_AA300_.jpg"));*/
        for (Track track : trackList) {
            ChartLyricsApi.getInstance().addLyric(track);
        }
        return trackList;
	}
	
}
