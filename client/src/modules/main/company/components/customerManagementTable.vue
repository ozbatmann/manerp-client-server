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
                <v-list-tile>Bayi ekle</v-list-tile>
                <v-list-tile @click="addDialog(item.bind)">Düzenle</v-list-tile>
            </template>
        </m-data-table>

        <customer-management-add-edit-dialog ref="customerManagementAddEditDialog"
                                          @save="addNewItem"
                                          @edit="editItem">
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
                    },
                    {
                        text: 'Vergi Dairesi',
                        sortable: true,
                        value: 'taxOffice',
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
                    // TODO: implement
                    // this.$http.get("api/v1/staff/" + data.id).then((result) => {
                    //     let items = result.data.data;
                    //     this.$refs.staffManagementAddEditDialog.open(items)
                    // }).catch((error) => {
                    //     console.error(error);
                    // })
                }
            },
            getAllCustomers() {
                let self = this;
                this.loading = true;

                this.$http.get('api/v1/customerCompany').then((result) => {
                    self.customers = result.data.data.items
                }).catch((error) => {
                    console.log(error);
                }).finally(() => this.loading = false)
            },
            addNewItem(item) {
                let self = this;
                this.newItem = item;

                this.$http.post('api/v1/customerCompany', this.newItem).then((result) => {
                    let status = result.data.status;
                    if (status < 299) {
                        self.snackbar.textColor = 'green--text text--accent-3';
                    } else {
                        self.snackbar.textColor = 'red--text';
                    }

                    self.snackbar.text = result.data.message;
                    self.snackbar.active = true;
                    self.getAllCustomers();
                }).catch((error) => {
                    console.log(error);
                })
            },
            editItem(item) {
                let self = this;

                this.$http.put('api/v1/customerCompany', item)
                    .then(result => {
                        let status = result.data.status;
                        if (status < 299) {
                            self.snackbar.textColor = 'green--text text--accent-3';
                        } else {
                            self.snackbar.textColor = 'red--text';
                        }

                        self.snackbar.text = result.data.message;
                        self.snackbar.active = true;
                        self.getAllCustomers()
                    }).catch(error => {
                    console.log(error);
                })
            },
            // getSysrefCountryList() {
            //     let self = this;
            //
            //     this.$http.get("api/v1/sysrefCountry").then((result) => {
            //         self.sysrefCountryList = result.data.data.items
            //         self.addEditFields.find(item => {
            //             return item.key === customerModel.sysrefCountry
            //         }).props = self.sysrefCountryList
            //
            //     }).catch((error) => {
            //         console.error(error);
            //     })
            // },
            // getSysrefCityList() {
            //     let self = this;
            //
            //     this.$http.get("api/v1/sysrefCity").then((result) => {
            //         self.sysrefCityList = result.data.data.items
            //         self.addEditFields.find(item => {
            //             return item.key === customerModel.sysrefCity
            //         }).props = self.sysrefCityList
            //
            //     }).catch((error) => {
            //         console.error(error);
            //     })
            // },
            // getSysrefDistrictList() {
            //     let self = this;
            //
            //     this.$http.get("api/v1/sysrefDistrict").then((result) => {
            //         self.sysrefDistrictList = result.data.data.items
            //         self.addEditFields.find(item => {
            //             return item.key === customerModel.sysrefDistrict
            //         }).props = self.sysrefDistrictList
            //
            //     }).catch((error) => {
            //         console.error(error);
            //     })
            // },
            // getRefCompanySectorList() {
            //     let self = this;
            //
            //     this.$http.get("api/v1/refCompanySector/getListForDropDown").then((result) => {
            //         self.refCompanySectorList = result.data.data.items
            //         self.addEditFields.find(item => {
            //             return item.key === customerModel.refCompanySector
            //         }).props = self.refCompanySectorList
            //
            //     }).catch((error) => {
            //         console.error(error);
            //     })
            // },
            deleteItem(item) {
                this.$http.delete(`api/v1/customerCompany/${item.id}`).then((result) => {
                    this.getAllCustomers();
                }).catch((error) => {
                    console.error(error);
                })
            }
        },

        mounted() {
            console.log('mounted')
            this.getAllCustomers();
            // this.getSysrefCountryList();
            // this.getSysrefCityList();
            // this.getSysrefDistrictList();
            // this.getRefCompanySectorList();
        }
    }
</script>

<style scoped>

</style>
