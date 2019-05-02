<template>
    <v-dialog v-model="showDialog" persistent max-width="70%" scrollable>
        <!-- <v-btn slot="activator" color="primary" dark>Open Dialog</v-btn> -->
        <v-card class="pa-3">
            <v-card-title>
        <span v-if="isEdit"
              class="headline">Güncelle</span>
                <span v-else
                      class="headline">Yeni Sevkiyat</span>
            </v-card-title>
            <v-card-text style="max-height: 90%;">
                <v-container grid-list-md pa-0>
                    <v-layout wrap>
                        <v-flex xs12>
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

    const voyageModel = require('@/modules/main/voyage/models/voyage-model-add-edit').default;

    export default {
        data() {
            return {
                showDialog: false,
                isEdit: false,
                data: JSON.parse(JSON.stringify(voyageModel)),
                vehicles: [],
                drivers: [],
                sysrefTransportationTypes: [],
                sysrefVoyageDirections: [],
                sysrefDeliveryStatuses: []
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
            getVehicles() {
                this.$http.get("api/v1/vehicle?limit=100&fields=id,plateNumber&vehicleStateCode=IDLE").then((result) => {
                    this.vehicles = result.data.data.items
                    console.log("vehicles: ", this.vehicles)
                }).catch((error) => {
                    console.error(error);
                }).finally(() => {

                })
            },
            getDrivers() {
                this.$http.get("api/v1/driver?fields=id,fullName&driverStateCode=IDLE").then((result) => {
                    this.drivers = result.data.data.items
                    console.log(this.drivers)

                }).catch((error) => {
                    console.error(error);
                }).finally(() => {

                })
            },
            getSysrefTransportationTypes() {
                this.$http.get("api/v1/sysrefTransportationType?fields=id,name").then((result) => {
                    this.sysrefTransportationTypes = result.data.data.items
                    console.log(this.sysrefTransportationTypes)


                }).catch((error) => {
                    console.error(error);
                }).finally(() => {

                })
            },
            getSysrefVoyageDirections() {
                this.$http.get("api/v1/sysrefVoyageDirection?fields=id,name").then((result) => {
                    this.sysrefVoyageDirections = result.data.data.items
                    console.log(this.sysrefVoyageDirections)

                }).catch((error) => {
                    console.error(error);
                }).finally(() => {

                })
            },
            getSysrefDeliveryStatuses() {
                this.$http.get("api/v1/sysrefDeliveryStatus?fields=id,name").then((result) => {
                    this.sysrefDeliveryStatuses = result.data.data.items
                    console.log(this.sysrefDeliveryStatuses)

                }).catch((error) => {
                    console.error(error);
                }).finally(() => {

                })
            }
        },
        mounted() {
            this.getVehicles();
            this.getDrivers();
            this.getSysrefTransportationTypes();
            this.getSysrefVoyageDirections();
            this.getSysrefDeliveryStatuses();
        }
    }
</script>
<style>

</style>