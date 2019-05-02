<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div>
        <m-data-table
            :headers="headers"
            :items="orders"
            :loading="loading"
        >
            <!-- Data table header slot -->
            <template v-slot:header>

                <!-- Add customer button -->
                <!--<m-data-table-action-->
                    <!--title="sipariş ekle"-->
                    <!--disabled-->
                <!--&gt;</m-data-table-action>-->
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
                loading: true,

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
            getAllVehicles() {
                let self = this;

                this.$http.get('api/v1/order?COMP').then((result) => {
                    self.orders = result.data.data.items
                }).catch((error) => {
                    console.log(error);
                }).finally(() => self.loading = false)
            },
        },

        mounted() {
            this.getAllVehicles();
        }
    }
</script>

<style scoped>

</style>
