Service = {
    search: (keyword, callback) => {
        $.getJSON('service/words/search/' + keyword, callback);
    },

    addSynonymToWord: (synonym, word, successCallback, errorCallback) => {
        $.post('service/words/addSynonymToWord', {
            word: word,
            synonym: synonym
        })
        .done(successCallback)
        .fail(errorCallback);
    },

    getSynonymsOfWord: (word, callback) => {
        $.getJSON('service/words/synonyms/' + word, callback);
    },

    addWord: (word, successCallback, erorCallback) => {
        $.post('service/words/add', {
            word: word
        })
        .done(successCallback)
        .fail(errorCallback);
    }
};