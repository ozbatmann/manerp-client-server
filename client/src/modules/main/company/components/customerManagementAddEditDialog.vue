<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div>
        <m-data-table-add-edit-form
                v-model="showDialog"
                :title="`${ dealer ? 'müşteri' : 'tedarikçi' } iş yeri ekle`"
                :tabs="['iş yeri bilgileri', 'bayi bilgileri']"
                :tabbed="dealer"
                :next-rule="true"
                @activeTab="vendorTabChanged"
                @edit="edit"
                @save="save"
        >
            <template v-slot:form>
                <v-layout wrap>
                    <v-flex xs6 pr-2>
                        <v-text-field v-validate="'required'"
                                      :error-messages="errors.collect('name')"
                                      v-model="data.name"
                                      label="İsim"
                                      name="name"
                                      :counter="50"
                                      maxlength="50"
                                      background-color="grey lighten-4"
                                      color="green accent-2"
                                      solo
                                      flat
                        >
                        </v-text-field>
                    </v-flex>
                    <v-flex xs6 pl-2>
                        <v-text-field v-validate="'required'"
                                      :error-messages="errors.collect('title')"
                                      v-model="data.title"
                                      label="Ünvan"
                                      name="title"
                                      :counter="150"
                                      maxlength="150"
                                      background-color="grey lighten-4"
                                      color="green accent-2"
                                      solo
                                      flat
                        >
                        </v-text-field>
                    </v-flex>
                    <v-flex xs6 pr-2>
                        <v-text-field
                                cache-items
                                hide-no-data v-validate="'required'"
                                :error-messages="errors.collect('customerRepresentative')"
                                v-model="data.customerRepresentative"
                                label="Müşteri Temsilcisi"
                                name="customerRepresentative"
                                :counter="50"
                                maxlength="50"
                                background-color="grey lighten-4"
                                color="green accent-2"
                                solo
                                flat
                        >
                        </v-text-field>
                    </v-flex>
                    <v-flex xs6 pl-2>
                        <v-text-field v-model="data.tradeRegistrationNo"
                                      label="Ticari Sicil Numarası"
                                      name="tradeRegistrationNo"
                                      :counter="30"
                                      maxlength="30"
                                      background-color="grey lighten-4"
                                      color="green accent-2"
                                      solo
                                      flat
                        >
                        </v-text-field>
                    </v-flex>
                    <v-flex xs6 pr-2>
                        <v-text-field v-model="data.employerRegistrationNo"
                                      label="İşveren Sicil Numarası"
                                      name="employerRegistrationNo"
                                      :counter="30"
                                      maxlength="30"
                                      background-color="grey lighten-4"
                                      color="green accent-2"
                                      solo
                                      flat
                        >
                        </v-text-field>
                    </v-flex>
                    <v-flex xs6 pl-2>
                        <v-text-field v-validate="'required'"
                                      :error-messages="errors.collect('taxNumber')"
                                      v-model="data.taxNumber"
                                      label="Vergi Numarası"
                                      name="taxNumber"
                                      :counter="50"
                                      maxlength="50"
                                      background-color="grey lighten-4"
                                      color="green accent-2"
                                      solo
                                      flat
                        >
                        </v-text-field>
                    </v-flex>
                    <v-flex xs6 pr-2>
                        <v-text-field v-validate="'required'"
                                      :error-messages="errors.collect('taxOffice')"
                                      v-model="data.taxOffice"
                                      label="Vergi Dairesi"
                                      name="taxOffice"
                                      :counter="255"
                                      maxlength="255"
                                      background-color="grey lighten-4"
                                      color="green accent-2"
                                      solo
                                      flat
                        >
                        </v-text-field>
                    </v-flex>
                    <v-flex xs6 pl-2>
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
                                    solo
                                    flat
                        >
                        </v-combobox>
                    </v-flex>
                    <v-flex xs6 pr-2>
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
                                    solo
                                    flat
                        >
                        </v-combobox>
                    </v-flex>
                    <v-flex xs6 pl-2>
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
                                    solo
                                    flat
                        >
                        </v-combobox>
                    </v-flex>
                    <v-flex xs6 pr-2>
                        <v-text-field v-validate="'email'"
                                      :error-messages="errors.collect('email')"
                                      v-model="data.email"
                                      :counter="50"
                                      maxlength="50"
                                      label="E-Mail"
                                      name="email"
                                      background-color="grey lighten-4"
                                      color="green accent-2"
                                      solo
                                      flat
                        >
                        </v-text-field>
                    </v-flex>
                    <v-flex xs6 pl-2>
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
                                      solo
                                      flat
                        >
                        </v-text-field>
                    </v-flex>
                    <v-flex xs12>
                        <v-textarea v-validate="'required'"
                                    :error-messages="errors.collect('address')"
                                    label="Adres"
                                    auto-grow
                                    v-model="data.address"
                                    :counter="255"
                                    maxlength="255"
                                    name="address"
                                    background-color="grey lighten-4"
                                    color="green accent-2"
                                    solo
                                    flat
                        >
                        </v-textarea>
                    </v-flex>
                </v-layout> <!-- Here -->
            </template>
            <template v-slot:tab-2>
                <v-layout row wrap>
                    <v-flex hidden-xs-only>
                        <div class="form-check" v-for="layer in layers" :key="layer.id">
                            <v-checkbox v-model="layer.active"
                                        :label="layer.name"
                                        @change="layerChanged(layer.id, layer.active)">
                            </v-checkbox>
                        </div>
                    </v-flex>
                    <v-flex xs12 style="margin: -16px; width: calc(100% + 32px);">
                        <leaflet-map
                                ref="leafletMap"
                                @save="addNewVendor"
                                @edit="editVendor"
                                @delete="deleteVendor"
                        ></leaflet-map>
                    </v-flex>
                </v-layout> <!-- And here -->
            </template>
        </m-data-table-add-edit-form>
    </div>

