package com.carlos.lyrics.rest;

import com.carlos.lyrics.domain.data.LyricsDomain;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface LyricsController {

    @PostMapping("/lyrics")
    ResponseEntity<Void> addLyrics(@RequestBody LyricsDomain lyricsDto);

    @DeleteMapping("/lyrics")
    ResponseEntity<String> removeLyrics(@RequestBody LyricsDomain lyricsDto);

    @PutMapping("/lyrics")
    ResponseEntity<String> updateLyrics(@RequestBody LyricsDomain lyricsDto);

    @GetMapping("/lyrics/{lyricsId}")
    ResponseEntity<LyricsDomain> getLyricsById(@PathVariable Long lyricsId);

    @GetMapping("/lyrics")
    ResponseEntity<List<LyricsDomain>> getLyrics();

    @GetMapping("/lyrics/random")
    ResponseEntity<LyricsDomain> getRandomLyric();

}
