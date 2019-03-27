<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div>
        <m-data-table
            :headers="headers"
            :items="vehicles"
            :to="to"
            @deleteItem="deleteItem"
            :loading="loading"
        >
            <!-- Data table header slot -->
            <template v-slot:header>

                <!-- Add customer button -->
                <m-data-table-action
                    title="araç ekle"
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
            title="Yeni Araç"
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
    import MDataTable from '@/modules/main/shared/components/data/components/MDataTable'
    import MDataTableAction from "@/modules/main/shared/components/data/components/MDataTableAction"
    import MDataTableAddNewForm from "@/modules/main/shared/components/data/components/MDataTableAddNewForm"

    const vehicleModel = require('@/modules/main/vehicle/models/vehicle-model').default;

    export default {
        name: "MVehicleManagementOnVoyage",

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
                    [vehicleModel.plateNumber]: null,
                    [vehicleModel.fleetCardNumber]: null,
                    // [orderModel.orderDate]: null,
                    [vehicleModel.km]: null,
                    [vehicleModel.isDualRegime]: null,
                    [vehicleModel.refWorkingArea]: null,
                    [vehicleModel.sysrefVehicleType]: null,
                    [vehicleModel.sysrefVehicleOwner]: null,
                    [vehicleModel.vehicleOwnerFullName]: null,
                    [vehicleModel.kgsNo]: null,
                    [vehicleModel.ogsNo]: null,
                    [vehicleModel.description]: null
                },

                // Data-table
                // add-edit dialog fields
                addEditFields: [
                    {
                        key: vehicleModel.plateNumber,
                        max: 20,
                        rules: [
                            'required', 'max:20'
                        ],
                        title: 'plaka',
                        type: 'text',
                    },
                    {
                        key: vehicleModel.brand,
                        max: 50,
                        rules: [
                            'required', 'max:50'
                        ],
                        title: 'marka',
                        type: 'text',
                    },
                    {
                        key: vehicleModel.fleetCardNumber,
                        max: 50,
                        title: 'filo kart numarası',
                        rules: [
                            'required', 'max:50'
                        ]
                    },
                    {
                        key: vehicleModel.km,
                        type: 'number',
                        max: 8,
                        title: 'km',
                        rules: [
                            'required', 'max:8'
                        ]
                    },
                    {
                        key: vehicleModel.refWorkingArea,
                        title: 'çalışma alanı',
                        type: 'select',
                        props: this.refWorkingAreaList
                    },
                    {
                        key: vehicleModel.sysrefVehicleType,
                        title: 'tipi',
                        type: 'select',
                        props: this.sysrefVehicleTypeList
                    },
                    {
                        key: vehicleModel.sysrefVehicleOwner,
                        title: 'mülk tipi',
                        type: 'select',
                        props: this.sysrefVehicleOwnerList
                    },
                    {
                        key: vehicleModel.vehicleOwnerFullName,
                        max: 50,
                        title: 'sahibi',
                        rules: [
                            'required', 'max:50'
                        ]
                    },
                    {
                        key: vehicleModel.kgsNo,
                        max: 50,
                        title: 'kgs numarası',
                        rules: [
                            'required', 'max:50'
                        ]
                    },
                    {
                        key: vehicleModel.ogsNo,
                        max: 50,
                        title: 'ogs numarası',
                        rules: [
                            'required', 'max:50'
                        ]
                    },
                    {
                        key: vehicleModel.isDualRegime,
                        max: null,
                        type: 'checkbox',
                        props: ['çift rejim']
                    },
                    {
                        key: vehicleModel.active,
                        max: null,
                        type: 'checkbox',
                        props: ['aktif']
                    }
                ],
                headers: [
                    {
                        text: 'ıd',
                        sortable: true,
                        value: vehicleModel.id,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'plaka',
                        sortable: true,
                        value: vehicleModel.plateNumber,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'filo kart numarası',
                        sortable: true,
                        value: vehicleModel.fleetCardNumber,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'tipi',
                        sortable: true,
                        value: vehicleModel.sysrefVehicleType,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'mülk tipi',
                        sortable: true,
                        value: vehicleModel.sysrefVehicleOwner,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    }
                ],

                vehicles: [],
                refWorkingAreaList: [],
                sysrefVehicleTypeList: [],
                sysrefVehicleOwnerList: [],

                newItem: null,

                snackbar: false,

                // Data table row click route
                // to: {
                //     name: require('@/modules/main/vehicle/route/index').routes.information
                // }
            }
        },
        methods: {

            // Activates add new item dialog
            addDialog(data) {
                this.$refs.addEditDialog.open(data)
            },

            // Adds a new driver
            // to the system
            getAllVehicles() {
                this.loading = true;
                let self = this;
                this.$http.get('api/v1/vehicle').then((result) => {
                    self.vehicles = result.data.data.items
                }).catch((error) => {
                    console.log(error);
                }).finally((result) => {
                    self.loading = false;
                })
            },
            addNewItem(item) {
                this.newItem = item
                this.loading = true;
                let self = this;
                this.$http.post('api/v1/vehicle', this.newItem).then((result) => {
                    self.getAllVehicles();
                }).catch((error) => {
                    console.log(error);
                }).finally((result) => {
                    self.loading = false;
                })
            },

            editItem(item) {
                let self = this;

                this.$http.put('api/v1/vehicle/', item)
                    .then(result => {
                        self.getAllVehicles();
                    }).catch(error => {
                    console.log(error)
                })
            },
            getSysrefVehicleOwnerList() {
                this.$http.get("api/v1/sysrefVehicleOwner").then((result) => {
                    this.sysrefVehicleOwnerList = result.data.data.items
                    this.addEditFields.find(item => {
                        return item.key === vehicleModel.sysrefVehicleOwner
                    }).props = this.sysrefVehicleOwnerList

                }).catch((error) => {
                    console.error(error);
                })
            },
            getSysrefVehicleTypeList() {
                this.$http.get("api/v1/sysrefVehicleType").then((result) => {
                    this.sysrefVehicleTypeList = result.data.data.items
                    this.addEditFields.find(item => {
                        return item.key === vehicleModel.sysrefVehicleType
                    }).props = this.sysrefVehicleTypeList

                }).catch((error) => {
                    console.error(error);
                })
            },
            getRefWorkingAreaList() {
                this.$http.get('api/v1/refWorkingArea/getListForDropDown').then((result) => {
                    this.refWorkingAreaList = result.data.data.items

                    this.addEditFields.find(item => {
                        return item.key === vehicleModel.refWorkingArea
                    }).props = this.refWorkingAreaList

                }).catch((error) => {
                    console.log(error);
                })
            },
            deleteItem(item){
                this.$http.delete(`api/v1/vehicle/${item.id}`).then((result) => {
                    this.getAllVehicles()
                }).catch((error) => {
                    console.error(error);
                })
            }
        },

        mounted() {
            this.getAllVehicles();
            this.getSysrefVehicleOwnerList();
            this.getSysrefVehicleTypeList();
            this.getRefWorkingAreaList();
        }
    }
</script>

<style scoped>

</style>
