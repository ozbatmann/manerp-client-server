<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div>
        <m-data-table
                :headers="headers"
                :items="orders"
                expand
        >
            <template v-slot:header>
                <m-data-table-action
                        title="yeni araç"
                        disabled
                ></m-data-table-action>
            </template>

            <!-- Template for expandable row -->
            <template v-slot:expand="props">
                <v-card flat class="py-2 px-2 text-xs-center transition"
                        style="border-bottom: 1px solid rgba(0, 0, 0, .12); margin: -1px;
                                border-top: 1px solid rgba(0, 0, 0, .12);">
                    <table style="width: 100%;">
                        <thead>
                        <tr style="height: 48px !important; line-height: 48px !important;">
                            <th colspan="1">#</th>
                            <th colspan="2">Bayi</th>
                            <th colspan="2">İl</th>
                            <th colspan="2">İlçe</th>
                            <th colspan="3">Açık Adres</th>
                        </tr>
                        </thead>
                        <tbody class="text-xs-center">
                        <tr
                                v-for="(route, index) in props.bind.item.routes"
                                :key="`data-table-route-item-${index}`"
                        >
                            <td colspan="1">{{index + 1}}.</td>
                            <td colspan="2" class="green--text text--accent-3">{{route.name}}</td>
                            <td colspan="2">{{route.city}}</td>
                            <td colspan="2">{{route.district}}</td>
                            <td colspan="3">{{route.address}}</td>
                        </tr>
                        </tbody>
                    </table>
                </v-card>
            </template>
        </m-data-table>

        <v-snackbar
                v-model="snackbar.active"
                color="grey darken-4"
                :class="snackbar.textColor"
                top
                right
        >
            {{ snackbar.text }}
            <v-btn
                    dark
                    flat
                    @click="snackbar.active = false"
            >
                geri al
            </v-btn>
        </v-snackbar>
    </div>
</template>

<script>
    import MDataTable from '@/modules/main/shared/components/data/components/MDataTable'
    import MDataTableAction from "@/modules/main/shared/components/data/components/MDataTableAction"

    const orderModel = require('@/modules/main/order/models/order-model').default;

    export default {
        name: "MVehicleManagementCompleted",

        components: {
            MDataTable,
            MDataTableAction,
        },

        data() {
            return {
                headers: [
                    {
                        text: 'ıd',
                        sortable: true,
                        value: orderModel.id,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'tipi',
                        sortable: true,
                        value: orderModel.type,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'müşteri firma',
                        sortable: true,
                        value: orderModel.customer,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'yön',
                        sortable: true,
                        value: orderModel.direction,
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'rota',
                        sortable: true,
                        value: 'route',
                        toggleable: false,
                        expandable: true,
                        htmlSource: true,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: '',
                        sortable: true,
                        value: orderModel.contractual.routes,
                        toggleable: false,
                        show: false
                    }
                ],

                orders: [],

                snackbar: false
            }
        },

        mounted() {
            for (let i = 0; i < 15; i++) {
                let order = {
                    id: `ORD-${i}`,
                    type: 'Sözleşmeli',
                    customer: 'Pinar Gida A.Ş',
                    direction: 'Gidiş/Dönüş',
                    route: `Sevenler Gıda
                            <i class="v-icon material-icons green--text text--accent-3"
                                style="font-size: 16px;">arrow_forward</i> Akmerler Bim`,
                    routes: [
                        {
                            name: 'Sevenler Gıda',
                            city: 'Ankara',
                            district: 'Çankaya',
                            address: 'Beytepe Mah., 1800.cd, No: 6, D: 6'
                        },

                        {
                            name: 'Akmerler Bim',
                            city: 'Trabzon',
                            district: 'Çobanlı',
                            address: 'Talatdere Mah., 2.cd, No: 12'
                        },

                        {
                            name: 'Akmerler Bim',
                            city: 'Trabzon',
                            district: 'Çobanlı',
                            address: 'Talatdere Mah., 2.cd, No: 12'
                        },

                        {
                            name: 'Akmerler Bim',
                            city: 'Trabzon',
                            district: 'Çobanlı',
                            address: 'Talatdere Mah., 2.cd, No: 12'
                        }
                    ]
                };

                this.orders.push(order)
            }
        }
    }
</script>

<style scoped>

</style>
