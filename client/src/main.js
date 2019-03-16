import Vue from 'vue'
import './plugins/vuetify'
import App from './App.vue'
import Vuetify from 'vuetify'
import router from './router'

// index.js or main.js
import 'vuetify/dist/vuetify.min.css' // Ensure you are using css-loader

Vue.use(Vuetify, {
    theme: {
        'primary-green' : '#13d893'
    }
})

Vue.config.productionTip = false
Vue.config.performance = true

window.onerror = function(message, source, lineno, colno, error) {
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

new Vue({
    render: h => h(App),
    router
}).$mount('#app')
