export const routes = {
    information: 'customer.information',
    orders: 'customer.orders',
    documents: 'customer.documents'
};

const meta = (title) => {
    return {
        title: title,
        tabbed: true,
        tabs: [
            {
                text: 'genel',
                to: { name: routes.information }
            },
            {
                text: 'geçmiş siparişler',
                to: { name: routes.orders }
            },
            {
                text: 'dokümanlar',
                to: { name: routes.documents }
            }
        ]
    }
};

export default [
    {
        path: 'customers',
        name: 'customers',
        component: () => import('@/modules/main/customer/pages/MCustomerManagement'),
        meta: { title: 'Firma Yönetimi' }
    },
    {
        path: 'customers/details/:id',
        component: () => import('@/modules/main/shared/components/core/MCoreDetails'),
        children: [
            {
                path: '',
                name: routes.information,
                component: () => import('@/modules/main/customer/pages/MCustomerDetailsInformation'),
                meta: meta('Pınar Gıda A.Ş')
            },
            {
                path: 'orders',
                name: routes.orders,
                component: () => import('@/modules/main/customer/pages/MCustomerDetailsOrders'),
                meta: meta('Pınar Gıda A.Ş')
            },
            {
                path: 'documents',
                name: routes.documents,
                component: () => import('@/modules/main/customer/pages/MCustomerDetailsDocuments'),
                meta: meta('Pınar Gıda A.Ş')
            }
        ]
    }
]

