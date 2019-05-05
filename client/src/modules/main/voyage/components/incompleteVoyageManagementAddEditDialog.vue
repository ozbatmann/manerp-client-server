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
                    <span v-else class="headline"><b>{{this.data._order ? this.data._order.fullName : ""}}</b> için Rota Oluştur</span>
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
                    <v-tab ripple v-on:change="tabChanged" :disabled="!data._order || !data.vehicle">
                        Rota Bilgileri
                    </v-tab>

                    <v-tab-item class="tab-item" :transition="false" :reverse-transition="false"
                    >
                        <v-container grid-list-md pa-0>
                            <v-container grid-list-md pa-0>
                                <v-layout wrap>
                                    <v-flex xs6 pr-2>
                                        <v-combobox v-validate="'required'"
                                                    :error-messages="errors.collect('order')"
                                                    v-on:change="getVendorsByOrder('onChange')"
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
                                    </v-flex>
                                    <v-flex xs6 pl-2>
                                        <v-combobox v-validate="'required'"
                                                    :error-messages="errors.collect('vehicle')"
                                                    v-on:change="setVehicleSpecs"
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
                                    </v-flex>
                                    <v-flex xs6 pr-2>
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
                                    </v-flex>
                                    <v-flex xs6 pl-2>
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
                                    </v-flex>
                                    <v-flex xs6 pr-2>
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
                                    </v-flex>
                                    <v-flex xs6 pl-2>
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
                                    </v-flex>
                                    <v-flex xs6 pr-2>
                                        <v-text-field v-model="data.transportWaybillNo"
                                                      label="Nakliye İrsaliye Numarası"
                                                      name="transportWaybillNo"
                                                      :counter="150"
                                                      maxlength="150"
                                                      background-color="grey lighten-4"
                                                      color="green accent-2"
                                                      full-width>
                                        </v-text-field>
                                    </v-flex>
                                    <v-flex xs6 pl-2>
                                        <v-text-field v-model="data.deliveryNoteNo"
                                                      label="Teslimat Notu Numarası"
                                                      name="deliveryNoteNo"
                                                      :counter="150"
                                                      maxlength="150"
                                                      background-color="grey lighten-4"
                                                      color="green accent-2"
                                                      full-width>
                                        </v-text-field>
                                    </v-flex>
                                    <v-flex xs6 pr-2>
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
                        </v-container>
                    </v-tab-item>
                    <v-tab-item class="tab-item" :transition="false" :reverse-transition="false"
                    >
                        <v-layout row wrap>
                            <v-flex xs4>
                                <div id="voyage_map_left_side">
                                    <v-layout row wrap justify-space-around>
                                        <div v-for="layer in layers" :key="layer.id">
                                            <v-flex xs12>
                                                <v-checkbox v-model="layer.active"
                                                            :label="layer.name"
                                                            @change="layerChanged(layer.id, layer.active)">
                                                </v-checkbox>
                                            </v-flex>
                                        </div>
                                    </v-layout>
                                    <template>
                                        <v-expansion-panel v-model="panel" expand>
                                            <v-expansion-panel-content>
                                                <template v-slot:header>
                                                    <div class="expansion-header">Rota Optimizasyon Kısıtları</div>
                                                </template>
                                                <v-card>
                                                    <v-combobox class="parameter-input"
                                                                :return-object="true"
                                                                v-model="avoidsModel"
                                                                :items="avoids"
                                                                item-value="id"
                                                                label="Yol Parametresi"
                                                                item-text="name"
                                                                name="avoid"
                                                                background-color="grey lighten-4"
                                                                color="green accent-2"
                                                                full-width
                                                                hide-details>
                                                    </v-combobox>
                                                    <v-combobox class="parameter-input"
                                                                :return-object="true"
                                                                v-model="optimizeModel"
                                                                :items="optimizes"
                                                                item-value="id"
                                                                label="Optimizasyon"
                                                                item-text="name"
                                                                name="optimize"
                                                                background-color="grey lighten-4"
                                                                color="green accent-2"
                                                                full-width
                                                                hide-details>
                                                    </v-combobox>
                                                    <v-combobox class="parameter-input"
                                                                :return-object="true"
                                                                v-model="vehicleHazardousMaterialModel"
                                                                :items="vehicleHazardousMaterials"
                                                                item-value="id"
                                                                label="Yük Tipi"
                                                                item-text="name"
                                                                name="vehicleHazardousMaterial"
                                                                background-color="grey lighten-4"
                                                                color="green accent-2"
                                                                full-width
                                                                hide-details>
                                                    </v-combobox>
                                                    <v-text-field
                                                        class="parameter-input"
                                                        type="number"
                                                        v-model="optimizationParameters.vehicleSpec.vehicleLength"
                                                        label="Aracın Yatay Uzunluğu (metre)"
                                                        name="vehicleLength"
                                                        max=1000
                                                        background-color="grey lighten-4"
                                                        color="green accent-2"
                                                        full-width
                                                        hide-details>
                                                    </v-text-field>
                                                    <v-text-field
                                                        class="parameter-input"
                                                        type="number"
                                                        v-model="optimizationParameters.vehicleSpec.vehicleHeight"
                                                        label="Aracın Boyuna Uzunluğu (metre)"
                                                        name="vehicleHeight"
                                                        max=1000
                                                        background-color="grey lighten-4"
                                                        color="green accent-2"
                                                        full-width
                                                        hide-details>
                                                    </v-text-field>
                                                    <v-text-field
                                                        class="parameter-input"
                                                        type="number"
                                                        v-model="optimizationParameters.vehicleSpec.vehicleWidth"
                                                        label="Araç Genişliği (metre)"
                                                        name="vehicleWidth"
                                                        max=1000
                                                        background-color="grey lighten-4"
                                                        color="green accent-2"
                                                        full-width
                                                        hide-details>
                                                    </v-text-field>
                                                    <v-text-field
                                                        class="parameter-input"
                                                        type="number"
                                                        v-model="optimizationParameters.vehicleSpec.vehicleWeight"
                                                        label="Araç Ağırlığı (kg)"
                                                        name="vehicleWeight"
                                                        max=1000
                                                        background-color="grey lighten-4"
                                                        color="green accent-2"
                                                        full-width
                                                        hide-details>
                                                    </v-text-field>
                                                    <v-text-field
                                                        class="parameter-input"
                                                        type="number"
                                                        v-model="optimizationParameters.vehicleSpec.vehicleAxles"
                                                        label="Araç Dingil Sayısı"
                                                        name="vehicleAxles"
                                                        max=20
                                                        background-color="grey lighten-4"
                                                        color="green accent-2"
                                                        full-width
                                                        hide-details>
                                                    </v-text-field>
                                                    <v-text-field
                                                        class="parameter-input"
                                                        type="number"
                                                        v-model="optimizationParameters.vehicleSpec.vehicleTrailers"
                                                        label="Araç Römork Sayısı"
                                                        name="vehicleTrailers"
                                                        max=20
                                                        background-color="grey lighten-4"
                                                        color="green accent-2"
                                                        full-width
                                                        hide-details>
                                                    </v-text-field>
                                                </v-card>
                                            </v-expansion-panel-content>
                                            <v-expansion-panel-content>
                                                <template v-slot:header>
                                                    <div class="expansion-header">Bayiler <span style="color: grey">(Sürükleyerek sırayı değiştir)</span>
                                                    </div>
                                                </template>
                                                <v-card>
                                                    <draggable
                                                        :list="vendorsForSort"
                                                        ghost-class="draggable-ghost"
                                                        class="draggable-list"
                                                        @start="dragging = true"
                                                        @end="dragging = false"
                                                    >
                                                        <div
                                                            class="draggable-list-item"
                                                            v-for="element in vendorsForSort"
                                                            :key="element.id"
                                                        >
                                                            {{ element.title }}
                                                        </div>
                                                    </draggable>
                                                </v-card>
                                            </v-expansion-panel-content>
                                        </v-expansion-panel>
                                    </template>
                                    <v-btn v-on:click="getOptimizedPath"
                                           class="optimize-btn"
                                           outline
                                           block
                                           round
                                           color="cyan"
                                    > Optimize Rotayı Oluştur
                                    </v-btn>
                                </div>
                            </v-flex>
                            <v-flex xs8>
                                <leaflet-map
                                    ref="leafletMap"
                                    @save="saveRoute"
                                ></leaflet-map>
                            </v-flex>
                        </v-layout>
                    </v-tab-item>
                </v-tabs>
            </v-card-text>
            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                    v-if="isEdit && currentTab === 0 && (calculatedRoute !== null && calculatedRoute.length > 0)"
                    color="blue darken-1" outline
                    @click.native="edit">Düzenle
                </v-btn>
                <v-btn
                    v-else-if="currentTab === 0 && (calculatedRoute !== null && calculatedRoute.length > 0)"
                    color="blue darken-1" outline
                    @click.native="save">
                    Kaydet
                </v-btn>
                <v-btn v-if="isEdit && currentTab === 1" color="blue darken-1" outline @click.native="saveRoute">Rotayı
                    Kaydet
                </v-btn>
                <v-btn color="orange darken-1" outline @click.native="close">Kapat</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script>
    import LeafletMap from "../leaflet/voyageLeafletMap";
    import '../leaflet/mapStyle.css';
    import draggable from 'vuedraggable'

    const voyageModel = require('@/modules/main/voyage/models/voyage-model-add-edit').default;
    const optimizationParametersModel = require('@/modules/main/voyage/models/optimization-parameters').default;
    export default {
        components: {LeafletMap, draggable},
        data() {
            return {
                panel: [],
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
                vendorsForSort: [],
                orders: [],
                calculatedRoute: [],
                optimizationParameters: JSON.parse(JSON.stringify(optimizationParametersModel)),
                optimizeModel: {id: null, name: null},
                avoidsModel: {id: null, name: null},
                vehicleHazardousMaterialModel: {id: null, name: null},
                avoids: [
                    {id: "highways", name: "Otoban kullanma"},
                    {id: "tolls", name: "Paralı yol kullanma"},
                    {id: "minimizeHighways", name: "Otoban kullanımı minimum"},
                    {id: "minimizeTolls", name: "Paralı yol kullanımı minimum"}
                ],
                optimizes: [
                    {id: "time", name: "Minimum zaman"},
                    {id: "timeWithTraffic", name: "Güncel trafik durumuna göre"}
                ],
                vehicleHazardousMaterials: [
                    {id: "Corrosive", name: "Aşındırıcı"},
                    {id: "Explosive", name: "Patlayıcı"},
                    {id: "Flammable", name: "Yanıcı"},
                    {id: "FlammableSolid", name: "Katı Yanıcı"},
                    {id: "Gas", name: "Gaz"},
                    {id: "GoodsHarmfulToWater", name: "Suya Zararlı Madde"},
                    {id: "Organic", name: "Organik"},
                    {id: "Poison", name: "Zehir"},
                    {id: "PoisonousInhalation", name: "Uçucu Zehir"},
                    {id: "Radioactive", name: "Radyoaktif"}
                ]
            }
        },
        methods: {
            open(data) {
                this.showDialog = true;
                this.getDropdownData();
                if (data) {
                    this.data = data;
                    if (this.data._order) {
                        this.getVendorsByOrder();
                    }
                    if (this.data.calculatedRoute) {
                        this.layerChanged(1, false);
                        this.calculatedRoute = this.data.calculatedRoute.route;
                        this.$refs.leafletMap.setWaypoints(this.calculatedRoute);
                        this.layerChanged(1, true);
                    }
                    if (this.data.sortedVendors) {
                        this.vendorsForSort = this.data.sortedVendors.vendors;
                    }
                    if (this.data.optimizationParameters.vehicleSpec) {
                        this.optimizationParameters = this.data.optimizationParameters;
                        this.avoidsModel = this.optimizationParameters.avoid;
                        this.optimizeModel = this.optimizationParameters.optimize;
                        this.vehicleHazardousMaterialModel = this.optimizationParameters.vehicleSpec.vehicleHazardousMaterials;
                    } else {
                        this.setVehicleSpecs();
                    }
                    this.isEdit = true;
                    this.initLeafletMap();
                } else {
                    this.clear();
                }
            },
            jsonArrToJson(arr, key) {
                let res = JSON.parse('{"' + key + '":' + "[]}");
                arr.forEach((item) => {
                    res[key].push(item)
                });
                return res;
            },
            array2dToJson(a, p, nl) {
                let i, j, s = '{"' + p + '":[';
                nl = nl || '';
                for (i = 0; i < a.length; ++i) {
                    s += nl + this.array1dToJson(a[i]);
                    if (i < a.length - 1) {
                        s += ',';
                    }
                }
                s += nl + ']}';
                return s;
            },
            array1dToJson(a, p) {
                let i, s = '[';
                for (i = 0; i < a.length; ++i) {
                    if (typeof a[i] == 'string') {
                        s += '"' + a[i] + '"';
                    } else {
                        s += a[i];
                    }
                    if (i < a.length - 1) {
                        s += ',';
                    }
                }
                s += ']';
                if (p) {
                    return '{"' + p + '":' + s + '}';
                }
                return s;
            },
            close() {
                this.showDialog = false;
                this.vendorsForSort = null;
                this.vendors = null;
                this.layerChanged(0, false);
                this.layerChanged(1, false);
                this.calculatedRoute = [];
                this.$refs.leafletMap.cleanData();
                this.optimizeModel = {id: null, name: null};
                this.avoidsModel = {id: null, name: null};
                this.vehicleHazardousMaterialModel = {id: null, name: null};
                this.optimizationParameters = JSON.parse(JSON.stringify(optimizationParametersModel));
                this.data = JSON.parse(JSON.stringify(voyageModel));
                this.currentTab = 0;
                this.panel = []
            },
            save() {
                this.$validator.validateAll().then((result) => {
                    if (result) {
                        this.data.calculatedRoute = this.array2dToJson(this.calculatedRoute, 'route', '');
                        this.data.calculatedRoute = JSON.parse(this.data.calculatedRoute);
                        this.optimizationParameters.avoid = this.avoidsModel;
                        this.optimizationParameters.optimize = this.optimizeModel;
                        this.optimizationParameters.vehicleSpec.vehicleHazardousMaterials = this.vehicleHazardousMaterialModel;
                        this.data.optimizationParameters = this.optimizationParameters;
                        this.data.sortedVendors = this.jsonArrToJson(this.vendorsForSort, 'vendors');
                        this.$emit("save", this.data);
                        this.close();
                    }
                });
            },
            edit() {
                this.$validator.validateAll().then((result) => {
                    if (result) {
                        this.data.calculatedRoute = this.array2dToJson(this.calculatedRoute, 'route', '');
                        this.data.calculatedRoute = JSON.parse(this.data.calculatedRoute);
                        this.optimizationParameters.avoid = this.avoidsModel;
                        this.optimizationParameters.optimize = this.optimizeModel;
                        this.optimizationParameters.vehicleSpec.vehicleHazardousMaterials = this.vehicleHazardousMaterialModel;
                        this.data.optimizationParameters = this.optimizationParameters;
                        this.data.sortedVendors = this.jsonArrToJson(this.vendorsForSort, 'vendors');
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
                this.$http.get("api/v1/vehicle?limit=100&fields=id,plateNumber,vehicleSpec&vehicleStateCode=IDLE").then((result) => {
                    this.vehicles = result.data.data.items
                }).catch((error) => {
                    console.error(error);
                }).finally(() => {

                })
            },
            getDrivers() {
                this.$http.get("api/v1/driver?fields=id,fullName&driverStateCode=IDLE").then((result) => {
                    this.drivers = result.data.data.items;
                    console.log("drivers:", this.drivers)
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
                }).catch((error) => {
                    console.error(error);
                }).finally(() => {

                })
            },
            getDropdownData() {
                this.getDrivers();
                this.getVehicles();
                this.getOrders();
            },
            getVendorsByOrder(type) {
                this.layerChanged(0, false);

                this.$http.get("api/v1/order/getAllVendorsByOrderId/" + this.data._order.id).then((result) => {
                    if (type === 'onChange' || !this.vendorsForSort) {
                        this.vendorsForSort = JSON.parse(JSON.stringify(result.data.data));
                    }
                    this.vendors = result.data.data;
                    this.initLeafletMap();
                }).catch((error) => {
                    console.error(error);
                }).finally(() => {

                })
            },
            setVehicleSpecs() {
                if (this.data.vehicle.vehicleSpec !== null) {
                    this.optimizationParameters.vehicleSpec = this.data.vehicle.vehicleSpec;
                }
            },
            saveRoute() {
                console.log(this.calculatedRoute)
            },
            initLeafletMap() {
                this.$refs.leafletMap.setLocations(this.vendors);
                this.layerChanged(0, true);
            },
            getMapLayers() {
                this.layers = this.$refs.leafletMap.getLayers();
            },
            layerChanged(layerId, active) {
                if (!this.layers[layerId].active && active) this.layers[layerId].active = true;
                if (this.layers[layerId].active && !active) this.layers[layerId].active = false;
                this.$refs.leafletMap.layerChanged(layerId, active);
            },
            getOptimizedPath() {
                this.optimizationParameters.avoid = this.avoidsModel.id;
                this.optimizationParameters.optimize = this.optimizeModel.id;
                this.optimizationParameters.vehicleSpec.vehicleHazardousMaterials = this.vehicleHazardousMaterialModel.id;
                this.layerChanged(1, false);

                let waypoints = this.prepareWaypoints();
                this.optimizationParameters.waypoints = waypoints;
                this.$http.post('https://dev.virtualearth.net/REST/v1/Routes/Truck?key=AhWheMPRKfucF4QUSzDJ7avIMgpQDwL6t0C_NufIKUUeOJHwgpMiAWV3tE0Qhiw1', this.optimizationParameters).then((result) => {
                    if (result.status <= 299) {
                        this.calculatedRoute = result.data.resourceSets[0].resources[0].routePath.line.coordinates;
                        this.$refs.leafletMap.setWaypoints(this.calculatedRoute);
                        this.layerChanged(1, true);
                    } else {
                        console.log(result.data.statusDescription)
                    }
                }).catch((error) => {
                    console.log(error);
                }).finally(() => {
                    this.loading = false;
                });
            },
            prepareWaypoints() {
                let waypoints = [];
                for (let i = 0; i < this.vendorsForSort.length; i++) {
                    waypoints.push(
                        {
                            "latitude": this.vendorsForSort[i].location[0],
                            "longitude": this.vendorsForSort[i].location[1]
                        }
                    );
                }
                return waypoints;
            },
            getOptimizedOrder() {
                // TODO: use mapbox optimization API
                let clonedVendors = JSON.parse(JSON.stringify(this.vendorsForSort));
                let tmp = clonedVendors[0];
                clonedVendors[0] = clonedVendors[1];
                clonedVendors[1] = tmp;
                this.vendorsForSort = clonedVendors;
            }
        },
        mounted() {
            this.getDropdownData();
            this.getSysrefTransportationTypes();
            this.getSysrefVoyageDirections();
            this.getSysrefDeliveryStatuses();
            this.getMapLayers();
        }
    }
</script>
<style scoped>
    #voyage_map {
        height: 450px;
    }

    #voyage_map img {
        max-height: none;
    }

    #voyage_tabs .tab-item {
        margin-top: 20px;
    }

    #voyage_map_left_side {
        overflow: auto;
        width: 460px;
        height: 500px;
    }

    .draggable-list {
        transform: scale(0.950);
        transform-origin: center;
        -webkit-box-orient: vertical;
        -webkit-box-direction: normal;
        flex-direction: column;
        padding-left: 0;
        margin-bottom: 0;
        font-size: 0.9rem;
        font-weight: 400;
        line-height: 2.3;
        color: #212529;
        text-align: left;
    }

    .draggable-list-item {
        word-wrap: break-word;
        cursor: move;
        position: relative;
        display: block;
        padding: .75rem 1.25rem;
        margin-bottom: 0;
        background-color: #fff9f2;
        border: 1px solid rgba(0, 0, 0, .125);

    }

    .draggable-list-item:hover {
        background: #fbf6f7;
    }

    .draggable-list-item:first-child {
        border-top-left-radius: .25rem;
        border-top-right-radius: .25rem;
        border-color: deepskyblue !important;
    }

    .draggable-list-item:last-child {
        border-bottom-left-radius: .25rem;
        border-bottom-right-radius: .25rem;
        margin-bottom: 1rem;
        border-color: darkred !important;
    }

    .draggable-ghost {
        opacity: 0.5;
        background: #c8ebfb;
    }

    .expansion-header {
        font-weight: 400;
        font-size: 1.3rem;
    }

    .parameter-input {
        transform: scale(0.950);
        transform-origin: center;
        margin-top: 0 !important;
    }

    .parameter-input:last-child {
        margin-bottom: 1rem;
    }

    .optimize-btn {
        margin-top: 1.3rem;
        transform: scale(0.850);
    }
</style>