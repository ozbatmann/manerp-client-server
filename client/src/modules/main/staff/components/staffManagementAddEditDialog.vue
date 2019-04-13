<template>
    <v-dialog v-model="showDialog" persistent max-width="80%" scrollable>
        <!-- <v-btn slot="activator" color="primary" dark>Open Dialog</v-btn> -->
        <v-card class="pa-3">
            <v-card-title>
        <span v-if="isEdit"
              class="headline">Güncelle</span>
                <span v-else
                      class="headline">Yeni Personel</span>
            </v-card-title>
            <v-card-text style="max-height: 90%;">
                <v-container grid-list-md pa-0>
                    <v-layout wrap>
                        <v-flex xs12>
                            <v-text-field v-validate="'required'"
                                          :error-messages="errors.collect('firstName')"
                                          v-model="data.firstName"
                                          label="Ad"
                                          name="firstName"
                                          :counter="30"
                                          maxlength="30"
                                          background-color="grey lighten-4"
                                          color="green accent-2"
                                          full-width>
                            </v-text-field>
                            <v-text-field v-model="data.middleName"
                                          :counter="30"
                                          maxlength="30"
                                          label="İkinci Ad"
                                          name="middleName"
                                          background-color="grey lighten-4"
                                          color="green accent-2"
                                          full-width>
                            </v-text-field>
                            <v-text-field v-validate="'required'"
                                          :error-messages="errors.collect('lastName')"
                                          v-model="data.lastName"
                                          :counter="50"
                                          maxlength="50"
                                          label="Soyad"
                                          name="lastName"
                                          background-color="grey lighten-4"
                                          color="green accent-2"
                                          full-width>
                            </v-text-field>
                            <v-text-field v-validate="'required'"
                                          :error-messages="errors.collect('tcIdNumber')"
                                          v-model="data.tcIdNumber"
                                          :counter="11"
                                          maxlength="11"
                                          label="TC Kimlik Numarası"
                                          name="tcIdNumber"
                                          background-color="grey lighten-4"
                                          color="green accent-2"
                                          full-width>
                            </v-text-field>
                            <v-combobox v-model="data.sysrefGender"
                                        :return-object="true"
                                        :items="sysrefGenders"
                                        item-value="id"
                                        label="Cinsiyet"
                                        item-text="name"
                                        name="sysrefGender"
                                        background-color="grey lighten-4"
                                        color="green accent-2"
                                        full-width>
                            </v-combobox>
                            <v-text-field v-validate="'required'"
                                          mask="phone"
                                          :error-messages="errors.collect('gsmNo')"
                                          v-model="data.gsmNo"
                                          :counter="11"
                                          maxlength="11"
                                          label="GSM"
                                          name="gsmNo"
                                          background-color="grey lighten-4"
                                          color="green accent-2"
                                          full-width>
                            </v-text-field>
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
                            <v-combobox v-validate="'required'"
                                        :error-messages="errors.collect('refStaffTitle')"
                                        v-model="data.refStaffTitle"
                                        :return-object="true"
                                        :items="refStaffTitles"
                                        item-value="id"
                                        label="Unvan"
                                        item-text="name"
                                        name="refStaffTitle"
                                        background-color="grey lighten-4"
                                        color="green accent-2"
                                        full-width>
                            </v-combobox>
                            <v-combobox v-validate="'required'"
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
                                        full-width>
                            </v-combobox>
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
                            <v-textarea box
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
    </v-dialog>
</template>

<script>

    const staffModel = require('@/modules/main/staff/models/staff-model-add-edit').default;

    export default {
        data() {
            return {
                showDialog: false,
                isEdit: false,
                data: JSON.parse(JSON.stringify(staffModel)),
                sysrefGenders: [],
                sysrefCountries: [],
                sysrefCities: [],
                sysrefDistricts: [],
                refStaffTitles: [],
                sysrefStaffContractTypes: []
            }
        },
        methods: {
            open(data) {
                this.showDialog = true;
                if (data) {
                    console.log(data);
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
                this.data = JSON.parse(JSON.stringify(staffModel));
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
            getRefStaffTitles() {
                this.$http.get("api/v1/refStaffTitle?fields=id,name&limit=100").then((result) => {
                    this.refStaffTitles = result.data.data.items
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
            }
        },
        mounted() {
            this.getSysrefGenders();
            this.getSysrefCountries();
            this.getRefStaffTitles();
            this.getSysrefStaffContractTypes();
        }
    }
</script>