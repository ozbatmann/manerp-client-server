import Vue from 'vue'
import './plugins/vuetify'
import App from './App.vue'
import router from './router'
import base,{bus, http, i18n, store} from 'manerp-vue-base'
// import auth from 'manerp-vue-auth'

// base.use(auth);

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
    bind: function (el, binding, vNode) {
        // Provided expression must evaluate to a function.
        if (typeof binding.value !== 'function') {
            const compName = vNode.context.name
            let warn = `[Vue-click-outside:] provided expression '${binding.expression}' is not a function, but has to be`
            if (compName) {
                warn += ` Found in component '${compName}'`
            }

            console.warn(warn)
        }
        // Define Handler and cache it on the element
        const bubble = binding.modifiers.bubble
        const handler = (e) => {
            if (bubble || (!el.contains(e.target) && el !== e.target)) {
                binding.value(e)
            }
        }
        el.__vueClickOutside__ = handler

        // add Event Listeners
        document.addEventListener('click', handler)
    },

    unbind: function (el, binding) {
        // Remove Event Listeners
        document.removeEventListener('click', el.__vueClickOutside__)
        el.__vueClickOutside__ = null

    }
});

//http.defaults.baseURL = "http://139.179.232.32:8082/";
http.defaults.baseURL = "http://192.168.1.37:8082/";

window.instance = new Vue({
    router,
    store,
    i18n,
    bus,
    render: h => h(App),
}).$mount('#app');
