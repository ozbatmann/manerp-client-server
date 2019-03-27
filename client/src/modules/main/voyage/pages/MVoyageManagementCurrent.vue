
<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div>
        <m-data-table
            :headers="headers"
            :items="voyages"
            :to="to"
            @deleteItem="deleteItem"
        >
            <!-- Data table header slot -->
            <template v-slot:header>

                <!-- Add customer button -->
                <m-data-table-action
                    title="sevkiyat ekle"
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
            title="Yeni Sevkiyat"
            @save="addNewItem"
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

    const voyageModel = require('@/modules/main/voyage/models/voyage-model').default;

    export default {
        name: "MVoyageManagementCurrent",

        components: {
            MDataTable,
            MDataTableAction,
            MDataTableAddNewForm
        },

        data() {
            return {
                // Data-table
                // add-edit dialog data
                addEditData: {
                    [voyageModel.company]: null,
                    [voyageModel.vehicle]: null,
                    [voyageModel.driver]: null,
                    [voyageModel.sysRefTransportationType]: null,
                    [voyageModel.sysRefVoyageDirection]: null,
                },

                // Data-table
                // add-edit dialog fields
                addEditFields: [
                    {
                        key: voyageModel.company,
                        max: 20,
                        rules: [
                            'required', 'max:30'
                        ],
                        title: 'şirket',
                        type: 'text',
                    },
                    {
                        key: voyageModel.vehicle,
                        max: 50,
                        title: 'araç',
                        rules: [
                            'required', 'max:30'
                        ]
                    },
                    {
                        key: voyageModel.driver,
                        type: 'text',
                        max: 30,
                        title: 'şoför',
                        rules: [
                            'required', 'max:30'
                        ]
                    },
                    {
                        key: voyageModel.sysRefTransportationType,
                        title: 'ulaşım türü',
                        type: 'select',
                        props: this.sysRefTransportationTypeList
                    },
                    {
                        key: voyageModel.sysRefVoyageDirection,
                        title: 'sevkiyat yönü',
                        type: 'select',
                        props: this.sysRefVoyageDirectionList
                    }
                ],
                headers: [
                    {
                        text: 'ıd',
                        sortable: true,
                        value: voyageModel.id,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'şirket',
                        sortable: true,
                        value: voyageModel.company,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'araç',
                        sortable: true,
                        value: voyageModel.vehicle,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'şoför',
                        sortable: true,
                        value: voyageModel.driver,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'ulaşım türü',
                        sortable: true,
                        value: voyageModel.sysRefTransportationType,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'sevkiyat yönü',
                        sortable: true,
                        value: voyageModel.sysRefVoyageDirection,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    }
                ],

                voyages: [],
                sysRefTransportationTypeList: [],
                sysRefVoyageDirectionList: [],

                newItem: null,

                snackbar: false,

                // Data table row click route
                to: {
                    name: require('@/modules/main/voyage/route/index').routes.information
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
            getAllVoyages() {
                this.$http.get('api/v1/voyage').then((result) => {
                    this.voyages = result.data.data.items
                }).catch((error) => {
                    console.log(error);
                })
            },
            addNewItem(item) {
                this.newItem = item
                this.$http.post('api/v1/voyage', this.newItem).then((result) => {
                    this.getAllVoyages();
                }).catch((error) => {
                    console.log(error);
                })
            },
            getSysRefTransportationTypeList() {
                this.$http.get("api/v1/sysrefTransportationType").then((result) => {
                    this.sysRefTransportationTypeList = result.data.data.items
                    this.addEditFields.find(item => {
                        return item.key === voyageModel.sysRefTransportationType
                    }).props = this.sysRefTransportationTypeList

                }).catch((error) => {
                    console.error(error);
                })
            },
            getSysRefVoyageDirectionList() {
                this.$http.get("api/v1/sysrefCity").then((result) => {
                    this.sysRefVoyageDirectionList = result.data.data.items
                    this.addEditFields.find(item => {
                        return item.key === voyageModel.sysRefVoyageDirection
                    }).props = this.sysRefVoyageDirectionList

                }).catch((error) => {
                    console.error(error);
                })
            },
            deleteItem(item){
                this.$http.delete(`api/v1/voyage/${item.id}`).then((result) => {
                    this.getAllVehicles()
                }).catch((error) => {
                    console.error(error);
                })
            }
        },

        mounted() {
            this.getAllVoyages();
            this.getSysRefTransportationTypeList();
            this.getSysRefVoyageDirectionList();
        }
    }
</script>

<style scoped>

</style>

