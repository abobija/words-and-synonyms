package com.alijabobija.Controller;

import com.alijabobija.Entity.Word;
import com.alijabobija.Service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for manipulating with Words
 *
 * Responses will be encoded and represented in JSON format
 */
@RestController
@RequestMapping("/service/words")
public class WordController {
    @Autowired
    private WordService wordService;

    @RequestMapping(value = "/search/{keyword}", method = RequestMethod.GET)
    public List<Word> search(@PathVariable("keyword") String keyword) {
        return wordService.search(keyword);
    }

    @RequestMapping(value = "/synonyms/{word}", method = RequestMethod.GET)
    public List<Word> synonyms(@PathVariable("word") String word) {
        return wordService.synonyms(word);
    }

    @RequestMapping(value = "/addSynonymToWord", method = RequestMethod.POST)
    public Word addSynonymToWord(@RequestParam String word, @RequestParam String synonym) throws Exception {
        return wordService.addSynonymForWord(word, synonym);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Word add(@RequestParam String word) throws Exception {
        return wordService.addWord(word);
    }
}
