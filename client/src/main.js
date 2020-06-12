import Vue from 'vue';
import App from './App.vue';

import router from './router';
import VueRouter from "vue-router";
Vue.use(VueRouter);

// 引入element-ui
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
Vue.use(ElementUI);

import store from './store';

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store: store,
  render: h => h(App)
}).$mount('#app')
