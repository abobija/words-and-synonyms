package com.alijabobija.Dao;

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
    private static List<int[]> relations;

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
        relations.add(new int[] { 0, 1 });

        // Make Create as synonym of Make
        relations.add(new int[] { 5, 4 });
    }

    /**
     * Find word index in database
     *
     * @param word word which need to be found
     * @return index of founded word or -1 if nothing is found
     */
    public int findWordIndex(String word) {
        for(int i = 0; i < words.size(); i++) {
            if(words.get(i).equals(word)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Insert word into database
     *
     * @param word value of word
     */
    public Word addWord(String word) throws Exception {
        if(findWordIndex(word) != -1) {
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
     * @return return Word object of created synonym
     * @throws Exception Trows exception if word does not exist in database
     */
    public Word addSynonymForWord(String word, String synonym) throws Exception {
        int wordIndex = findWordIndex(word);

        if(wordIndex == -1) {
            throw new Exception(String.format("Word <%s> does not exist in database", word));
        }

        int synonymIndex = findWordIndex(synonym);

        if(synonymIndex == -1) {
            addWord(synonym);
            synonymIndex = findWordIndex(synonym);
        }

        // TODO:
        // Before creating relation, first check if relation already exists?

        relations.add(new int[]{ wordIndex, synonymIndex });

        return words.get(synonymIndex);
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
     * Searching for synonyms of a word
     *
     * @param word value of word
     * @return List of synonyms for a word
     */
    public List<Word> synonyms(String word) {
        List<Word> results = new LinkedList<>();

        int wordIndex = findWordIndex(word);

        if(wordIndex != -1) {
            for(int[] rel : relations) {
                if(rel[0] == wordIndex) {
                    results.add(words.get(rel[1]));
                } else if(rel[0] == wordIndex) {
                    results.add(words.get(rel[1]));
                }
            }
        }

        return results;
    }
}
