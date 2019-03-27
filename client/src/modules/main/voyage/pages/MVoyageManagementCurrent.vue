
<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div>
        <m-data-table
            :headers="headers"
            :items="voyages"
            :loading="loading"
            @deleteItem="deleteItem"
        >
            <!-- Data table header slot -->
            <template v-slot:header>

                <!-- Add customer button -->
                <m-data-table-action
                    title="sevkiyat ekle"
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
            title="Yeni Sevkiyat"
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

    const voyageModel = require('@/modules/main/voyage/models/voyage-model').default;

    export default {
        name: "MVoyageManagementCurrent",

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
                    [voyageModel.order]: null,
                    [voyageModel.company]: null,
                    [voyageModel.vehicle]: null,
                    [voyageModel.driver]: null,
                    [voyageModel.sysRefTransportationType]: null,
                    [voyageModel.sysRefVoyageDirection]: null,
                },

                // Data-table
                // add-edit dialog fields
                addEditFields: [
                    {
                        key: voyageModel.order,
                        title: 'sipariş',
                        type: 'select',
                        props: this.sysRefOrderList
                    },
                    {
                        key: voyageModel.company,
                        rules: [
                            'required'
                        ],
                        title: 'şirket',
                        type: 'select',
                        props: this.sysRefCompanyList
                    },
                    {
                        key: voyageModel.vehicle,
                        title: 'araç',
                        rules: [
                            'required'
                        ],
                        type: 'select',
                        props: this.sysRefVehicleList
                    },
                    {
                        key: voyageModel.driver,
                        type: 'select',
                        title: 'şoför',
                        rules: [
                            'required'
                        ],
                        props: this.sysRefDriverList
                    },
                    {
                        key: voyageModel.sysRefTransportationType,
                        title: 'ulaşım türü',
                        type: 'select',
                        props: this.sysRefTransportationTypeList
                    },
                    {
                        key: voyageModel.sysRefVoyageDirection,
                        title: 'sevkiyat yönü',
                        type: 'select',
                        props: this.sysRefVoyageDirectionList
                    }
                ],
                headers: [
                    {
                        text: 'ıd',
                        sortable: true,
                        value: voyageModel.code,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'sipariş',
                        sortable: true,
                        value: voyageModel.order,
                        toggleable: false,
                        show: true,
                        search: { chip: false, value: null },
                    },
                    {
                        text: 'şirket',
                        sortable: true,
                        value: voyageModel.company,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'araç',
                        sortable: true,
                        value: voyageModel.vehicle,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'şoför',
                        sortable: true,
                        value: voyageModel.driver,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'ulaşım türü',
                        sortable: true,
                        value: voyageModel.sysRefTransportationType,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'sevkiyat yönü',
                        sortable: true,
                        value: voyageModel.sysRefVoyageDirection,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    }
                ],

                voyages: [],
                sysRefCompanyList: [],
                sysRefVehicleList: [],
                sysRefDriverList: [],
                sysRefOrderList: [],
                sysRefTransportationTypeList: [],
                sysRefVoyageDirectionList: [],

                newItem: null,

                snackbar: false,

                // // Data table row click route
                // to: {
                //     name: require('@/modules/main/voyage/route/index').routes.information
                // }
            }
        },
        methods: {

            // Activates add new item dialog
            addDialog(data) {
                this.$refs.addEditDialog.open(data)
            },

            // List all voyages
            getAllVoyages() {
                let self = this;
                this.loading = true;

                this.$http.get('api/v1/voyage').then((result) => {
                    self.voyages = result.data.data.items;
                    console.log(result.data.data.items)
                }).catch((error) => {
                    console.log(error);
                }).finally(() => self.loading = false)
            },
            // Adds a new item
            // to the system
            addNewItem(item) {
                let self = this;
                this.newItem = item;

                this.$http.post('api/v1/voyage', this.newItem).then((result) => {
                    self.getAllVoyages();
                }).catch((error) => {
                    console.log(error);
                })
            },

            // Edits an item
            editItem(item) {
                let self = this;

                this.$http.put('api/v1/voyage', item).then((result) => {
                    self.getAllVoyages();
                }).catch((error) => {
                    console.log(error);
                })
            },

            // Gets waiting orders
            getSysRefOrder () {
                let self = this;

                this.$http.get("api/v1/order/getListForDropDown?WAIT").then((result) => {
                    self.sysRefOrderList = result.data.data.items;
                    self.addEditFields.find(item => {
                        return item.key === voyageModel.order
                    }).props = self.sysRefOrderList
                }).catch((error) => {
                    console.error(error);
                })
            },

            // Get customers list
            getSysRefCompany() {
                let self = this;

                this.$http.get("api/v1/customerCompany/getListForDropDown").then((result) => {
                    self.sysRefCompanyList = result.data.data.items;
                    self.addEditFields.find(item => {
                        return item.key === voyageModel.company
                    }).props = self.sysRefCompanyList
                }).catch((error) => {
                    console.error(error);
                })
            },

            // Get vehicle list
            getSysRefVehicle() {
                let self = this;

                this.$http.get("api/v1/vehicle/getListForDropDown").then((result) => {
                    self.sysRefVehicleList = result.data.data.items;
                    self.addEditFields.find(item => {
                        return item.key === voyageModel.vehicle
                    }).props = self.sysRefVehicleList
                }).catch((error) => {
                    console.error(error);
                })
            },

            // Get driver list
            getSysRefDriver() {
                let self = this;

                this.$http.get("api/v1/driver/getListForDropDown").then((result) => {
                    self.sysRefDriverList = result.data.data.items;
                    self.addEditFields.find(item => {
                        return item.key === voyageModel.driver
                    }).props = self.sysRefDriverList
                }).catch((error) => {
                    console.error(error);
                })
            },

            // Get transportation type list
            getSysRefTransportationTypeList() {
                let self = this;

                this.$http.get("api/v1/sysrefTransportationType").then((result) => {
                    self.sysRefTransportationTypeList = result.data.data.items
                    self.addEditFields.find(item => {
                        return item.key === voyageModel.sysRefTransportationType
                    }).props = self.sysRefTransportationTypeList
                }).catch((error) => {
                    console.error(error);
                })
            },

            // Get voyage direction list
            getSysRefVoyageDirectionList() {
                let self = this;

                this.$http.get("api/v1/sysrefVoyageDirection").then((result) => {
                    self.sysRefVoyageDirectionList = result.data.data.items
                    self.addEditFields.find(item => {
                        return item.key === voyageModel.sysRefVoyageDirection
                    }).props = self.sysRefVoyageDirectionList

                }).catch((error) => {
                    console.error(error);
                })
            },

            // Deletes an item
            deleteItem(item){
                let self = this;

                this.$http.delete(`api/v1/voyage/${item.id}`).then((result) => {
                    self.getAllVoyages()
                }).catch((error) => {
                    console.error(error);
                })
            }
        },

        mounted() {
            this.getAllVoyages();
            this.getSysRefOrder();
            this.getSysRefCompany();
            this.getSysRefDriver();
            this.getSysRefVehicle();
            this.getSysRefTransportationTypeList();
            this.getSysRefVoyageDirectionList();
        }
    }
</script>

<style scoped>

</style>

