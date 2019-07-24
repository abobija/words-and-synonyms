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

    public Word addWord(String word) throws Exception {
        return wordDao.addWord(word);
    }

    public Word addSynonymForWord(String word, String synonym) throws Exception {
        return wordDao.addSynonymForWord(word, synonym);
    }

    public List<Word> search(String keyword) {
        return wordDao.search(keyword);
    }

    public List<Word> synonyms(String word) {
        return wordDao.synonyms(word);
    }
}
