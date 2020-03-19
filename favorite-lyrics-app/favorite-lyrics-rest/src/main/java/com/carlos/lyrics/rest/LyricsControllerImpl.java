package com.carlos.lyrics.rest;

import com.carlos.lyrics.core.service.LyricsService;
import com.carlos.lyrics.domain.data.LyricsDomain;
import com.carlos.lyrics.domain.exception.LyricsNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@Slf4j
@RestController
public class LyricsControllerImpl implements LyricsController {

    private final LyricsService lyricsService;

    private final Random random = new Random();

    public LyricsControllerImpl(LyricsService lyricsService) {
        this.lyricsService = lyricsService;
    }

    @Override
    public ResponseEntity<Void> addLyrics(LyricsDomain lyricsDomain) {
        lyricsService.addLyrics(lyricsDomain);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> removeLyrics(LyricsDomain lyricsDomain) {
        lyricsService.removeLyrics(lyricsDomain);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> updateLyrics(LyricsDomain lyricsDomain) {
        lyricsService.updateLyrics(lyricsDomain);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<LyricsDomain> getLyricsById(Long lyricsId) {
        try {
            return new ResponseEntity<>(lyricsService.getLyricsById(lyricsId), HttpStatus.OK);
        } catch (LyricsNotFoundException ex) {
            log.error("Error!", ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<List<LyricsDomain>> getLyrics() {
        return new ResponseEntity<>(lyricsService.getAllLyrics(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<LyricsDomain> getRandomLyric() {
        final List<LyricsDomain> allLyrics = lyricsService.getAllLyrics();
        final int size = allLyrics.size();
        return new ResponseEntity<>(allLyrics.get(random.nextInt(size)), HttpStatus.OK);
    }
}
