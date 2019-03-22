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

        </m-data-table>

        <m-data-table-add-new-form
                ref="addEditDialog"
                :data="addEditFields"
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
                addEditFields: [
                    {
                        key: driverModel.name,
                        max: 20,
                        rules: [
                            'required', 'max:20'
                        ],
                        title: 'ad',
                        type: 'text',
                        value: null
                    },
                    {
                        key: driverModel.surname,
                        max: 20,
                        rules: [
                            'required', 'max:20'
                        ],
                        title: 'soyad',
                        type: 'text',
                        value: null,
                    },
                    {
                        key: driverModel.contract,
                        title: 'sözleşme tipi',
                        type: 'select',
                        props: [
                            'Kadrolu', 'Sözleşmeli'
                        ],
                        value: null
                    },
                    {
                        key: driverModel.phone,
                        max: 10,
                        title: 'telefon',
                        type: 'text',
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
                    {
                        text: '',
                        sortable: false,
                        align: 'end',
                        value: 'action',
                        toggleable: false,
                        show: true
                    }
                ],

                drivers: [],

                to: {
                    name: 'driver.information'
                }
            }
        },

        methods: {

            // Activates add new item dialog
            addDialog () {
                console.log(this.$refs.addEditDialog)
                this.$refs.addEditDialog.open()
            }
        },

        mounted () {
            for (let i = 0; i < 15; i++) {
                let driver = {
                    id: `DRV-${i}`,
                    name: 'Ahmet',
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
