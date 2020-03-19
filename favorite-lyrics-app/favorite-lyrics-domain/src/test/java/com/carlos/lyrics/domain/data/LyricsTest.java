package com.carlos.lyrics.domain.data;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LyricsTest {

    @Test
    public void givenName_whenLyricsWithName_thenNameIsSet() {
        final LyricsDomain lyricsDto = LyricsDomain.builder()
                .artist("Lady Gaga")
                .lyrics("You can read my Pokerface")
                .build();

        assertThat(lyricsDto.getArtist()).isEqualTo("Lady Gaga");
        assertThat(lyricsDto.getLyrics()).isEqualTo("You can read my Pokerface");
    }

    @Test
    public void givenTwoLyrics_whenSameNameAndLyrics_thenEqual() {
        final LyricsDomain lyricsDto1 = LyricsDomain.builder()
                .artist("Freddy Mercury")
                .lyrics("We are the champions")
                .build();
        final LyricsDomain lyricsDto2 = LyricsDomain.builder()
                .artist("Freddy Mercury")
                .lyrics("We are the champions")
                .build();

        assertThat(lyricsDto1).isEqualTo(lyricsDto2);
    }

    @Test
    public void givenTwoLyrics_whenDifferentParticipatingArtist_thenNotEqual() {
        final LyricsDomain lyricsDto1 = LyricsDomain.builder()
                .artist("Ariana Grande")
                .lyrics("I've got one less problem")
                .build();
        final LyricsDomain lyricsDto2 = LyricsDomain.builder()
                .artist("Iggy Azalea")
                .lyrics("I've got one less problem")
                .build();

        assertThat(lyricsDto1).isNotEqualTo(lyricsDto2);
    }

    @Test
    public void givenTwoLyrics_whenDifferentLyrics_thenNotEqual() {
        final LyricsDomain lyricsDto1 = LyricsDomain.builder()
                .artist("Ariana Grande")
                .lyrics("I've got one less problem")
                .build();
        final LyricsDomain lyricsDto2 = LyricsDomain.builder()
                .artist("Ariana Grande")
                .lyrics("don't call me angerl")
                .build();

        assertThat(lyricsDto1).isNotEqualTo(lyricsDto2);
    }

    @Test
    public void givenTwoLyrics_whenSameParticipatingArtistSameLyrics_thenSameHashCode() {
        final LyricsDomain lyricsDto1 = LyricsDomain.builder()
                .artist("Cardi B")
                .lyrics("Bloody shoes")
                .build();
        final LyricsDomain lyricsDto2 = LyricsDomain.builder()
                .artist("Cardi B")
                .lyrics("Bloody shoes")
                .build();

        assertThat(lyricsDto1.hashCode()).isEqualTo(lyricsDto2.hashCode());
    }

    @Test
    public void givenTwoLyrics_whenDifferentParticipatingArtist_thenNotSameHashCode() {
        final LyricsDomain lyricsDto1 = LyricsDomain.builder()
                .artist("BTS")
                .lyrics("boy with luv")
                .build();
        final LyricsDomain lyricsDto2 = LyricsDomain.builder()
                .artist("Halsey")
                .lyrics("boy with luv")
                .build();

        assertThat(lyricsDto1.hashCode()).isNotEqualTo(lyricsDto2.hashCode());
    }

    @Test
    public void givenLyrics_whenToString_thenSeeADescriptiveString() {
        final LyricsDomain lyricsDto1 = LyricsDomain.builder()
                .artist("Ed Sheeran")
                .lyrics("I'm in love with the shape of you")
                .build();

        assertThat(lyricsDto1.toString()).isEqualTo("LyricsDomain(lyrics=I'm in love with the shape of you, artist=Ed Sheeran)");
    }





}
