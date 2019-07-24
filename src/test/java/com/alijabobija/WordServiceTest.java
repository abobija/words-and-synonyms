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

    /**
     * Comparing two identically words
     */
    @Test
    public void compareTwoMatchingWords_CaseSensitive() {
        assertThat(new Word("Clean").like("Clean")).isTrue();
    }

    /**
     * Comparing two words which only difference is that the first one is capitalize
     * and second one is all-lower-letter word
     */
    @Test
    public void compareTwoMatchingWords_CaseNotSensitive() {
        assertThat(new Word("Clean").like("clean")).isTrue();
    }

    /**
     * Comparing two words
     * First word contains second one
     */
    @Test
    public void compareTwoMatchingWords_PartOfWord() {
        assertThat(new Word("Clean").like("lea")).isTrue();
    }

    /**
     * Test for successfully adding new word
     */
    @Test
    public void addWordTest_Success() throws Exception  {
        Word word = wordService.addWord("Programming");
        
        assertThat(word.getValue().equals("Programming")).isTrue();
    }

    /**
     * Test which will fail in time of adding new word
     */
    @Test(expected = Exception.class)
    public void addWordTest_DuplicateWordRestriction() throws Exception {
        wordService.addWord("Test");
        wordService.addWord("Test");
    }

    /**
     * Adding new synonym for existing word
     */
    @Test
    public void addSynonymForWord_Success() throws Exception {
        wordService.addWord("Eat_X");
        wordService.addSynonymForWord("Eat_X", "Lunch");

        assertThat(wordService.synonyms("Eat_X").size() == 1).isTrue();
    }

    /**
     * Trying to add synonym to non-existing word
     */
    @Test(expected = Exception.class)
    public void addSynonymForWord_NonExistingWord() throws Exception {
        wordService.addSynonymForWord("NonExistingWord", "Synonym");
    }

    /**
     * Testing if word has added synonym
     */
    @Test
    public void wordHasAddedSynonym() throws Exception {
    	Word word = wordService.addWord("Super");
    	Word synonym = wordService.addSynonymForWord(word, "Awesome");
    	
    	assertThat(wordService.synonyms(word).contains(synonym)).isTrue();
    }

    /**
     * Test if synonym of word has word as synonym as well
     */
    @Test
    public void synonymOfWordHasWordAsSynonym() throws Exception {
    	Word word = wordService.addWord("Conversation");
    	Word synonym = wordService.addSynonymForWord(word, "Talk");
    	
    	assertThat(wordService.synonyms(synonym).contains(word)).isTrue();
    }
    
    /**
     * Tests if synonyms of word is synonyms to each other at the same time
     * @throws Exception
     */
    @Test
    public void oneSynonymOfWordIsSynonymForOtherSynonymOfWord() throws Exception {
    	Word word = wordService.addWord("Appearance");
    	Word synonym1 = wordService.addSynonymForWord(word, "Demonstration");
    	Word synonym2 = wordService.addSynonymForWord(word, "Presentation");
    	
    	assertThat(wordService.synonyms(synonym2).contains(synonym1)).isTrue();
    }
}
