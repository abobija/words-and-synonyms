/**
 * Main script fof application which
 * purpose is to initialize UI and send requests to service
 * on button click events etc...
 *
 * Script depends on
 *  - jQuery
 *  - Bootstrap
 */

(function($) {
    var ENTER = 13;
    var SPACE = 32;

    var isSearcherBusy = false;
    var $search = null;
    var $searchResultsList = null;
    var $notInDatabaseInfo = null;

    var service = new function() {
        this.search = function(keyword, callback) {
            $.getJSON('service/words/search/' + keyword, function(response) {
                callback(response);
            });
        };

        this.addSynonymToWord = function(synonym, word, successCallback, errorCallback) {
            $.post('service/words/addSynonymToWord', {
                word: word,
                synonym: synonym
            }).done(function(response) {
                successCallback(response);
            }).fail(function(e) {
                errorCallback(e);
            });
        };

        this.getSynonymsOfWord = function(word, callback) {
            $.getJSON('service/words/synonyms/' + word, function(response) {
                callback(response);
            });
        };

        this.addWord = function(word, successCallback, erorCallback) {
            $.post('service/words/add', {
                word: word
            }).done(function(response) {
                successCallback(response);
            }).fail(function(e) {
                errorCallback(e);
            });
        };
    };

    /**
     * Search service for word
     *
     * @param keyword - Word for search
     */
    var search = function(keyword) {
        keyword = keyword.trim();

        if(keyword.length > 0) {
            isSearcherBusy = true;
            $searchResultsList.empty();
            $notInDatabaseInfo.hide();

            service.search(keyword, function(response) {
                var wordIsInDatabase = false;

                $.each(response, function() {
                    var wordVal = this.value;

                    if(keyword.toLowerCase() == wordVal.toLowerCase()) {
                        wordIsInDatabase = true;
                    }

                    var $li = $('<li />');
                    var $ahref = $('<a href="#" />').html(wordVal);
                    var loaded = false;

                    $ahref.click(function() {
                        if(! loaded) {
                            var $synonymsList = $('<ul />');
                            var $addSynonym = $('<a href="#" />').html('Add new synonym...');

                            $li.append($synonymsList);

                            $synonymsList.append(
                                $('<li />').append($addSynonym.click(function() {
                                    var $synonym = $('<input type="text" class="form-control" placeholder="Synonym..." />');

                                    new _modal({
                                        title: 'New synonym',
                                        content: $('<div />')
                                            .append($('<p />').html('Synonym for "' + wordVal + '"'))
                                            .append($synonym),
                                        ok: {
                                            callback: function(mdl) {
                                                var synonym = $synonym.val().trim();

                                                if(synonym.length < 2) {
                                                    alert('Synonym should be wide at least 2 characters');
                                                } else {
                                                    mdl.close();

                                                    service.addSynonymToWord(
                                                        synonym,
                                                        wordVal,
                                                        function(response) {
                                                            $('<li />')
                                                                .append($('<span />').html(response.value))
                                                                .insertBefore($addSynonym);
                                                        },
                                                        function(e) {
                                                            alert(e.responseJSON.message);
                                                        }
                                                    );
                                                }
                                            }
                                        }
                                    }).show(function() {
                                        $synonym.focus();
                                    });
                                }))
                            );

                            service.getSynonymsOfWord(wordVal, function(response) {
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
                            var $word = $('<input type="text" class="form-control" value="' + keyword + '" />');

                            new _modal({
                                title: 'New word',
                                content: $('<div />')
                                    .append($('<p />').html('Type value of new word...'))
                                    .append($word),
                                ok: {
                                    callback: function(mdl) {
                                        var _word = $word.val().trim();

                                        if(_word.length < 2) {
                                            alert('Word should be wide at least 2 characters');
                                        } else {
                                            mdl.close();

                                            service.addWord(
                                                _word,
                                                function(response) {
                                                    $search.val('');
                                                    search(response.value);
                                                },
                                                function(e) {
                                                    alert(e.responseJSON.message);
                                                }
                                            );
                                        }
                                    }
                                }
                            }).show(function() {
                                $word.focus();
                            });
                        }))
                        .show();
                }

                isSearcherBusy = false;
            });
        }
    };

    /**
     * Attaching events etc...
     */
    $(document).ready(function() {
        $search = $('#search').focus();
        $searchResultsList = $('#searchResultsList');
        $notInDatabaseInfo = $('#notInDatabaseInfo');

        $search.keypress(function(e) {
            if(e.keyCode == SPACE) {
                e.preventDefault();
                return;
            }

            if(e.keyCode == ENTER && ! isSearcherBusy) {
                search($search.val());
            }
        });
    });

    var _modal = function(options) {
        var self = this;
        this.$modal = null;

        options = $.extend(true, {
            title: 'Modal',
            content: null,
            ok: {
                title: 'OK',
                class: 'btn-primary',
                callback: function(_modal) {}
            },
            cancel: {
                title: 'Cancel',
                callback: function(_modal) { _modal.close(); }
            }
        }, options);


        this.show = function(shown) {
            shown = shown || function(){};

            var $header = $('<div class="modal-header" />')
                .append($('<h5 class="modal-title"/>').html(options.title))
                .append($('<button type="button" class="close" />')
                    .append($('<span aria-hidden="true"/>').html('&times;'))
                    .click(function() { self.close(); }));

            var $body = $('<div class="modal-body" >')
                .append(options.content);

            var $footer = $('<div class="modal-footer" />');


            if(options.ok != null) {
                $footer.append(
                    $('<button type="button" class="btn" />')
                        .addClass('btn')
                        .addClass(options.ok.class || 'btn-default')
                        .html(options.ok.title)
                        .click(function () {
                            options.ok.callback(self);
                        })
                );
            }

            if(options.cancel != null) {
                $footer.append(
                    $('<button type="button" class="btn" />')
                        .addClass('btn')
                        .addClass(options.cancel.class || 'btn-default')
                        .html(options.cancel.title)
                        .click(function () {
                            options.cancel.callback(self);
                        })
                );
            }

            self.$modal = $('<div class="modal fade" tabindex="-1" role="dialog" />')
                .append($('<div class="modal-dialog" role="document" />')
                    .append($('<div class="modal-content" />')
                        .append($header)
                        .append($body)
                        .append($footer)
                    ));

            $('body').append(self.$modal);

            self.$modal.on('shown.bs.modal', function (e) {
                shown(self);
            });

            self.$modal.modal();
        };

        this.close = function() {
            if(self.$modal != null) {
                self.$modal.modal('hide');
                $('body').removeClass('modal-open');
                $('.modal-backdrop').remove();
                self.$modal.remove();
                self.$modal = null;
            }
        };
    };
})(jQuery);