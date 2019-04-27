<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div>
        <v-layout>
            <v-flex>
                <v-alert
                        :value="true"
                        type="info"
                        dismissible
                >
                    Yeni kullanıcı ekleyin, kullanıcı izinlerini yönetin, yeni izin ekleyin ve kullanıcıları yetkilendirin.
                </v-alert>
            </v-flex>
        </v-layout>
        <v-layout row mt-3>
            <v-flex
                    mr-2
                    md4
                    lg3
            >
                <v-list
                        two-line
                        dense
                        id="permissionList"
                        class="m-filter-date pt-0 pb-2"
                        style="border: 1px solid #f5f5f5;"
                >
                    <v-subheader
                            style="height:56px;"
                            class="text-uppercase pr-1"
                    >
                        izin tipleri
                        <v-spacer></v-spacer>
                        <v-tooltip right>
                            <template v-slot:activator="{ on }">
                                <v-btn
                                        v-on="on"
                                        class="primary-green--text"
                                        icon
                                >
                                    <v-icon>add</v-icon>
                                </v-btn>
                            </template>
                            <span>Yeni izin ekle</span>
                        </v-tooltip>
                    </v-subheader>

                    <v-autocomplete
                            cache-items
                            class="mx-3 mb-2 m-settings__label"
                            flat
                            hide-details
                            hide-no-data
                            background-color="grey lighten-3"
                            label="İzinler arasında arayın"
                            solo
                    ></v-autocomplete>

                    <v-list-tile
                            v-for="(item, index) in items"
                            :key="`permission-list-item-${index}`"
                            :class="activeClass(index)"
                            @click="select(index)"
                    >
                        <v-list-tile-content style="max-width: 24px; !important">
                            <div class="m-permission__icon"></div>
                        </v-list-tile-content>
                        <v-list-tile-content>
                            <v-list-tile-title>{{item.title}}</v-list-tile-title>
                            <v-list-tile-sub-title class="caption text--secondary">45 Kullanıcı</v-list-tile-sub-title>
                        </v-list-tile-content>

                        <v-list-tile-action class="m-settings__action">
                            <v-btn
                                    class="ma-0"
                                    icon
                                    @click="showMenu(index)"
                            >
                                <v-icon size="16">edit</v-icon>
                            </v-btn>
                        </v-list-tile-action>
                    </v-list-tile>
                </v-list>
            </v-flex>

            <v-flex mx-2 lg6>
                <v-list
                        class="m-settings__list pt-0 pb-3"
                        dense
                        two-line
                >
                    <v-subheader
                            style="height:56px;"
                            class="text-uppercase pr-1"
                    >
                        {{selectedPermissionTitle}} iznine sahip kullanıcılar
                        <v-spacer></v-spacer>
                        <v-tooltip right>
                            <template v-slot:activator="{ on }">
                                <v-btn
                                        v-on="on"
                                        class="primary-green--text"
                                        icon
                                >
                                    <v-icon>add</v-icon>
                                </v-btn>
                            </template>
                            <span>Yeni kullanıcı ekle</span>
                        </v-tooltip>
                    </v-subheader>

                    <v-autocomplete
                            cache-items
                            class="mx-3 mb-2 m-settings__label"
                            flat
                            hide-details
                            hide-no-data
                            background-color="grey lighten-3"
                            label="Kullanıcılar arasında arayın"
                            solo
                    ></v-autocomplete>

                    <v-list-tile
                            v-for="(user, index) in users"
                            :key="`settings-user-item-${index}`"
                    >
                        <v-list-tile-avatar>
                            <img :src="user.img">
                        </v-list-tile-avatar>

                        <v-list-tile-content>
                            <v-list-tile-title class="black--text">{{user.name}}</v-list-tile-title>
                            <v-list-tile-sub-title class="caption">{{user.groups}}</v-list-tile-sub-title>
                        </v-list-tile-content>

                        <v-list-tile-action>
                            <v-btn icon>
                                <v-icon size="18">close</v-icon>
                            </v-btn>
                        </v-list-tile-action>
                    </v-list-tile>
                </v-list>
            </v-flex>

            <v-flex ml-2 lg3>
                <v-layout
                        row
                >
                    <v-flex>

                        <v-card
                                dark
                                class="elevation-0 pb-1"
                                color="deep-purple"
                        >

                            <!-- Title -->
                            <v-card-title class="text-uppercase font-weight-medium">
                                <span class="white--text">{{selectedPermissionTitle}} iznine ait yetkiler</span><br>
                            </v-card-title>
                            <v-card-text class="py-0 my-0">
                                <span class="d-block caption green--text text--accent-2">
                                    <span class="text-capitalize">
                                        {{selectedPermissionTitle}}
                                    </span> grubuna dahil olan kullanıcılar aşağıdaki yetkilere sahip olacak.
                                </span>
                            </v-card-text>

                            <!-- Permission checkboxes -->
                            <v-card-text>
                                <v-layout
                                        justify-start
                                        wrap
                                >
                                    <v-flex
                                            v-for="(permission, index) in permissions"
                                            :key="`permission-item-${index}`"
                                            shrink
                                            ma-2
                                    >
                                        <!-- Checkbox items -->
                                        <!-- Toggles data table columns -->
                                        <v-checkbox
                                                v-model="permission.active"
                                                :label="permission.title"
                                                class="mt-0 text-capitalize font-weight-regular m-settings__label"
                                                color="green accent-2"
                                                hide-details
                                                :ref="`permission-checkbox-${index}`"
                                        ></v-checkbox>
                                    </v-flex>
                                </v-layout>
                            </v-card-text>
                            <v-btn
                                    color="primary-green"
                                    flat
                                    block
                            >
                                KAYDET
                            </v-btn>
                        </v-card>
                    </v-flex>
                </v-layout>
            </v-flex>
        </v-layout>
    </div>
