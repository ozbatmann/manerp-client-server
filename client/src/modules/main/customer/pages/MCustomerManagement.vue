<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div>
        <m-data-table
                :headers="headers"
                :items="firms"
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
            <template v-slot:action-menu="props">
                <v-list-tile>Bayi ekle</v-list-tile>
            </template>
        </m-data-table>

        <m-data-table-add-new-form
                ref="addEditDialog"
                :data="formFields"
                title="Yeni Firma"
        ></m-data-table-add-new-form>
    </div>
</template>

<script>
    import MDataTable from '../../shared/components/data/components/MDataTable'
    import MDataTableAction from "@/modules/main/shared/components/data/components/MDataTableAction"
    import MDataTableAddNewForm from "../../shared/components/data/components/MDataTableAddNewForm";

    export default {
        name: "MCustomerManagement",
        components: {
            MDataTableAddNewForm,
            MDataTableAction,
            MDataTable
        },

        data () {
            return {
                formFields: [
                    { title: 'Firma Ünvanı', key: 'title', type: 'text', value: null,
                        rules: ['required'] },

                    { title: 'Telefon',key: 'phone', type: 'phone', value: null, mask: 'phone',
                        rules: ['required', 'max:10'], max: 10 },

                    { title: 'E-posta', key: 'mail', type: 'mail', value: null, rules: ['required', 'email'] },

                    { title: 'Vergi Numarası', key: 'taxNo', type: 'text', value: null, rules: ['required'] },

                    { title: 'Vergi Dairesi', key: 'taxAdmin', type: 'text', value: null, rules: ['required'] },

                    { title: 'Firma İlişkisi', key: 'type', type: 'select', rules: ['required'],
                        props: [ 'Müşteri', 'Tedarikçi', 'Müşteri/Tedarikçi' ], value: 'Müşteri' },

                    { title: 'Firma Temsilcisi', key: 'rep', type: 'text', value: null, rules: ['required'] },

                    { title: 'Adres', key: 'address', type: 'text', value: null, rules: ['required'] }
                ],

                datePickerShowing: false,
                startDate: null,

                headers: [
                    {text: 'ID', sortable: true, value: 'id', toggleable: false, show: true, search: {chip: false, value: null}},
                    {text: 'Ünvan', sortable: true, value: 'title', toggleable: false, show: true, search: {chip: false, value: null}},
                    {text: 'Telefon', sortable: true, value: 'phone', toggleable: true, show: true, search: {chip: false, value: null}},
                    {text: 'E-Posta', sortable: true, value: 'mail', toggleable: true, show: true, search: {chip: false, value: null}},
                    {text: 'Firma İlişkisi', sortable: true, value: 'type', toggleable: true, show: true, search: {chip: false, value: null}},
                    {text: 'Firma Temsilcisi', sortable: true, value: 'rep', toggleable: true, show: true, search: {chip: false, value: null}},
                    {text: '', sortable: false, align: 'end', value: 'action', toggleable: false, show: true}
                ],

                firms: [
                    {
                        id: 'FIRM-01',
                        title: 'Pınar Gıda A.Ş',
                        phone: '0 232 545 6320',
                        mail: 'info@pinar.com.tr',
                        type: 'Müşteri',
                        rep: 'Seval Çıtanak'
                    },
                    {
                        id: 'FIRM-02',
                        title: 'Pınar Gıda A.Ş',
                        phone: '0 232 545 6320',
                        mail: 'info@pinar.com.tr',
                        type: 'Müşteri',
                        rep: 'Seval Çıtanak'
                    },
                    {
                        id: 'FIRM-03',
                        title: 'Pınar Gıda A.Ş',
                        phone: '0 232 545 6320',
                        mail: 'info@pinar.com.tr',
                        type: 'Müşteri',
                        rep: 'Seval Çıtanak'
                    },
                    {
                        id: 'FIRM-04',
                        title: 'Pınar Gıda A.Ş',
                        phone: '0 232 545 6320',
                        mail: 'info@pinar.com.tr',
                        type: 'Müşteri',
                        rep: 'Seval Çıtanak'
                    },
                    {
                        id: 'FIRM-05',
                        title: 'Pınar Gıda A.Ş',
                        phone: '0 232 545 6320',
                        mail: 'info@pinar.com.tr',
                        type: 'Müşteri',
                        rep: 'Seval Çıtanak'
                    },
                    {
                        id: 'FIRM-06',
                        title: 'Pınar Gıda A.Ş',
                        phone: '0 232 545 6320',
                        mail: 'info@pinar.com.tr',
                        type: 'Müşteri',
                        rep: 'Seval Çıtanak'
                    }
                ],

                to: {
                    name: require('@/modules/main/customer/route/index').routes.information,
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

        mounted() {
            // this.$http.get("/auth").then((result) => {
            //     console.log(result)
            // })
        }
    }
</script>

<style scoped>

</style>
