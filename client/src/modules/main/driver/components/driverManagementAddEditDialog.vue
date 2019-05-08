<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <m-data-table-add-edit-form
        v-model="showDialog"
        title="Yeni Personel"
        :editing="isEdit"
        @clear="clear"
        @edit="edit"
        @save="save"
        :hideSaveButton="onVoyageDriver"
        :hideClearButton="onVoyageDriver"
    >
        <template v-slot:form>
            <v-layout wrap>
                <v-flex xs6 pr-2>
                    <v-text-field
                        :disabled="onVoyageDriver"
                        v-validate="'required'"
                        :error-messages="errors.collect('driver.firstName')"
                        v-model="data.firstName"
                        :counter="30"
                        maxlength="30"
                        label="Ad"
                        background-color="grey lighten-4"
                        color="green accent-2"
                        class="m-settings__label"
                        name="driver.firstName"
                        solo
                        flat
                    ></v-text-field>
                </v-flex>
                <v-flex xs6 pl-2>
                    <v-text-field
                        :disabled="onVoyageDriver"
                        v-model="data.middleName"
                        :counter="30"
                        maxlength="30"
                        label="İkinci Ad"
                        name="middleName"
                        background-color="grey lighten-4"
                        color="green accent-2"
                        class="m-settings__label"
                        solo
                        flat
                    ></v-text-field>
                </v-flex>
                <v-flex xs6 pr-2>
                    <v-text-field
                        :disabled="onVoyageDriver"
                        v-validate="'required'"
                        :error-messages="errors.collect('lastName')"
                        v-model="data.lastName"
                        :counter="50"
                        maxlength="50"
                        label="Soyad"
                        name="lastName"
                        background-color="grey lighten-4"
                        color="green accent-2"
                        class="m-settings__label"
                        solo
                        flat
                    ></v-text-field>
                </v-flex>
                <v-flex xs6 pl-2>
                    <v-text-field
                        :disabled="onVoyageDriver"
                        v-validate="'required'"
                        :error-messages="errors.collect('tcIdNumber')"
                        v-model="data.tcIdNumber"
                        :counter="11"
                        maxlength="11"
                        label="TC Kimlik Numarası"
                        name="tcIdNumber"
                        background-color="grey lighten-4"
                        color="green accent-2"
                        class="m-settings__label"
                        solo
                        flat
                    ></v-text-field>
                </v-flex>
                <v-flex xs6 pr-2>
                    <v-combobox
                        :disabled="onVoyageDriver"
                        v-model="data.sysrefGender"
                        :return-object="true"
                        :items="sysrefGenders"
                        item-value="id"
                        label="Cinsiyet"
                        item-text="name"
                        name="sysrefGender"
                        background-color="grey lighten-4"
                        color="green accent-2"
                        class="m-settings__label"
                        solo
                        flat
                    ></v-combobox>
                </v-flex>
                <v-flex xs6 pl-2>
                    <v-text-field
                        :disabled="onVoyageDriver"
                        v-validate="'required'"
                        mask="phone"
                        :error-messages="errors.collect('gsmNo')"
                        v-model="data.gsmNo"
                        :counter="11"
                        maxlength="11"
                        label="GSM"
                        name="gsmNo"
                        background-color="grey lighten-4"
                        color="green accent-2"
                        class="m-settings__label"
                        solo
                        flat
                    ></v-text-field>
                </v-flex>
                <v-flex xs6 pr-2>
                    <v-text-field
                        :disabled="onVoyageDriver"
                        v-validate="'email'"
                        :error-messages="errors.collect('email')"
                        v-model="data.email"
                        :counter="50"
                        maxlength="50"
                        label="E-Mail"
                        name="email"
                        background-color="grey lighten-4"
                        color="green accent-2"
                        class="m-settings__label"
                        solo
                        flat
                    ></v-text-field>
                </v-flex>
                <v-flex xs6 pl-2>
                    <v-text-field
                        :disabled="onVoyageDriver"
                        v-validate="'required'"
                        :error-messages="errors.collect('drivingLicenseNumber')"
                        v-model="data.drivingLicenseNumber"
                        :counter="30"
                        maxlength="30"
                        label="Ehliyet Lisans Numarası"
                        name="drivingLicenseNumber"
                        background-color="grey lighten-4"
                        color="green accent-2"
                        class="m-settings__label"
                        solo
                        flat
                    ></v-text-field>
                </v-flex>
                <v-flex xs6 pr-2>
                    <v-combobox
                        :disabled="onVoyageDriver"
                        v-validate="'required'"
                        :error-messages="errors.collect('sysrefStaffContractType')"
                        v-model="data.sysrefStaffContractType"
                        :return-object="true"
                        :items="sysrefStaffContractTypes"
                        item-value="id"
                        label="Sözleşme Tipi"
                        item-text="name"
                        name="sysrefStaffContractType"
                        background-color="grey lighten-4"
                        color="green accent-2"
                        class="m-settings__label"
                        solo
                        flat
                    ></v-combobox>
                </v-flex>
                <v-flex xs6 pl-2>
                    <v-combobox
                        :disabled="onVoyageDriver"
                        v-model="data.sysrefCountry"
                        v-on:change="getSysrefCities"
                        :return-object="true"
                        :items="sysrefCountries"
                        item-value="id"
                        label="Ülke"
                        item-text="name"
                        name="sysrefCity"
                        background-color="grey lighten-4"
                        color="green accent-2"
                        class="m-settings__label"
                        solo
                        flat
                    ></v-combobox>
                </v-flex>
                <v-flex xs6 pr-2>
                    <v-combobox
                        v-model="data.sysrefCity"
                        v-on:change="getSysrefDistricts"
                        :disabled="!data.sysrefCountry || onVoyageDriver"
                        :return-object="true"
                        :items="sysrefCities"
                        item-value="id"
                        label="İl"
                        item-text="name"
                        name="sysrefCity"
                        background-color="grey lighten-4"
                        color="green accent-2"
                        class="m-settings__label"
                        solo
                        flat
                    ></v-combobox>
                </v-flex>
                <v-flex xs6 pl-2>
                    <v-combobox
                        v-model="data.sysrefDistrict"
                        :disabled="!data.sysrefCity || onVoyageDriver"
                        :return-object="true"
                        :items="sysrefDistricts"
                        item-value="id"
                        label="İlçe"
                        item-text="name"
                        name="sysrefDistrict"
                        background-color="grey lighten-4"
                        color="green accent-2"
                        class="m-settings__label"
                        solo
                        flat
                    ></v-combobox>
                </v-flex>
                <v-flex xs12>
                    <v-textarea
                        :disabled="onVoyageDriver"
                        label="Adres"
                        auto-grow
                        v-model="data.address"
                        :counter="255"
                        maxlength="255"
                        name="address"
                        background-color="grey lighten-4"
                        color="green accent-2"
                        class="m-settings__label"
                        solo
                        flat
                    ></v-textarea>
                </v-flex>
                <v-flex xs6 pr-2>
                    <v-checkbox
                        v-on:change="isUserChanged"
                        v-model="data.isUser"
                        :disabled="isEdit && retrievedUsername != null || onVoyageDriver"
                        label="Sistem Kullanıcısı"
                        name="isUser"
                        color="green accent-2"
                        class="m-settings__label mt-2"
                        solo
                        flat
                    ></v-checkbox>
                </v-flex>
                <v-flex xs6 pl-2 v-if="data.isUser">
                    <v-text-field
                        :disabled="isEdit && retrievedUsername != null || onVoyageDriver"
                        v-validate="'required'"
                        :error-messages="errors.collect('username')"
                        v-model="data.username"
                        :counter="50"
                        maxlength="50"
                        label="Kullanıcı Adı"
                        name="username"
                        background-color="grey lighten-4"
                        color="green accent-2"
                        class="m-settings__label"
                        solo
                        flat
                    ></v-text-field>
                </v-flex>
            </v-layout>
        </template>
    </m-data-table-add-edit-form>
