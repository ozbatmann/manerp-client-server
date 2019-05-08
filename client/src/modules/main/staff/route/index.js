export const routes = {
    staffs: 'staffs',
    information: 'staff.information',
    orders: 'staff.routes',
    documents: 'staff.documents'
};

const meta = (title) => {
    return {
        requiresAuth: true,
        title: title,
        tabbed: true,
        tabs: [
            {
                text: 'genel',
                to: { name: routes.information }
            },
            {
                text: 'geçmiş rotalar',
                to: { name: routes.orders }
            },
            {
                text: 'dokümanlar',
                to: { name: routes.documents }
            },
        ],
        permission: 'staff:show'
    }
};

export default [
    {
        path: 'staffs',
        name: routes.staffs,
        component: () => import('@/modules/main/staff/pages/StaffManagement'),
        meta: { title: 'Personel Yönetimi', permission: 'staff:show' }
    },
    {
        path: 'staffs/details/:id',
        component: () => import('@/modules/main/shared/components/core/MCoreDetails'),
        children: [

        ]
    }
]

