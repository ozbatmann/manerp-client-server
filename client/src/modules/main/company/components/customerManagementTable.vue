<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div>
        <m-data-table
            :headers="headers"
            :items="customers"
            :loading="loading"
            :to="to"
            @deleteItem="deleteItem"
        >
            <!-- Data table header slot -->
            <template v-slot:header>

                <!-- Add customer button -->
                <m-data-table-action
                    title="müşteri iş yeri ekle"
                    @click="addDialog"
                ></m-data-table-action>
            </template>

            <!-- Data table action menu slot -->
            <template v-slot:action-menu="item">
                <v-list-tile @click="editVendorDialog(item.bind)">Bayi ekle</v-list-tile>
                <v-list-tile @click="editDialog(item.bind)">Düzenle</v-list-tile>
            </template>
        </m-data-table>

        <customer-management-add-edit-dialog ref="customerManagementAddEditDialog"
                                             @save="addNewItem"
                                             @edit="editItem"
                                             @displayMessage="displaySnackMessage">
        </customer-management-add-edit-dialog>

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
    import customerManagementAddEditDialog
        from "@/modules/main/company/components/customerManagementAddEditDialog"

    const customerModel = require('@/modules/main/company/models/company-model').default;

    export default {
        name: "customerManagementTable",
        components: {
            MDataTable,
            MDataTableAction,
            customerManagementAddEditDialog
        },

        data() {
            return {
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
                        value: customerModel.phone,
                        toggleable: true,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'E-Posta',
                        sortable: true,
                        value: customerModel.email,
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

                customers: [],
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
                this.$refs.customerManagementAddEditDialog.open(data)
            },
            editDialog(data) {
                if (data !== undefined && data !== null) {
                    this.$http.get("api/v1/company/" + data.id).then((result) => {
                        let items = result.data.data;
                        this.$refs.customerManagementAddEditDialog.open(items)
                    }).catch((error) => {
                        console.error(error);
                    })
                }
            },
            editVendorDialog(data) {
                if (data !== undefined && data !== null) {
                    this.$http.get("api/v1/company/" + data.id).then((result) => {
                        let items = result.data.data;
                        this.$refs.customerManagementAddEditDialog.openVendor(items)
                    }).catch((error) => {
                        console.error(error);
                    })
                }
            },
            getAllCustomers() {
                let self = this;
                this.loading = true;

                let fields = 'fields=id,code,title,phone,email,taxNumber';
                let pagination = 'limit=10&offset=0';
                let companyTypeCode = 'companyTypeCode=CST';
                this.$http.get('api/v1/company?' + fields + '&' + pagination + '&' + companyTypeCode).then((result) => {
                    self.customers = result.data.data.items
                }).catch((error) => {
                    console.log(error);
                }).finally(() => this.loading = false)
            },
            addNewItem(item) {
                let self = this;
                this.newItem = item;

                this.newItem.sysrefCompanyType = 'ff8081816a2261ec016a226283a30005'; // TODO: change - companyType customer

                this.$http.post('api/v1/company', this.newItem).then((result) => {
                    self.displaySnackMessage(result);
                    self.getAllCustomers();
                }).catch((error) => {
                    console.log(error);
                })
            },
            editItem(item) {
                let self = this;
                this.$http.put('api/v1/company', item)
                    .then(result => {
                        self.displaySnackMessage(result);
                        self.getAllCustomers()
                    }).catch(error => {
                    console.log(error);
                })
            },
            deleteItem(item) {
                this.$http.delete(`api/v1/company/${item.id}`).then((result) => {
                    this.getAllCustomers();
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
            this.getAllCustomers();
            this.tmp();
        }
    }
</script>

<style scoped>

</style>
