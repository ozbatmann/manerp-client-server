export const routes = {
    customer: 'companies.customers',
    supplier: 'companies.suppliers',
    information: 'company.information',
    orders: 'company.orders',
    documents: 'company.documents'
};

const meta = (title) => {
    return {
        requiresAuth: true,
        title: title,
        tabbed: true,
        tabs: [
            {
                text: 'Müşteri İş Yerleri',
                to: {name: routes.customer}
            },
            {
                text: 'Tedarikçi İş Yerleri',
                to: {name: routes.supplier}
            }
        ]
    }
};

export default [
    {
        path: 'companies',
        component: () => import('@/modules/main/shared/components/core/MCoreDetails'),
        redirect: { name: routes.customer },
        children: [
            {
                path: '',
                name: routes.customer,
                component: () => import('@/modules/main/company/pages/CustomerManagement'),
                meta: meta("Müşteri İş Yeri Yönetimi")
            },
            {
                path: 'suppliers',
                name: routes.supplier,
                component: () => import('@/modules/main/company/pages/SupplierManagement'),
                meta: meta('Tedarikçi İş Yeri Yönetimi')
            }
        ]
    },
]

