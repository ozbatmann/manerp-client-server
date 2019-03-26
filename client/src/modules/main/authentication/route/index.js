
// Default routes for authentication
export const routes = {
    login: 'login',
    register: 'register'
};

export default [
    {
        path: 'login',
        name: routes.login,
        component: () => import('@/modules/main/authentication/pages/MLogin')
    },
    {
        path: 'register',
        name: routes.register,
        component: () => import('@/modules/main/authentication/pages/MRegister')
    }
]

