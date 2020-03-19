package com.carlos.lyrics.core.service;

import com.carlos.lyrics.domain.data.LyricsDomain;

import java.util.List;

public interface LyricsService {

    void addLyrics(LyricsDomain lyricsObject);

    void removeLyrics(LyricsDomain lyricsObject);

    void updateLyrics(LyricsDomain lyricsObject);

    List<LyricsDomain> getAllLyrics();

    LyricsDomain getLyricsById(Long id);

}
