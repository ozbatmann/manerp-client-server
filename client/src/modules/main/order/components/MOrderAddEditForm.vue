<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-dialog
            v-model="showDialog"
            max-width="750"
            persistent
            @keydown.enter.prevent="save"
            @keydown.esc.stop="close"
            class="overflow-hidden"
            lazy
            scrollable
    >
        <v-card>
            <v-card-title class="py-2">
                <v-flex>
                    <v-layout align-center>
                        <v-flex>
                            <h4 class="headline font-weight-light">Yeni Sipariş</h4>
                        </v-flex>
                        <v-spacer></v-spacer>
                        <v-flex shrink>
                            <v-btn
                                    icon
                                    class="mx-0"
                            >
                                <v-icon>close</v-icon>
                            </v-btn>
                        </v-flex>
                    </v-layout>
                </v-flex>
            </v-card-title>
            <v-divider></v-divider>
            <v-card-text>

                <v-flex>
                    <v-layout wrap>
                        <v-flex xs6 pr-2>
                            <v-text-field
                                    solo
                                    flat
                                    v-validate="'required'"
                                    :error-messages="errors.collect('firstName')"
                                    label="Sipariş Tanımı"
                                    name="firstName"
                                    :counter="30"
                                    maxlength="30"
                                    background-color="grey lighten-4"
                                    color="green accent-2"
                                    class="m-settings__label"
                            ></v-text-field>
                        </v-flex>
                        <v-flex xs6 pl-2>
                            <v-select
                                    solo
                                    flat
                                    v-validate="'required'"
                                    :error-messages="errors.collect('firstName')"
                                    label="Gelir Tipi"
                                    name="firstName"
                                    :counter="30"
                                    maxlength="30"
                                    background-color="grey lighten-4"
                                    color="green accent-2"
                                    class="m-settings__label"
                            ></v-select>
                        </v-flex>
                        <v-flex xs6 pr-2>
                            <v-text-field
                                    solo
                                    flat
                                    v-validate="'required'"
                                    :error-messages="errors.collect('firstName')"
                                    label="Fatura Numarası"
                                    name="firstName"
                                    :counter="30"
                                    maxlength="30"
                                    background-color="grey lighten-4"
                                    color="green accent-2"
                                    class="m-settings__label"
                            ></v-text-field>
                        </v-flex>
                        <v-flex xs6 pl-2>
                            <v-select
                                    solo
                                    flat
                                    v-validate="'required'"
                                    :error-messages="errors.collect('firstName')"
                                    label="Müşteri İş Yeri"
                                    name="firstName"
                                    :counter="30"
                                    maxlength="30"
                                    background-color="grey lighten-4"
                                    color="green accent-2"
                                    class="m-settings__label"
                            ></v-select>
                        </v-flex>
                        <v-flex xs6 pr-2>
                            <v-select
                                    solo
                                    flat
                                    v-validate="'required'"
                                    :error-messages="errors.collect('firstName')"
                                    label="Bayi"
                                    name="firstName"
                                    :counter="30"
                                    maxlength="30"
                                    background-color="grey lighten-4"
                                    color="green accent-2"
                                    class="m-settings__label"
                            ></v-select>
                        </v-flex>
                        <v-flex xs6 pl-2>
                            <v-text-field
                                    solo
                                    flat
                                    v-validate="'required'"
                                    :error-messages="errors.collect('firstName')"
                                    label="İş Emri Numarası"
                                    name="firstName"
                                    :counter="30"
                                    maxlength="30"
                                    background-color="grey lighten-4"
                                    color="green accent-2"
                                    class="m-settings__label"
                            ></v-text-field>
                        </v-flex>
                    </v-layout>
                    <v-layout
                            v-if="dealers.length"
                            wrap
                            align-center
                    >
                        <v-flex
                                grow
                                pr-2
                        >
                            <v-divider></v-divider>
                        </v-flex>
                        <v-flex
                                shrink
                                px-2
                                py-3
                        >
                            <h4 class="subheading text-xs-center">Gidilecek Bayiler</h4>
                        </v-flex>
                        <v-flex
                                grow
                                pl-2
                        >
                            <v-divider></v-divider>
                        </v-flex>

                        <v-flex pt-2 xs12>
                            <v-data-table
                                    :headers="headers"
                                    :items="dealers"
                                    hide-actions
                                    style="border: 1px solid #f4f4f4"
                            >
                                <template v-slot:items="props">
                                    <td>{{ props.index + 1 }}.</td>
                                    <td class="text-xs-left">{{ props.item.name }}</td>
                                    <td class="text-xs-left">{{ props.item.city }}</td>
                                    <td class="text-xs-left">{{ props.item.district }}</td>
                                    <td class="text-xs-left">{{ props.item.address }}</td>
                                    <td class="text-xs-right">
                                        <v-btn
                                                icon
                                                color="red--text"
                                                @click="removeDealer(props.item.id)"
                                        >
                                            <v-icon size="16">close</v-icon>
                                        </v-btn>
                                    </td>
                                </template>
                            </v-data-table>
                        </v-flex>
                    </v-layout>
                </v-flex>
            </v-card-text>
            <v-divider></v-divider>
            <v-card-actions>

                <v-layout
                        column
                        py-1
                >
                    <v-flex shrink>
                        <v-layout px-2>
                            <v-spacer></v-spacer>
                            <v-flex shrink>
                                <v-btn
                                        depressed
                                >
                                    HEPSİNİ TEMİZLE
                                </v-btn>

                                <v-btn
                                        depressed
                                        color="deep-purple white--text"
                                >
                                    KAYDET
                                </v-btn>
                            </v-flex>
                        </v-layout>
                    </v-flex>
                </v-layout>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script>
    const staffModel = require('@/modules/main/staff/models/staff-model-add-edit').default;

    export default {
        name: 'MOrderAddEditForm',
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
                sysrefStaffContractTypes: [],

                headers: [
                    {
                        text: '#',
                        align: 'left',
                        sortable: false,
                        value: 'order'
                    },
                    {
                        text: 'Bayi Adı',
                        align: 'left',
                        sortable: false,
                        value: 'name'
                    },
                    {
                        text: 'İl',
                        align: 'left',
                        sortable: false,
                        value: 'city'
                    },
                    {
                        text: 'İlçe',
                        align: 'left',
                        sortable: false,
                        value: 'district'
                    },
                    {
                        text: 'Açık Adres',
                        align: 'left',
                        sortable: false,
                        value: 'address'
                    },
                    {
                        text: '',
                        align: 'center',
                        sortable: false,
                        value: 'action'
                    }
                ],

                dealers: [
                    {
                        id: 0,
                        name: 'Beşevler Bim',
                        city: 'Ankara',
                        district: 'Beşevler',
                        address: 'Gazi Mah. No: 15, Beşevler'
                    },
                    {
                        id: 1,
                        name: 'Beşevler Bim',
                        city: 'Ankara',
                        district: 'Beşevler',
                        address: 'Gazi Mah. No: 15, Beşevler'
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

            removeDealer(id) {
                this.dealers = this.dealers.filter(dealer => {
                    return dealer.id !== id;
                });
            },

            // data retrieve methods
            getSysrefGenders() {
                let self = this;

                this.$http.get("api/v1/sysrefGender?fields=id,name").then((result) => {
                    self.sysrefGenders = result.data.data.items
                }).catch((error) => {
                    console.error(error);
                }).finally(() => {

                })
            },
            getSysrefCountries() {
                let self = this;

                this.$http.get("api/v1/sysrefCountry?fields=id,name").then((result) => {
                    self.sysrefCountries = result.data.data.items
                }).catch((error) => {
                    console.error(error);
                }).finally(() => {

                })
            },
            getSysrefCities() {
                let self = this;

                this.data.sysrefCity = null;
                this.$http.get("api/v1/sysrefCity?limit=81&fields=id,name&countryId="
                    + this.data.sysrefCountry.id).then((result) => {
                    self.sysrefCities = result.data.data.items
                }).catch((error) => {
                    console.error(error);
                }).finally(() => {

                })
            },
            getSysrefDistricts() {
                let self = this;

                this.data.sysrefDistrict = null;
                this.$http.get("api/v1/sysrefDistrict?limit=500&fields=id,name&cityId=" + this.data.sysrefCity.id).then((result) => {
                    self.sysrefDistricts = result.data.data.items
                }).catch((error) => {
                    console.error(error);
                }).finally(() => {

                })
            },
            getRefStaffTitles() {
                let self = this;

                this.$http.get("api/v1/refStaffTitle?fields=id,name&limit=100").then((result) => {
                    self.refStaffTitles = result.data.data.items
                }).catch((error) => {
                    console.error(error);
                }).finally(() => {

                })
            },
            getSysrefStaffContractTypes() {
                let self = this;

                this.$http.get("api/v1/sysrefStaffContractType?fields=id,name&limit=100").then((result) => {
                    self.sysrefStaffContractTypes = result.data.data.items
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

<style scoped>

</style>
