package com.carlos.lyrics.domain.port;

import com.carlos.lyrics.domain.data.LyricsDomain;

import java.util.List;

public interface LyricsPersistencePort {

    void addLyrics(LyricsDomain lyricsObject);
    void removeLyrics(LyricsDomain lyricsObject);
    void updateLyrics(LyricsDomain lyricsObject);
    List<LyricsDomain> getAll();
    LyricsDomain getLyricsById(Long id);

}
