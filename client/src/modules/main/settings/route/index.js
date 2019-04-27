export const routes = {
    settings: 'settings',
    permission: 'settings.permissions',
    billing: 'settings.billing',
    userInfo: 'settings.profile.information',
    accountInfo: 'settings.profile.account',
    notification: 'settings.profile.notifications'
};

const meta = (title) => {
    return {
        title: title,
        tabbed: true,
        tabs: [
            {
                text: 'profil',
                to: { name: routes.userInfo }
            },
            {
                text: 'kullanıcılar ve izinler',
                to: { name: routes.permission }
            },
            {
                text: 'faturalandırma',
                to: { name: routes.billing }
            }
        ]
    }
};

export default [
    {
        path: routes.settings,
        name: '',
        component: () => import('@/modules/main/settings/pages/MSettingsCore'),
        meta: meta('Ayarlar'),
        redirect: { name: routes.userInfo },
        children: [
            {
                path: 'profile',
                name: '',
                component: () => import('@/modules/main/settings/pages/MSettingsProfile'),
                meta: meta('Ayarlar'),
                redirect: { name: routes.userInfo },
                children: [
                    {
                        path: 'user',
                        name: routes.userInfo,
                        component: () => import('@/modules/main/settings/pages/MSettingsProfileInformation'),
                        meta: meta('Ayarlar')
                    },
                    {
                        path: 'account',
                        name: routes.accountInfo,
                        component: () => import('@/modules/main/settings/pages/MSettingsProfileAccount'),
                        meta: meta('Ayarlar')
                    },
                    {
                        path: 'notifications',
                        name: routes.notification,
                        component: () => import('@/modules/main/settings/pages/MSettingsProfileNotifications'),
                        meta: meta('Ayarlar')
                    }
                ]
            },
            {
                path: 'permissions',
                name: routes.permission,
                component: () => import('@/modules/main/settings/pages/MSettingsUserPermission'),
                meta: meta('Ayarlar')
            },
            {
                path: 'billing',
                name: routes.billing,
                component: () => import('@/modules/main/settings/pages/MSettingsUserPermission'),
                meta: meta('Ayarlar')
            }
        ]
    },
]

