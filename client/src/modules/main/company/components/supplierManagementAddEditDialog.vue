<template>
    <v-dialog v-model="showDialog" persistent max-width="90%" scrollable>
        <!-- <v-btn slot="activator" color="primary" dark>Open Dialog</v-btn> -->
        <v-card class="pa-12">
            <v-tabs
                v-model="currentTab"
                fixed-tabs
                slider-color="green accent-2"
            >
                <v-tab ripple>
                    İş Yeri Bilgileri
                </v-tab>
                <v-tab :disabled="!isEdit" ripple v-on:change="vendorTabChanged">
                    Bayi Bilgileri
                </v-tab>
                <v-tab-item :transition="false" :reverse-transition="false"
                >
                    <v-card class="pa-3">
                        <v-card-title>
                            <span v-if="isEdit" class="headline">Güncelle</span>
                            <span v-else class="headline">Yeni Müşteri İş Yeri</span>
                        </v-card-title>
                        <v-card-text>
                            <v-container grid-list-md pa-0>
                                <v-layout wrap>
                                    <v-flex xs12>
                                        <v-text-field v-validate="'required'"
                                                      :error-messages="errors.collect('name')"
                                                      v-model="data.name"
                                                      label="İsim"
                                                      name="name"
                                                      :counter="50"
                                                      maxlength="50"
                                                      background-color="grey lighten-4"
                                                      color="green accent-2"
                                                      full-width>
                                        </v-text-field>
                                        <v-text-field v-validate="'required'"
                                                      :error-messages="errors.collect('title')"
                                                      v-model="data.title"
                                                      label="Unvan"
                                                      name="title"
                                                      :counter="50"
                                                      maxlength="50"
                                                      background-color="grey lighten-4"
                                                      color="green accent-2"
                                                      full-width>
                                        </v-text-field>
                                        <v-text-field v-validate="'required'"
                                                      :error-messages="errors.collect('customerRepresentative')"
                                                      v-model="data.customerRepresentative"
                                                      label="Müşteri Temsilcisi"
                                                      name="customerRepresentative"
                                                      :counter="50"
                                                      maxlength="50"
                                                      background-color="grey lighten-4"
                                                      color="green accent-2"
                                                      full-width>
                                        </v-text-field>
                                        <v-text-field v-model="data.tradeRegistrationNo"
                                                      label="Ticari Sicil Numarası"
                                                      name="tradeRegistrationNo"
                                                      :counter="30"
                                                      maxlength="30"
                                                      background-color="grey lighten-4"
                                                      color="green accent-2"
                                                      full-width>
                                        </v-text-field>
                                        <v-text-field v-model="data.employerRegistrationNo"
                                                      label="İşveren Sicil Numarası"
                                                      name="employerRegistrationNo"
                                                      :counter="30"
                                                      maxlength="30"
                                                      background-color="grey lighten-4"
                                                      color="green accent-2"
                                                      full-width>
                                        </v-text-field>
                                        <v-text-field v-validate="'required'"
                                                      :error-messages="errors.collect('taxNumber')"
                                                      v-model="data.taxNumber"
                                                      label="Vergi Numarası"
                                                      name="taxNumber"
                                                      :counter="50"
                                                      maxlength="50"
                                                      background-color="grey lighten-4"
                                                      color="green accent-2"
                                                      full-width>
                                        </v-text-field>
                                        <v-text-field v-validate="'required'"
                                                      :error-messages="errors.collect('taxOffice')"
                                                      v-model="data.taxOffice"
                                                      label="Vergi Dairesi"
                                                      name="taxOffice"
                                                      :counter="255"
                                                      maxlength="255"
                                                      background-color="grey lighten-4"
                                                      color="green accent-2"
                                                      full-width>
                                        </v-text-field>
                                        <!--                                        <v-combobox v-model="data.sysrefNaceCode"-->
                                        <!--                                                    :return-object="true"-->
                                        <!--                                                    :items="sysrefNaceCodes"-->
                                        <!--                                                    item-value="id"-->
                                        <!--                                                    label="Nace Kodu"-->
                                        <!--                                                    item-text="name"-->
                                        <!--                                                    name="sysrefNaceCode"-->
                                        <!--                                                    background-color="grey lighten-4"-->
                                        <!--                                                    color="green accent-2"-->
                                        <!--                                                    full-width>-->
                                        <!--                                        </v-combobox>-->
                                        <v-combobox v-model="data.sysrefCountry"
                                                    v-on:change="getSysrefCities"
                                                    :return-object="true"
                                                    :items="sysrefCountries"
                                                    item-value="id"
                                                    label="Ülke"
                                                    item-text="name"
                                                    name="sysrefCity"
                                                    background-color="grey lighten-4"
                                                    color="green accent-2"
                                                    full-width>
                                        </v-combobox>
                                        <v-combobox v-model="data.sysrefCity"
                                                    v-on:change="getSysrefDistricts"
                                                    :disabled="!data.sysrefCountry"
                                                    :return-object="true"
                                                    :items="sysrefCities"
                                                    item-value="id"
                                                    label="İl"
                                                    item-text="name"
                                                    name="sysrefCity"
                                                    background-color="grey lighten-4"
                                                    color="green accent-2"
                                                    full-width>
                                        </v-combobox>
                                        <v-combobox v-model="data.sysrefDistrict"
                                                    :disabled="!data.sysrefCity"
                                                    :return-object="true"
                                                    :items="sysrefDistricts"
                                                    item-value="id"
                                                    label="İlçe"
                                                    item-text="name"
                                                    name="sysrefDistrict"
                                                    background-color="grey lighten-4"
                                                    color="green accent-2"
                                                    full-width>
                                        </v-combobox>
                                        <v-textarea v-validate="'required'"
                                                    :error-messages="errors.collect('address')"
                                                    box
                                                    style="padding-top: 3%;"
                                                    label="Adres"
                                                    auto-grow
                                                    v-model="data.address"
                                                    :counter="255"
                                                    maxlength="255"
                                                    name="address"
                                                    background-color="grey lighten-4"
                                                    color="green accent-2">
                                        </v-textarea>
                                        <v-text-field v-validate="'email'"
                                                      :error-messages="errors.collect('email')"
                                                      v-model="data.email"
                                                      :counter="50"
                                                      maxlength="50"
                                                      label="E-Mail"
                                                      name="email"
                                                      background-color="grey lighten-4"
                                                      color="green accent-2"
                                                      full-width>
                                        </v-text-field>
                                        <v-text-field v-validate="'required'"
                                                      :error-messages="errors.collect('phone')"
                                                      mask="phone"
                                                      v-model="data.phone"
                                                      :counter="11"
                                                      maxlength="11"
                                                      label="Telefon"
                                                      name="phone"
                                                      background-color="grey lighten-4"
                                                      color="green accent-2"
                                                      full-width>
                                        </v-text-field>
                                    </v-flex>
                                </v-layout>
                            </v-container>
                        </v-card-text>
                        <v-card-actions>
                            <v-spacer></v-spacer>
                            <v-btn color="orange darken-1" outline @click.native="close">Kapat</v-btn>
                            <v-btn v-if="isEdit" color="blue darken-1" outline @click.native="edit">Düzenle</v-btn>
                            <v-btn v-else color="blue darken-1" outline @click.native="save">Kaydet</v-btn>
                        </v-card-actions>
                    </v-card>
                </v-tab-item>
                <v-tab-item :transition="false" :reverse-transition="false"
                >
                    <v-card class="pa-3">
                        <v-card-title>
                            <span v-if="isEdit" class="headline">Bayiler</span>
                            <span v-else class="headline">Yeni Bayi</span>
                        </v-card-title>
                        <v-card-text>
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
                                        @save="addNewVendor"
                                        @edit="editVendor"
                                    ></leaflet-map>
                                </v-flex>
                            </v-layout>
                        </v-card-text>
                        <v-card-actions>
                            <v-spacer></v-spacer>
                            <v-btn color="orange darken-1" outline @click.native="close">Kapat</v-btn>
                        </v-card-actions>
                    </v-card>
                </v-tab-item>
            </v-tabs>
        </v-card>
    </v-dialog>
