export const routes = {
    staffs: 'staffs',
    information: 'staff.information',
    orders: 'staff.routes',
    documents: 'staff.documents'
};

const meta = (title) => {
    return {
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
            }
        ]
    }
};

export default [
    {
        path: 'staffs',
        name: routes.staffs,
        component: () => import('@/modules/main/staff/pages/MStaffManagement'),
        meta: { title: 'Şoför Yönetimi' }
    },
    {
        path: 'staffs/details/:id',
        component: () => import('@/modules/main/shared/components/core/MCoreDetails'),
        children: [
            {
                path: '',
                name: routes.information,
                component: () => import('@/modules/main/customer/pages/MCustomerDetailsInformation'),
                meta: meta('Ahmet Aliakça')
            },
            {
                path: 'routes',
                name: routes.orders,
                component: () => import('@/modules/main/customer/pages/MCustomerDetailsOrders'),
                meta: meta('Ahmet Aliakça')
            },
            {
                path: 'documents',
                name: routes.documents,
                component: () => import('@/modules/main/customer/pages/MCustomerDetailsDocuments'),
                meta: meta('Ahmet Aliakça')
            }
        ]
    }
]

