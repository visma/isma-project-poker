tpl = {

    // Hash of preloaded templates for the app
    templates:{},

    // Recursively pre-load all the templates for the app.
    // This implementation should be changed in a production environment. All the template files should be
    // concatenated in a single file.
    loadTemplates:function (names, callback) {
        console.log("\t\tloadTemplates() start");
        var that = this;

        var loadTemplateOld = function (index) {
            var name = names[index];
            console.log("\t\t\tloadTemplate() start");
            console.log('Loading template: ' + name);
            $.get('templates/' + name + '.html', function (data) {
                that.templates[name] = data;
                index++;
                if (index < names.length) {
                    loadTemplateOld(index);
                } else {
                    callback();
                }
            });
            console.log("\t\t\tloadTemplate() end");
        };
        jQuery.ajaxSetup({async:false});
        loadTemplateOld(0);
        jQuery.ajaxSetup({async:true});

        console.log("\t\tloadTemplates() end");
    },

    // Get template by name from hash of preloaded templates
    get:function (name) {
        return this.templates[name];
    }

};
var utilLoaded = true;

