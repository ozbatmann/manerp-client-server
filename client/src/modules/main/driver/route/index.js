export const routes = {
    drivers: 'drivers',
    information: 'driver.information',
    orders: 'driver.routes',
    documents: 'driver.documents'
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
                text: 'geçmiş rotalar',
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
        path: 'drivers',
        name: routes.drivers,
        component: () => import('@/modules/main/driver/pages/MDriverManagement'),
        meta: { title: 'Şoför Yönetimi' }
    },
    {
        path: 'drivers/details/:id',
        component: () => import('@/modules/main/shared/components/core/MCoreDetails'),
        children: [
            {
                path: '',
                name: routes.information,
                component: () => import('@/modules/main/customer/pages/MCustomerDetailsInformation'),
                meta: meta('Ahmet Aliakça')
            },
            {
                path: 'routes',
                name: routes.orders,
                component: () => import('@/modules/main/customer/pages/MCustomerDetailsOrders'),
                meta: meta('Ahmet Aliakça')
            },
            {
                path: 'documents',
                name: routes.documents,
                component: () => import('@/modules/main/customer/pages/MCustomerDetailsDocuments'),
                meta: meta('Ahmet Aliakça')
            }
        ]
    }
]

