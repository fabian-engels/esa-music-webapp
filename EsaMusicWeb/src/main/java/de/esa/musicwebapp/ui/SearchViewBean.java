/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.esa.musicwebapp.ui;

import de.esa.musicwebapp.entity.Track;
import de.esa.musicwebapp.service.userauth.ChangePWClient;
import de.esa.musicwebapp.services.lastfm.LastFMApi;
import de.esa.userauth.domain.UserObject;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author nto
 */
@ManagedBean(name = "search")
@SessionScoped
public class SearchViewBean implements Serializable {

    private List<Track> trackList;
    private Track selectedTrack;
    private String lyricInp;
    private String titleInp;
    private String artistInp;
    private String oldPwdInp;
    private String newPwdInp;
    @EJB
    private ChangePWClient changePWClient;
    @ManagedProperty(value = "#{login.currentUser}")
    private UserObject currentUser;

    public void setCurrentUser(UserObject currentUser) {
        this.currentUser = currentUser;
    }
    
    public String changePW() {
        boolean result = false;
        if (currentUser == null) {
            displayFailure("SearchViewBean currentUser is NULL.");
        }else if (this.newPwdInp == null || this.newPwdInp.isEmpty()) {
            displayFailure("SearchViewBean new password is NULL or empty.");
        }else if (this.newPwdInp != this.oldPwdInp) {
            displayFailure("SearchViewBean new and old password do not match.");
        } else{
            result = changePWClient.sendJMSMessage("changepw:" + currentUser.getName() + ":" + this.newPwdInp);
        }
        if (result) {
            displayInfo("Password successfuly changed.");
        } else {
            displayFailure("Password change failed.");
        }
        return "";
    }

    private void displayFailure(String message) {
        FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failure", message));
    }

    private void displayInfo(String message) {
        FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", message));
    }

    public String deleteUser() {
        boolean result = changePWClient.sendJMSMessage("deleteuser:" + currentUser.getName());
        if (result) {
        } else {
            displayFailure("Unable to delete the user named: " + currentUser.getName());
            return "";
        }
        return "login";
    }

    public SearchViewBean() {
        // trackList = SearchManager.getInstance().searchTracks("Thriller", "Michael Jackson");
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login";
    }

    public String getOldPwdInp() {
        return oldPwdInp;
    }

    public void setOldPwdInp(String oldPwdInp) {
        this.oldPwdInp = oldPwdInp;
    }

    public String getNewPwdInp() {
        return newPwdInp;
    }

    public void setNewPwdInp(String newPwdInp) {
        this.newPwdInp = newPwdInp;
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
