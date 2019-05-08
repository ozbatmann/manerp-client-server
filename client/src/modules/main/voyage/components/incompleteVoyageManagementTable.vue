<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div>
        <m-data-table
            :headers="headers"
            :items="voyages"
            :loading="loading"
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
                <v-list-tile @click="editDialog(item.bind)">Düzenle</v-list-tile>
            </template>
        </m-data-table>

        <voyage-management-add-edit-dialog ref="voyageManagementAddEditDialog"
                                           @save="addNewItem"
                                           @edit="editItem">
        </voyage-management-add-edit-dialog>
    </div>
</template>

<script>
    import MDataTable from '../../shared/components/data/components/MDataTable'
    import MDataTableAction from "@/modules/main/shared/components/data/components/MDataTableAction"
    import voyageManagementAddEditDialog
        from "@/modules/main/voyage/components/incompleteVoyageManagementAddEditDialog"

    const voyageModel = require('@/modules/main/voyage/models/voyage-model').default;

    export default {
        name: "completedVoyageManagementTable",
        components: {
            MDataTable,
            MDataTableAction,
            voyageManagementAddEditDialog
        },
        data() {
            return {
                // Data-table headers
                headers: [
                    {
                        text: 'ıd',
                        sortable: true,
                        value: voyageModel.code,
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
                        value: voyageModel.sysrefTransportationType,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'sevkiyat yönü',
                        sortable: true,
                        value: voyageModel.sysrefVoyageDirection,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    }
                ],

                voyages: [],
                newItem: null,

                // Data table row click route
                to: {
                    name: require('@/modules/main/voyage/route/index').routes.information
                },

                loading: true
            }
        },
        methods: {
            // Activates add new item dialog
            addDialog(data) {
                this.$refs.voyageManagementAddEditDialog.open(data)
            },
            editDialog(data) {
                if (data !== undefined && data !== null) {
                    this.$http.get("api/v1/voyage/" + data.id).then((result) => {
                        let items = result.data.data;
                        this.$refs.voyageManagementAddEditDialog.open(items)
                    }).catch((error) => {
                        console.error(error);
                    })
                }
            },
            getAllVoyages() {
                let self = this;
                this.loading = true;

                let fields = 'fields=id,code,vehicle,driver,sysrefTransportationType,sysrefVoyageDirection';
                let pagination = 'limit=10&offset=0';
                let deliveryStatusCode = 'deliveryStatusCode=REZ';
                this.$http.get('api/v1/voyage?' + fields + "&" + pagination + "&" + deliveryStatusCode).then((result) => {
                    self.voyages = result.data.data.items;
                }).catch((error) => {
                    console.log(error);
                }).finally(() => self.loading = false)
            },
            addNewItem(item) {
                this.newItem = item;
                let self = this;
                this.$http.post('api/v1/voyage', this.newItem)
                    .then((result) => {
                        self.getAllVoyages();
                    }).catch((error) => {
                    console.log(error);
                })
            },
            editItem(item) {
                let self = this;
                this.$http.put('api/v1/voyage/', item)
                    .then(result => {
                        self.getAllVoyages();
                    }).catch(error => {
                    console.log(error)
                })
            },
            deleteItem(item) {
                let self = this;

                this.$http.delete(`api/v1/voyage/${item.id}`).then((result) => {
                    self.getAllVoyages();
                }).catch((error) => {
                    console.error(error);
                })
            },
        },
        mounted() {
            this.getAllVoyages();
        }
    }
</script>

<style scoped>

</style>
