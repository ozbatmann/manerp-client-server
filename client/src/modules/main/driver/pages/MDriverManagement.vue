<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div>
        <m-data-table
                :headers="headers"
                :items="drivers"
                :to="to"
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
                :fields="addEditFields"
                title="Yeni Şoför"
        ></m-data-table-add-new-form>
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
        data () {
            return {
                // Data-table
                // add-edit dialog data
                addEditData: {
                    [driverModel.name]: null,
                    [driverModel.surname]: null,
                    [driverModel.contract]: null,
                    [driverModel.phone]: null,
                    [driverModel.ssn]: null,
                    [driverModel.driversLicense]: null,
                    [driverModel.address]: null
                },

                // Data-table
                // add-edit dialog fields
                addEditFields: [
                    {
                        key: driverModel.name,
                        max: 20,
                        rules: [
                            'required', 'max:20'
                        ],
                        title: 'ad',
                        type: 'text',
                    },
                    {
                        key: driverModel.surname,
                        max: 20,
                        rules: [
                            'required', 'max:20'
                        ],
                        title: 'soyad',
                        type: 'text',
                    },
                    {
                        key: driverModel.contract,
                        title: 'sözleşme tipi',
                        type: 'select',
                        props: [
                            'Kadrolu', 'Sözleşmeli'
                        ],
                    },
                    {
                        key: driverModel.phone,
                        mask: 'phone',
                        max: 10,
                        title: 'telefon',
                        type: 'tel',
                        rules: [
                            'required', 'max:10'
                        ]
                    },
                    {
                        key: driverModel.ssn,
                        mask: '###########',
                        max: 11,
                        title: 't.c. kimlik numarası',
                        type: 'tel',
                        rules: [
                            'required', 'max:11'
                        ]
                    },
                    {
                        key: driverModel.driversLicense,
                        max: null,
                        title: 'ehliyet numarası',
                        type: 'tel',
                        rules: [
                            'required'
                        ]
                    },
                    {
                        key: driverModel.address,
                        max: null,
                        title: 'adres',
                        type: 'text',
                        rules: [
                            'required'
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
                        search: { chip: false, value: null }
                    },
                    {
                        text: 'ad',
                        sortable: true,
                        value: driverModel.name,
                        toggleable: false,
                        show: true,
                        search: { chip: false, value: null }
                    },
                    {
                        text: 'soyad',
                        sortable: true,
                        value: driverModel.surname,
                        toggleable: false,
                        show: true,
                        search: { chip: false, value: null }
                    },
                    {
                        text: 'sözleşme tipi',
                        sortable: true,
                        value: driverModel.contract,
                        toggleable: true,
                        show: true
                    },
                    {
                        text: 'telefon',
                        sortable: true,
                        value: driverModel.phone,
                        toggleable: true,
                        show: true,
                        search: { chip: false, value: null }
                    },
                    {
                        text: 't.c. kimlik numarası',
                        sortable: true,
                        value: driverModel.ssn,
                        toggleable: true,
                        show: true,
                        search: { chip: false, value: null }
                    },
                    {
                        text: 'ehliyet numarası',
                        sortable: true,
                        value: driverModel.driversLicense,
                        toggleable: true,
                        show: true,
                        search: { chip: false, value: null }
                    },
                ],

                // Drivers array
                drivers: [],

                // Data table row click route
                to: {
                    name: require('@/modules/main/driver/route/index').routes.information
                }
            }
        },

        methods: {

            // Activates add new item dialog
            addDialog (data) {
                console.log(data)
                this.$refs.addEditDialog.open(data)
            }
        },

        mounted () {
            for (let i = 0; i < 15; i++) {
                let driver = {
                    id: `DRV-${i}`,
                    name: 'Ahmet Ahmet Ahmet Ahmet v Ahmet Ahmet v v v Ahmet',
                    surname: 'Aliakça',
                    contract: 'Kadrolu',
                    phone: '(541) 598-4560',
                    ssn: '14786457866',
                    driversLicense: '58231045620'
                };

                this.drivers.push(driver)
            }
        }
    }
</script>

<style scoped>

</style>
