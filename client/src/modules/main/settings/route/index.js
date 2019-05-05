export const routes = {
    settings: 'settings',
    permission: 'settings.permissions',
    billing: 'settings.billing',
    profile: 'settings.profile',
};

const meta = (title) => {
    return {
        requiresAuth: true,
        title: title,
        tabbed: true,
        tabs: [
            {
                text: 'profil',
                to: { name: routes.profile }
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
        redirect: { name: routes.profile },
        children: [
            {
                path: 'profile',
                name: routes.profile,
                component: () => import('@/modules/main/settings/pages/MSettingsProfile'),
                meta: meta('Ayarlar'),
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

