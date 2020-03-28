import VueRouter from 'vue-router'
import Vue from 'vue'
import Index from './views/Index.vue'
import Sumbit from './views/Submit.vue'
import Login from './views/auth/Login.vue'
import UBenchmarks from './views/auth/UBenchmarks.vue'

Vue.use(VueRouter)

const routes = [
    { path: '/', component: Index },
    { path: '/submit', component: Sumbit },
    { path: '/login', component: Login },
    { path: '/unconfirmedBenchmarks', component: UBenchmarks }
  ];

export default new VueRouter({
    mode: 'history',
    routes
});