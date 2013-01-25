/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.esamusicwebapp;

import de.esamusicwebapp.core.SearchManager;
import de.esamusicwebapp.core.entity.Track;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author nto
 */
@ManagedBean
@SessionScoped
public class ViewController implements Serializable {

    @Inject
    private ViewDataModelLocal viewModel;
    private List<Track> trackList;
    private Track selectedTrack;
    private String lyricInp;
    private String titleInp;
    private String artistInp;
    //@EJB
    //private UserAuthBean userAuthBean;
//    @EJB(lookup="java:global/EsaUserAuth/UserAuthImpl!de.esa.auth.jpa.UserAuth")

    public ViewController() {
        trackList = SearchManager.getInstance().searchTracks("test", "linkin Park");
        selectedTrack = new Track("foo", "foo", "foo", "foo");
    }

    /**
     * Proxy Method to call the business logic.
     */
    public void searchTitle() {
        Logger.getLogger("ViewController").log(Level.INFO, "searchTitle called");
        //userAuthBean.register("bob","pw");
        //  this.trackList = viewModel.searchTitle(titleInp);
    }

    /**
     * Proxy Method to call the business logic.
     */
    public void searchArtist() {
        System.out.print("searchArtist");
        //   this.trackList = viewModel.searchArtist(artistInp);
    }

    /**
     * Proxy Method to call the business logic.
     */
    public void searchLyric() {
        System.out.print("searchLyric");
        //  this.trackList = viewModel.searchLyric(lyricInp);
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
        if(selectedTrack == null) {
            this.selectedTrack = this.trackList.get(0);
        }else{
            this.selectedTrack = selectedTrack;
        }
    }
}