</template>

<script>

    import MDataTableAddEditForm from "../../shared/components/data/components/MDataTableAddEditForm";

    const driverModel = require('@/modules/main/driver/models/driver-model-add-edit').default;

    export default {
        components: {MDataTableAddEditForm},
        props: {onVoyageDriver: {type: Boolean, default: false}},
        data() {
            return {
                showDialog: false,
                isEdit: false,
                data: JSON.parse(JSON.stringify(driverModel)),
                sysrefGenders: [],
                sysrefCountries: [],
                sysrefCities: [],
                sysrefDistricts: [],
                sysrefStaffContractTypes: [],
                sysrefDriverStates: [],

                retrievedUsername: null
            }
        },
        methods: {
            open(data) {
                this.showDialog = true;
                if (data) {
                    this.data = data;
                    this.retrievedUsername = this.data.username;
                    if (this.retrievedUsername) {
                        this.data.isUser = true;
                    } else {
                        this.data.isUser = false;
                    }
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
                this.data = JSON.parse(JSON.stringify(driverModel));
                this.$validator.reset();
                this.isEdit = false;
            },

            // data retrieve methods
            getSysrefGenders() {
                this.$http.get("api/v1/sysrefGender?fields=id,name").then((result) => {
                    this.sysrefGenders = result.data.data.items
                }).catch((error) => {
                    console.error(error);
                }).finally(() => {
                })
            },
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
            getSysrefStaffContractTypes() {
                this.$http.get("api/v1/sysrefStaffContractType?fields=id,name&limit=100").then((result) => {
                    this.sysrefStaffContractTypes = result.data.data.items
                }).catch((error) => {
                    console.error(error);
                }).finally(() => {
                })
            },
            getSysrefDriverStates() {
                this.$http.get("api/v1/sysrefDriverState?fields=id,name&limit=100").then((result) => {
                    this.sysrefDriverStates = result.data.data.items
                }).catch((error) => {
                    console.error(error);
                }).finally(() => {
                })
            },
            isUserChanged() {
                this.data.username = null;
            }
        },
        mounted() {
            this.getSysrefGenders();
            this.getSysrefCountries();
            this.getSysrefStaffContractTypes();
            this.getSysrefDriverStates();
        }
    }
</script>
<style>

</style>
