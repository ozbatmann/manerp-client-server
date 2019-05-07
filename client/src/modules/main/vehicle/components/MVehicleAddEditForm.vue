<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <m-data-table-add-edit-form
        v-model="showDialog"
        title="Yeni Araç"
        :editing="isEdit"
        @clear="clear"
        @edit="edit"
        @save="save"
        :hideSaveButton="onVoyageVehicle"
        :hideClearButton="onVoyageVehicle"
    >
        <template v-slot:form>
            <v-layout wrap>
                <v-flex
                    xs6
                    pr-2
                >
                    <!-- Plate -->
                    <v-text-field
                        :disabled="onVoyageVehicle"
                        v-validate="'required'"
                        :error-messages="errors.collect('plate')"
                        v-model="data.plateNumber"
                        :counter="11"
                        maxlength="11"
                        label="Plaka"
                        name="plate"
                        background-color="grey lighten-4"
                        color="green accent-2"
                        class="m-settings__label"
                        solo
                        flat
                    ></v-text-field>
                </v-flex>
                <v-flex
                    xs6
                    pl-2
                >
                    <!-- Plate -->
                    <v-text-field
                        :disabled="onVoyageVehicle"
                        v-validate="'required'"
                        :error-messages="errors.collect('brand')"
                        v-model="data.brand"
                        :counter="15"
                        maxlength="15"
                        label="Marka"
                        name="brand"
                        background-color="grey lighten-4"
                        color="green accent-2"
                        class="m-settings__label"
                        solo
                        flat
                    ></v-text-field>
                </v-flex>
                <v-flex
                    xs6
                    pr-2
                >
                    <!-- Plate -->
                    <v-text-field
                        :disabled="onVoyageVehicle"
                        v-validate="'required'"
                        :error-messages="errors.collect('fleetCardNumber')"
                        v-model="data.fleetCardNumber"
                        :counter="20"
                        maxlength="20"
                        label="Filo Kart Numarası"
                        name="fleetCardNumber"
                        background-color="grey lighten-4"
                        color="green accent-2"
                        class="m-settings__label"
                        solo
                        flat
                    ></v-text-field>
                </v-flex>
                <v-flex
                    xs6
                    pl-2
                >
                    <!-- Plate -->
                    <v-text-field
                        :disabled="onVoyageVehicle"
                        v-validate="'required'"
                        :error-messages="errors.collect('km')"
                        v-model="data.km"
                        :counter="15"
                        maxlength="15"
                        label="Kilometre"
                        name="km"
                        background-color="grey lighten-4"
                        color="green accent-2"
                        class="m-settings__label"
                        solo
                        flat
                    ></v-text-field>
                </v-flex>
                <v-flex
                    xs6
                    pr-2
                >
                    <!-- Plate -->
                    <v-combobox
                        :disabled="onVoyageVehicle"
                        cache-items
                        hide-no-data
                        v-validate="'required'"
                        :error-messages="errors.collect('workingArea')"
                        v-model="data.refWorkingArea"
                        :return-object="true"
                        :items="refWorkingAreaList"
                        item-value="id"
                        item-text="name"
                        label="Çalışma Bölgesi"
                        name="workingArea"
                        background-color="grey lighten-4"
                        color="green accent-2"
                        class="m-settings__label"
                        solo
                        flat
                    ></v-combobox>
                </v-flex>
                <v-flex
                    xs6
                    pl-2
                >
                    <!-- Plate -->
                    <v-combobox
                        :disabled="onVoyageVehicle"
                        cache-items
                        v-validate="'required'"
                        :error-messages="errors.collect('type')"
                        v-model="data.sysrefVehicleType"
                        :return-object="true"
                        :items="sysrefVehicleTypeList"
                        item-value="id"
                        item-text="name"
                        label="Tipi"
                        name="type"
                        background-color="grey lighten-4"
                        color="green accent-2"
                        class="m-settings__label"
                        solo
                        flat
                    ></v-combobox>
                </v-flex>
                <v-flex
                    xs6
                    pr-2
                >
                    <!-- Plate -->
                    <v-combobox
                        :disabled="onVoyageVehicle"
                        cache-items
                        v-validate="'required'"
                        v-on:change="ownerChanged"
                        :error-messages="errors.collect('ownerType')"
                        v-model="data.sysrefVehicleOwner"
                        :return-object="true"
                        :items="sysrefVehicleOwnerList"
                        item-value="id"
                        item-text="name"
                        label="Mülk Tipi"
                        name="ownerType"
                        background-color="grey lighten-4"
                        color="green accent-2"
                        class="m-settings__label"
                        solo
                        flat
                    ></v-combobox>
                </v-flex>
                <v-flex
                    xs6
                    pl-2
                    v-if="isRental"
                >
                    <!-- Plate -->
                    <!-- TODO burasi tedarikci firma olacak - show only if sysrefVehicleOwner is kiralik -->
                    <v-combobox
                        :disabled="onVoyageVehicle"
                        cache-items
                        v-validate="'required'"
                        :error-messages="errors.collect('supplier')"
                        v-model="data.vehicleOwnerFullName"
                        :return-object="true"
                        :items="sysrefSupplierList"
                        item-value="id"
                        item-text="title"
                        label="Tedarikçi Firma"
                        name="supplier"
                        background-color="grey lighten-4"
                        color="green accent-2"
                        class="m-settings__label"
                        solo
                        flat
                    ></v-combobox>
                </v-flex>
                <v-flex
                    xs6
                    :class="[ !isRental ? 'pl-2' : 'pr-2' ]"
                >
                    <!-- Plate -->
                    <v-text-field
                        :disabled="onVoyageVehicle"
                        v-validate="'required'"
                        :error-messages="errors.collect('kgs')"
                        v-model="data.kgsNo"
                        :counter="15"
                        maxlength="15"
                        label="KGS Numarası"
                        name="kgs"
                        background-color="grey lighten-4"
                        color="green accent-2"
                        class="m-settings__label"
                        solo
                        flat
                    ></v-text-field>
                </v-flex>
                <v-flex
                    xs6
                    :class="[ !isRental ? 'pr-2' : 'pl-2' ]"
                >
                    <!-- Plate -->
                    <v-text-field
                        :disabled="onVoyageVehicle"
                        v-validate="'required'"
                        :error-messages="errors.collect('ogs')"
                        v-model="data.ogsNo"
                        :counter="15"
                        maxlength="15"
                        label="OGS Numarası"
                        name="ogs"
                        background-color="grey lighten-4"
                        color="green accent-2"
                        class="m-settings__label"
                        solo
                        flat
                    ></v-text-field>
                </v-flex>
                <v-flex
                    xs6
                    pr-2
                >
                    <!-- Plate -->
                    <v-checkbox
                        :disabled="onVoyageVehicle"
                        v-model="data.isDualRegime"
                        label="Çift Rejim"
                        name="dualRegime"
                        color="green accent-2"
                        class="m-settings__label"
                        solo
                        flat
                    ></v-checkbox>
                </v-flex>
            </v-layout>
        </template>
    </m-data-table-add-edit-form>
