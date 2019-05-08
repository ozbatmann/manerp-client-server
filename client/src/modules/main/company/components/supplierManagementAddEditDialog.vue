<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <m-data-table-add-edit-form
            v-model="showDialog"
            title="Yeni Tedarikçi İş Yeri"
            :editing="isEdit"
            @clear="clear"
            @edit="edit"
            @save="save"
            @close="close"
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
                                  flat
                                  solo>
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
                                  flat
                                  solo>
                    </v-text-field>
                </v-flex>
                <v-flex xs6 pr-2>
                    <v-text-field v-validate="'required'"
                                  :error-messages="errors.collect('customerRepresentative')"
                                  v-model="data.customerRepresentative"
                                  label="Müşteri Temsilcisi"
                                  name="customerRepresentative"
                                  :counter="50"
                                  maxlength="50"
                                  background-color="grey lighten-4"
                                  color="green accent-2"
                                  flat
                                  solo>
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
                                  flat
                                  solo>
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
                                  flat
                                  solo>
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
                                  flat
                                  solo>
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
                                  flat
                                  solo>
                    </v-text-field>
                </v-flex>
                <!--                                        <v-combobox v-model="data.sysrefNaceCode"-->
                <!--                                                    :return-object="true"-->
                <!--                                                    :items="sysrefNaceCodes"-->
                <!--                                                    item-value="id"-->
                <!--                                                    label="Nace Kodu"-->
                <!--                                                    item-text="name"-->
                <!--                                                    name="sysrefNaceCode"-->
                <!--                                                    background-color="grey lighten-4"-->
                <!--                                                    color="green accent-2"-->
                <!--                                                    flat
                                  solo>-->
                <!--                                        </v-combobox>-->
                <v-flex xs6 pl-2>
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
                                flat
                                  solo>
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
                                flat
                                  solo>
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
                                flat
                                  solo>
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
                                  flat
                                  solo>
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
                                  flat
                                  solo>
                    </v-text-field>
                </v-flex>
                <v-flex xs12>
                    <v-textarea v-validate="'required'"
                                :error-messages="errors.collect('address')"
                                flat
                                solo
                                label="Adres"
                                auto-grow
                                v-model="data.address"
                                :counter="255"
                                maxlength="255"
                                name="address"
                                background-color="grey lighten-4"
                                color="green accent-2">
                    </v-textarea>
                </v-flex>
            </v-layout>
        </template>
    </m-data-table-add-edit-form>
</template>

<script>
    import MDataTableAddEditForm from "../../shared/components/data/components/MDataTableAddEditForm";

    const companyModel = require('@/modules/main/company/models/company-model-add-edit').default;

    export default {
        components: {MDataTableAddEditForm},
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
            }
        },
        methods: {
            open(data) {
                this.currentTab = 0;
                if (data) {
                    this.data = data;
                    this.isEdit = true;
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
                        this.close();
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

    #supplier_tabs .tab-item {
        margin-top: 20px;
    }
</style>
