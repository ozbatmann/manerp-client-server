<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div>
        <m-data-table
            :headers="headers"
            :items="staffs"
            :loading="loading"
            :to="to"
            @editItem="editDialog"
            @deleteItem="deleteItem"
        >
            <!-- Data table header slot -->
            <template v-slot:header>

                <!-- Add customer button -->
                <m-data-table-action
                    title="personel ekle"
                    @click="addDialog"
                ></m-data-table-action>
            </template>
        </m-data-table>

        <staff-management-add-edit-dialog
                ref="staffManagementAddEditDialog"
                @save="addNewItem"
                @edit="editItem"
        ></staff-management-add-edit-dialog>
    </div>
</template>

<script>
    import MDataTable from '../../shared/components/data/components/MDataTable'
    import MDataTableAction from "@/modules/main/shared/components/data/components/MDataTableAction"
    import staffManagementAddEditDialog
        from "@/modules/main/staff/components/staffManagementAddEditDialog"

    const staffModel = require('@/modules/main/staff/models/staff-model').default;

    export default {
        name: "staffManagementTable",
        components: {
            MDataTable,
            MDataTableAction,
            staffManagementAddEditDialog
        },
        data() {
            return {
                // Data-table headers
                headers: [
                    {
                        text: 'ID',
                        sortable: true,
                        value: staffModel.code,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'ad',
                        sortable: true,
                        value: staffModel.firstName,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'soyad',
                        sortable: true,
                        value: staffModel.lastName,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'unvan',
                        sortable: true,
                        value: staffModel.refStaffTitle,
                        toggleable: true,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'sözleşme tipi',
                        sortable: true,
                        value: staffModel.sysrefStaffContractType,
                        toggleable: true,
                        show: true
                    },
                    {
                        text: 't.c. kimlik numarası',
                        sortable: true,
                        value: staffModel.tcIdNumber,
                        toggleable: true,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'telefon',
                        sortable: true,
                        value: staffModel.gsmNo,
                        toggleable: true,
                        show: true,
                        search: {chip: false, value: null}
                    }
                ],

                staffs: [],
                newItem: null,

                // Data table row click route
                to: {
                    name: require('@/modules/main/staff/route/index').routes.information
                },

                loading: true
            }
        },
        methods: {
            // Activates add new item dialog
            addDialog(data) {
                this.$refs.staffManagementAddEditDialog.open(data)
            },
            editDialog(data) {
                if (data !== undefined && data !== null) {
                    this.$http.get("api/v1/staff/" + data.id).then((result) => {
                        let items = result.data.data;
                        this.$refs.staffManagementAddEditDialog.open(items)
                    }).catch((error) => {
                        console.error(error);
                    })
                }
            },
            getAllStaffs() {
                let self = this;
                this.loading = true;

                let fields = 'fields=id,code,firstName,lastName,refStaffTitle=name,sysrefStaffContractType=name,tcIdNumber,gsmNo';
                let pagination = 'limit=10&offset=0';
                this.$http.get('api/v1/staff?' + fields + "&" + pagination).then((result) => {
                    self.staffs = result.data.data.items;
                }).catch((error) => {
                    console.log(error);
                }).finally(() => self.loading = false)
            },
            addNewItem(item) {
                this.newItem = item;
                let self = this;

                this.$http.post('api/v1/staff', this.newItem)
                    .then((result) => {
                        self.getAllStaffs();
                    }).catch((error) => {
                    console.log(error);
                })
            },
            editItem(item) {
                let self = this;

                this.$http.put('api/v1/staff/', item)
                    .then(result => {
                        self.getAllStaffs();
                    }).catch(error => {
                    console.log(error)
                })
            },
            deleteItem(item) {
                let self = this;

                this.$http.delete(`api/v1/staff/${item.id}`).then((result) => {
                    self.getAllStaffs();
                }).catch((error) => {
                    console.error(error);
                })
            },
        },
        mounted() {
            this.getAllStaffs();
        }
    }
</script>

<style scoped>

</style>
