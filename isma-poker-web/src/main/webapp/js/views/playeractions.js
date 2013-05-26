PlayerActionsView = Backbone.View.extend({
    initialize: function (attrs) {
        this.template = playeractions_template;
        this.model = attrs.model;
        this.$el.html(this.template);

        this.sitoutButton = $("#sitout-button");
        this.foldButton = $("#fold-button");
        this.smallBlindButton = $("#smallblind-button");
        this.bigBlindButton = $("#bigblind-button");
        this.checkButton = $("#check-button");
        this.callButton = $("#call-button");
        this.betButton = $("#bet-button");
        this.raiseButton = $("#raise-button");
        this.allInButton = $("#allin-button");
        this.showButton = $("#show-button");

        this.chipsNumberField = $("#chips-amount");

        this.render();
        this.model.on('change', this.render, this);
    },
    events: {
        "click #sitout-button": "sitOut",
        "click #fold-button": "fold",
        "click #smallblind-button": "smallblind",
        "click #bigblind-button": "bigblind",
        "click #check-button": "check",
        "click #call-button": "call",
        "click #bet-button": "bet",
        "click #raise-button": "raise",
        "click #allin-button": "allin",
        "click #show-button": "show"
    },

    sitOut: function () {
        alert("todo sitout");
    },
    smallblind: function () {
        game.paySmallBlind(models['login'].get('nickname'));
    },
    bigblind: function () {
        game.payBigBlind(models['login'].get('nickname'));
    },
    check: function () {
        game.check(models['login'].get('nickname'));
    },
    call: function () {
        game.call(models['login'].get('nickname'));
    },
    bet: function () {
        var chips = this.chipsNumberField.val();
        if (chips <= 0) {
            alert("Enter a bet amount !");
        } else {
            game.bet(models['login'].get('nickname'), chips);
        }
    },
    raise: function () {
        var chips = this.chipsNumberField.val();
        if (chips <= 0) {
            alert("Enter a raise amount !");
        } else {
            game.raise(models['login'].get('nickname'), chips);
        }
    },
    allin: function () {
        game.allin(models['login'].get('nickname'));
    },
    fold: function () {
        game.fold(models['login'].get('nickname'));
    },
    show: function () {
        game.show(models['login'].get('nickname'));
    },

    render: function () {
        console.info("todo actions rendering : " + this.model.get('actions'));
        this.renderButtonAction('SIT_OUT', this.sitoutButton);
        this.renderButtonAction('FOLD', this.foldButton);
        this.renderButtonAction('PAY_SMALL_BLIND', this.smallBlindButton);
        this.renderButtonAction('PAY_BIG_BLIND', this.bigBlindButton);
        this.renderButtonAction('CHECK', this.checkButton);
        this.renderButtonAction('CALL', this.callButton);
        this.renderButtonAction('BET', this.betButton);
        this.renderButtonAction('RAISE', this.raiseButton);
        this.renderButtonAction('ALLIN', this.allInButton);
        this.renderButtonAction('SHOW', this.showButton);

        if ($.inArray('BET', this.model.get('actions')) != -1 ||
            $.inArray('RAISE', this.model.get('actions')) != -1) {
            this.chipsNumberField.show();
        } else {
            this.chipsNumberField.hide();
        }
    },
    renderButtonAction: function (action, button) {
        if ($.inArray(action, this.model.get('actions')) != -1) {
            button.show();
        } else {
            button.hide();
        }
    }
});