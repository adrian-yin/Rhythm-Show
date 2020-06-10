import Vue from 'vue';
import VueRouter from 'vue-router';
Vue.use(VueRouter);
import store from '../store';

import HelloWorld from "@/components/HelloWorld";
import Login from '@/components/Login';
import Create from '@/components/Create';

const router = new VueRouter({
    routes: [
        // 主页
        {
            path: '/',
            name: 'Home',
            // TODO: 改变主页组件
            component: HelloWorld,
            meta: {
                required: false,
                title: '“炫·律”音乐旋律分享'
            }
        },
        // 登录页
        {
            path: '/login',
            name: Login,
            component: Login,
            meta: {
                required: false,
                title: '登录页'
            }
        },
        // 钢琴页
        {
            path: '/create',
            name: Create,
            component: Create,
            meta: {
                required: false,
                title: '创作页'
            }
        }
    ]
})

// 判断用户是否登陆，未登陆跳转至登陆页面
router.beforeEach((to, from, next) => {
    // 自动添加title
    if (to.meta.title) {
        document.title = to.meta.title;
    }
    // 判断是否需要登陆权限
    if (to.meta.required) {
        if (store.state.token) {
            next();
        } else {
            if (sessionStorage.getItem('token')) {
                store.state.token = sessionStorage.getItem('token');
                next();
            } else {
                // 跳转到登陆页面
                next({
                    path: '/login',
                    query: {
                        redirect: to.fullPath
                    }
                })
            }
        }
    } else {
        next();
    }
})

export default router;
