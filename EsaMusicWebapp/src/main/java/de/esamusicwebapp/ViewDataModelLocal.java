/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.esamusicwebapp;

import de.esamusicwebapp.core.entity.Track;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author nto
 */
@Local
public interface ViewDataModelLocal {

    List<Track> searchArtist(final String artist);

    List<Track> searchTitle(final String title);

    List<Track> searchLyric(final String lyric);
    
}
