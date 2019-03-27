<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div>
        <m-data-table
            :headers="headers"
            :items="vehicles"
            :to="to"
        >
            <!-- Data table header slot -->
            <template v-slot:header>

                <!-- Add customer button -->
                <m-data-table-action
                    title="araç ekle"
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
            title="Yeni Araç"
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
    import MDataTable from '@/modules/main/shared/components/data/components/MDataTable'
    import MDataTableAction from "@/modules/main/shared/components/data/components/MDataTableAction"

    const vehicleModel = require('@/modules/main/vehicle/models/vehicle-model').default;

    export default {
        name: "MVehicleManagementIdle",

        components: {
            MDataTable,
            MDataTableAction,
        },

        data() {
            return {
                // Data-table
                // add-edit dialog data
                addEditData: {
                    [vehicleModel.plateNumber]: null,
                    [vehicleModel.fleetCardNumber]: null,
                    // [orderModel.orderDate]: null,
                    [vehicleModel.km]: null,
                    [vehicleModel.isDualRegime]: null,
                    [vehicleModel.refWorkingArea]: null,
                    [vehicleModel.sysrefVehicleType]: null,
                    [vehicleModel.sysrefVehicleOwner]: null,
                    [vehicleModel.vehicleOwnerFullName]: null,
                    [vehicleModel.kgsNo]: null,
                    [vehicleModel.ogsNo]: null,
                    [vehicleModel.description]: null
                },

                // Data-table
                // add-edit dialog fields
                addEditFields: [
                    {
                        key: vehicleModel.plateNumber,
                        max: 20,
                        rules: [
                            'required', 'max:30'
                        ],
                        title: 'plaka',
                        type: 'text',
                    },
                    {
                        key: vehicleModel.isDualRegime,
                        max: null,
                        type: 'checkbox',
                        props: ['çift rejim']
                    },
                    {
                        key: vehicleModel.active,
                        max: null,
                        type: 'checkbox',
                        props: ['aktif']
                    }
                ],
                headers: [
                    {
                        text: 'ıd',
                        sortable: true,
                        value: vehicleModel.id,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    }
                ],

                vehicles: [],

                newItem: null,

                snackbar: false,

                // Data table row click route
                to: {
                    name: require('@/modules/main/vehicle/route/index').routes.information
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
            getAllOrders() {
                this.$http.get('api/v1/order').then((result) => {
                    this.orders = result.data.data.items
                }).catch((error) => {
                    console.log(error);
                })
            },
            addNewItem(item) {
                this.newItem = item
                this.$http.post('api/v1/order?WAIT', this.newItem).then((result) => {
                    this.getAllDrivers();
                }).catch((error) => {
                    console.log(error);
                })
            },
            getSysrefRevenueTypeList() {
                this.$http.get('api/v1/sysrefRevenueType').then((result) => {
                    this.sysrefRevenueTypeList = result.data.data.items

                    this.addEditFields.find(item => {
                        return item.key === orderModel.sysrefRevenueType
                    }).props = this.sysrefRevenueTypeList

                }).catch((error) => {
                    console.log(error);
                })
            },
            getCustomerCompanyList() {
                this.$http.get('api/v1/customerCompany/getListForDropDown/').then((result) => {
                    this.customerCompanyList = result.data.data.items

                    this.addEditFields.find(item => {
                        return item.key === orderModel.customer
                    }).props = this.customerCompanyList

                }).catch((error) => {
                    console.log(error);
                })
            }
        },
        mounted() {

        }
    }
</script>

<style scoped>

</style>
