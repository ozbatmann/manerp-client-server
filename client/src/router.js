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
            // Main route
            path: '/',
            // beforeEnter: authenticated,
            component: () => import('@/App'),
            children: [
                {
                    path: '',
                    component: () => import('@/modules/main/AppMain'),
                    children: [
                        // Index overview routes
                        ...require('@/modules/main/index/route/index').default,

                        // Customer routes
                        ...require('@/modules/main/customer/route/index').default,

                        // Driver routes
                        ...require('@/modules/main/driver/route/index').default
                    ],
                },
                {
                    path: 'login',
                    name: 'login',
                    component: () => import('@/modules/main/customer/pages/MCustomerManagement')
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
