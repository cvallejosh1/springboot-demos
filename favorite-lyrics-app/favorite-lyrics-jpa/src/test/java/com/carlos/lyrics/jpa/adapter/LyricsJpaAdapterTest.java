package com.carlos.lyrics.jpa.adapter;

import com.carlos.lyrics.domain.data.LyricsDomain;
import com.carlos.lyrics.domain.exception.LyricsNotFoundException;
import com.carlos.lyrics.domain.port.LyricsPersistencePort;
import com.carlos.lyrics.jpa.model.LyricsEntity;
import com.carlos.lyrics.jpa.repository.LyricsRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {
        LyricsJpaAdapter.class,
        LyricsPersistencePort.class})
public class LyricsJpaAdapterTest {

    private static final String TEST_AUTHOR = "Beyonce";
    private static final String TEST_AUTHOR_2 = "Lady Gaga";
    private static final String TEST_LYRICS = "Sweet dreams or a beautiful nightmare";
    private static final String TEST_LYRICS_2 = "Put your hands up";
    private static final String TEST_LYRICS_3 = "videophone";

    @Autowired
    private LyricsPersistencePort lyricsPersistencePort;

    @MockBean
    private LyricsRepository mockLyricsRepository;

    @Captor
    private ArgumentCaptor<LyricsEntity> lyricsEntityArgumentCaptor;

    @Test
    void givenLyrics_whenAddLyrics_thenEntityIsPortedToRepository() {
        final LyricsDomain testLyrics = LyricsDomain.builder()
                .artist(TEST_AUTHOR)
                .lyrics(TEST_LYRICS)
                .build();

        lyricsPersistencePort.addLyrics(testLyrics);

        verify(mockLyricsRepository, only()).save(lyricsEntityArgumentCaptor.capture());
        final LyricsEntity lyricsEntity = lyricsEntityArgumentCaptor.getValue();
        assertThat(lyricsEntity.getParticipatingArtist()).isEqualTo(TEST_AUTHOR);
        assertThat(lyricsEntity.getLyrics()).isEqualTo(TEST_LYRICS);
    }

    @Test
    void givenLyrics_whenRemoveLyrics_thenEntityRemovalIsPortedToRepository() {
        final LyricsDomain testLyrics = LyricsDomain.builder()
                .artist(TEST_AUTHOR)
                .lyrics(TEST_LYRICS)
                .build();

        lyricsPersistencePort.removeLyrics(testLyrics);

        verify(mockLyricsRepository, only()).deleteAllByParticipatingArtist(testLyrics.getArtist());
    }

    @Test
    void givenCallToAllLyricss_whenNoParams_thenFindAllIsPortedToRepository() {
        final LyricsEntity testLyrics = LyricsEntity.builder()
                .participatingArtist(TEST_AUTHOR)
                .lyrics(TEST_LYRICS)
                .build();
        final List<LyricsEntity> testListLyricss = Collections.singletonList(testLyrics);
        when(mockLyricsRepository.findAll()).thenReturn(testListLyricss);

        final List<LyricsDomain> allLyrics = lyricsPersistencePort.getAll();

        verify(mockLyricsRepository, only()).findAll();
        assertThat(allLyrics).hasSize(1);
        final LyricsDomain lyricsDto = allLyrics.get(0);
        assertThat(lyricsDto.getArtist()).isEqualTo(TEST_AUTHOR);
        assertThat(lyricsDto.getLyrics()).isEqualTo(TEST_LYRICS);
    }

    @Test
    void givenArtisId_whenCallingGetLyricsById_thenFindByIdToRepository() {
        final LyricsEntity testLyrics = LyricsEntity.builder()
                .participatingArtist(TEST_AUTHOR)
                .lyrics(TEST_LYRICS)
                .build();
        when(mockLyricsRepository.findById(1L)).thenReturn(Optional.of(testLyrics));

        final LyricsDomain lyricsById = lyricsPersistencePort.getLyricsById(1L);

        verify(mockLyricsRepository, only()).findById(1L);
        assertThat(lyricsById.getArtist()).isEqualTo(TEST_AUTHOR);
        assertThat(lyricsById.getLyrics()).isEqualTo(TEST_LYRICS);
    }

    @Test
    void givenUnexistingArtisId_whenCallingGetLyricsById_thenFindByIdToRepositoryFails() {
        when(mockLyricsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(LyricsNotFoundException.class, () -> lyricsPersistencePort.getLyricsById(1L));
    }

    @Test
    void givenAnExistingParticipatingArtist_whenUpdateLyrics_thenUpdateLyrics() {
        final LyricsEntity testLyrics = LyricsEntity.builder()
                .participatingArtist(TEST_AUTHOR)
                .lyrics(TEST_LYRICS)
                .build();
        final LyricsDomain testLyricsDto = LyricsDomain.builder()
                .artist(TEST_AUTHOR)
                .lyrics(TEST_LYRICS_2)
                .build();
        when(mockLyricsRepository.findByParticipatingArtist(TEST_AUTHOR)).thenReturn(testLyrics);

        lyricsPersistencePort.updateLyrics(testLyricsDto);

        verify(mockLyricsRepository, times(1)).findByParticipatingArtist(TEST_AUTHOR);
        verify(mockLyricsRepository, times(1)).save(lyricsEntityArgumentCaptor.capture());
        verifyNoMoreInteractions(mockLyricsRepository);
        final LyricsEntity lyricsEntity = lyricsEntityArgumentCaptor.getValue();
        assertThat(lyricsEntity).isNotNull();
        assertThat(lyricsEntity.getParticipatingArtist()).isEqualTo(TEST_AUTHOR);
        assertThat(lyricsEntity.getLyrics()).isEqualTo(TEST_LYRICS_2);
    }

    @Test
    void givenAnExistingLyrics_whenUpdateLyrics_thenUpdateParticipatingArtist() {
        final LyricsEntity testLyrics = LyricsEntity.builder()
                .participatingArtist(TEST_AUTHOR)
                .lyrics(TEST_LYRICS_3)
                .build();
        final LyricsDomain testLyricsDto = LyricsDomain.builder()
                .artist(TEST_AUTHOR_2)
                .lyrics(TEST_LYRICS_3)
                .build();
        when(mockLyricsRepository.findByLyrics(TEST_LYRICS_3)).thenReturn(testLyrics);

        lyricsPersistencePort.updateLyrics(testLyricsDto);

        verify(mockLyricsRepository, times(1)).findByParticipatingArtist(TEST_AUTHOR_2);
        verify(mockLyricsRepository, times(1)).findByLyrics(TEST_LYRICS_3);
        verify(mockLyricsRepository, times(1)).save(lyricsEntityArgumentCaptor.capture());
        verifyNoMoreInteractions(mockLyricsRepository);
        final LyricsEntity lyricsEntity = lyricsEntityArgumentCaptor.getValue();
        assertThat(lyricsEntity).isNotNull();
        assertThat(lyricsEntity.getParticipatingArtist()).isEqualTo(TEST_AUTHOR_2);
        assertThat(lyricsEntity.getLyrics()).isEqualTo(TEST_LYRICS_3);
    }


}
