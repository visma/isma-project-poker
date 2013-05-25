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

        this.render();
        this.model.on('change', this.render, this);
    },
    events: {
        "click #sitout-button": "sitOut",
        "click #fold-button": "todo",
        "click #smallblind-button": "smallblind",
        "click #bigblind-button": "bigblind",
        "click #check-button": "todo",
        "click #call-button": "todo",
        "click #bet-button": "todo",
        "click #raise-button": "todo",
        "click #allin-button": "todo",
        "click #show-button": "todo"
    },

    sitOut : function(){
        alert("todo sitout");
    },
    smallblind : function(){
        game.paySmallBlind(models['login'].get('nickname'));
    },
    bigblind : function(){
        game.payBigBlind(models['login'].get('nickname'));
    },
    todo : function(){
        alert("todo X");
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
    },
    renderButtonAction: function (action, button) {
        if ($.inArray(action, this.model.get('actions')) != -1) {
            button.show();
        } else {
            button.hide();
        }
    }
});