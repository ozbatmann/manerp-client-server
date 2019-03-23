export const routes = {
    current: 'orders.current',
    completed: 'orders.completed'
};

const meta = (title) => {
    return {
        title: title,
        tabbed: true,
        tabs: [
            {
                text: 'bekleyen siparişler',
                to: { name: routes.current }
            },
            {
                text: 'tamamlanmış siparişler',
                to: { name: routes.completed }
            },
        ]
    }
};

export default [
    {
        path: 'orders',
        component: () => import('@/modules/main/shared/components/core/MCoreDetails'),
        children: [
            {
                path: '',
                name: routes.current,
                component: () => import('@/modules/main/order/pages/MOrderManagementCurrent'),
                meta: meta('Sipariş Yönetimi')
            },
            {
                path: 'completed',
                name: routes.completed,
                component: () => import('@/modules/main/order/pages/MOrderManagementCompleted'),
                meta: meta('Sipariş Yönetimi')
            },
        ]
    }
]

