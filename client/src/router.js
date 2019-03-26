import Vue from 'vue'
import VueRouter from 'vue-router'
// import store from '../store'

Vue.use(VueRouter);

const router = new VueRouter({
    routes: [
        {
            path: '/404',
            component: () => import('@/modules/main/AppNotFound')
        },
        {
            path: '*',
            redirect: '/404'
        },
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
                        ...require('@/modules/main/driver/route/index').default,

                        // Order routes
                        ...require('@/modules/main/order/route/index').default,

                        // Vehicle routes
                        ...require('@/modules/main/vehicle/route/index').default,

                        // Parameter routes
                        ...require('@/modules/main/parameter/route/index').default
                    ],
                },

                // Authentication routes
                ...require('@/modules/main/authentication/route/index').default,
            ]
        },
    ],
    mode: 'history'
});

router.beforeEach((to, from, next) => {
    next()
});

export default router
