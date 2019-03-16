import Vue from 'vue'
import Vuex from 'vuex'
import VuexPersistence from 'vuex-persist'
import camelCase from "lodash/camelCase";

//Get every modules' store
//Ex. dir: src/modules/core/store
const requireModule = require.context("@/modules", true, /.*\/store\/index\.js/); //extract js files inside modules folder
const modules = {};

//populate vuex modules
requireModule.keys().forEach(fileName => {

  const moduleName = camelCase(fileName.split('/')[1]);   //get module name
  modules[moduleName] = requireModule(fileName).default; //add module itself to the modules
});

Vue.use(Vuex);

//Set persistence plugin for vuex
const vuexLocal = new VuexPersistence({

  storage: window.localStorage,
  modules:['core']
});

export default new Vuex.Store({

  modules,
  plugins: [

    vuexLocal.plugin
  ],
  state: {
  },
  getters: {
  },
  mutations: {
  },
  actions: {
  }
})
