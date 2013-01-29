/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.esamusicwebapp.ui;

import de.esamusicwebapp.core.SearchManager;
import de.esamusicwebapp.core.entity.Track;
import de.esamusicwebapp.core.services.lastfm.LastFMApi;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author nto
 */
@ManagedBean
@SessionScoped
public class ViewController implements Serializable {

    private List<Track> trackList;
    private Track selectedTrack;
    private String lyricInp;
    private String titleInp;
    private String artistInp;
    
//    @EJB(lookup="java:global/EsaUserAuth/UserAuthImpl!de.esa.auth.jpa.UserAuth")

    public ViewController() {
        trackList = SearchManager.getInstance().searchTracks("Thriller", "Michael Jackson");
    }

    /**
     * Proxy Method to call the business logic.
     */
    public void searchTrack() {
       this.trackList = LastFMApi.getInstance().searchTrack(this.titleInp, this.artistInp);
    }

    public String getLyricInp() {
        return lyricInp;
    }

    public void setLyricInp(String lyricInp) {
        this.lyricInp = lyricInp;
    }

    public String getTitleInp() {
        return titleInp;
    }

    public void setTitleInp(String titleInp) {
        this.titleInp = titleInp;
    }

    public String getArtistInp() {
        return artistInp;
    }

    public void setArtistInp(String artistInp) {
        this.artistInp = artistInp;
    }

    /**
     * Getter accessed by datatable component.
     *
     * @return current view model
     */
    public List<Track> getTrackList() {
        return trackList;
    }

    /**
     * Getter accessed by datatable selectRow event.
     *
     * @return current selection
     */
    public Track getSelectedTrack() {
        return selectedTrack;
    }

    /**
     * Setter accessed by datatable selectRow event.
     *
     * @param selectedTrack
     */
    public void setSelectedTrack(Track selectedTrack) {
        this.selectedTrack = selectedTrack;
    }
}
