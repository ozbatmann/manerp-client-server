
// Default routes for authentication
import {store} from 'manerp-vue-base'


export const routes = {
    login: 'login',
};

// function authenticated () {
//     if (store.state.shared.loginStatus) {
//         this.$router.push({ name: 'overview' })
//     }
// }

export default [
    {
        path: 'login',
        name: routes.login,
        // beforeEnter: authenticated(),
        component: () => import('@/modules/main/authentication/pages/MLogin')
    },
]

