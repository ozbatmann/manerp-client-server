<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div>
        <m-data-table
            :headers="headers"
            :items="suppliers"
            :loading="loading"
            :to="to"
            @editItem="editDialog"
            @deleteItem="deleteItem"
        >
            <!-- Data table header slot -->
            <template v-slot:header>

                <!-- Add supplier button -->
                <m-data-table-action
                    title="tedarikçi iş yeri ekle"
                    @click="addDialog"
                ></m-data-table-action>
            </template>
        </m-data-table>

        <supplier-management-add-edit-dialog
            ref="supplierManagementAddEditDialog"
            @save="addNewItem"
            @edit="editItem"
        ></supplier-management-add-edit-dialog>

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
    import SupplierManagementAddEditDialog from "./supplierManagementAddEditDialog";

    const supplierModel = require('@/modules/main/company/models/company-model').default;

    export default {
        name: "supplierManagementTable",
        components: {
            SupplierManagementAddEditDialog,
            MDataTable,
            MDataTableAction,
        },

        data() {
            return {
                sysrefCompanyTypeSplId: null,
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
                        value: supplierModel.phone,
                        toggleable: true,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'E-Posta',
                        sortable: true,
                        value: supplierModel.email,
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
                    }
                ],

                snackbar: {
                    active: false,
                    text: null,
                    textColor: null
                },

                suppliers: [],
                newItem: null,

                to: {
                    name: require('@/modules/main/company/route/index').routes.information,
                },

                loading: true
            }
        },

        methods: {
            // Activates add new item dialog
            addDialog(data) {
                this.$refs.supplierManagementAddEditDialog.open(data)
            },
            editDialog(data) {
                if (data !== undefined && data !== null) {
                    this.$http.get("api/v1/company/" + data.id).then((result) => {
                        let items = result.data.data;
                        this.$refs.supplierManagementAddEditDialog.open(items)
                    }).catch((error) => {
                        console.error(error);
                    })
                }
            },
            editVendorDialog(data) {
                if (data !== undefined && data !== null) {
                    this.$http.get("api/v1/company/" + data.id).then((result) => {
                        let items = result.data.data;
                        this.$refs.supplierManagementAddEditDialog.openVendor(items)
                    }).catch((error) => {
                        console.error(error);
                    })
                }
            },
            getAllSuppliers() {
                let self = this;
                this.loading = true;

                let fields = 'fields=id,code,title,phone,email,taxNumber';
                let pagination = 'limit=10&offset=0';
                let companyTypeCode = 'sysrefCompanyTypeCode=SPL';
                this.$http.get('api/v1/company?' + fields + '&' + pagination + '&' + companyTypeCode).then((result) => {
                    self.suppliers = result.data.data.items
                }).catch((error) => {
                    console.log(error);
                }).finally(() => this.loading = false)
            },
            addNewItem(item) {
                let self = this;
                this.newItem = item;

                this.newItem.sysrefCompanyType = this.sysrefCompanyTypeSplId;
                console.log(this.newItem.sysrefCompanyType)
                this.$http.post('api/v1/company', this.newItem).then((result) => {
                    let status = result.data.status;
                    if (status < 299) {
                        self.snackbar.textColor = 'green--text text--accent-3';
                    } else {
                        self.snackbar.textColor = 'red--text';
                    }

                    self.showDialog = false;
                    self.snackbar.text = result.data.message;
                    self.snackbar.active = true;
                    self.getAllSuppliers();
                }).catch((error) => {
                    console.log(error);
                })
            },
            editItem(item) {
                let self = this;

                this.$http.put('api/v1/company', item)
                    .then(result => {
                        let status = result.data.status;
                        if (status < 299) {
                            self.snackbar.textColor = 'green--text text--accent-3';
                        } else {
                            self.snackbar.textColor = 'red--text';
                        }

                        self.snackbar.text = result.data.message;
                        self.snackbar.active = true;
                        self.getAllSuppliers()
                    }).catch(error => {
                    console.log(error);
                })
            },
            deleteItem(item) {
                this.$http.delete(`api/v1/company/${item.id}`).then((result) => {
                    this.getAllSuppliers();
                }).catch((error) => {
                    console.error(error);
                })
            },
            getSysrefCompanyTypeSpl() {
                this.$http.get("api/v1/sysrefCompanyType").then((result) => {
                    let items = result.data.data.items;
                    items.forEach((item) => {
                        if (item.code === 'SPL') {
                            this.sysrefCompanyTypeSplId = item.id;
                        }
                    });
                }).catch((error) => {
                    console.error(error);
                })
            }
        },

        mounted() {
            this.getAllSuppliers();
            this.getSysrefCompanyTypeSpl();
        }
    }
</script>

<style scoped>

</style>
