export const routes = {
    search: 'search'
};

const meta = (title) => {
    return {
        title: title,
        tabbed: false,
    }
};

export default [
    {
        path: 'search',
        name: routes.search,
        component: () => import('@/modules/main/search/pages/MSearch'),
        meta: meta('Arama')
    }
]

