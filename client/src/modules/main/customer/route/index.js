const meta = {
    tabbed: true,
    tabs: [
        {
            text: 'genel',
            to: {name: 'customer.information'}
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
};

export default [
    {
        path: 'customers',
        name: 'customers',
        component: () => import('@/modules/main/customer/pages/MCustomerManagement'),
        meta: { title: 'Müşteri Yönetimi' }
    },
    {
        path: 'customers/details/:id',
        component: () => import('@/modules/main/shared/components/core/MCoreDetails'),
        children: [
            {
                path: '',
                name: 'customer.information',
                component: () => import('@/modules/main/customer/pages/MCustomerDetailsInformation'),
                meta: meta
            },
            {
                path: 'orders',
                name: 'customer.orders',
                component: () => import('@/modules/main/customer/pages/MCustomerDetailsOrders'),
                meta: meta
            },
            {
                path: 'documents',
                name: 'customer.documents',
                component: () => import('@/modules/main/customer/pages/MCustomerDetailsDocuments'),
                meta: meta
            }
        ]
    }
]

