<!-- Default application menu component -->
<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-navigation-drawer
        app
        light
        :mini-variant="localDrawer"
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

                                <v-slide-x-transition>
                                    <v-list-tile-title
                                            class="font-weight-light black--text text--accent-5"
                                            style="white-space: nowrap; text-overflow: ellipsis;"
                                    >

                                        {{ organizationTitle }}
                                    </v-list-tile-title>
                                </v-slide-x-transition>
                            </v-list-tile-content>
                        </v-list-tile>
                    </v-list>
                </v-toolbar>

                <div
                    v-for="menuItem in menuItems"
                    :key="`menu-` + menuItem.title"
                    class="mt-2"
                >
                    <v-subheader class="px-4">{{ menuItem.title }}</v-subheader>

                    <v-list-tile
                        v-for="item in menuItem.items"
                        :key="`menu-item-${item.title}`"
                        :to="item.to"
                        outline
                        active-class="grey lighten-2"
                        v-auth-permission="item.permission"
                    >

                        <v-tooltip
                            right
                            :disabled="!localDrawer"
                        >
                            <template v-slot:activator="{ on }">
                                <v-list-tile-action
                                    v-on="on"
                                    class="px-2"
                                >
                                    <v-icon color="grey darken-3">{{ item.icon }}</v-icon>
                                </v-list-tile-action>
                            </template>
                            <span>{{ item.title }}</span>
                        </v-tooltip>

                        <v-slide-x-transition>
                            <v-list-tile-title
                                v-if="!localDrawer"
                                v-text="item.title"
                                class="body-1 text--secondary px-2">

                            </v-list-tile-title>
                        </v-slide-x-transition>
                    </v-list-tile>

                    <v-divider class="my-3"></v-divider>
                </div>
            </v-list>
        </v-layout>
    </v-navigation-drawer>
</template>

<script>
    import {AuthPermission} from "manerp-vue-auth/src/directives/auth-permission/auth-permission";

    export default {
        name: "AppMenu",
        directive: {AuthPermission},
        props: {
            value: {
                type: Boolean,
                default: false,
            }
        },

        data() {
            return {
                localDrawer: this.value,

                menuItems: [
                    {
                        title: 'GENEL',
                        items: [
                            {
                                title: 'Genel Bakış',
                                icon: 'dashboard',
                                to: '/',
                                permission: "vehicle:show"

                            },
                            {
                                title: 'İnsan Kaynakları',
                                icon: 'group',
                                to: {name: require('@/modules/main/staff/route/index').routes.staffs},
                                permission: "staff:show"
                            },
                        ]
                    },

                    {
                        title: 'YÖNETİM',
                        items: [
                            {
                                title: 'Araç Yönetimi',
                                icon: 'directions_bus',
                                to: {name: require('@/modules/main/vehicle/route/index').routes.idle},
                                permission: "vehicle:show"
                            },
                            {
                                title: 'İş Yeri Yönetimi',
                                icon: 'business',
                                to: {name: require('@/modules/main/company/route/index').routes.customer},
                                permission: "company:show"

                            },
                            // {
                            //     title: 'Parametre Yönetimi',
                            //     icon: 'tune',
                            //     to: { name: 'parameters' }
                            // },
                            {
                                title: 'Sevkiyat Yönetimi',
                                icon: 'local_shipping',
                                to: {name: 'voyages.incomplete'},
                                permission: "voyage:show"
                            },
                            {
                                title: 'Sipariş Yönetimi',
                                icon: 'work',
                                to: {name: require('@/modules/main/order/route/index').routes.current},
                                permission: "order:show"
                            },
                            {
                                title: 'Şoför Yönetimi',
                                icon: 'account_circle',
                                to: {name: require('@/modules/main/driver/route/index').routes.idle},
                                permission: "driver:show"
                            }
                        ]
                    }
                ],

                mini: false
            }
        },

        computed: {
            // TODO ellipsis...
            organizationTitle () {
                return this.$store.state.shared.organization.name;
            }
        },

        methods: {
        },
        watch: {
            value() {
                this.localDrawer = this.value
            }
        }

    }
</script>

<style scoped>
</style>
