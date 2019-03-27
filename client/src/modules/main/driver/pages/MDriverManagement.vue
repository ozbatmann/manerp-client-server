<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div>
        <m-data-table
            :headers="headers"
            :items="drivers"
            :loading="loading"
            :to="to"
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
            title="Yeni Şoför"
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

    const driverModel = require('@/modules/main/driver/models/driver-model').default;

    export default {
        name: "MDriverManagement",
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
                    [driverModel.firstName]: null,
                    [driverModel.middleName]: null,
                    [driverModel.lastName]: null,
                    [driverModel.tcIdNumber]: null,
                    // [driverModel.birthDate]: null,
                    [driverModel.gsmNo]: null,
                    [driverModel.homePhone]: null,
                    [driverModel.email]: null,
                    [driverModel.sysrefCountry]: null,
                    [driverModel.sysrefCity]: null,
                    [driverModel.sysrefDistrict]: null,
                    [driverModel.sysrefStaffContractType]: null,
                    [driverModel.drivingLicenseNumber]: null,
                    [driverModel.address]: null
                },

                // Data-table
                // add-edit dialog fields
                addEditFields: [
                    {
                        key: driverModel.firstName,
                        max: 20,
                        rules: [
                            'required', 'max:30'
                        ],
                        title: 'ad',
                        type: 'text',
                    },
                    {
                        key: driverModel.middleName,
                        max: 20,
                        rules: [
                            'required', 'max:30'
                        ],
                        title: 'İkinci ad',
                        type: 'text',
                    },
                    {
                        key: driverModel.lastName,
                        max: 20,
                        rules: [
                            'required', 'max:30'
                        ],
                        title: 'soyad',
                        type: 'text',
                    },
                    {
                        key: driverModel.tcIdNumber,
                        mask: '###########',
                        max: 11,
                        title: 't.c. kimlik numarası',
                        rules: [
                            'required', 'max:11'
                        ]
                    },
                    // {
                    //     key: driverModel.birthDate,
                    //     mask: 'date',
                    //     title: 'doğum tarihi',
                    //     type: 'date',
                    //     rules: []
                    // },
                    {
                        key: driverModel.gsmNo,
                        mask: 'phone',
                        max: 10,
                        title: 'gsm numarası',
                        type: 'tel',
                        rules: [
                            'required', 'max:11'
                        ]
                    },
                    {
                        key: driverModel.homePhone,
                        mask: 'phone',
                        max: 10,
                        title: 'ev telefonu',
                        type: 'tel',
                        rules: [
                            'required', 'max:11'
                        ]
                    },
                    {
                        key: driverModel.email,
                        max: 50,
                        title: 'eposta',
                        type: 'email',
                        rules: [
                            'required', 'max:11'
                        ]
                    },
                    {
                        key: driverModel.sysrefCountry,
                        title: 'ülke',
                        type: 'select',
                        props: this.sysrefCountryList
                    },
                    {
                        key: driverModel.sysrefCity,
                        title: 'İl',
                        type: 'select',
                        props: this.sysrefCityList
                    },
                    {
                        key: driverModel.sysrefDistrict,
                        title: 'ilçe',
                        type: 'select',
                        props: this.sysrefDistrictList
                    },
                    {
                        key: driverModel.address,
                        max: 255,
                        rules: [
                            'required', 'max:255'
                        ],
                        title: 'adres',
                        type: 'text',
                    },
                    {
                        key: driverModel.sysrefStaffContractType,
                        title: 'sözleşme tipi',
                        type: 'select',
                        props: this.sysrefStaffContractTypeList
                    },
                    {
                        key: driverModel.active,
                        max: null,
                        type: 'checkbox',
                        disabled: true,
                        props: [ 'aktif' ]
                    },
                    {
                        key: driverModel.drivingLicenseNumber,
                        max: null,
                        title: 'ehliyet numarası',
                        type: 'tel',
                        rules: [
                            'required', 'max:30'
                        ]
                    }
                ],

                // Data-table headers
                headers: [
                    {
                        text: 'ID',
                        sortable: true,
                        value: driverModel.id,
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
                        text: 'sözleşme tipi',
                        sortable: true,
                        value: driverModel.sysrefStaffContractType,
                        toggleable: true,
                        show: true
                    },
                    {
                        text: 'telefon',
                        sortable: true,
                        value: driverModel.gsmNo,
                        toggleable: true,
                        show: true,
                        search: {chip: false, value: null}
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
                        text: 'ehliyet numarası',
                        sortable: true,
                        value: driverModel.drivingLicenseNumber,
                        toggleable: true,
                        show: true,
                        search: {chip: false, value: null}
                    }
                ],

                // Drivers array
                drivers: [],
                sysrefCountryList: [],
                sysrefCityList: [],
                sysrefDistrictList: [],
                sysrefStaffContractTypeList: [],

                // Object that holds
                // snackbar information
                snackbar: {
                    active: false,
                    text: null,
                    textColor: null
                },

                newItem: null,

                // Data table row click route
                to: {
                    name: require('@/modules/main/driver/route/index').routes.information
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
            getAllDrivers() {
                let self = this;
                this.loading = true;

                this.$http.get('api/v1/driver').then((result) => {
                    self.drivers = result.data.data.items;
                }).catch((error) => {
                    console.log(error);
                }).finally(() => this.loading = false)
            },

            addNewItem(item) {
                this.newItem = item;
                let self = this;

                this.$http.post('api/v1/driver', this.newItem)
                    .then((result) => {
                        let status = result.data.status;
                        if (status < 299) {
                            self.snackbar.textColor = 'green--text text--accent-3';
                        } else {
                            self.snackbar.textColor = 'red--text';
                        }

                        self.snackbar.text = result.data.message;
                        self.snackbar.active = true;
                        self.getAllDrivers();
                }).catch((error) => {
                    console.log(error);
                })
            },

            editItem (item) {
                let self = this;

                this.$http.put('api/v1/driver/', item)
                    .then(result => {
                        let status = result.data.status;
                        if (status < 299) {
                            self.snackbar.textColor = 'green--text text--accent-3';
                        } else {
                            self.snackbar.textColor = 'red--text';
                        }

                        self.snackbar.text = result.data.message;
                        self.snackbar.active = true;
                        self.getAllDrivers();
                    }).catch(error => {
                        console.log(error)
                })
            },

            getSysrefCountryList() {
                let self = this;

                this.$http.get("api/v1/sysrefCountry").then((result) => {
                    self.sysrefCountryList = result.data.data.items;
                    self.addEditFields.find(item => {
                        return item.key === driverModel.sysrefCountry
                    }).props = self.sysrefCountryList

                }).catch((error) => {
                    console.error(error);
                })
            },

            getSysrefCityList() {
                let self = this;

                this.$http.get("api/v1/sysrefCity").then((result) => {
                    self.sysrefCityList = result.data.data.items
                    self.addEditFields.find(item => {
                        return item.key === driverModel.sysrefCity
                    }).props = self.sysrefCityList

                }).catch((error) => {
                    console.error(error);
                })
            },

            getSysrefDistrictList() {
                let self = this;

                this.$http.get("api/v1/sysrefDistrict").then((result) => {
                    self.sysrefDistrictList = result.data.data.items
                    self.addEditFields.find(item => {
                        return item.key === driverModel.sysrefDistrict
                    }).props = self.sysrefDistrictList

                }).catch((error) => {
                    console.error(error);
                })
            },

            getSysrefStaffContractType() {
                let self = this;

                this.$http.get("api/v1/sysrefStaffContractType").then((result) => {
                    self.sysrefStaffContractTypeList = result.data.data.items
                    self.addEditFields.find(item => {
                        return item.key === driverModel.sysrefStaffContractType
                    }).props = self.sysrefStaffContractTypeList

                }).catch((error) => {
                    console.error(error);
                })
            },
            deleteItem(item){
                let self = this;

                this.$http.delete(`api/v1/driver/${item.id}`).then((result) => {
                    self.getAllDrivers()
                }).catch((error) => {
                    console.error(error);
                })
            }
        },

        mounted() {
            this.getAllDrivers();
            this.getSysrefCountryList();
            this.getSysrefCityList();
            this.getSysrefDistrictList();
            this.getSysrefStaffContractType();
        }
    }
</script>

<style scoped>

</style>
