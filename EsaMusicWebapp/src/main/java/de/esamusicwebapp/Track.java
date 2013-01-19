/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.esamusicwebapp;

import java.io.Serializable;

/**
 *
 * @author nto
 */
public class Track implements Serializable {

    Long id;
    String artist;
    String title;
    String album;
    String imageUri;
    String year;
    String lyric;
    String lyricUri;
    String amazonLink;
    String sampleUri;

    public Track(String artist, String title, String album, String imageUri) {
        this.artist = artist;
        this.title = title;
        this.album = album;
        this.imageUri = imageUri;
    }

    public Track(String artist, String title, String album, String imageUri, String year, String lyric, String lyricUri, String amazonLink, String sampleUri) {
        this.artist = artist;
        this.title = title;
        this.album = album;
        this.imageUri = imageUri;
        this.year = year;
        this.lyric = lyric;
        this.lyricUri = lyricUri;
        this.amazonLink = amazonLink;
        this.sampleUri = sampleUri;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

    public String getSampleUri() {
        return sampleUri;
    }

    public void setSampleUri(String sampleUri) {
        this.sampleUri = sampleUri;
    }

    public String getLyricUri() {
        return lyricUri;
    }

    public void setLyricUri(String lyricUri) {
        this.lyricUri = lyricUri;
    }

    public String getAmazonLink() {
        return amazonLink;
    }

    public void setAmazonLink(String amazonLink) {
        this.amazonLink = amazonLink;
    }
}
