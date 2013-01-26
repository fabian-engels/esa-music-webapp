package de.esamusicwebapp.core.services.lastfm;

import de.esamusicwebapp.core.entity.Track;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Michael
 */
public class LastFM {
    private static final String APPLICATION_URL = "http://ws.audioscrobbler.com/2.0/?";
    private static final String API_KEY         = "b3e1a560606cc5ac6b121eca31e54084";
    
    /** 
     * Beispiel:
     * http://ws.audioscrobbler.com/2.0/?method=artist.search&artist=cher&api_key=b3e1a560606cc5ac6b121eca31e54084    
     */
    
	 private static LastFM instance;
	
	 public static LastFM getInstance() {
	     if (instance == null) {
            instance = new LastFM();
		  }
		  return instance;
	 }
    
    class AlbumInfo {
       public String mbid;
       public String albumname;
       public String artist;
       public String image;
    }
    
    class TrackInfo {
       public String mbid;
       public String trackname;
       public String artist;
       public String image;
    }    
	
    private LastFM() {}
    
    public List<Track> searchTrack(final String trackName, final String optionalArtistName) {
        List<Track> resultList = _getTracks(trackName, optionalArtistName);
        return resultList;
    }
    
    private List<Track> _getTracks(final String trackName, final String optionalArtistName) {
        List<Track> trackList = new ArrayList<Track>();

        try {
            String searchTerm = Parameter.track + "=" + trackName + "&";
            
            if (optionalArtistName != null && optionalArtistName.isEmpty() == false) {
                searchTerm += Parameter.artist + "=" + optionalArtistName + "&";
            }               
           
            final String uri = APPLICATION_URL + Parameter.method  + "=" + Method.track_search + "&" + 
                                                 searchTerm +
                                                 Parameter.api_key + "=" + API_KEY;
 
            Logger.getLogger(LastFM.class.getName()).log(Level.INFO, "SearchTerm: " + uri, new Exception());
            
            final Document xmlResult = _fetchResourceAtURI(uri);
            
            if (xmlResult != null) {
            
                final NodeList nodeList = xmlResult.getElementsByTagName(Parameter.track.toString());

                for (int idx = 0; idx < nodeList.getLength(); idx++) {
                    final Node node = nodeList.item(idx);
                    
                    if (node.hasChildNodes() == false) { continue; }
                    
                    final NodeList parameter = node.getChildNodes();
                    
                    TrackInfo info = new TrackInfo();
                    
                    for (int idxSub = 0; idxSub < parameter.getLength(); idxSub++) {
                        final Node parameterNode = parameter.item(idxSub);
                        
                        if (parameterNode.getNodeType() != Node.ELEMENT_NODE) { continue; }
                        
                        if (parameterNode.getNodeName().equals(Parameter.mbid.toString())) {
                            info.mbid = parameterNode.getTextContent();
                        }                        
                        if (parameterNode.getNodeName().equals(Parameter.name.toString())) {
                            info.trackname = parameterNode.getTextContent();
                        }                        
                        if (parameterNode.getNodeName().equals(Parameter.artist.toString())) {
                            info.artist = parameterNode.getTextContent();
                        }                        
                        if (parameterNode.getNodeName().equals(Parameter.image.toString())) {
                            info.image = parameterNode.getTextContent();
                        }                        
                        
                        if (info.mbid != null && info.trackname != null && info.artist != null && info.image != null) {
                            trackList.add(new Track(info.artist,info.trackname,"",info.image));
                            break;
                        }
                    } 
                }
            }     
        } catch (Exception ex) {
            Logger.getLogger(LastFM.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return trackList;
    }    
    
    public List<Track> searchArtist(final String artistName) {
        List<Track> resultList = new ArrayList<Track>();

        List<AlbumInfo> albums = _getAlbumInfos(artistName);
        for (AlbumInfo album : albums) {
            resultList.add(new Track(album.artist, "", album.albumname, album.image));
            //Logger.getLogger(LastFM.class.getName()).log(Level.INFO, "Found Album " + album.artist + ", " + album.albumname, new Exception());
            _addTracksToList(resultList, album);
        }
        
        return resultList;
    }
    
    private List<AlbumInfo> _getAlbumInfos(final String artistName) {
        List<AlbumInfo> albumList = new ArrayList<AlbumInfo>();

        try {
            final String uri = APPLICATION_URL + Parameter.method  + "=" + Method.artist_gettopalbums + "&" + 
                                                 Parameter.artist  + "=" + artistName + "&" +
                                                 Parameter.api_key + "=" + API_KEY;
 
            Logger.getLogger(LastFM.class.getName()).log(Level.INFO, "SearchTerm: " + uri, new Exception());
            
            final Document xmlResult = _fetchResourceAtURI(uri);
            
            if (xmlResult != null) {
            
                final NodeList nodeList = xmlResult.getElementsByTagName(Parameter.album.toString());

                for (int idx = 0; idx < nodeList.getLength(); idx++) {
                    final Node node = nodeList.item(idx);
                    
                    if (node.hasChildNodes() == false) { continue; }
                    
                    final NodeList parameter = node.getChildNodes();
                    
                    AlbumInfo info = new AlbumInfo();
                    
                    for (int idxSub = 0; idxSub < parameter.getLength(); idxSub++) {
                        final Node parameterNode = parameter.item(idxSub);
                        
                        if (parameterNode.getNodeType() != Node.ELEMENT_NODE) { continue; }
                        
                        if (parameterNode.getNodeName().equals(Parameter.mbid.toString())) {
                            info.mbid = parameterNode.getTextContent();
                        }                        
                        if (parameterNode.getNodeName().equals(Parameter.name.toString())) {
                            info.albumname = parameterNode.getTextContent();
                        }                        
                        if (parameterNode.getNodeName().equals(Parameter.image.toString())) {
                            info.image = parameterNode.getTextContent();
                        }                        
                        
                        if (info.mbid != null && info.albumname != null && info.image != null) {
                            info.artist = artistName;
                            albumList.add(info);
                            break;
                        }
                    }   
                }
            }     
        } catch (Exception ex) {
            Logger.getLogger(LastFM.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return albumList;
    }
    
    private void _addTracksToList(final List<Track> tracksList, final AlbumInfo albumInfo) {
        try {
            String searchTerm;
            if (albumInfo.mbid.isEmpty() == false) {
               searchTerm = Parameter.mbid  + "=" + albumInfo.mbid;
            } else {
               searchTerm = Parameter.album  + "=" + albumInfo.albumname + "&"+
                            Parameter.artist + "=" + albumInfo.artist;
            }
           
            final String uri = APPLICATION_URL + Parameter.method  + "=" + Method.album_getInfo + "&" + 
                                                 searchTerm + "&" +
                                                 Parameter.api_key + "=" + API_KEY;
 
            //Logger.getLogger(LastFM.class.getName()).log(Level.INFO, "SEARCHING AlbumInfo, SearchTerm: " + uri, new Exception());
            
            final Document xmlResult = _fetchResourceAtURI(uri);
            
            if (xmlResult != null) {
            
                final NodeList nodeList = xmlResult.getElementsByTagName(Parameter.track.toString());

                for (int idx = 0; idx < nodeList.getLength(); idx++) {
                    final Node node = nodeList.item(idx);
                    
                    if (node.hasChildNodes() == false) { continue; }
                    
                    final NodeList parameter = node.getChildNodes();
                    
                    for (int idxSub = 0; idxSub < parameter.getLength(); idxSub++) {
                        final Node parameterNode = parameter.item(idxSub);
                        
                        if (parameterNode.getNodeType() != Node.ELEMENT_NODE) { continue; }
                        
                        if (parameterNode.getNodeName().equals(Parameter.name.toString())) {
                            tracksList.add(new Track(albumInfo.artist, parameterNode.getTextContent(), albumInfo.albumname, albumInfo.image));
                            break;
                        }                        
                    }
                }
            }     
        } catch (Exception ex) {
            Logger.getLogger(LastFM.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }
    
    
	 private Document _fetchResourceAtURI(final String url) {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder builder;
        
        Document result = null;
        
        try {
            builder = factory.newDocumentBuilder();
            result = builder.parse(url);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(LastFM.class.getName()).log(Level.SEVERE, "XMLParser --> ParserConfigurationException", ex);
        } catch (SAXException ex) {
            Logger.getLogger(LastFM.class.getName()).log(Level.SEVERE, "XMLParser --> SAXException", ex);
        } catch (IOException ex) {
            Logger.getLogger(LastFM.class.getName()).log(Level.SEVERE, "XMLParser --> IOException", ex);
        }        
        
        return result;
	 }
}
