import VueRouter from 'vue-router'
import Vue from 'vue'
import Index from './views/Index.vue'
import Sumbit from './views/Submit.vue'

Vue.use(VueRouter)

const routes = [
    { path: '/', component: Index },
    { path: '/submit', component: Sumbit }
  ];

export default new VueRouter({
    mode: 'history',
    routes
});