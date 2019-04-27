<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div>
        <m-data-table
            :headers="headers"
            :items="orders"
            :to="to"
            :loading="loading"
            :deleteItem="deleteItem"
        >
            <!-- Data table header slot -->
            <template v-slot:header>

                <!-- Add customer button -->
                <m-data-table-action
                    title="sipariş ekle"
                    @click="addDialog"
                ></m-data-table-action>
            </template>

            <!-- Data-table action menu slot -->
            <template v-slot:action-menu="item">

                <!-- Edit button -->
                <v-list-tile @click="addDialog(item.bind)">Düzenle</v-list-tile>
            </template>
        </m-data-table>

        <!-- Data table add-edit form -->
        <m-data-table-add-new-form
            ref="addEditDialog"
            :data="addEditData"
            :inputs="addEditFields"
            title="Yeni Sipariş"
            @save="addNewItem"
            @edit="editItem"
        ></m-data-table-add-new-form>

        <v-snackbar
            v-model="snackbar.active"
            color="grey darken-4"
            :class="snackbar.textColor"
            top
            right
        >
            {{ snackbar.text }}
            <v-btn
                dark
                flat
                @click="snackbar.active = false"
            >
                geri al
            </v-btn>
        </v-snackbar>
    </div>
</template>

<script>
    import MDataTable from '../../shared/components/data/components/MDataTable'
    import MDataTableAction from "@/modules/main/shared/components/data/components/MDataTableAction"
    import MDataTableAddNewForm from "../../shared/components/data/components/MDataTableAddNewForm"

    const orderModel = require('@/modules/main/order/models/order-model').default;

    export default {
        name: "MOrderManagementCurrent",

        components: {
            MDataTable,
            MDataTableAction,
            MDataTableAddNewForm
        },

        data() {
            return {
                loading: true,

                // Data-table
                // add-edit dialog data
                addEditData: {
                    [orderModel.name]: null,
                    [orderModel.sysrefRevenueType]: null,
                    // [orderModel.orderDate]: null,
                    [orderModel.billingNo]: null,
                    [orderModel.customer]: null,
                    [orderModel.workOrderNo]: null
                },

                // Data-table
                // add-edit dialog fields
                addEditFields: [
                    {
                        key: orderModel.name,
                        max: 20,
                        rules: [
                            'required', 'max:30'
                        ],
                        title: 'sipariş tanımı',
                        type: 'text',
                    },
                    // {
                    //     key: orderModel.orderDate,
                    //     max: 20,
                    //     rules: [
                    //         'required', 'max:30'
                    //     ],
                    //     title: 'ad',
                    //     type: 'text',
                    // },
                    {
                        key: orderModel.sysrefRevenueType,
                        title: 'gelir tipi',
                        type: 'select',
                        props: this.sysrefRevenueTypeList
                    },
                    {
                        key: orderModel.billingNo,
                        max: 20,
                        rules: [
                            'required', 'max:30'
                        ],
                        title: 'fatura numarası',
                        type: 'text',
                    },
                    {
                        key: orderModel.customer,
                        title: 'müşteri İş yeri',
                        type: 'select',
                        props: this.customerCompanyList
                    },
                    {
                        key: orderModel.workOrderNo,
                        max: 30,
                        title: 'iş emri numarası',
                        rules: [
                            'required', 'max:30'
                        ]
                    },
                    {
                        key: orderModel.active,
                        max: null,
                        type: 'checkbox',
                        props: ['aktif']
                    }
                ],
                headers: [
                    {
                        text: 'ıd',
                        sortable: true,
                        value: orderModel.id,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'gelir tipi',
                        sortable: true,
                        value: orderModel.sysrefRevenueType,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'müşteri firma',
                        sortable: true,
                        value: orderModel.customer,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'fatura numarası',
                        sortable: true,
                        value: orderModel.billingNo,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'sipariş tarihi',
                        sortable: true,
                        value: orderModel.orderDate,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    }
                ],

                orders: [],
                sysrefRevenueTypeList: [],
                customerCompanyList: [],

                newItem: null,

                snackbar: {
                    text: null,
                    textColor: null,
                    active: false
                },

                // Data table row click route
                to: {
                    name: require('@/modules/main/driver/route/index').routes.information
                }
            }
        },

        methods: {

            // Activates add new item dialog
            addDialog(data) {
                this.$refs.addEditDialog.open(data)
            },

            // Adds a new driver
            // to the system
            getAllVehicles() {
                let self = this;

                this.$http.get('api/v1/order?WAIT').then((result) => {
                    self.orders = result.data.data.items
                }).catch((error) => {
                    console.log(error);
                }).finally(() => this.loading = false)
            },

            addNewItem(item) {
                let self = this;
                this.newItem = item;

                this.$http.post('api/v1/order', this.newItem).then((result) => {
                    let status = result.data.status;
                    if (status < 299) {
                        self.snackbar.textColor = 'green--text text--accent-3';
                    } else {
                        self.snackbar.textColor = 'red--text';
                    }

                    self.snackbar.text = result.data.message;
                    self.snackbar.active = true;
                    self.getAllVehicles();
                }).catch((error) => {
                    console.log(error);
                })
            },

            editItem (item) {
                let self = this;

                this.$http.put('api/v1/order', item)
                    .then(result => {
                        let status = result.data.status;
                        if (status < 299) {
                            self.snackbar.textColor = 'green--text text--accent-3';
                        } else {
                            self.snackbar.textColor = 'red--text';
                        }

                        self.snackbar.text = result.data.message;
                        self.snackbar.active = true;
                        self.getAllVehicles()
                    }).catch(error => {
                    console.log(error)
                })
            },

            getSysrefRevenueTypeList() {
                let self = this;

                this.$http.get('api/v1/sysrefRevenueType').then((result) => {
                    self.sysrefRevenueTypeList = result.data.data.items

                    self.addEditFields.find(item => {
                        return item.key === orderModel.sysrefRevenueType
                    }).props = self.sysrefRevenueTypeList

                }).catch((error) => {
                    console.log(error);
                })
            },

            getCustomerCompanyList() {
                let self = this;

                this.$http.get('api/v1/customerCompany').then((result) => {
                    self.customerCompanyList = result.data.data.items

                    self.addEditFields.find(item => {
                        return item.key === orderModel.customer
                    }).props = self.customerCompanyList

                }).catch((error) => {
                    console.log(error);
                })
            },
            deleteItem(item){
                this.$http.delete(`api/v1/order/${item.id}`).then((result) => {
                    this.getAllVehicles()
                }).catch((error) => {
                    console.error(error);
                })
            }
        },

        mounted() {
            this.getAllVehicles();
            this.getSysrefRevenueTypeList();
            this.getCustomerCompanyList();
        }
    }
</script>

<style scoped>

</style>
