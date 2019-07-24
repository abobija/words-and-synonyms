Modal = function(options) {
    const self = this;
    this.$modal = null;

    options = $.extend(true, {
        title: 'Modal',
        content: null,
        ok: {
            title: 'OK',
            class: 'btn-primary',
            callback: (modal) => {}
        },
        cancel: {
            title: 'Cancel',
            callback: (modal) => { modal.close(); }
        }
    }, options);
    
    this.show = (shown) => {
        shown = shown || (() => {});

        const $header = $('<div class="modal-header" />')
            .append($('<h5 class="modal-title"/>').html(options.title))
            .append($('<button type="button" class="close" />')
                .append($('<span aria-hidden="true"/>').html('&times;'))
                .click(() => self.close()));

        const $body = $('<div class="modal-body" >')
            .append(options.content);

        const $footer = $('<div class="modal-footer" />');


        if(options.ok != null) {
            $footer.append(
                $('<button type="button" class="btn" />')
                    .addClass('btn')
                    .addClass(options.ok.class || 'btn-default')
                    .html(options.ok.title)
                    .click(() => options.ok.callback(self))
            );
        }

        if(options.cancel != null) {
            $footer.append(
                $('<button type="button" class="btn" />')
                    .addClass('btn')
                    .addClass(options.cancel.class || 'btn-default')
                    .html(options.cancel.title)
                    .click(() => options.cancel.callback(self))
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

        self.$modal.on('shown.bs.modal', e => {
            shown(self);
        });

        self.$modal.modal();
    };

    this.close = () => {
        if(self.$modal != null) {
            self.$modal.modal('hide');
            $('body').removeClass('modal-open');
            $('.modal-backdrop').remove();
            self.$modal.remove();
            self.$modal = null;
        }
    };
};