package com.alijabobija.Dao;

import com.alijabobija.Entity.Relation;
import com.alijabobija.Entity.Word;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

/**
 * Repository class for Word(s)
 *
 * Functionality of this class is written in way
 * that all data (words in this case) will be stored
 * in memory
 */
@Repository
public class WordDao {
    /**
     * Words collection
     */
    private static List<Word> words;

    /**
     * Relations between words
     * representing relations between words and synonyms
     */
    private static List<Relation> relations;

    /**
     * Insert some dummy default data...
     *
     * This part of code will generate structure of
     * words and synonyms like this:
     *
     * -----------------------
     * [0] Clean
     *      | [1] Wash
     * [1] Wash
     * [2] Run
     * [3] Build
     * [4] Create
     * [5] Make
     *      | [4] Create
     *      | [3] Build
     * -----------------------
     */
    static {
        words = new LinkedList<>();
        relations = new LinkedList<>();

        // Insert some dummy data
        
        words.add(new Word("Clean"));
        words.add(new Word("Wash"));
        words.add(new Word("Run"));
        words.add(new Word("Build"));
        words.add(new Word("Create"));
        words.add(new Word("Make"));

        // Make Wash as synonym of Clean
        addRelation(words.get(0), words.get(1));
        
        // Make Build as synonym of Make
        addRelation(words.get(5), words.get(3));

        // Make Create as synonym of Make
        addRelation(words.get(5), words.get(4));
    }
    
    private static void addRelation(Word w1, Word w2) {
        // TODO:
        // Before creating relation, first check if relation already exists?

        relations.add(new Relation(w1, w2));
    }

    /**
     * Find word in database
     *
     * @param word - word which need to be found
     * @return founded word or null if nothing is found
     */
    public Word findWord(String word) {
    	for(Word w : words) {
    		if(w.equals(word)) {
    			return w;
    		}
    	}

        return null;
    }

    /**
     * Insert word into database
     *
     * @param word value of word
     * @return added word
     */
    public Word addWord(String word) throws Exception {
        if(findWord(word) != null) {
            throw new Exception(String.format("Word <%s> already exist in database", word));
        }
        
        Word _word = new Word(word);
        words.add(_word);

        return _word;
    }

    /**
     * Attach synonym to word
     *
     * @param word value of word
     * @param synonym value of synonym
     * @return Word object of created synonym
     * @throws Exception If word does not exist in database
     */
    public Word addSynonymForWord(String word, String synonym) throws Exception {
        Word dbWord = findWord(word);
        
        if(dbWord == null) {
            throw new Exception(String.format("Word <%s> does not exist in database", word));
        }
        
        return addSynonymForWord(dbWord, synonym);
    }
    
    /**
     * 
     * @param word word
     * @param synonym synonym that needs to be added
     * @return Word object of created synonym
     * @throws Exception If word does not exist in database
     */
    public Word addSynonymForWord(Word word, String synonym) throws Exception {
    	Word dbSynonym = findWord(synonym);
        
        if(dbSynonym == null) {
            addWord(synonym);
            dbSynonym = findWord(synonym);
        }
        
        addRelation(word, dbSynonym);

        return dbSynonym;
    }

    /**
     * Search for words by keyword
     *
     * @param keyword searching pattern
     * @return list of matching words
     */
    public List<Word> search(String keyword) {
        List<Word> results = new LinkedList<>();

        for(Word w : words) {
            if(w.like(keyword)) {
                results.add(w);
            }
        }

        return results;
    }
    
    /**
     * 
     * @param word - Word of which synonyms will be searched
     * @return List of synonyms of word
     */
    public List<Word> synonyms(Word word) {
    	List<Word> results = new LinkedList<>();
    	
    	if(word != null) {
    		for(Relation rel : relations) {
                if(rel.getWord1() == word) {
                    results.add(rel.getWord2());
                } else if(rel.getWord2() == word) {
                    results.add(rel.getWord1());
                }
            }
    	}
    	
        return results;
    }

    /**
     * Searching for synonyms of a word
     *
     * @param word value of word
     * @return List of synonyms for a word
     */
    public List<Word> synonyms(String word) {
        return synonyms(findWord(word));
    }
}
