/**
 * External plugins added through the server-side FieldFactory are automatically registered.
 * Other external plugins (e.g. client-only) may still be registered here (and subsequently added via config.extraPlugins).
 *
 * e.g. if your plugin resides in src/main/resources/VAADIN/js:
 * CKEDITOR.plugins.addExternal("abbr", CKEDITOR.vaadinDirUrl + "js/abbr/");
 */

(function() {

    CKEDITOR.plugins.addExternal("abbr", CKEDITOR.vaadinDirUrl + "ckeditor/plugins/abbr/", "plugin.js");


    CKEDITOR.editorConfig = function( config ) {

        // MIRROR info.magnolia.ui.form.field.definition.RichTextFieldDefinition
        definition = {
            alignment: false,
            images: true,
            lists: true,
            source: true,
            tables: true,

            styles: true,
            colors: null,
            fonts: null,
            fontSizes: null
        }

        // MIRROR info.magnolia.ui.form.field.factory.RichTextFieldFactory
        removePlugins = [];

        // CONFIGURATION FROM DEFINITION
        if (!definition.alignment) {
            removePlugins.push("justify");
        }
        if (!definition.images) {
            removePlugins.push("image");
        }
        if (!definition.lists) {
            // In CKEditor 4.1.1 enterkey depends on indent which itself depends on list
            removePlugins.push("enterkey");
            removePlugins.push("indent");
            removePlugins.push("list");
        }
        if (!definition.source) {
            removePlugins.push("sourcearea");
        }
        if (!definition.tables) {
            removePlugins.push("table");
            removePlugins.push("tabletools");
        }

        if (!definition.styles) {
            removePlugins.push("Styles");
            config.stylesSet = false;
        } else {
            config.stylesSet = 'basic:' + CKEDITOR.vaadinDirUrl + 'ckeditor/basic-editor-styles.js';
        }

        if (definition.colors != null) {
            config.colorButton_colors = definition.colors;
            config.colorButton_enableMore = false;
            removePlugins.push("colordialog");
        } else {
            removePlugins.push("colorbutton");
            removePlugins.push("colordialog");
        }
        if (definition.fonts != null) {
            config.font_names = definition.fonts;
        } else {
            config.removeButtons = "Font";
        }
        if (definition.fontSizes != null) {
            config.fontSize_sizes = definition.fontSizes;
        } else {
            config.removeButtons = "FontSize";
        }
        if (definition.fonts == null && definition.fontSizes == null) {
            removePlugins.push("font");
            removePlugins.push("fontSize");
        }

        // DEFAULT CONFIGURATION FROM FIELD FACTORY
        removePlugins.push("elementspath");
        removePlugins.push("filebrowser");
        config.removePlugins = removePlugins.join(",");
        config.extraPlugins = "magnolialink,magnoliaFileBrowser,abbr";

        config.format_tags = 'h5;h6;p;pre';

        config.baseFloatZIndex = 150;
        config.resize_enabled = false;
        config.toolbar = "Magnolia";

        config.toolbar_Magnolia = [
            { name: "undo",          items: [ "Undo", "Redo" ] },
            { name: "clipboard",     items: [ "Cut", "Copy", "Paste", "PasteText", "PasteFromWord" ] },
            { name: 'basicstyles',   items: [ 'Bold', 'Italic', 'Underline', 'Subscript', 'Superscript', 'Strike', '-', 'RemoveFormat' ] },
            { name: 'paragraph',     items: [ 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', '-', 'Blockquote', '-', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock' ] },
            { name: "links",         items: [ "Link", "InternalLink", "DamLink", "Unlink", 'Anchor' ] },
            { name: 'insert',        items: [ 'Image', 'Table', 'HorizontalRule', 'SpecialChar', 'PageBreak', 'Abbr' ] },
            '/',
            { name: "styles",        items: [ 'Styles', 'Format', "Font", "FontSize", "TextColor" ] },
            { name: "tools",         items: [ "Source" ] }
        ];

    };

})();