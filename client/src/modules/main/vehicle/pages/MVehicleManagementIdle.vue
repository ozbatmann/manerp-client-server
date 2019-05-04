<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div>
        <m-data-table
            :headers="headers"
            :items="vehicles"
            :loading="loading"
            @deleteItem="deleteItem"
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

        <m-vehicle-add-edit-form
                ref="addEditDialog"
                @save="addNewItem"
                @edit="editItem"
        ></m-vehicle-add-edit-form>

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
    import MDataTableAddNewForm from '@/modules/main/shared/components/data/components/MDataTableAddNewForm'
    import MVehicleAddEditForm from "../components/MVehicleAddEditForm";

    const vehicleModel = require('@/modules/main/vehicle/models/vehicle-model').default;

    export default {
        name: "MVehicleManagementIdle",
        components: {
            MVehicleAddEditForm,
            MDataTable,
            MDataTableAction,
            MDataTableAddNewForm
        },

        data() {
            return {
                loading: true,

                headers: [
                    {
                        text: 'ıd',
                        sortable: true,
                        value: vehicleModel.id,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'plaka',
                        sortable: true,
                        value: vehicleModel.plateNumber,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'filo kart numarası',
                        sortable: true,
                        value: vehicleModel.fleetCardNumber,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'tipi',
                        sortable: true,
                        value: vehicleModel.sysrefVehicleType,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'mülk tipi',
                        sortable: true,
                        value: vehicleModel.sysrefVehicleOwner,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    }
                ],

                vehicles: [],

                newItem: null,

                snackbar: {
                    text: null,
                    textColor: null,
                    active: false
                },

                // Data table row click route
                // to: {
                //     name: require('@/modules/main/vehicle/route/index').routes.information
                // }
            }
        },
        methods: {

            // Activates add new item dialog
            addDialog(data) {
                console.log('vehicle', data);
                this.$refs.addEditDialog.open(data)
            },

            // Adds a new driver
            // to the system
            getAllVehicles() {
                let self = this;
                this.loading = true;

                this.$http.get('api/v1/vehicle').then((result) => {
                    self.vehicles = result.data.data.items
                }).catch((error) => {
                    console.log(error);
                }).finally((result) => {
                    self.loading = false;
                })
            },
            addNewItem(item) {
                let self = this;
                this.newItem = item;
                this.loading = true;

                this.$http.post('api/v1/vehicle', this.newItem).then((result) => {
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
                }).finally((result) => {
                    self.loading = false;
                })
            },

            editItem(item) {
                let self = this;

                this.$http.put('api/v1/vehicle/', item)
                    .then(result => {
                        let status = result.data.status;
                        if (status < 299) {
                            self.snackbar.textColor = 'green--text text--accent-3';
                        } else {
                            self.snackbar.textColor = 'red--text';
                        }

                        self.snackbar.text = result.data.message;
                        self.snackbar.active = true;
                        self.getAllVehicles();
                    }).catch(error => {
                    console.log(error)
                })
            },

            deleteItem(item) {
                this.$http.delete(`api/v1/vehicle/${item.id}`).then((result) => {
                    this.getAllVehicles()
                }).catch((error) => {
                    console.error(error);
                })
            }
        },

        mounted() {
            this.getAllVehicles();
        }
    }
</script>

<style scoped>

</style>