</template>

<script>
    import LeafletMap from "../leaflet/leafletMap";
    import '../leaflet/mapStyle.css';

    const companyModel = require('@/modules/main/company/models/company-model-add-edit').default;

    export default {
        components: {LeafletMap},
        data() {
            return {
                currentTab: 0,
                sysrefCountries: [],
                sysrefCities: [],
                sysrefDistricts: [],
                sysrefNaceCodes: [],
                showDialog: false,
                isEdit: false,
                data: JSON.parse(JSON.stringify(companyModel)),
                layers: [
                    {
                        id: 0,
                        name: 'Bayileri Göster',
                        active: false,
                        features: [
                            {
                                id: 0,
                                name: 'Bogart\'s Smokehouse',
                                type: 'marker',
                                coords: ['39.813119', '32.711207'],
                            },
                            {
                                id: 1,
                                name: 'Pappy\'s Smokehouse',
                                type: 'marker',
                                coords: [39.853454, 32.722818],
                            },
                            {
                                id: 2,
                                name: 'Broadway Oyster Bar',
                                type: 'marker',
                                coords: [39.865876, 32.709700],
                            },
                            {
                                id: 3,
                                name: 'Charlie Gitto\'s On the Hill',
                                type: 'marker',
                                coords: [39.862163, 32.764860],
                            },
                            {
                                id: 4,
                                name: 'Charlie Gitto\'s On the Hill',
                                type: 'marker',
                                coords: [39.874472, 32.764193],
                            }
                        ]
                    },
                    {
                        id: 1,
                        name: 'Bayi Sınır Çizgileri',
                        active: false,
                        features: [{
                            id: 0,
                            name: 'City of St. Louis',
                            type: 'polygon',
                            coords: [
                                [39.813119, 32.711207],
                                [39.853454, 32.722818],
                                [39.865876, 32.709700],
                                [39.862163, 32.764860],
                                [39.874472, 32.764193]
                            ],
                        }],
                    }
                ]
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
            openVendor(data) {
                this.currentTab = 1;
                if (data) {
                    this.data = data;
                    this.isEdit = true;
                    this.vendorTabChanged();
                } else {
                    this.clear();
                }
                this.showDialog = true;
            },
            close() {
                this.showDialog = false;
                this.currentTab = 0;
            },
            save() {
                this.$validator.validateAll().then((result) => {
                    if (result) {
                        this.$emit("save", this.data);
                        // TODO: spinner should be used
                        // this.close();
                        this.isEdit = true;
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
                this.data = JSON.parse(JSON.stringify(companyModel));
                this.$validator.reset();
                this.isEdit = false;
            },
            vendorTabChanged() {
                // to display all tiles
                window.dispatchEvent(new Event('resize'));
            },
            addNewVendor() {
            },
            editVendor() {
            },
            layerChanged(layerId, active) {
                this.$refs.leafletMap.layerChanged(layerId, active);
            },
            setPopupContent() {
                let popupTemplate = require('../leaflet/popupContent');
                this.$refs.leafletMap.setPopupContent(popupTemplate.template);
            },
            initLeafletMap() {
                this.$refs.leafletMap.setLayers(this.layers);
                this.setPopupContent();
            },
            // data retrieve methods
            getSysrefCountries() {
                this.$http.get("api/v1/sysrefCountry?fields=id,name").then((result) => {
                    this.sysrefCountries = result.data.data.items
                }).catch((error) => {
                    console.error(error);
                }).finally(() => {

                })
            },
            getSysrefCities() {
                this.data.sysrefCity = null;
                this.$http.get("api/v1/sysrefCity?limit=81&fields=id,name&countryId=" + this.data.sysrefCountry.id).then((result) => {
                    this.sysrefCities = result.data.data.items
                }).catch((error) => {
                    console.error(error);
                }).finally(() => {

                })
            },
            getSysrefDistricts() {
                this.data.sysrefDistrict = null;
                this.$http.get("api/v1/sysrefDistrict?limit=500&fields=id,name&cityId=" + this.data.sysrefCity.id).then((result) => {
                    this.sysrefDistricts = result.data.data.items
                }).catch((error) => {
                    console.error(error);
                }).finally(() => {

                })
            },
            getSysrefNaceCodes() {
                this.data.sysrefDistrict = null;
                this.$http.get("api/v1/sysrefNaceCode?limit=500&fields=id,name").then((result) => {
                    this.sysrefNaceCodes = result.data.data.items
                }).catch((error) => {
                    console.error(error);
                }).finally(() => {

                })
            }
        },
        mounted() {
            this.initLeafletMap();
            this.getSysrefCountries();
        }
    }
</script>
<style>
    #map {
        height: 450px;
    }

    #map img {
        max-height: none;
    }
</style>