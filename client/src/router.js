import Vue from 'vue'
import VueRouter from 'vue-router'
// import store from '../store'

Vue.use(VueRouter)

const router = new VueRouter({
    routes: [
        // {
        //   path: '/login',
        //   name: 'Login',
        //   component: Login
        //   // beforeEnter: notAuthenticated
        // },
        {
            path: '/',
            // beforeEnter: authenticated,
            component: () => import('@/App'),
            children: [
                {
                    path: '',
                    component: () => import('@/modules/main/AppMain'),
                    children: [
                        ...require('@/modules/main/index/route/index').default,
                        ...require('@/modules/main/customer/route/index').default
                    ]
                }
            ]
        },
    ],
    mode: 'history'
})

router.beforeEach((to, from, next) => {
    next()
})

export default router
