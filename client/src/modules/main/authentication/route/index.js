
// Default routes for authentication
export const routes = {
    login: 'login',
};

function authenticated () {
    if (this.$store.shared.loginStatus) {
        this.$router.push({ name: 'overview' })
    }
}

export default [
    {
        path: 'login',
        name: routes.login,
        beforeEnter: authenticated(),
        component: () => import('@/modules/main/authentication/pages/MLogin')
    },
]

