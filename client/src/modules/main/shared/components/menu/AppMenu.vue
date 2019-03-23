<!-- Default application menu component -->
<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-navigation-drawer
            app
            light
            floating
            width="240"
            class="grey lighten-4"
    >
        <v-layout fill-height>
            <v-list class="grow pt-0">
                <v-toolbar class="toolbar-shadow">
                    <v-list class="pa-0">

                        <v-list-tile avatar>

                            <v-list-tile-avatar>

                                <img src="../../../../../assets/logo.png">
                            </v-list-tile-avatar>

                            <v-list-tile-content>

                                <v-list-tile-title class="font-weight-light black--text text--accent-5">

                                    Bumerang Lojistik
                                </v-list-tile-title>
                            </v-list-tile-content>
                        </v-list-tile>
                    </v-list>
                </v-toolbar>

                <div
                    v-for="menuItem in menuItems"
                    :key="`menu-` + menuItem.title"
                    class="mt-2">

                    <v-subheader class="px-4">{{ menuItem.title }}</v-subheader>

                    <v-list-tile
                            v-for="item in menuItem.items"
                            :key="`menu-item-${item.title}`"
                            :to="item.to"
                            active-class="grey lighten-2">

                        <v-list-tile-action class="px-2">

                            <v-icon color="grey darken-3">{{ item.icon }}</v-icon>
                        </v-list-tile-action>

                        <v-list-tile-title
                                v-text="item.title"
                                class="body-1 text--secondary px-2">

                        </v-list-tile-title>
                    </v-list-tile>

                    <v-divider class="my-3"></v-divider>
                </div>
            </v-list>
        </v-layout>
    </v-navigation-drawer>
</template>

<script>
    export default {
        name: "AppMenu",
        data() {
            return {
                menuItems: [
                    {
                        title: 'GENEL',
                        items: [
                            {
                                title: 'Genel Bakış',
                                icon: 'dashboard',
                                to: '/'
                            },
                            {
                                title: 'Muhasebe',
                                icon: 'account_balance_wallet'
                            },
                            {
                                title: 'İnsan Kaynakları',
                                icon: 'group'
                            },
                            {
                                title: 'Firma Yönetimi',
                                icon: 'business'
                            }
                        ]
                    },

                    {
                        title: 'YÖNETİM',
                        items: [
                            {
                                title: 'Müşteri Yönetimi',
                                icon: 'business',
                                to: { name: 'customers' }
                            },
                            {
                                title: 'Sevkiyat Yönetimi',
                                icon: 'local_shipping',
                            },
                            {
                                title: 'Sipariş Yönetimi',
                                icon: 'add',
                                to: { name: 'orders.current' }
                            },
                            {
                                title: 'Şoför Yönetimi',
                                icon: 'person',
                                to: { name: 'drivers' }
                            },
                        ]
                    }
                ],

                mini: true
            }
        },
        props: {
            drawer: Boolean
        }
    }
</script>

<style scoped>
</style>
