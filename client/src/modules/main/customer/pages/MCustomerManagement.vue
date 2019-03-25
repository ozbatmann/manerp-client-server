<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div>
        <m-data-table
            :headers="headers"
            :items="firms"
            :to="to"
        >
            <!-- Data table header slot -->
            <template v-slot:header>

                <!-- Add customer button -->
                <m-data-table-action
                    title="FİRMA EKLE"
                    @click="addDialog"
                ></m-data-table-action>
            </template>

            <!-- Data table action menu slot -->
            <template v-slot:action-menu="item">
                <v-list-tile>Bayi ekle</v-list-tile>
                <v-list-tile @click="addDialog(item.bind)">Düzenle</v-list-tile>
            </template>
        </m-data-table>

        <m-data-table-add-new-form
            ref="addEditDialog"
            :data="addEditData"
            :inputs="addEditFields"
            title="Yeni Firma"
        ></m-data-table-add-new-form>
    </div>
</template>

<script>
    import MDataTable from '../../shared/components/data/components/MDataTable'
    import MDataTableAction from "@/modules/main/shared/components/data/components/MDataTableAction"
    import MDataTableAddNewForm from "../../shared/components/data/components/MDataTableAddNewForm";

    const customerModel = require('@/modules/main/customer/models/customer-model').default;

    export default {
        name: "MCustomerManagement",
        components: {
            MDataTableAddNewForm,
            MDataTableAction,
            MDataTable
        },

        data() {
            return {
                addEditData: {
                    [customerModel.name]: null,
                    [customerModel.phone]: null,
                    [customerModel.email]: null,
                    [customerModel.taxNumber]: null,
                    [customerModel.taxOffice]: null,
                    [customerModel.address]: null,
                },

                addEditFields: [
                    {
                        title: 'Firma Ünvanı',
                        key: customerModel.name,
                        type: 'text',
                        value: null,
                        rules: ['required']
                    },

                    {
                        title: 'Telefon',
                        key: customerModel.phone,
                        type: 'phone',
                        value: null,
                        mask: 'phone',
                        rules: ['required', 'max:10'],
                        max: 10
                    },

                    {
                        title: 'E-posta',
                        key: customerModel.email,
                        type: 'mail',
                        value: null,
                        rules: ['required', 'email']
                    },

                    {
                        title: 'Vergi Numarası',
                        key: customerModel.taxNumber,
                        type: 'text',
                        value: null,
                        rules: ['required']
                    },

                    {
                        title: 'Vergi Dairesi',
                        key: customerModel.taxOffice,
                        type: 'text',
                        value: null,
                        rules: ['required']
                    },

                    {
                        title: 'Adres',
                        key: customerModel.address,
                        type: 'text',
                        value: null,
                        rules: ['required']
                    }
                ],

                headers: [
                    {
                        text: 'ID',
                        sortable: true,
                        value: 'code',
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'Ünvan',
                        sortable: true,
                        value: 'title',
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'Telefon',
                        sortable: true,
                        value: customerModel.phone,
                        toggleable: true,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'E-Posta',
                        sortable: true,
                        value: customerModel.email,
                        toggleable: true,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'Vergi Numarası',
                        sortable: true,
                        value: 'taxNumber',
                        toggleable: true,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'Vergi Dairesi',
                        sortable: true,
                        value: 'taxOffice',
                        toggleable: true,
                        show: true,
                        search: {chip: false, value: null}
                    }
                ],

                firms: [
                    // {
                    //     id: 'FIRM-01',
                    //     title: 'Pınar Gıda A.Ş',
                    //     phone: '0 232 545 6320',
                    //     mail: 'info@pinar.com.tr',
                    //     type: 'Müşteri',
                    //     rep: 'Seval Çıtanak'
                    // },
                    // {
                    //     id: 'FIRM-02',
                    //     title: 'Pınar Gıda A.Ş',
                    //     phone: '0 232 545 6320',
                    //     mail: 'info@pinar.com.tr',
                    //     type: 'Müşteri',
                    //     rep: 'Seval Çıtanak'
                    // },
                    // {
                    //     id: 'FIRM-03',
                    //     title: 'Pınar Gıda A.Ş',
                    //     phone: '0 232 545 6320',
                    //     mail: 'info@pinar.com.tr',
                    //     type: 'Müşteri',
                    //     rep: 'Seval Çıtanak'
                    // },
                    // {
                    //     id: 'FIRM-04',
                    //     title: 'Pınar Gıda A.Ş',
                    //     phone: '0 232 545 6320',
                    //     mail: 'info@pinar.com.tr',
                    //     type: 'Müşteri',
                    //     rep: 'Seval Çıtanak'
                    // },
                    // {
                    //     id: 'FIRM-05',
                    //     title: 'Pınar Gıda A.Ş',
                    //     phone: '0 232 545 6320',
                    //     mail: 'info@pinar.com.tr',
                    //     type: 'Müşteri',
                    //     rep: 'Seval Çıtanak'
                    // },
                    // {
                    //     id: 'FIRM-06',
                    //     title: 'Pınar Gıda A.Ş',
                    //     phone: '0 232 545 6320',
                    //     mail: 'info@pinar.com.tr',
                    //     type: 'Müşteri',
                    //     rep: 'Seval Çıtanak'
                    // }
                ],

                to: {
                    name: require('@/modules/main/customer/route/index').routes.information,
                }
            }
        },

        methods: {
            // Activates add new item dialog
            addDialog(data) {
                this.$refs.addEditDialog.open(data)
            },
            getAllCustomers() {
                this.$http.get('api/v1/customerCompany').then((result) => {
                    this.firms = result.data.data.items
                }).catch((error) => {
                    console.log(error);
                })
            },
            addNewItem(item) {
                this.newItem = item
                this.$http.post('api/v1/customerCompany', this.newItem).then((result) => {
                    this.snackbar.text = "Başarıyla eklendi."
                    this.snackbar.textColor = 'green--text text--accent-3'
                    this.snackbar.active = true
                    this.getAllCustomers();
                }).catch((error) => {
                    console.log(error);
                })
            }
        },

        mounted() {
            this.getAllCustomers();
        }
    }
</script>

<style scoped>

</style>
