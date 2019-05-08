<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div>
        <m-data-table
                :headers="headers"
                :items="vehicles"
                :loading="loading"
                @editItem="editDialog"
        >

        </m-data-table>

        <driver-management-add-edit-dialog
            ref="driverManagementAddEditDialog"
            onVoyageDriver
        ></driver-management-add-edit-dialog>
    </div>
</template>

<script>
    import MDataTable from '../../shared/components/data/components/MDataTable'
    import MDataTableAction from "@/modules/main/shared/components/data/components/MDataTableAction"
    import driverManagementAddEditDialog
        from "@/modules/main/driver/components/driverManagementAddEditDialog"

    const driverModel = require('@/modules/main/driver/models/driver-model').default;

    export default {
        name: "MDriveOnVoyage",
        components: {
            MDataTable,
            MDataTableAction,
            driverManagementAddEditDialog
        },
        data() {
            return {

                loading: false,
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

                vehicles: [],

                newItem: null,

                // Data table row click route
                // to: {
                //     name: require('@/modules/main/vehicle/route/index').routes.information
                // },
            }
        },
        methods: {
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
                let stateCode = 'driverStateCode=ONVOYAGE';
                this.$http.get('api/v1/driver?' + fields + "&" + pagination + '&' + stateCode).then((result) => {
                    self.drivers = result.data.data.items;
                }).catch((error) => {
                    console.log(error);
                }).finally(() => self.loading = false)
            },
        },

        mounted() {
            this.getAllDrivers();
        }
    }
</script>

<style scoped>

</style>
