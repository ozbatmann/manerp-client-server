<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <m-data-table-add-edit-form
        v-model="show"
        title="sipariş ekle"
        :editing="isEdit"
        @clear="clear"
        @edit="edit"
        @save="save"
        :hideSaveButton="completedOrder"
        :hideClearButton="completedOrder"
    >
        <template v-slot:form>
            <v-layout wrap>
                <v-flex xs6 pr-2>
                    <v-text-field
                        :disabled="completedOrder"
                        solo
                        flat
                        v-validate="'required'"
                        :error-messages="errors.collect('name')"
                        v-model="data.name"
                        label="Sipariş Tanımı"
                        name="name"
                        :counter="50"
                        maxlength="50"
                        background-color="grey lighten-4"
                        color="green accent-2"
                        class="m-settings__label"
                    ></v-text-field>
                </v-flex>
                <v-flex xs6 pl-2>
                    <v-combobox
                        :disabled="completedOrder"
                        v-validate="'required'"
                        :error-messages="errors.collect('sysrefRevenueType')"
                        v-model="data.sysrefRevenueType"
                        :return-object="true"
                        :items="sysrefRevenueTypes"
                        item-value="id"
                        label="Gelir Tipi"
                        item-text="name"
                        name="sysrefRevenueType"
                        background-color="grey lighten-4"
                        color="green accent-2"
                        class="m-settings__label"
                        solo
                        flat
                    ></v-combobox>
                </v-flex>
                <v-flex xs6 pr-2>
                    <v-text-field
                        :disabled="completedOrder"
                        solo
                        flat
                        v-model="data.billingNo"
                        v-validate="'required'"
                        :error-messages="errors.collect('billingNo')"
                        label="Fatura Numarası"
                        name="billingNo"
                        :counter="50"
                        maxlength="50"
                        background-color="grey lighten-4"
                        color="green accent-2"
                        class="m-settings__label"
                    ></v-text-field>
                </v-flex>
                <v-flex xs6 pl-2>
                    <v-text-field
                        :disabled="completedOrder"
                        solo
                        flat
                        label="İş Emri Numarası"
                        v-model="data.workOrderNo"
                        name="workOrderNo"
                        :counter="30"
                        maxlength="30"
                        background-color="grey lighten-4"
                        color="green accent-2"
                        class="m-settings__label"
                    ></v-text-field>
                </v-flex>
                <v-flex xs6 pr-2>
                    <v-combobox
                        :disabled="completedOrder"
                        v-on:change="getVendorsByCompanyId"
                        solo
                        flat
                        :return-object="true"
                        v-validate="'required'"
                        :error-messages="errors.collect('customer')"
                        v-model="data.company"
                        :items="customers"
                        item-value="id"
                        item-text="title"
                        label="Müşteri İş Yeri"
                        name="customer"
                        background-color="grey lighten-4"
                        color="green accent-2"
                        class="m-settings__label"
                    ></v-combobox>
                </v-flex>
                <v-flex xs6 pl-2>
                    <v-combobox
                        :disabled="completedOrder"
                        solo
                        flat
                        v-validate="'required'"
                        :error-messages="errors.collect('vendor')"
                        v-on:change="selectVendor"
                        :items="vendors"
                        v-model="selectedVendor"
                        label="Bayi"
                        item-value="id"
                        item-text="title"
                        name="vendor"
                        background-color="grey lighten-4"
                        color="green accent-2"
                        class="m-settings__label"
                        required
                    ></v-combobox>
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
                            <td class="text-xs-left">{{ props.item.title }}</td>
                            <!--                            <td class="text-xs-left">{{ props.item.city }}</td>-->
                            <!--                            <td class="text-xs-left">{{ props.item.district }}</td>-->
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
        </template>
    </m-data-table-add-edit-form>
</template>

