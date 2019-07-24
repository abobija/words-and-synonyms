package com.alijabobija;

import com.alijabobija.Entity.Word;
import com.alijabobija.Service.WordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WordServiceTest {
    @Autowired
    private WordService wordService;

    @Test
    /**
     * Comparing two identically words
     */
    public void compareTwoMatchingWords_CaseSensitive() {
        assertThat(
                new Word("Clean").like("Clean")
        ).isEqualTo(true);
    }

    @Test
    /**
     * Comparing two words which only difference is that the first one is capitalize
     * and second one is all-lower-letter word
     */
    public void compareTwoMatchingWords_CaseNotSensitive() {
        assertThat(
                new Word("Clean").like("clean")
        ).isEqualTo(true);
    }

    @Test
    /**
     * Comparing two words
     * First word contains second one
     */
    public void compareTwoMatchingWords_PartOfWord() {
        assertThat(
                new Word("Clean").like("lea")
        ).isEqualTo(true);
    }

    @Test
    /**
     * Test for successfully adding new word
     */
    public void addWordTest_Success() throws Exception  {
        wordService.addWord("Programming");
    }

    @Test(expected = Exception.class)
    /**
     * Test which will fail in time of adding new word
     */
    public void addWordTest_DuplicateWordRestriction() throws Exception {
        wordService.addWord("Test");
        wordService.addWord("Test");
    }

    @Test
    /**
     * Adding new synonym for existing word
     */
    public void addSynonymForWord_Success() throws Exception {
        wordService.addWord("Eat_X");
        wordService.addSynonymForWord("Eat_X", "Lunch");

        assertThat(wordService.synonyms("Eat_X").size() > 0)
                .isEqualTo(true);
    }

    @Test(expected = Exception.class)
    /**
     * Trying to add synonym to non-existing word
     */
    public void addSynonymForWord_NonExistingWord() throws Exception {
        wordService.addSynonymForWord("NonExistingWord", "Synonym");
    }
}