</template>

<script>
    import LeafletMap from "../leaflet/leafletMap";
    import '../leaflet/mapStyle.css';
    import MDataTableAddEditForm from "../../shared/components/data/components/MDataTableAddEditForm";

    const companyModel = require('@/modules/main/company/models/company-model-add-edit').default;

    export default {
        components: {MDataTableAddEditForm, LeafletMap},

        props: {
            dealer: {
                type: Boolean,
                default: false
            }
        },

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
                layers: [],
                vendors: []
            }
        },

        methods: {

            open(data) {
                this.currentTab = 0;
                if (data) {
                    this.data = data;
                    this.isEdit = true;
                    this.getAllVendors();
                    this.initLeafletMap();
                } else {
                    this.clear();
                }
                this.showDialog = true;
            },
            openVendor(data) {
                this.currentTab = 1;
                if (data) {
                    this.data = data;
                    this.isEdit = true;
                    this.getAllVendors();
                    this.vendorTabChanged();
                } else {
                    this.clear();
                }
                this.showDialog = true;
            },
            close() {
                this.showDialog = false;
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
            addNewVendor(data) {
                data.company = this.data.id;
                this.$http.post("api/v1/vendor", data).then((result) => {
                    if (result.data.status < 299) {
                        this.getAllVendors();
                        this.$refs.leafletMap.closePopup();
                    }
                    this.displayMessage(result);
                }).catch((error) => {
                    console.error(error);
                }).finally(() => {

                })
            },
            editVendor(data) {
                data.company = this.data.id;
                this.$http.put("api/v1/vendor", data).then((result) => {
                    if (result.data.status < 299) {
                        this.getAllVendors();
                        this.$refs.leafletMap.closePopup();
                    }
                    this.displayMessage(result);
                }).catch((error) => {
                    console.error(error);
                }).finally(() => {

                })
            },
            deleteVendor(id) {
                this.$http.delete(`api/v1/vendor/${id}`).then((result) => {
                    if (result.data.status < 299) {
                        this.getAllVendors();
                        this.$refs.leafletMap.closePopup();
                    }
                    this.displayMessage(result);
                }).catch((error) => {
                    console.error(error);
                })
            },
            layerChanged(layerId, active) {
                if (!this.layers[layerId].active && active) this.layers[layerId].active = true;
                if (this.layers[layerId].active && !active) this.layers[layerId].active = false;
                this.$refs.leafletMap.layerChanged(layerId, active);
            },
            setPopupContent() {
                let popupTemplate = require('../leaflet/popupContent');
                this.$refs.leafletMap.setPopupContent(popupTemplate.template);
            },
            initLeafletMap() {
                this.$refs.leafletMap.setLocations(this.vendors);
                this.setPopupContent();
                this.layers = this.$refs.leafletMap.getLayers();
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
            },
            getAllVendors() {
                let fields = 'fields=id,code,title,address,phone,location,active';
                let pagination = 'limit=200&offset=0';
                let companyId = 'companyId=' + this.data.id;
                this.layerChanged(0, false);
                this.layerChanged(1, false);

                this.$http.get('api/v1/vendor?' + fields + '&' + pagination + '&' + companyId).then((result) => {
                    this.vendors = result.data.data.items;
                    this.$refs.leafletMap.setLocations(this.vendors);
                    this.layerChanged(0, true);
                }).catch((error) => {
                    console.log(error);
                }).finally(() => {
                })
            },
            displayMessage(result) {
                this.$emit('displayMessage', result);
            }
        },
        mounted() {
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

    #customer_tabs .tab-item {
        margin-top: 20px;
    }
</style>
