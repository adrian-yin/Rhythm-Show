import Vue from 'vue';
import App from './App.vue';

import router from './router';
import VueRouter from "vue-router";
Vue.use(VueRouter);

import store from './store';

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store: store,
  render: h => h(App)
}).$mount('#app')
