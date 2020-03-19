package com.carlos.lyrics;

import com.carlos.lyrics.domain.data.LyricsDomain;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LyricsDemoApplicationLauncherTest {

    private static final String TEST_ARTIST_NAME = "Florence and the Machine";
    private static final String TEST_LYRICS = "Sweet Nothings";
    private static final String TEST_LYRICS_2 = "You've got the love I need to see me through";

    @Autowired
    private MockMvc mvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void givenLyrics_whenAddAndUpdateAndThenRemoveLyrics_thenEntity() throws Exception {
        final LyricsDomain testLyrics = LyricsDomain.builder()
                .artist(TEST_ARTIST_NAME)
                .lyrics(TEST_LYRICS)
                .build();

        mvc.perform(MockMvcRequestBuilders.post("/lyrics")
                .content(objectMapper.writeValueAsString(testLyrics))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isCreated());

        mvc.perform(MockMvcRequestBuilders.get("/lyrics/6")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.artist").exists())
                .andExpect(jsonPath("$.artist").value(TEST_ARTIST_NAME))
                .andExpect(jsonPath("$.lyrics").exists())
                .andExpect(jsonPath("$.lyrics").value(TEST_LYRICS));

        testLyrics.setLyrics(TEST_LYRICS_2);

        mvc.perform(MockMvcRequestBuilders.put("/lyrics")
                .content(objectMapper.writeValueAsString(testLyrics))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders.get("/lyrics/6")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.artist").exists())
                .andExpect(jsonPath("$.artist").value(TEST_ARTIST_NAME))
                .andExpect(jsonPath("$.lyrics").exists())
                .andExpect(jsonPath("$.lyrics").value(TEST_LYRICS_2));

        mvc.perform(MockMvcRequestBuilders.delete("/lyrics")
                .content(objectMapper.writeValueAsString(testLyrics))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders.get("/lyrics/6")
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.artist").doesNotExist())
                .andExpect(jsonPath("$.lyrics").doesNotExist());
    }

    @Test
    void givenCallToAllLyrics_whenNoParams_thenFindAll() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/lyrics")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].artist").exists())
                .andExpect(jsonPath("$[0].artist").value("Kurt"))
                .andExpect(jsonPath("$[0].lyrics").exists())
                .andExpect(jsonPath("$[0].lyrics").value("The man who sold the world"))
                .andExpect(jsonPath("$[1].artist").exists())
                .andExpect(jsonPath("$[1].artist").value("Paul Banks"))
                .andExpect(jsonPath("$[1].lyrics").exists())
                .andExpect(jsonPath("$[1].lyrics").value("Say Hello to the Angels"))
                .andExpect(jsonPath("$[2].artist").exists())
                .andExpect(jsonPath("$[2].artist").value("Alex Turner"))
                .andExpect(jsonPath("$[2].lyrics").exists())
                .andExpect(jsonPath("$[2].lyrics").value("Number One Party Anthem"))
                .andExpect(jsonPath("$[3].artist").exists())
                .andExpect(jsonPath("$[3].artist").value("Julian Casablancas"))
                .andExpect(jsonPath("$[3].lyrics").exists())
                .andExpect(jsonPath("$[3].lyrics").value("Heart in the cage"))
                .andExpect(jsonPath("$[4].artist").exists())
                .andExpect(jsonPath("$[4].artist").value("Chris Cornell"))
                .andExpect(jsonPath("$[4].lyrics").exists())
                .andExpect(jsonPath("$[4].lyrics").value("Shadow in the sun"));
    }

    @Test
    void givenArtisId_whenCallingGetLyricsById_thenReturnsLyrics() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/lyrics/1")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.artist").exists())
                .andExpect(jsonPath("$.artist").value("Kurt"))
                .andExpect(jsonPath("$.lyrics").exists())
                .andExpect(jsonPath("$.lyrics").value("The man who sold the world"));
    }

    @Test
    void givenUnexistingArtisId_whenCallingGetLyricsById_thenIsNotFound() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/lyrics/7")
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.artist").doesNotExist())
                .andExpect(jsonPath("$.lyrics").doesNotExist());
    }




}
