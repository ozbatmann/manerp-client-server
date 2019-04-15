export const routes = {
    customer: 'companies.customers',
    supplier: 'companies.suppliers',
    information: 'company.information',
    orders: 'company.orders',
    documents: 'company.documents'
};

const meta = (title) => {
    return {
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

const detailsMeta = (title) => {
    return {
        title: title,
        tabbed: true,
        tabs: [
            {
                text: 'genel',
                to: {name: routes.information}
            },
            {
                text: 'geçmiş siparişler',
                to: {name: routes.orders}
            },
            {
                text: 'dokümanlar',
                to: {name: routes.documents}
            }
        ]
    }
};

export default [
    {
        path: 'companies',
        component: () => import('@/modules/main/shared/components/core/MCoreDetails'),
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
    {
        path: 'customers/details/:id',
        component: () => import('@/modules/main/shared/components/core/MCoreDetails'),
        children: [
            {
                path: '',
                name: routes.information,
                component: () => import('@/modules/main/company/pages/MCustomerDetailsInformation'),
                meta: detailsMeta('Pınar Gıda A.Ş')
            },
            {
                path: 'orders',
                name: routes.orders,
                component: () => import('@/modules/main/company/pages/MCustomerDetailsOrders'),
                meta: detailsMeta('Pınar Gıda A.Ş')
            },
            {
                path: 'documents',
                name: routes.documents,
                component: () => import('@/modules/main/company/pages/MCustomerDetailsDocuments'),
                meta: detailsMeta('Pınar Gıda A.Ş')
            }
        ]
    }
]

