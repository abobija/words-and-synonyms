/**
 * Main script fof application which
 * purpose is to initialize UI and send requests to service
 * on button click events etc...
 *
 * Script depends on
 *  - jQuery
 *  - Bootstrap
 *  - ./modal
 */

const KEY_ENTER = 13;
const KEY_SPACE = 32;

let isSearcherBusy = false;

const $search = $('#search').focus();
const $searchResultsList = $('#searchResultsList');
const $notInDatabaseInfo = $('#notInDatabaseInfo');

/**
 * Attaching events etc...
 */

$search.keypress(e => {
    if(e.keyCode == KEY_SPACE) {
    	return e.preventDefault();
    }

    if(e.keyCode == KEY_ENTER && ! isSearcherBusy) {
        search($search.val());
    }
});

/**
 * Search service for word
 *
 * @param keyword - Word for search
 */
var search = keyword => {
    keyword = keyword.trim();

    if(keyword.length > 0) {
        isSearcherBusy = true;
        $searchResultsList.empty();
        $notInDatabaseInfo.hide();

        Service.search(keyword, response => {
            let wordIsInDatabase = false;

            $.each(response, function() {
                const wordVal = this.value;

                if(keyword.toLowerCase() == wordVal.toLowerCase()) {
                    wordIsInDatabase = true;
                }

                const $li = $('<li />');
                const $ahref = $('<a href="#" />').html(wordVal);
                let loaded = false;

                $ahref.click(() => {
                    if(! loaded) {
                        const $synonymsList = $('<ul />');
                        const $addSynonym = $('<a href="#" />').html('Add new synonym...');

                        $li.append($synonymsList);

                        $synonymsList.append(
                            $('<li />').append($addSynonym.click(() => {
                                const $synonym = $('<input type="text" class="form-control" placeholder="Synonym..." />');

                                new Modal({
                                    title: 'New synonym',
                                    content: $('<div />')
                                        .append($('<p />').html('Synonym for "' + wordVal + '"'))
                                        .append($synonym),
                                    ok: {
                                        callback: mdl => {
                                            const synonym = $synonym.val().trim();

                                            if(synonym.length < 2) {
                                                alert('Synonym should be wide at least 2 characters');
                                            } else {
                                                mdl.close();

                                                Service.addSynonymToWord(
                                                    synonym,
                                                    wordVal,
                                                    response => {
                                                        $('<li />')
                                                            .append($('<span />').html(response.value))
                                                            .insertBefore($addSynonym);
                                                    },
                                                    e => alert(e.responseJSON.message)
                                                );
                                            }
                                        }
                                    }
                                }).show(() => $synonym.focus());
                            }))
                        );

                        Service.getSynonymsOfWord(wordVal, response => {
                            $.each(response, function() {
                                $synonymsList.prepend(
                                    $('<li />').append($('<span />').html(this.value))
                                );
                            });
                        });

                        loaded = true;
                    }
                });

                $searchResultsList.append(
                    $li.append($ahref)
                );
            });

            if(! wordIsInDatabase && keyword.length > 1) {
                $notInDatabaseInfo
                    .empty()
                    .append('Word ')
                    .append($('<strong />').html(keyword))
                    .append(' has not been added, yet. ')
                    .append($('<a href="#" />').html('Add now...').click(function() {
                        const $word = $('<input type="text" class="form-control" value="' + keyword + '" />');

                        new Modal({
                            title: 'New word',
                            content: $('<div />')
                                .append($('<p />').html('Type value of new word...'))
                                .append($word),
                            ok: {
                                callback: mdl => {
                                    const _word = $word.val().trim();

                                    if(_word.length < 2) {
                                        alert('Word should be wide at least 2 characters');
                                    } else {
                                        mdl.close();

                                        Service.addWord(
                                            _word,
                                            response => {
                                                $search.val('');
                                                search(response.value);
                                            },
                                            e => alert(e.responseJSON.message)
                                        );
                                    }
                                }
                            }
                        }).show(() => $word.focus());
                    }))
                    .show();
            }

            isSearcherBusy = false;
        });
    }
};