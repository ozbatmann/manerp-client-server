import Vue from 'vue'
import './plugins/vuetify'
import App from './App.vue'
import router from './router'
import {bus, http, i18n, store} from "manerp-vue-base"

const langFiles = require.context("./../../client/src/modules/", true, /.*\/i18n\/(.*)\.json$/);
langFiles.keys().forEach(function (fileName) {

    let lang = fileName.match(/([^/]+)\.json$/)[1];
    i18n.mergeLocaleMessage(lang, langFiles(fileName));

});

Vue.config.productionTip = false;
Vue.config.performance = true;
Vue.prototype.$vuetify.theme["primary-green"] = "#13d893";

window.onerror = function (message, source, lineno, colno, error) {
    console.log('Exception: ', error)
}

Vue.config.errorHandler = err => {
    console.log('Exception: ', err)
}

Vue.directive('click-outside', {
    bind: function (el, binding, vnode) {
        el.clickOutsideEvent = function (event) {
            // here I check that click was outside the el and his childrens
            if (!(el == event.target || el.contains(event.target))) {
                // and if it did, call method provided in attribute value
                vnode.context[binding.expression](event);
            }
        };
        document.body.addEventListener('click', el.clickOutsideEvent)
    },
    unbind: function (el) {
        document.body.removeEventListener('click', el.clickOutsideEvent)
    },
});

//http.defaults.baseURL = "http://139.179.232.32:8082/";
http.defaults.baseURL = "http://192.168.1.39:8082/";

window.instance = new Vue({
    router,
    store,
    i18n,
    render: h => h(App),
}).$mount('#app');
