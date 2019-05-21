/*
 * Copyright (c) 2019.
 * Author: Mikhail Sigachev
 */


// register the component
Vue.component('treeselect', VueTreeselect.Treeselect)

/*var childrenVar = [5, 6];*/
var urlParams = new URLSearchParams(window.location.search);
var catID = urlParams.get('id');
console.log("id = " + catID);

new Vue({
    el: '#treeApp',
    data: {
        /*varX: [1, 2, 3],*/

        // define default value
        parentCategoriesValue: null,
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
            $.getJSON('/admin/rest/categories/getParentCategoriesOptions/' + catID, function (json) {
                _this.options = json;
            })

            $.getJSON('/admin/rest/categories/getParentCategoriesIDs?catID=' + catID, function (json) {
                _this.parentCategoriesValue = json;
            })

        }
    }
})
