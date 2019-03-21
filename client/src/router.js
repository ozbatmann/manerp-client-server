import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

import MCustomerManagement from './modules/main/customer/pages/MCustomerManagement'
import MIndexOverview from './modules/main/index/pages/IndexOverview'
import MCustomerDetails from "@/modules/main/customer/pages/MCustomerDetails";

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
            name: 'Genel Bakış',
            // beforeEnter: authenticated,
            component: MIndexOverview
        },
        {
            path: '/customers',
            name: 'Müşteri Yönetimi',
            component: MCustomerManagement,
            children: [
                {
                    path: '/details/:id',
                    name: 'Müşteri Detayı',
                    component: MCustomerDetails
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
