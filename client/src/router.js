import Vue from 'vue'
import VueRouter from 'vue-router'
// import store from '../store'
import MCustomerManagement from './modules/main/customer/pages/MCustomerManagement'
import MIndexOverview from './modules/main/index/pages/IndexOverview'
import MCustomerDetailsInformation from "./modules/main/customer/pages/MCustomerDetailsInformation";
import MCoreDetails from "./modules/main/shared/components/core/MCoreDetails";
import MCustomerDetailsDocuments from "./modules/main/customer/pages/MCustomerDetailsDocuments";
import MCustomerDetailsOrders from "./modules/main/customer/pages/MCustomerDetailsOrders";

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
            name: 'overview',
            // beforeEnter: authenticated,
            component: MIndexOverview,
            meta: {
                title: 'Genel Bakış',
            }
        },
        {
            path: '/customers',
            name: 'customers',
            component: MCustomerManagement,
            meta: {
                title: 'Müşteri Yönetimi',
            },
        },
        {
            path: '/customers/details/:id/',
            component: MCoreDetails,
            children: [
                {
                    path: 'orders',
                    component: MCustomerDetailsOrders,
                    name: 'customer.orders',
                    meta: {
                        tabbed: true,
                        tabs: [
                            {
                                text: 'genel',
                                to: {name: 'customer.info'}
                            },
                            {
                                text: 'geçmiş siparişler',
                                to: {name: 'customer.orders'}
                            },
                            {
                                text: 'dokümanlar',
                                to: {name: 'customer.documents'}
                            }
                        ]
                    }
                },
                {
                    path: 'documents',
                    component: MCustomerDetailsDocuments,
                    name: 'customer.documents',
                    meta: {
                        tabbed: true,
                        tabs: [
                            {
                                text: 'genel',
                                to: {name: 'customer.info'}
                            },
                            {
                                text: 'geçmiş siparişler',
                                to: {name: 'customer.orders'}
                            },
                            {
                                text: 'dokümanlar',
                                to: {name: 'customer.documents'}
                            }
                        ]
                    }
                },
                {
                    path: '',
                    component: MCustomerDetailsInformation,
                    name: 'customer.info',
                    params: {
                        id: ':id'
                    },
                    meta: {
                        tabbed: true,
                        tabs: [
                            {
                                text: 'genel',
                                to: {name: 'customer.info'}
                            },
                            {
                                text: 'geçmiş siparişler',
                                to: {name: 'customer.orders'}
                            },
                            {
                                text: 'dokümanlar',
                                to: {name: 'customer.documents'}
                            }
                        ]
                    },
                },
            ]
        }
    ],
    mode: 'history'
})

router.beforeEach((to, from, next) => {
    next()
})

export default router
