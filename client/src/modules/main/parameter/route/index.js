export const routes = {};

const meta = (title) => {
    return {
        requiresAuth: true,
        title: title,
        tabbed: false,
        tabs: []
    }
};

export default [
    {
        path: 'parameters',
        component: () => import('@/modules/main/parameter/pages/MParameterManagement'),
        name: 'parameters',
        meta: meta('Parametre Yönetimi')
    }
]

