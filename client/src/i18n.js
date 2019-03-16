import Vue from 'vue'
import VueI18n from 'vue-i18n'

Vue.use(VueI18n);

let i18n = new VueI18n({

  locale: 'tr',
  fallbackLocale: 'tr',
  messages: {}
});

/*
  Get and combine language json files of every module
 */
const langFiles = require.context("@/modules", true, /.*\/i18n\/(.*)\.json$/);

langFiles.keys().forEach(function (fileName) {

  //get language identifier from filename
  let lang = fileName.match(/([^/]+)\.json$/)[1];

  //merge language file into vue-i18n
  i18n.mergeLocaleMessage(lang,langFiles(fileName));
});

export default i18n
