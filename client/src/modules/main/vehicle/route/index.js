export const routes = {
    onVoyage: 'vehicles.onVoyage',
    idle: 'vehicles.idle'
};

const meta = (title) => {
    return {
        title: title,
        tabbed: true,
        tabs: [
            {
                text: 'sevkiyatta olan araçlar',
                to: {name: routes.onVoyage}
            },
            {
                text: 'boşta olan araçlar',
                to: {name: routes.idle}
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
                name: routes.onVoyage,
                component: () => import('@/modules/main/vehicle/pages/MVehicleManagementCurrent'),
                meta: meta('Araç Yönetimi')
            },
            {
                path: 'idle',
                name: routes.idle,
                component: () => import('@/modules/main/vehicle/pages/MVehicleManagementCompleted'),
                meta: meta('Araç Yönetimi')
            },
        ]
    }
]