</template>

<script>
    import MDataTableAddEditForm from "../../shared/components/data/components/MDataTableAddEditForm";

    const vehicleModel = require('../models/vehicle-add-edit-model').default;

    export default {
        name: "MVehicleAddEditForm",
        components: {MDataTableAddEditForm},
        props: {onVoyageVehicle: {type: Boolean, default: false}},
        data() {
            return {
                showDialog: false,
                isEdit: false,
                data: JSON.parse(JSON.stringify(vehicleModel)),
                sysrefVehicleOwnerList: null,
                sysrefVehicleTypeList: null,
                sysrefSupplierList: null,
                refWorkingAreaList: null,
            }
        },

        computed: {
            isRental() {
                return this.data.sysrefVehicleOwner && this.data.sysrefVehicleOwner.name !== 'Özmal'
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
                this.data = JSON.parse(JSON.stringify(vehicleModel));
                this.$validator.reset();
                this.isEdit = false;
            },

            getSysrefVehicleOwnerList() {
                let self = this;

                this.$http.get("api/v1/sysrefVehicleOwner").then((result) => {
                    self.sysrefVehicleOwnerList = result.data.data.items;
                }).catch((error) => {
                    console.error(error);
                })
            },
            getSysrefVehicleTypeList() {
                let self = this;

                this.$http.get("api/v1/sysrefVehicleType").then((result) => {
                    self.sysrefVehicleTypeList = result.data.data.items;
                }).catch((error) => {
                    console.error(error);
                })
            },
            getRefWorkingAreaList() {
                let self = this;

                this.$http.get('api/v1/refWorkingArea').then((result) => {
                    self.refWorkingAreaList = result.data.data.items;
                }).catch((error) => {
                    console.log(error);
                })
            },

            getSupplierList() {
                let self = this;

                this.$http.get('api/v1/company?sysrefCompanyTypeCode=SPL').then((result) => {
                    self.sysrefSupplierList = result.data.data.items;
                }).catch((error) => {
                    console.log(error);
                })
            },
            ownerChanged() {
                console.log(this.data.sysrefVehicleOwner);
            }
        },

        mounted() {
            this.getSysrefVehicleOwnerList();
            this.getSysrefVehicleTypeList();
            this.getRefWorkingAreaList();
            this.getSupplierList();
        }
    }
</script>

<style scoped>

</style>
