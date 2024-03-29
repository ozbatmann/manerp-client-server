export const routes = {
    completed: 'voyages.complete',
    incomplete: 'voyages.incomplete'
};

const meta = (title) => {
    return {
        requiresAuth: true,
        title: title,
        tabbed: true,
        tabs: [
            {
                text: 'devam eden sevkiyatlar',
                to: {name: routes.incomplete}
            },
            {
                text: 'tamamlanmış sevkiyatlar',
                to: {name: routes.completed}
            }
        ],
        permission: 'voyage:show'
    }
};

export default [
    {
        path: 'voyages',
        component: () => import('@/modules/main/shared/components/core/MCoreDetails'),
        children: [
            {
                path: 'completed',
                name: routes.completed,
                component: () => import('@/modules/main/voyage/pages/CompletedVoyageManagement'),
                meta: meta('Sevkiyat Yönetimi')
            },
            {
                path: '',
                name: routes.incomplete,
                component: () => import('@/modules/main/voyage/pages/IncompleteVoyageManagement'),
                meta: meta('Sevkiyat Yönetimi')
            },
        ]
    }
]

