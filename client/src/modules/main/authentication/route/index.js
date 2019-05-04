
// Default routes for authentication
import {store} from 'manerp-vue-base'


export const routes = {
    login: 'auth.login',
    sendResetMail: 'auth.mail',
    resetPassword: 'auth.reset'
};

// function authenticated () {
//     if (store.state.shared.loginStatus) {
//         this.$router.push({ name: 'overview' })
//     }
// }

export default [
    {
        path: 'auth',
        component: () => import('@/modules/main/authentication/pages/MCoreAuthentication'),
        redirect: { name: routes.login },
        children: [
            {
                path: 'login',
                name: routes.login,
                // beforeEnter: authenticated(),
                component: () => import('@/modules/main/authentication/pages/MLogin')
            },
            {
                path: 'reset-mail',
                name: routes.sendResetMail,
                component: () => import('@/modules/main/authentication/pages/MResetPasswordSendMail')
            },
            {
                path: 'reset-password/:id',
                name: routes.resetPassword,
                component: () => import('@/modules/main/authentication/pages/MResetPassword')
            }
        ]
    }
]

