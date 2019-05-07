export const routes = {
    idle: 'driver.idle',
    onVoyage: 'driver.onVoyage',
};

const meta = (title) => {
    return {
        requiresAuth: true,
        title: title,
        tabbed: true,
        tabs: [
            {
                text: 'boştaki şoförler',
                to: { name: routes.drivers }
            },
            {
                text: 'sevkiyattaki şoförler',
                to: { name: routes.orders }
            },
        ]
    }
};

export default [
    {
        path: 'drivers',
        component: () => import('@/modules/main/shared/components/core/MCoreDetails'),
        redirect: { name: routes.customer },
        children: [
            {
                path: '',
                name: routes.idle,
                component: () => import('@/modules/main/driver/pages/DriverManagement'),
                meta: meta("Şoför Yönetimi")
            },
            {
                path: 'voyage',
                name: routes.onVoyage,
                component: () => import('@/modules/main/driver/pages/MDriverOnVoyage'),
                meta: meta("Şoför Yönetimi")
            }
        ]
    },
]

