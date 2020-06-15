import Vue from 'vue';
import VueRouter from 'vue-router';
Vue.use(VueRouter);
import store from '../store';

// import HelloWorld from "@/components/HelloWorld";
import Home from "@/components/Home";
import Login from '@/components/Login';
import Create from '@/components/Create';
import Register from "@/components/Register";
import Square from "@/components/Square";
import Play from "@/components/Play";
import Record from "@/components/Record";

const router = new VueRouter({
    routes: [
        // TODO: 将需求权限的页改为true
        // 主页
        {
            path: '/',
            name: 'Home',
            component: Home,
            meta: {
                required: false,
                title: '“炫·律”音乐旋律分享'
            }
        },
        // 登录页
        {
            path: '/login',
            name: 'Login',
            component: Login,
            meta: {
                required: false,
                title: '登录'
            }
        },
        // 注册页
        {
            path: '/register',
            name: 'Register',
            component: Register,
            meta: {
                required: false,
                title: '注册'
            }
        },
        // 创作页
        {
            path: '/create',
            name: 'Create',
            component: Create,
            meta: {
                required: false,
                title: '创作'
            }
        },
        // 录音页
        {
            path: '/record',
            name: 'Record',
            component: Record,
            meta: {
                required: false,
                title: '录音'
            }
        },
        // 广场页
        {
            path: '/square',
            name: 'Square',
            component: Square,
            meta: {
                required: false,
                title: '广场',
            }
        },
        // 演奏页
        {
            path: '/play',
            name: 'Play',
            component: Play,
            meta: {
                required: false,
                title: '演奏'
            }
        }
    ]
})

router.beforeEach((to, from, next) => {
    // 自动添加title
    if (to.meta.title) {
        document.title = to.meta.title;
    }
    // 判断是否需要登陆权限，未登陆跳转至登陆页面
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
