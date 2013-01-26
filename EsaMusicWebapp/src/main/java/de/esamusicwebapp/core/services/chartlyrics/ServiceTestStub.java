/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.esamusicwebapp.core.services.chartlyrics;

import de.esamusicwebapp.core.entity.Track;
import de.esamusicwebapp.core.services.chartlyrics.ChartLyricsApi;
import de.esamusicwebapp.core.services.chartlyrics.ChartLyricsApi;
import java.rmi.RemoteException;
import org.apache.axis2.AxisFault;


/**
 *
 * @author erik
 */
public class ServiceTestStub {

    public static void main(String[] args) throws AxisFault, RemoteException {
        Track track = new Track("Michael Jacksonasdfsafas", "Thrisafsafller", "test", "http://www.web.de");
        ChartLyricsApi.getInstance().addLyric(track);
        System.out.println(track.getLyric());
    }
}
    