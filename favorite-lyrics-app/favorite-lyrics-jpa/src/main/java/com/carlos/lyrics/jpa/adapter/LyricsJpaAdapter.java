package com.carlos.lyrics.jpa.adapter;

import com.carlos.lyrics.domain.data.LyricsDomain;
import com.carlos.lyrics.domain.exception.LyricsNotFoundException;
import com.carlos.lyrics.domain.port.LyricsPersistencePort;
import com.carlos.lyrics.jpa.model.LyricsEntity;
import com.carlos.lyrics.jpa.repository.LyricsRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class LyricsJpaAdapter implements LyricsPersistencePort {

    private LyricsRepository lyricsRepository;

    public LyricsJpaAdapter(LyricsRepository lyricsRepository) {
        this.lyricsRepository = lyricsRepository;
    }

    @Override
    public void addLyrics(LyricsDomain lyricsObject) {
        final LyricsEntity lyricsEntity = getLyricsEntity(lyricsObject);
        lyricsRepository.save(lyricsEntity);
    }

    @Override
    public void removeLyrics(LyricsDomain lyricsObject) {
        lyricsRepository.deleteAllByParticipatingArtist(lyricsObject.getArtist());
    }

    @Override
    public void updateLyrics(LyricsDomain lyricsObject) {
        final LyricsEntity byParticipatingArtist = lyricsRepository.findByParticipatingArtist(lyricsObject.getArtist());
        if (Objects.nonNull(byParticipatingArtist)) {
            byParticipatingArtist.setLyrics(lyricsObject.getLyrics());
            lyricsRepository.save(byParticipatingArtist);
        } else {
            final LyricsEntity byLyrics = lyricsRepository.findByLyrics(lyricsObject.getLyrics());
            if (Objects.nonNull(byLyrics)) {
                byLyrics.setParticipatingArtist(lyricsObject.getArtist());
                lyricsRepository.save(byLyrics);
            }
        }
    }

    @Override
    public List<LyricsDomain> getAll() {
        return lyricsRepository.findAll()
                .stream()
                .map(this::getLyrics)
                .collect(Collectors.toList());

    }

    @SneakyThrows
    @Override
    public LyricsDomain getLyricsById(Long id) {
        return getLyrics(lyricsRepository.findById(id)
                .orElseThrow((Supplier<Throwable>) () -> new LyricsNotFoundException(id)));

    }

    private LyricsEntity getLyricsEntity(LyricsDomain lyricsObject) {
        return LyricsEntity.builder()
                .participatingArtist(lyricsObject.getArtist())
                .lyrics(lyricsObject.getLyrics())
                .build();
    }

    private LyricsDomain getLyrics(LyricsEntity lyricsEntity) {
        return LyricsDomain.builder()
                .artist(lyricsEntity.getParticipatingArtist())
                .lyrics(lyricsEntity.getLyrics())
                .build();
    }

}
