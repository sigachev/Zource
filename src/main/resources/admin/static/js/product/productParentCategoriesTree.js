/*
 * Copyright (c) 2019.
 * Author: Mikhail Sigachev
 */


Vue.component('treeselect', VueTreeselect.Treeselect)

var urlParams = new URLSearchParams(window.location.search);
var productID = urlParams.get('id');

new Vue({
    el: '#treeApp',
    data: {
        // define default value
        value: null,
        // define options
        options: null,
        normalizer(node) {
            return {
                id: node.id,
                label: node.id + ' :: ' + node.name,
                children: node.children,
            }
        },
    },
    mounted: function () {
    },

    created: function () {
        this.loadData();
    },
    methods: {
        loadData: function () {
            var _this = this;
            $.getJSON('/admin/rest/categories/getRootCategories', function (json) {
                _this.options = json;
            })

            $.getJSON('/admin/rest/products/getProductCategoriesIDs?id=' + productID, function (json) {
                _this.value = json;
            })

        }
    }
})
