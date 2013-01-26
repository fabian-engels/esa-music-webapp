package de.esamusicwebapp.core.services.lastfm;

/**
 *
 * @author Michael
 */
public enum Method {
   artist_search("artist.search"),
   artist_gettopalbums("artist.gettopalbums"),
   album_getInfo("album.getInfo"),
   track_search("track.search");
   
   private final String searchParameter;
   
   Method(final String searchParameter) {
      this.searchParameter = searchParameter;
   }
   
   @Override
   public String toString() {
      return this.searchParameter;
   }
}
