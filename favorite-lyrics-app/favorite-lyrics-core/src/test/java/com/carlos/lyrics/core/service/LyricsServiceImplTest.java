package com.carlos.lyrics.core.service;

import com.carlos.lyrics.domain.data.LyricsDomain;
import com.carlos.lyrics.domain.port.LyricsPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LyricsServiceImplTest {

    @InjectMocks
    private LyricsServiceImpl lyricsServicePort;

    @Mock
    private LyricsPersistencePort lyricsPersistencePort;

    @Mock
    private List<LyricsDomain> mockLyricsList;

    @Test
    public void givenLyrics_whenAdd_thenAddPortCalled() {
        final LyricsDomain mockLyrics = mock(LyricsDomain.class);

        lyricsServicePort.addLyrics(mockLyrics);

        verify(lyricsPersistencePort, only()).addLyrics(mockLyrics);
    }

    @Test
    public void givenLyrics_whenRemove_thenRemovePortCalled() {
        final LyricsDomain mockLyrics = mock(LyricsDomain.class);

        lyricsServicePort.removeLyrics(mockLyrics);

        verify(lyricsPersistencePort, only()).removeLyrics(mockLyrics);
    }


    @Test
    public void givenLyrics_whenUpdate_thenUpdateLyricsPortCalled() {
        final LyricsDomain mockLyrics = mock(LyricsDomain.class);

        lyricsServicePort.updateLyrics(mockLyrics);

        verify(lyricsPersistencePort, only()).updateLyrics(mockLyrics);
    }

    @Test
    public void givenCallToAllLyrics_whenNothingSpecified_thenGetAllLyricssPortCalled() {
        when(lyricsPersistencePort.getAll()).thenReturn(mockLyricsList);

        final List<LyricsDomain> allLyrics = lyricsServicePort.getAllLyrics();

        assertThat(allLyrics).isSameAs(mockLyricsList);
        verify(lyricsPersistencePort, only()).getAll();
    }

    @Test
    public void givenLyricsId_whenGetLyricssById_thenGetLyricsByIdPortCalled() {
        final Long testLyricsId = 1L;
        final LyricsDomain mockLyrics = mock(LyricsDomain.class);
        when(lyricsPersistencePort.getLyricsById(testLyricsId)).thenReturn(mockLyrics);

        final LyricsDomain lyrics = lyricsServicePort.getLyricsById(testLyricsId);

        assertThat(lyrics).isSameAs(mockLyrics);
        verify(lyricsPersistencePort, only()).getLyricsById(testLyricsId);
    }

}
