/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.esamusicwebapp.core.services.chartlyrics;

import com.chartlyrics.api.GetLyricResult;
import com.chartlyrics.api.SearchLyricDirectDocument;
import de.chartlyricsclient.Apiv1Stub;
import de.esamusicwebapp.core.SearchManager;
import de.esamusicwebapp.core.entity.Track;
import java.io.StreamCorruptedException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.axis2.AxisFault;

/**
 * This class is a Singleton it is used to add the lyric information
 * to a Track with the ChartLyrics.com - API
 * @author erik
 */
public class ChartLyricsApi {

    private static ChartLyricsApi instance;

    public static ChartLyricsApi getInstance() {
        if (instance == null) {
            instance = new ChartLyricsApi();
        }
        return instance;
    }
    
    private ChartLyricsApi(){};
    
    /**
     * This method adds a lyric to a given track
     * @param track The Track to add the Lyrics to.
     */
    public void addLyric(final Track track) {
        try {
            Apiv1Stub apiStub = new Apiv1Stub();
            SearchLyricDirectDocument searchDocument = SearchLyricDirectDocument.Factory.newInstance();
            SearchLyricDirectDocument.SearchLyricDirect searchLyric = 
                    SearchLyricDirectDocument.SearchLyricDirect.Factory.newInstance();
            searchLyric.setArtist(track.getArtist());
            searchLyric.setSong(track.getTitle());
            searchDocument.setSearchLyricDirect(searchLyric);
            GetLyricResult result = apiStub.SearchLyricDirect(searchDocument)
                    .getSearchLyricDirectResponse().getSearchLyricDirectResult();
            if(result.getLyricId() != 0) {
                //Lyric wurde gefunden
                track.setLyric(result.getLyric());
                track.setLyricUri(result.getLyricUrl());                
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ChartLyricsApi.class.getName())
                    .log(Level.WARNING, "No connection to the Lyrics-Api possible.", ex);
        }
        if(track.getLyricUri() == null || track.getLyricUri().isEmpty()) {
            track.setLyricUri("http://www.chartlyrics.com/");
        }
    }
}
