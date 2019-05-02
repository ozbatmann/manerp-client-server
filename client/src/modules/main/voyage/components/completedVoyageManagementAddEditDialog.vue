<template>
    <v-dialog v-model="showDialog" persistent max-width="100%" scrollable>
        <!-- <v-btn slot="activator" color="primary" dark>Open Dialog</v-btn> -->
        <v-card class="pa-12">
            <v-card-title>
                <div v-if="currentTab === 0">
                    <span v-if="isEdit" class="headline">Güncelle</span>
                    <span v-else class="headline">Yeni Sevkiyat</span>
                </div>
                <div v-else>
                    <span v-if="isEdit" class="headline">Rota Yönetimi</span>
                    <span v-else class="headline">Rota Oluştur</span>
                </div>
            </v-card-title>
            <v-card-text>
                <v-tabs
                    id="voyage_tabs"
                    v-model="currentTab"
                    fixed-tabs
                    slider-color="green accent-2"
                >
                    <v-tab ripple>
                        Sevkiyat Bilgileri
                    </v-tab>
                    <v-tab ripple v-on:change="tabChanged">
                        Rota Bilgileri
                    </v-tab>

                    <v-tab-item class="tab-item" :transition="false" :reverse-transition="false"
                    >
                        <v-container grid-list-md pa-0>
                            <v-layout wrap>
                                <v-flex xs12>
                                    <v-combobox v-validate="'required'"
                                                :error-messages="errors.collect('order')"
                                                v-on:change="orderChanged"
                                                v-model="data._order"
                                                :return-object="true"
                                                :items="orders"
                                                item-value="id"
                                                label="Sipariş"
                                                item-text="fullName"
                                                name="order"
                                                background-color="grey lighten-4"
                                                color="green accent-2"
                                                full-width>
                                    </v-combobox>
                                    <v-combobox v-validate="'required'"
                                                :error-messages="errors.collect('vehicle')"
                                                v-model="data.vehicle"
                                                :return-object="true"
                                                :items="vehicles"
                                                item-value="id"
                                                label="Araç"
                                                item-text="plateNumber"
                                                name="vehicle"
                                                background-color="grey lighten-4"
                                                color="green accent-2"
                                                full-width>
                                    </v-combobox>
                                    <v-combobox v-validate="'required'"
                                                :error-messages="errors.collect('driver')"
                                                v-model="data.driver"
                                                :return-object="true"
                                                :items="drivers"
                                                item-value="id"
                                                label="Şoför"
                                                item-text="fullName"
                                                name="driver"
                                                background-color="grey lighten-4"
                                                color="green accent-2"
                                                full-width>
                                    </v-combobox>
                                    <v-combobox v-validate="'required'"
                                                :error-messages="errors.collect('sysrefTransportationType')"
                                                v-model="data.sysrefTransportationType"
                                                :return-object="true"
                                                :items="sysrefTransportationTypes"
                                                item-value="id"
                                                label="Taşıma Tipi"
                                                item-text="name"
                                                name="sysrefTransportationType"
                                                background-color="grey lighten-4"
                                                color="green accent-2"
                                                full-width>
                                    </v-combobox>
                                    <v-combobox v-validate="'required'"
                                                :error-messages="errors.collect('sysrefVoyageDirection')"
                                                v-model="data.sysrefVoyageDirection"
                                                :return-object="true"
                                                :items="sysrefVoyageDirections"
                                                item-value="id"
                                                label="Sevkiyat Yönü"
                                                item-text="name"
                                                name="sysrefVoyageDirection"
                                                background-color="grey lighten-4"
                                                color="green accent-2"
                                                full-width>
                                    </v-combobox>
                                    <v-combobox v-validate="'required'"
                                                :error-messages="errors.collect('sysrefDeliveryStatus')"
                                                v-model="data.sysrefDeliveryStatus"
                                                :return-object="true"
                                                :items="sysrefDeliveryStatuses"
                                                item-value="id"
                                                label="Durum"
                                                item-text="name"
                                                name="sysrefDeliveryStatus"
                                                background-color="grey lighten-4"
                                                color="green accent-2"
                                                full-width>
                                    </v-combobox>
                                    <v-text-field v-model="data.transportWaybillNo"
                                                  label="Nakliye İrsaliye Numarası"
                                                  name="transportWaybillNo"
                                                  :counter="150"
                                                  maxlength="150"
                                                  background-color="grey lighten-4"
                                                  color="green accent-2"
                                                  full-width>
                                    </v-text-field>
                                    <v-text-field v-model="data.deliveryNoteNo"
                                                  label="Teslimat Notu Numarası"
                                                  name="deliveryNoteNo"
                                                  :counter="150"
                                                  maxlength="150"
                                                  background-color="grey lighten-4"
                                                  color="green accent-2"
                                                  full-width>
                                    </v-text-field>
                                    <v-text-field v-model="data.sasNumber"
                                                  label="Sas Numarası"
                                                  name="sasNumber"
                                                  :counter="150"
                                                  maxlength="150"
                                                  background-color="grey lighten-4"
                                                  color="green accent-2"
                                                  full-width>
                                    </v-text-field>
                                </v-flex>
                            </v-layout>
                        </v-container>
                    </v-tab-item>
                    <v-tab-item class="tab-item" :transition="false" :reverse-transition="false"
                    >
                        <v-layout row wrap>
                            <v-flex xs2>
                                <div class="form-check" v-for="layer in layers" :key="layer.id">
                                    <v-checkbox v-model="layer.active"
                                                :label="layer.name"
                                                @change="layerChanged(layer.id, layer.active)">
                                    </v-checkbox>
                                </div>
                            </v-flex>
                            <v-flex xs10>
                                <leaflet-map
                                    ref="leafletMap"
                                    @save="saveWaypoints"
                                ></leaflet-map>
                            </v-flex>
                        </v-layout>
                    </v-tab-item>
                </v-tabs>
            </v-card-text>
            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn v-if="isEdit && currentTab === 0" color="blue darken-1" outline @click.native="edit">Düzenle
                </v-btn>
                <v-btn v-else-if="currentTab === 0" color="blue darken-1" outline @click.native="save">Kaydet</v-btn>
                <v-btn color="orange darken-1" outline @click.native="close">Kapat</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script>
    import LeafletMap from "../leaflet/leafletMap";
    import '../leaflet/mapStyle.css';

    const voyageModel = require('@/modules/main/voyage/models/voyage-model-add-edit').default;

    export default {
        components: {LeafletMap},
        data() {
            return {
                currentTab: 0,
                showDialog: false,
                isEdit: false,
                data: JSON.parse(JSON.stringify(voyageModel)),
                vehicles: [],
                drivers: [],
                sysrefTransportationTypes: [],
                sysrefVoyageDirections: [],
                sysrefDeliveryStatuses: [],
                layers: [],
                vendors: [],
                orders: []
            }
        },
        methods: {
            open(data) {
                this.showDialog = true;
                if (data) {
                    this.data = data;
                    this.isEdit = true;
                } else {
                    this.clear();
                }
            },
            close() {
                this.showDialog = false;
            },
            save() {
                this.$validator.validateAll().then((result) => {
                    if (result) {
                        this.$emit("save", this.data);
                        this.close();
                    }
                });
            },
            edit() {
                this.$validator.validateAll().then((result) => {
                    if (result) {
                        this.$emit("edit", this.data);
                        this.close();
                    }
                });
            },
            clear() {
                this.data = JSON.parse(JSON.stringify(voyageModel));
                this.$validator.reset();
                this.isEdit = false;
            },
            tabChanged() {
                // to display all tiles
                window.dispatchEvent(new Event('resize'));
            },
            getVehicles() {
                this.$http.get("api/v1/vehicle?limit=100&fields=id,plateNumber&vehicleStateCode=IDLE").then((result) => {
                    this.vehicles = result.data.data.items
                }).catch((error) => {
                    console.error(error);
                }).finally(() => {

                })
            },
            getDrivers() {
                this.$http.get("api/v1/driver?fields=id,fullName&driverStateCode=IDLE").then((result) => {
                    this.drivers = result.data.data.items
                }).catch((error) => {
                    console.error(error);
                }).finally(() => {

                })
            },
            getSysrefTransportationTypes() {
                this.$http.get("api/v1/sysrefTransportationType?fields=id,name").then((result) => {
                    this.sysrefTransportationTypes = result.data.data.items
                }).catch((error) => {
                    console.error(error);
                }).finally(() => {

                })
            },
            getSysrefVoyageDirections() {
                this.$http.get("api/v1/sysrefVoyageDirection?fields=id,name").then((result) => {
                    this.sysrefVoyageDirections = result.data.data.items
                }).catch((error) => {
                    console.error(error);
                }).finally(() => {

                })
            },
            getSysrefDeliveryStatuses() {
                this.$http.get("api/v1/sysrefDeliveryStatus?fields=id,name").then((result) => {
                    this.sysrefDeliveryStatuses = result.data.data.items
                }).catch((error) => {
                    console.error(error);
                }).finally(() => {

                })
            },
            getOrders() {
                this.$http.get("api/v1/order?fields=id,fullName&orderStateCode=WAIT").then((result) => {
                    this.orders = result.data.data.items
                    console.log(this.orders)
                }).catch((error) => {
                    console.error(error);
                }).finally(() => {

                })
            },
            orderChanged() {
                console.log(this.data._order);
                this.$http.get("api/v1/vendor?fields=id,fullName&orderStateCode=WAIT").then((result) => {
                    this.orders = result.data.data.items
                    console.log(this.orders)
                }).catch((error) => {
                    console.error(error);
                }).finally(() => {

                })
            },
            saveWaypoints() {

            },
            initLeafletMap() {
                this.$refs.leafletMap.setLocations(this.vendors);
                this.layers = this.$refs.leafletMap.getLayers();
            }
        },
        mounted() {
            this.getOrders();
            this.getVehicles();
            this.getDrivers();
            this.getSysrefTransportationTypes();
            this.getSysrefVoyageDirections();
            this.getSysrefDeliveryStatuses();
            this.initLeafletMap();
        }
    }
</script>
<style>
    #voyage_map {
        height: 450px;
    }

    #voyage_map img {
        max-height: none;
    }

    #voyage_tabs .tab-item {
        margin-top: 20px;
    }
</style>