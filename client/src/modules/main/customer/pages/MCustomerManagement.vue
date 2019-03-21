<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div>
        <m-data-table
                :headers="headers"
                :items="firms"
                to="/details/"
        >
            <!-- Data table header slot -->
            <template v-slot:header>

                <!-- Add customer button -->
                <m-data-table-action
                        title="MÜŞTERİ EKLE"
                ></m-data-table-action>
            </template>

            <!-- Data table action menu slot -->
            <template v-slot:action-menu="props">
                <v-list-tile>Bayi ekle</v-list-tile>
            </template>
        </m-data-table>
    </div>
</template>

<script>
    import MDataTable from '../../shared/components/data/components/MDataTable'
    import MDataTableAction from "@/modules/main/shared/components/data/components/MDataTableAction"

    export default {
        name: "MCustomerManagement",
        components: {
            MDataTableAction,
            MDataTable
        },

        data () {
            return {
                datePickerShowing: false,
                startDate: null,

                headers: [
                    {text: 'ID', sortable: true, value: 'id', toggleable: false, show: true, search: {chip: false, value: null}},
                    {text: 'Ünvan', sortable: true, value: 'title', toggleable: false, show: true, search: {chip: false, value: null}},
                    {text: 'Telefon', sortable: true, value: 'phone', toggleable: true, show: true, search: {chip: false, value: null}},
                    {text: 'E-Posta', sortable: true, value: 'mail', toggleable: true, show: true, search: {chip: false, value: null}},
                    {text: 'Firma İlişkisi', sortable: true, value: 'type', toggleable: true, show: true, search: {chip: false, value: null}},
                    {text: 'Firma Temsilcisi', sortable: true, value: 'rep', toggleable: true, show: true, search: {chip: false, value: null}},
                    {text: '#', sortable: false, align: 'end', value: 'action', toggleable: false, show: true}
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
                ]
            }
        },
        mounted() {
            this.$http.get("/auth").then((result) => {
                console.log(result)
            })
        }
    }
</script>

<style scoped>

</style>