/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.esamusicwebapp;

import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author nto
 */
@Stateless
public class ViewDataModel implements ViewDataModelLocal {

    @Override
    public List<Track> searchArtist(final String artist) {
        // TODO insert service call
        return null;
    }
    
    

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public List<Track> searchTitle(final String title) {
        // TODO insert service call
        return null;
    }

    @Override
    public List<Track> searchLyric(final String lyric) {
        // TODO insert service call
        return null;
    }

}
