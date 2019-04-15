import Vue from 'vue'
import VueRouter from 'vue-router'
import {store} from 'manerp-vue-base'

Vue.use(VueRouter);

const router = new VueRouter({
    routes: [
        {
            path: '/404',
            name: '404',
            component: () => import('@/modules/main/AppNotFound')
        },
        {
            path: '*',
            redirect: {name: '404'}
        },
        {
            // Main route
            path: '/',
            component: () => import('@/App'),
            children: [
                {
                    path: '',
                    component: () => import('@/modules/main/AppMain'),
                    //beforeEnter: authenticated(),
                    children: [
                        // Index overview routes
                        ...require('@/modules/main/index/route/index').default,

                        // Company routes
                        ...require('@/modules/main/company/route/index').default,

                        // Staff routes
                        ...require('@/modules/main/staff/route/index').default,

                        // Driver routes
                        ...require('@/modules/main/driver/route/index').default,

                        // Order routes
                        ...require('@/modules/main/order/route/index').default,

                        // Voyage routes
                        ...require('@/modules/main/voyage/route/index').default,

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

// function authenticated () {
//     if (!store.state.shared.loginStatus) {
//
//       router.push({ name: 'login' })
//     }
// }

router.beforeEach((to, from, next) => {
    next()
});

export default router
