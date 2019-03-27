
// Default routes for authentication
export const routes = {
    login: 'login',
};

export default [
    {
        path: 'login',
        name: routes.login,
        component: () => import('@/modules/main/authentication/pages/MLogin')
    },
]

