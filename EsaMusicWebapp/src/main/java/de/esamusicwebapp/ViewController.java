/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.esamusicwebapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author nto
 */
@ManagedBean
@ViewScoped
public class ViewController implements Serializable {

    @Inject
    private ViewDataModelLocal viewModel;
    private List<Track> trackList;
    private Track selectedTrack;
    private String lyricInp;
    private String titleInp;
    private String artistInp;
    @EJB
    private UserAuthBean userAuthBean;
    @EJB
    private SessionManagerBean sessionManagerBean;
//    @EJB(lookup="java:global/EsaUserAuth/UserAuthImpl!de.esa.auth.jpa.UserAuth")

    public ViewController() {
        trackList = new ArrayList<Track>();

        // dummy value creation
        trackList.add(new Track("Linkin Park", "Papercut", "Hybrid Theory",
                "http://kwinofnothing.files.wordpress.com/2012/09/linkinpark-hybridtheory.jpg", "2000",
                "Why does it feel like night today?\nSomething in here is not right today\nWhy am I so uptight today?\nParanoia\'s all I got left\nI don\'t know what stressed me first\nOr how the pressure was fed",
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


        trackList.add(new Track("Cypress Hill", "Intro", "Stoned Raiders", "http://ecx.images-amazon.com/images/I/61vw7Ah7rzL._SL500_AA300_.jpg"));
        selectedTrack = new Track("foo", "foo", "foo", "foo");
    }

    private void authenticate(){
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        HttpSession session = null;
        FacesContext context = FacesContext.getCurrentInstance();
        request = (HttpServletRequest) context.getExternalContext().getRequest();
        response = (HttpServletResponse) context.getExternalContext().getResponse();
        session = request.getSession(true);
    }
   

    /**
     * Proxy Method to call the business logic.
     */
    public void searchTitle(AjaxBehaviorEvent event) {
        userAuthBean.register("bob","pw");
        //  this.trackList = viewModel.searchTitle(titleInp);
    }

    /**
     * Proxy Method to call the business logic.
     */
    public void searchArtist(AjaxBehaviorEvent event) {
        //   this.trackList = viewModel.searchArtist(artistInp);
    }

    /**
     * Proxy Method to call the business logic.
     */
    public void searchLyric() {
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
        System.out.println("setSelectedTrack called: " + selectedTrack.title);
        this.selectedTrack = selectedTrack;
    }
}
