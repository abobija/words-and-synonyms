package com.alijabobija.Service;

import com.alijabobija.Dao.WordDao;
import com.alijabobija.Entity.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for Word entity
 */
@Service
public class WordService {
    @Autowired
    private WordDao wordDao;
    
    /**
     * Add new word to database
     * @param word Word that needs to be added
     * @return Added word
     * @throws Exception
     */
    public Word addWord(String word) throws Exception {
        return wordDao.addWord(word);
    }
    
    /**
     * Add synonym for word
     * @param word 
     * @param synonym
     * @return Object Word of added synonym
     * @throws Exception
     */
    public Word addSynonymForWord(Word word, String synonym) throws Exception {
    	return wordDao.addSynonymForWord(word, synonym);
    }

    /**
     * Add synonym for word
     * @param word 
     * @param synonym
     * @return Object Word of added synonym
     * @throws Exception
     */
    public Word addSynonymForWord(String word, String synonym) throws Exception {
        return wordDao.addSynonymForWord(word, synonym);
    }
    
    /**
     * Find word
     * @param word - word that needs to be found
     * @return Word or null if nothing is found
     */
    public Word findWord(String word) {
    	return wordDao.findWord(word);
    }
    
    /**
     * Search for words that match keyword
     * @param keyword
     * @return List of founded words
     */
    public List<Word> search(String keyword) {
        return wordDao.search(keyword);
    }
    
    /**
     * Get synonyms for word
     * @param word
     * @return List of synonyms
     */
    public List<Word> synonyms(Word word) {
    	return wordDao.synonyms(word);
    }
    
    /**
     * Get synonyms for word
     * @param word
     * @return List of synonyms
     */
    public List<Word> synonyms(String word) {
        return wordDao.synonyms(word);
    }
}
