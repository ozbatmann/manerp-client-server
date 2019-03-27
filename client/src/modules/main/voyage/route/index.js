export const routes = {
    current: 'voyages.current',
    completed: 'voyages.completed'
};

const meta = (title) => {
    return {
        title: title,
        tabbed: true,
        tabs: [
            {
                text: 'devam eden sevkiyatlar',
                to: {name: routes.current}
            },
            {
                text: 'tamamlanmış sevkiyatlar',
                to: {name: routes.completed}
            }
        ]
    }
};

export default [
    {
        path: 'voyages',
        component: () => import('@/modules/main/shared/components/core/MCoreDetails'),
        children: [
            {
                path: '',
                name: routes.current,
                component: () => import('@/modules/main/voyage/pages/MVoyageManagementCurrent'),
                meta: meta('Sevkiyat Yönetimi')
            },
            {
                path: 'completed',
                name: routes.completed,
                component: () => import('@/modules/main/voyage/pages/MVoyageManagementCompleted'),
                meta: meta('Sevkiyat Yönetimi')
            },
        ]
    }
]

