let customerInformationRoute = 'customer.information';
let customerOrdersRoute = 'customer.orders';
let customerDocumentsRoute = 'customer.documents';

const meta = {
    tabbed: true,
    tabs: [
        {
            text: 'genel',
            to: { name: customerInformationRoute }
        },
        {
            text: 'geçmiş siparişler',
            to: { name: customerOrdersRoute }
        },
        {
            text: 'dokümanlar',
            to: { name: customerDocumentsRoute }
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
                name: customerInformationRoute,
                component: () => import('@/modules/main/customer/pages/MCustomerDetailsInformation'),
                meta: meta
            },
            {
                path: 'orders',
                name: customerOrdersRoute,
                component: () => import('@/modules/main/customer/pages/MCustomerDetailsOrders'),
                meta: meta
            },
            {
                path: 'documents',
                name: customerDocumentsRoute,
                component: () => import('@/modules/main/customer/pages/MCustomerDetailsDocuments'),
                meta: meta
            }
        ]
    }
]

