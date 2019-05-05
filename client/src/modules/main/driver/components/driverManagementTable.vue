<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div>
        <m-data-table
            :headers="headers"
            :items="drivers"
            :loading="loading"
            :to="to"
            @editItem="editDialog"
            @deleteItem="deleteItem"
        >
            <!-- Data table header slot -->
            <template v-slot:header>

                <!-- Add customer button -->
                <m-data-table-action
                    title="şoför ekle"
                    @click="addDialog"
                ></m-data-table-action>
            </template>
        </m-data-table>

        <driver-management-add-edit-dialog
            ref="driverManagementAddEditDialog"
            @save="addNewItem"
            @edit="editItem"
        ></driver-management-add-edit-dialog>

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
    import driverManagementAddEditDialog
        from "@/modules/main/driver/components/driverManagementAddEditDialog"

    const driverModel = require('@/modules/main/driver/models/driver-model').default;

    export default {
        name: "driverManagementTable",
        components: {
            MDataTable,
            MDataTableAction,
            driverManagementAddEditDialog
        },
        data() {
            return {
                // Data-table headers
                headers: [
                    {
                        text: 'ID',
                        sortable: true,
                        value: driverModel.code,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'ad',
                        sortable: true,
                        value: driverModel.firstName,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'soyad',
                        sortable: true,
                        value: driverModel.lastName,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'ehliyet numarası',
                        sortable: true,
                        value: driverModel.drivingLicenseNumber,
                        toggleable: true,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'sözleşme tipi',
                        sortable: true,
                        value: driverModel.sysrefStaffContractType,
                        toggleable: true,
                        show: true
                    },
                    {
                        text: 't.c. kimlik numarası',
                        sortable: true,
                        value: driverModel.tcIdNumber,
                        toggleable: true,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'telefon',
                        sortable: true,
                        value: driverModel.gsmNo,
                        toggleable: true,
                        show: true,
                        search: {chip: false, value: null}
                    }
                ],

                // Object that holds
                // snackbar information
                snackbar: {
                    active: false,
                    text: null,
                    textColor: null
                },

                drivers: [],
                newItem: null,

                // Data table row click route
                to: {
                    name: require('@/modules/main/driver/route/index').routes.information
                },

                loading: true
            }
        },
        methods: {
            // Activates add new item dialog
            addDialog(data) {
                this.$refs.driverManagementAddEditDialog.open(data)
            },
            editDialog(data) {
                if (data !== undefined && data !== null) {
                    this.$http.get("api/v1/driver/" + data.id).then((result) => {
                        let items = result.data.data;
                        this.$refs.driverManagementAddEditDialog.open(items)
                    }).catch((error) => {
                        console.error(error);
                    })
                }
            },
            getAllDrivers() {
                let self = this;
                this.loading = true;

                let fields = 'fields=id,code,firstName,lastName,drivingLicenseNumber,sysrefStaffContractType=name,tcIdNumber,gsmNo';
                let pagination = 'limit=10&offset=0';
                this.$http.get('api/v1/driver?' + fields + "&" + pagination).then((result) => {
                    self.drivers = result.data.data.items;
                }).catch((error) => {
                    console.log(error);
                }).finally(() => self.loading = false)
            },
            addNewItem(item) {
                this.newItem = item;
                let self = this;

                this.$http.post('api/v1/driver', this.newItem)
                    .then((result) => {
                        self.displaySnackMessage(result);
                        self.getAllDrivers();
                    }).catch((error) => {
                    console.log(error);
                })
            },
            editItem(item) {
                let self = this;

                this.$http.put('api/v1/driver/', item)
                    .then(result => {
                        self.displaySnackMessage(result);
                        self.getAllDrivers();
                    }).catch(error => {
                    console.log(error)
                })
            },
            deleteItem(item) {
                let self = this;

                this.$http.delete(`api/v1/driver/${item.id}`).then((result) => {
                    self.displaySnackMessage(result);
                    self.getAllDrivers();
                }).catch((error) => {
                    console.error(error);
                })
            },
            displaySnackMessage(result) {
                let status = result.data.status;
                if (status < 299) {
                    this.snackbar.textColor = 'green--text text--accent-3';
                } else {
                    this.snackbar.textColor = 'red--text';
                }

                this.snackbar.text = result.data.message;
                this.snackbar.active = true;
            }
        },
        mounted() {
            this.getAllDrivers();
        }
    }
</script>

<style scoped>

</style>