<script>
    import MDataTableAddEditForm from "../../shared/components/data/components/MDataTableAddEditForm";

    const orderModel = require('@/modules/main/order/models/order-model-add-edit').default;

    export default {
        name: 'MOrderAddEditForm',
        components: {MDataTableAddEditForm},
        props: {completedOrder: {type: Boolean, default: false}},
        data() {
            return {
                show: false,
                isEdit: false,
                data: JSON.parse(JSON.stringify(orderModel)),
                customers: [],
                sysrefRevenueTypes: [],
                sysrefOrderStates: [],
                vendors: [],
                selectedVendor: null,

                headers: [
                    {
                        text: '#',
                        align: 'left',
                        sortable: false,
                        value: 'order'
                    },
                    {
                        text: 'Bayi Unvanı',
                        align: 'left',
                        sortable: false,
                        value: 'title'
                    },
                    // {
                    //     text: 'İl',
                    //     align: 'left',
                    //     sortable: false,
                    //     value: 'city'
                    // },
                    // {
                    //     text: 'İlçe',
                    //     align: 'left',
                    //     sortable: false,
                    //     value: 'district'
                    // },
                    {
                        text: 'Açık Adres',
                        align: 'center',
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
                dealers: []
            }
        },
        methods: {
            open(data) {
                this.show = true;
                if (data) {
                    this.data = data;
                    if (this.data.selectedVendors) {
                        this.dealers = this.data.selectedVendors;
                    }
                    this.getVendorsByCompanyIdForOpen(this.data.company.id);
                    this.isEdit = true;
                } else {
                    this.clear();
                }
            },
            close() {
                this.show = false;
            },
            save() {
                this.data.selectedVendors = this.getSelectedVendorIds();
                this.$validator.validateAll().then((result) => {
                    if (result) {
                        if (this.data.selectedVendors.length > 1) {
                            this.$emit("save", this.data);
                            this.close();
                        }
                    }
                });
            },
            edit() {
                this.$validator.validateAll().then((result) => {
                    if (result) {
                        this.data.selectedVendors = this.dealers;
                        if (this.data.selectedVendors.length > 1) {
                            this.$emit("edit", this.data);
                            this.close();
                        }
                    }
                });
            },
            clear() {
                this.data = JSON.parse(JSON.stringify(orderModel));
                this.selectedVendor = null;
                this.dealers = [];
                this.$validator.reset();
                this.isEdit = false;
            },

            removeDealer(id) {
                this.dealers = this.dealers.filter(dealer => {
                    return dealer.id !== id;
                });
            },

            // data retrieve methods
            getCustomerCompanies() {
                this.$http.get("api/v1/company?fields=id,title&limit=100&sysrefCompanyTypeCode=CST").then((result) => {
                    this.customers = result.data.data.items
                }).catch((error) => {
                    console.error(error);
                }).finally(() => {
                })
            },
            getSysrefRevenueTypes() {
                this.$http.get("api/v1/sysrefRevenueType?fields=id,name&limit=100").then((result) => {
                    this.sysrefRevenueTypes = result.data.data.items
                }).catch((error) => {
                    console.error(error);
                }).finally(() => {
                })
            },
            getSysrefOrderStates() {
                this.$http.get("api/v1/sysrefOrderState?fields=id,name&limit=100").then((result) => {
                    this.sysrefOrderStates = result.data.data.items
                }).catch((error) => {
                    console.error(error);
                }).finally(() => {
                })
            },
            getVendorsByCompanyId() {
                this.dealers = [];
                this.$http.get("api/v1/vendor?fields=id,title,address&limit=100&companyId=" + this.data.company.id).then((result) => {
                    this.vendors = result.data.data.items;
                }).catch((error) => {
                    console.error(error);
                }).finally(() => {
                })
            },
            getVendorsByCompanyIdForOpen() {
                this.$http.get("api/v1/vendor?fields=id,title,address&limit=100&companyId=" + this.data.company.id).then((result) => {
                    this.vendors = result.data.data.items;
                }).catch((error) => {
                    console.error(error);
                }).finally(() => {
                })
            },
            selectVendor(item) {
                if (this.dealers.indexOf(item) === -1) {
                    this.dealers.push(item);
                }
            },
            getSelectedVendorIds() {
                let list = [];
                this.dealers.forEach(dealer => {
                    list.push(dealer.id);
                });
                return list;
            }
        },
        mounted() {
            this.getCustomerCompanies();
            this.getSysrefRevenueTypes();
            this.getSysrefOrderStates();
            const dict = {
                custom: {
                    order: {
                        description: {
                            required: 'Lütfen iş tanımını boş bırakmayın'
                        },

                        sysrefRevenueType: {
                            required: 'Lütfen gelir tipini seçin'
                        },

                        billingNo: {
                            required: 'Lütfen fatura numarasını boş bırakmayın'
                        },

                        customer: {
                            required: 'Lütfen müşteri firmayı seçin'
                        },

                        dealer: {
                            required: 'Lütfen gidilecek bayileri seçin'
                        },

                        workOrderNo: {
                            required: 'Lütfen iş emri numarasını boş bırakmayın'
                        }
                    }
                }
            };

            this.$validator.localize('en', dict);
        }
    }
</script>

<style scoped>

</style>
