export const routes = {
    customer: 'firms.customers',
    supplier: 'firms.suppliers',
    information: 'firm.information',
    orders: 'firm.orders',
    documents: 'firm.documents'
};

const meta = (title) => {
    return {
        title: title,
        tabbed: true,
        tabs: [
            {
                text: 'Müşteri Firmalar',
                to: { name: routes.customer }
            },
            {
                text: 'Tedarikci Firmalar',
                to: { name: routes.supplier }
            }
        ]
    }
};

const detailsMeta = (title) => {
    return {
        title: title,
        tabbed: true,
        tabs: [
            {
                text: 'genel',
                to: { name: routes.information }
            },
            {
                text: 'geçmiş siparişler',
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
        path: 'firms',
        component: () => import('@/modules/main/shared/components/core/MCoreDetails'),
        children: [
            {
                path: '',
                name: routes.customer,
                component: () => import('@/modules/main/customer/pages/MCustomerManagement'),
                meta: meta("Firma Yonetimi")
            },
            {
                path: 'suppliers',
                name: routes.supplier,
                component: () => import('@/modules/main/customer/pages/MCustomerManagement'),
                meta: meta('Firma Yonetimi')
            }
        ]
    },
    {
        path: 'customers/details/:id',
        component: () => import('@/modules/main/shared/components/core/MCoreDetails'),
        children: [
            {
                path: '',
                name: routes.information,
                component: () => import('@/modules/main/customer/pages/MCustomerDetailsInformation'),
                meta: detailsMeta('Pınar Gıda A.Ş')
            },
            {
                path: 'orders',
                name: routes.orders,
                component: () => import('@/modules/main/customer/pages/MCustomerDetailsOrders'),
                meta: detailsMeta('Pınar Gıda A.Ş')
            },
            {
                path: 'documents',
                name: routes.documents,
                component: () => import('@/modules/main/customer/pages/MCustomerDetailsDocuments'),
                meta: detailsMeta('Pınar Gıda A.Ş')
            }
        ]
    }
]

