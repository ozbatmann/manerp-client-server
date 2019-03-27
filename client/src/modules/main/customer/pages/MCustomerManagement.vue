<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div>
        <m-data-table
                :headers="headers"
                :items="firms"
                :loading="loading"
                :to="to"
        >
            <!-- Data table header slot -->
            <template v-slot:header>

                <!-- Add customer button -->
                <m-data-table-action
                        title="FİRMA EKLE"
                        @click="addDialog"
                ></m-data-table-action>
            </template>

            <!-- Data table action menu slot -->
            <template v-slot:action-menu="item">
                <v-list-tile>Bayi ekle</v-list-tile>
                <v-list-tile @click="addDialog(item.bind)">Düzenle</v-list-tile>
            </template>
        </m-data-table>

        <m-data-table-add-new-form
                title="Yeni Firma"
                ref="addEditDialog"
                :data="addEditData"
                :inputs="addEditFields"
                @save="addNewItem"
                @edit="editItem"
        ></m-data-table-add-new-form>
    </div>
</template>

<script>
    import MDataTable from '../../shared/components/data/components/MDataTable'
    import MDataTableAction from "@/modules/main/shared/components/data/components/MDataTableAction"
    import MDataTableAddNewForm from "../../shared/components/data/components/MDataTableAddNewForm";

    const customerModel = require('@/modules/main/customer/models/customer-model').default;

    export default {
        name: "MCustomerManagement",
        components: {
            MDataTableAddNewForm,
            MDataTableAction,
            MDataTable
        },

        data() {
            return {
                loading: true,

                addEditData: {
                    [customerModel.name]: null,
                    [customerModel.sysrefCountry]: null,
                    [customerModel.sysrefCity]: null,
                    [customerModel.sysrefDistrict]: null,
                    [customerModel.address]: null,
                    [customerModel.phone]: null,
                    [customerModel.phone2]: null,
                    [customerModel.fax]: null,
                    [customerModel.gsm]: null,
                    [customerModel.webAddress]: null,
                    [customerModel.email]: null,
                    [customerModel.refCompanySector]: null,
                    [customerModel.customerRepresentative]: null,
                    [customerModel.numberOfStaff]: null,
                    [customerModel.employerRegistrationNo]: null,
                    [customerModel.tradeRegistrationNo]: null,
                    [customerModel.taxOffice]: null,
                    [customerModel.taxNumber]: null,
                },

                addEditFields: [
                    {
                        title: 'Firma Adı',
                        key: customerModel.name,
                        max: 50,
                        type: 'text',
                        value: null,
                        rules: ['required', 'max:50']
                    },
                    {
                        title: 'Firma Ünvanı',
                        key: customerModel.title,
                        max: 50,
                        type: 'text',
                        value: null,
                        rules: ['required', 'max:50']
                    },

                    {
                        title: 'Telefon',
                        key: customerModel.phone,
                        type: 'phone',
                        value: null,
                        mask: 'phone',
                        rules: ['required', 'max:11'],
                        max: 11
                    },
                    {
                        title: 'E-posta',
                        key: customerModel.email,
                        type: 'mail',
                        value: null,
                        rules: ['required', 'email', 'max:50'],
                        max: 50
                    },
                    {
                        key: customerModel.sysrefCountry,
                        title: 'ülke',
                        type: 'select',
                        props: this.sysrefCountryList
                    },
                    {
                        key: customerModel.sysrefCity,
                        title: 'İl',
                        type: 'select',
                        props: this.sysrefCityList
                    },
                    {
                        key: customerModel.sysrefDistrict,
                        title: 'ilçe',
                        type: 'select',
                        props: this.sysrefDistrictList
                    },
                    {
                        title: 'Adres',
                        key: customerModel.address,
                        type: 'text',
                        value: null,
                        rules: ['required', 'max:255'],
                        max: 255
                    },
                    {
                        title: 'Vergi Numarası',
                        key: customerModel.taxNumber,
                        type: 'text',
                        value: null,
                        rules: ['required', 'max:50'],
                        max: 50
                    },

                    {
                        title: 'Vergi Dairesi',
                        key: customerModel.taxOffice,
                        type: 'text',
                        value: null,
                        rules: ['required', 'max:255'],
                        max: 255
                    },
                    {
                        title: 'Faks Numarası',
                        key: customerModel.fax,
                        type: 'text',
                        value: null,
                        rules: ['required', 'max:20'],
                        max: 20
                    },
                    {
                        title: 'Web Adresi',
                        key: customerModel.webAddress,
                        type: 'text',
                        value: null,
                        rules: ['required', 'max:30'],
                        max: 30
                    },
                    {
                        key: customerModel.refCompanySector,
                        title: 'İş Yeri Sektörü',
                        type: 'select',
                        props: this.refCompanySectorList
                    },
                    {
                        title: 'Müşteri Temsilcisi',
                        key: customerModel.customerRepresentative,
                        type: 'text',
                        value: null,
                        rules: ['required', 'max:30'],
                        max: 30
                    },
                    {
                        title: 'İşveren Sicil Numarası',
                        key: customerModel.employerRegistrationNo,
                        type: 'text',
                        value: null,
                        rules: ['required', 'max:30'],
                        max: 30
                    },
                    {
                        title: 'Ticari Sicil Numarası',
                        key: customerModel.tradeRegistrationNo,
                        type: 'text',
                        value: null,
                        rules: ['required', 'max:30'],
                        max: 30
                    },
                    {
                        key: customerModel.active,
                        max: null,
                        type: 'checkbox',
                        props: ['aktif']
                    }
                ],

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

                firms: [],
                sysrefCountryList: [],
                sysrefCityList: [],
                sysrefDistrictList: [],
                refCompanySectorList: [],

                newItem: null,
                to: {
                    name: require('@/modules/main/customer/route/index').routes.information,
                }
            }
        },

        methods: {
            // Activates add new item dialog
            addDialog(data) {
                this.$refs.addEditDialog.open(data)
            },
            getAllCustomers() {
                let self = this;
                this.loading = true;

                this.$http.get('api/v1/customerCompany').then((result) => {
                    self.firms = result.data.data.items
                }).catch((error) => {
                    console.log(error);
                }).finally(() => this.loading = false)
            },
            addNewItem(item) {
                let self = this;
                this.newItem = item;

                this.$http.post('api/v1/customerCompany', this.newItem).then((result) => {
                    self.getAllCustomers();
                }).catch((error) => {
                    console.log(error);
                })
            },
            editItem (item) {
                let self = this;

                this.$http.put('api/v1/customerCompany', item)
                    .then(result => {
                        self.getAllCustomers()
                    }).catch(error => {
                        console.log(error);
                })
            },
            getSysrefCountryList() {
                this.$http.get("api/v1/sysrefCountry").then((result) => {
                    this.sysrefCountryList = result.data.data.items
                    this.addEditFields.find(item => {
                        return item.key === customerModel.sysrefCountry
                    }).props = this.sysrefCountryList

                }).catch((error) => {
                    console.error(error);
                })
            },
            getSysrefCityList() {
                this.$http.get("api/v1/sysrefCity").then((result) => {
                    this.sysrefCityList = result.data.data.items
                    this.addEditFields.find(item => {
                        return item.key === customerModel.sysrefCity
                    }).props = this.sysrefCityList

                }).catch((error) => {
                    console.error(error);
                })
            },
            getSysrefDistrictList() {
                this.$http.get("api/v1/sysrefDistrict").then((result) => {
                    this.sysrefDistrictList = result.data.data.items
                    this.addEditFields.find(item => {
                        return item.key === customerModel.sysrefDistrict
                    }).props = this.sysrefDistrictList

                }).catch((error) => {
                    console.error(error);
                })
            },
            getRefCompanySectorList() {
                this.$http.get("api/v1/refCompanySector/getListForDropDown").then((result) => {
                    this.refCompanySectorList = result.data.data.items
                    this.addEditFields.find(item => {
                        return item.key === customerModel.refCompanySector
                    }).props = this.refCompanySectorList

                }).catch((error) => {
                    console.error(error);
                })
            }
        },

        mounted() {
            this.getAllCustomers();
            this.getSysrefCountryList();
            this.getSysrefCityList();
            this.getSysrefDistrictList();
            this.getRefCompanySectorList();
        }
    }
</script>

<style scoped>

</style>
