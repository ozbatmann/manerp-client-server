export const routes = {
    drivers: 'drivers',
    information: 'driver.information',
    orders: 'driver.routes',
    documents: 'driver.documents'
};

const meta = (title) => {
    return {
        requiresAuth: true,
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

        ]
    }
]

