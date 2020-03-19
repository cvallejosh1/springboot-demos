package com.carlos.lyrics.core.service;

import com.carlos.lyrics.domain.data.LyricsDomain;
import com.carlos.lyrics.domain.port.LyricsPersistencePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LyricsServiceImpl implements LyricsService {

    private final LyricsPersistencePort port;

    public LyricsServiceImpl(LyricsPersistencePort port) {
        this.port = port;
    }

    @Override
    public void addLyrics(LyricsDomain lyricsObject) {
        port.addLyrics(lyricsObject);
    }

    @Override
    @Transactional
    public void removeLyrics(LyricsDomain lyricsObject) {
        port.removeLyrics(lyricsObject);
    }

    @Override
    public void updateLyrics(LyricsDomain lyricsObject) {
        port.updateLyrics(lyricsObject);
    }

    @Override
    public List<LyricsDomain> getAllLyrics() {
        return port.getAll();
    }

    @Override
    public LyricsDomain getLyricsById(Long id) {
        return port.getLyricsById(id);
    }
}