</template>

<script>
    import MDataTable from "../../shared/components/data/components/MDataTable";
    import MDataTableAction from "../../shared/components/data/components/MDataTableAction";

    export default {
        name: "MSettingsUserPermission",
        components: {MDataTableAction, MDataTable},

        data() {
            return {
                data: [
                    {
                        id: 0,
                        firstname: 'Tunahan',
                        lastname: 'Bayındır',
                        department: 'Operasyon'
                    },
                    {
                        id: 0,
                        firstname: 'Tunahan',
                        lastname: 'Bayındır',
                        department: 'Operasyon'
                    },
                    {
                        id: 0,
                        firstname: 'Tunahan',
                        lastname: 'Bayındır',
                        department: 'Operasyon'
                    },
                    {
                        id: 0,
                        firstname: 'Tunahan',
                        lastname: 'Bayındır',
                        department: 'Operasyon'
                    },
                    {
                        id: 0,
                        firstname: 'Tunahan',
                        lastname: 'Bayındır',
                        department: 'Operasyon'
                    }
                ],

                items: [
                    {
                        title: 'Operasyon',
                        value: 0,
                    },
                    {
                        title: 'Mutabakat',
                        value: 1,
                    },
                    {
                        title: 'Muhasebe',
                        value: 2,
                    },
                ],

                // Data-table headers
                headers: [
                    {
                        text: 'ID',
                        sortable: true,
                        value: 'id',
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'ad',
                        sortable: true,
                        value: 'firstname',
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'soyad',
                        sortable: true,
                        value: 'lastname',
                        toggleable: false,
                        show: true,
                        search: {chip: false, value: null}
                    },
                    {
                        text: 'birim',
                        sortable: true,
                        value: 'department',
                        toggleable: false,
                        show: true
                    },
                ],

                menuActive: false,

                permissions: [
                    {
                        title: 'operasyon',
                        value: 'operations',
                        active: true,
                    },
                    {
                        title: 'mutabakat',
                        value: 'operations',
                        active: true,
                    },
                    {
                        title: 'şoför ekle/düzenle',
                        value: 'operations',
                        active: true,
                    },
                    {
                        title: 'iş yeri ekle/düzenle',
                        value: 'operations',
                        active: true,
                    },
                    {
                        title: 'tedarikçi ekle/düzenle',
                        value: 'operations',
                        active: true,
                    },
                    {
                        title: 'araç ekle/düzenle',
                        value: 'operations',
                        active: true,
                    },
                    {
                        title: 'sipariş ekle/düzenle',
                        value: 'operations',
                        active: true,
                    },
                    {
                        title: 'sevkiyat ekle/düzenle',
                        value: 'operations',
                        active: true,
                    }
                ],

                selected: 0,

                users: [
                    {
                        name: 'Tunahan Bayındır',
                        img: 'https://randomuser.me/api/portraits/men/52.jpg',
                        groups: 'Operasyon, Mutabakat, Muhasebe'
                    },
                    {
                        name: 'Berat Postalcıoğlu',
                        img: 'https://randomuser.me/api/portraits/men/75.jpg',
                        groups: 'Operasyon, Muhasebe'
                    },
                    {
                        name: 'Fatih Akbayrak',
                        img: 'https://randomuser.me/api/portraits/men/90.jpg',
                        groups: 'Admin'
                    },
                    {
                        name: 'Ali İmran Özbatman',
                        img: 'https://randomuser.me/api/portraits/men/48.jpg',
                        groups: 'Operasyon, Mutabakat, Muhasebe'
                    }
                ]
            }
        },

        computed: {
            selectedPermissionTitle() {
                return this.permissions[this.selected].title;
            }
        },

        methods: {

            activeClass(index) {
                return this.selected === index ? 'm-settings__active' : ''
            },

            showMenu (index) {
                this.menuActive = true;
            },

            select(index) {
                this.selected = index;
            }
        }
    }
</script>

<style scoped>


    .m-permission__icon {
        width: 6px;
        height: 6px;
        border-radius: 50%;
    }

    .m-settings__list {
        position: relative;
        color: #767676;
        border: 1px solid #f5f5f5;
    }

    .m-settings__action {
        transform: translate3d(-10%, 0, 0);
        opacity: 0;

        -webkit-transition: transform 300ms ease-in-out, opacity 300ms ease-in-out;
        -moz-transition: transform 300ms ease-in-out, opacity 300ms ease-in-out;
        -ms-transition: transform 300ms ease-in-out, opacity 300ms ease-in-out;
        -o-transition: transform 300ms ease-in-out, opacity 300ms ease-in-out;
        transition: transform 300ms ease-in-out, opacity 300ms ease-in-out;
    }

    .m-settings__active .m-settings__action,
    .v-list__tile:hover > .m-settings__action {
        transform: translate3d(0, 0, 0);
        opacity: 1;
    }

    .m-settings__active .m-permission__icon {
        background: #13d893;
    }

    .m-settings__active ~ span {
        color: #333;
    }
</style>
