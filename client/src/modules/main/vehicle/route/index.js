export const routes = {
    onVoyage: 'vehicles.onVoyage',
    idle: 'vehicles.idle'
};

const meta = (title) => {
    return {
        requiresAuth: true,
        title: title,
        tabbed: true,
        tabs: [
            {
                text: 'boşta olan araçlar',
                to: {name: routes.idle}
            },
            {
                text: 'sevkiyatta olan araçlar',
                to: {name: routes.onVoyage}
            },
        ]
    }
};

export default [
    {
        path: 'vehicles',
        component: () => import('@/modules/main/shared/components/core/MCoreDetails'),
        children: [
            {
                path: '',
                name: routes.idle,
                component: () => import('@/modules/main/vehicle/pages/MVehicleManagementIdle'),
                meta: meta('Araç Yönetimi')
            },
            {
                path: 'voyage',
                name: routes.onVoyage,
                component: () => import('@/modules/main/vehicle/pages/MVehicleManagementOnVoyage'),
                meta: meta('Araç Yönetimi')
            }
        ]
    }
]

