Backbone.View.prototype.close = function () {
    console.log('Closing view ' + this);
    if (this.beforeClose) {
        this.beforeClose();
    }
    this.remove();
    this.unbind();
};

function loadAllTemplates() {
    console.log("\tloadAllTemplates() start");
    tpl.loadTemplates([
        'playerinfos-template',
        'gameinfos-template',
        'player-template',
        'card-template',
        'playeractions-template',
        'login-template'
    ], function () {
        //Run app
        console.log("run app...");
    });
    console.log("\tloadAllTemplates() end");
};