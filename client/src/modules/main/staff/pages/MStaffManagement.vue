<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div>
        <m-data-table
            :headers="headers"
            :items="staffs"
            :loading="loading"
            :to="to"
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
            title="Yeni Personel"
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

    const staffModel = require('@/modules/main/staff/models/staff-model').default;

    export default {
        name: "MStaffManagement",
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
                    [staffModel.firstName]: null,
                    [staffModel.middleName]: null,
                    [staffModel.lastName]: null,
                    [staffModel.lastName]: null,
                    [staffModel.refStaffTitle]: null,
                    [staffModel.gsmNo]: null,
                    [staffModel.homePhone]: null,
                    [staffModel.email]: null,
                    [staffModel.sysrefCountry]: null,
                    [staffModel.sysrefCity]: null,
                    [staffModel.sysrefDistrict]: null,
                    [staffModel.sysrefStaffContractType]: null,
                    [staffModel.drivingLicenseNumber]: null,
                    [staffModel.address]: null
                },

                // Data-table
                // add-edit dialog fields
                addEditFields: [
                    {
                        key: staffModel.firstName,
                        max: 20,
                        rules: [
                            'required', 'max:30'
                        ],
                        title: 'ad',
                        type: 'text',
                    },
                    {
                        key: staffModel.middleName,
                        max: 20,
                        rules: [
                            'required', 'max:30'
                        ],
                        title: 'İkinci ad',
                        type: 'text',
                    },
                    {
                        key: staffModel.lastName,
                        max: 20,
                        rules: [
                            'required', 'max:30'
                        ],
                        title: 'soyad',
                        type: 'text',
                    },
                    {
                        key: staffModel.tcIdNumber,
                        mask: '###########',
                        max: 11,
                        title: 't.c. kimlik numarası',
                        rules: [
                            'required', 'max:11'
                        ]
                    },
                    {
                        key: staffModel.refStaffTitle,
                        title: 'unvanı',
                        type: 'select',
                        props: this.refStaffTitleList
                    },
                    {
                        key: staffModel.gsmNo,
                        mask: 'phone',
                        max: 10,
                        title: 'gsm numarası',
                        type: 'tel',
                        rules: [
                            'required', 'max:11'
                        ]
                    },
                    {
                        key: staffModel.homePhone,
                        mask: 'phone',
                        max: 10,
                        title: 'ev telefonu',
                        type: 'tel',
                        rules: [
                            'required', 'max:11'
                        ]
                    },
                    {
                        key: staffModel.email,
                        max: 50,
                        title: 'e-posta',
                        type: 'email',
                        rules: [
                            'required', 'max:11'
                        ]
                    },
                    {
                        key: staffModel.sysrefCountry,
                        title: 'ülke',
                        type: 'select',
                        props: this.sysrefCountryList
                    },
                    {
                        key: staffModel.sysrefCity,
                        title: 'İl',
                        type: 'select',
                        props: this.sysrefCityList
                    },
                    {
                        key: staffModel.sysrefDistrict,
                        title: 'ilçe',
                        type: 'select',
                        props: this.sysrefDistrictList
                    },
                    {
                        key: staffModel.address,
                        max: 255,
                        rules: [
                            'required', 'max:255'
                        ],
                        title: 'adres',
                        type: 'text',
                    },
                    {
                        key: staffModel.sysrefStaffContractType,
                        title: 'sözleşme tipi',
                        type: 'select',
                        props: this.sysrefStaffContractTypeList
                    },
                    {
                        key: staffModel.active,
                        max: null,
                        type: 'checkbox',
                        disabled: true,
                        props: ['aktif']
                    }
                ],

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

                // Staffs array
                staffs: [],
                sysrefCountryList: [],
                sysrefCityList: [],
                sysrefDistrictList: [],
                sysrefStaffContractTypeList: [],
                refStaffTitleList: [],

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
                    name: require('@/modules/main/staff/route/index').routes.information
                }
            }
        },

        methods: {

            // Activates add new item dialog
            addDialog(data) {
                this.$refs.addEditDialog.open(data)
            },

            getAllStaffs() {
                let self = this;
                this.loading = true;

                this.$http.get('api/v1/staff').then((result) => {
                    console.log(result.data.data.items)
                    self.staffs = result.data.data.items;
                }).catch((error) => {
                    console.log(error);
                }).finally(() => self.loading = false)
            },

            // Adds a new staff
            // to the system
            addNewItem(item) {
                this.newItem = item;
                let self = this;

                this.$http.post('api/v1/staff', this.newItem)
                    .then((result) => {
                        let status = result.data.status;
                        if (status < 299) {
                            self.snackbar.textColor = 'green--text text--accent-3';
                        } else {
                            self.snackbar.textColor = 'red--text';
                        }

                        self.snackbar.text = result.data.message;
                        self.snackbar.active = true;
                        self.getAllStaffs();
                    }).catch((error) => {
                    console.log(error);
                })
            },

            editItem(item) {
                let self = this;

                this.$http.put('api/v1/staff/', item)
                    .then(result => {
                        let status = result.data.status;
                        if (status < 299) {
                            self.snackbar.textColor = 'green--text text--accent-3';
                        } else {
                            self.snackbar.textColor = 'red--text';
                        }

                        self.snackbar.text = result.data.message;
                        self.snackbar.active = true;
                        self.getAllStaffs();
                    }).catch(error => {
                    console.log(error)
                })
            },

            getSysrefCountryList() {
                let self = this;

                this.$http.get("api/v1/sysrefCountry").then((result) => {
                    self.sysrefCountryList = result.data.data.items;
                    self.addEditFields.find(item => {
                        return item.key === staffModel.sysrefCountry
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
                        return item.key === staffModel.sysrefCity
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
                        return item.key === staffModel.sysrefDistrict
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
                        return item.key === staffModel.sysrefStaffContractType
                    }).props = self.sysrefStaffContractTypeList

                }).catch((error) => {
                    console.error(error);
                })
            },

            getRefStaffTitleList() {
                let self = this;

                this.$http.get("api/v1/refStaffTitle/getListForDropDown").then((result) => {
                    self.refStaffTitleList = result.data.data.items
                    self.addEditFields.find(item => {
                        return item.key === staffModel.refStaffTitle
                    }).props = self.refStaffTitleList

                }).catch((error) => {
                    console.error(error);
                })
            },
            deleteItem(item) {
                let self = this;

                this.$http.delete(`api/v1/staff/${item.id}`).then((result) => {
                    let status = result.data.status;
                    if (status < 299) {
                        self.snackbar.textColor = 'green--text text--accent-3';
                    } else {
                        self.snackbar.textColor = 'red--text';
                    }

                    self.snackbar.text = result.data.message;
                    self.snackbar.active = true;
                    self.getAllStaffs()
                }).catch((error) => {
                    console.error(error);
                })
            }
        },

        mounted() {
            staffModel.active = true;
            this.getAllStaffs();
            this.getSysrefCountryList();
            this.getSysrefCityList();
            this.getSysrefDistrictList();
            this.getSysrefStaffContractType();
            this.getRefStaffTitleList();
        }
    }
</script>

<style scoped>

</style>
