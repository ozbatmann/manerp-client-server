<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div>
        <m-data-table
            :headers="headers"
            :items="orders"
            :to="to"
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
        name: "MOrderManagementCompleted",

        components: {
            MDataTable,
            MDataTableAction,
            MDataTableAddNewForm
        },

        data() {
            return {
                // Data-table
                // add-edit dialog data
                addEditData: {
                    [orderModel.sysrefRevenueType]: null,
                    [orderModel.orderDate]: null,
                    [orderModel.billingNo]: null,
                    [orderModel.customer]: null
                },

                // Data-table
                // add-edit dialog fields
                addEditFields: [

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

                snackbar: false
            }
        },

        methods: {

            // Activates add new item dialog
            addDialog(data) {
                this.$refs.addEditDialog.open(data)
            },

            // Adds a new driver
            // to the system
            getAllOrders() {
                this.$http.get('api/v1/order?COMP').then((result) => {
                    this.orders = result.data.data.items
                }).catch((error) => {
                    console.log(error);
                })
            },
            addNewItem(item) {
                this.newItem = item
                this.$http.post('api/v1/order', this.newItem).then((result) => {
                    this.getAllDrivers();
                }).catch((error) => {
                    console.log(error);
                })
            }
        },

        mounted() {
            this.getAllOrders();
            // for (let i = 0; i < 15; i++) {
            //     let order = {
            //         id: `ORD-${i}`,
            //         type: 'Sözleşmeli',
            //         customer: 'Pinar Gida A.Ş',
            //         direction: 'Gidiş/Dönüş',
            //         route: `Sevenler Gıda
            //                 <i class="v-icon material-icons green--text text--accent-3"
            //                     style="font-size: 16px;">arrow_forward</i> Akmerler Bim`,
            //         routes: [
            //             {
            //                 name: 'Sevenler Gıda',
            //                 city: 'Ankara',
            //                 district: 'Çankaya',
            //                 address: 'Beytepe Mah., 1800.cd, No: 6, D: 6'
            //             },
            //
            //             {
            //                 name: 'Akmerler Bim',
            //                 city: 'Trabzon',
            //                 district: 'Çobanlı',
            //                 address: 'Talatdere Mah., 2.cd, No: 12'
            //             },
            //
            //             {
            //                 name: 'Akmerler Bim',
            //                 city: 'Trabzon',
            //                 district: 'Çobanlı',
            //                 address: 'Talatdere Mah., 2.cd, No: 12'
            //             },
            //
            //             {
            //                 name: 'Akmerler Bim',
            //                 city: 'Trabzon',
            //                 district: 'Çobanlı',
            //                 address: 'Talatdere Mah., 2.cd, No: 12'
            //             }
            //         ]
            //     };
            //
            //     this.orders.push(order)
            // }
        }
    }
</script>

<style scoped>

</style>
