<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div>
        <v-layout>
            <v-flex>
                <v-alert
                        :value="true"
                        type="info"
                        dismissible
                >
                    Yeni kullanıcı ekleyin, kullanıcı izinlerini yönetin, yeni izin ekleyin ve kullanıcıları
                    yetkilendirin.
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
                        <v-tooltip right v-if="!loading.role">
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
                        <v-progress-circular
                                v-if="loading.role"
                                indeterminate
                                color="primary-green"
                                size="18"
                                class="mr-3"
                        ></v-progress-circular>
                    </v-subheader>

                    <v-text-field
                            v-model="filter.role"
                            class="mx-3 mb-2 m-settings__label"
                            flat
                            hide-details
                            background-color="grey lighten-3"
                            label="İzinler arasında arayın"
                            solo
                    ></v-text-field>

                    <v-list-tile
                            v-for="(item, index) in filteredItems(items, 'role')"
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
                            <v-icon
                                    size="16"
                                    class="mr-2 black--text"
                            >
                                edit
                            </v-icon>
                        </v-list-tile-action>
                    </v-list-tile>
                </v-list>
            </v-flex>

            <v-flex mx-2 lg5>
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
                        <v-tooltip right v-if="!loading.user">
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
                        <v-progress-circular
                                v-if="loading.user"
                                indeterminate
                                color="primary-green"
                                size="18"
                                class="mr-3"
                        ></v-progress-circular>
                    </v-subheader>

                    <v-text-field
                            v-model="filter.user"
                            class="mx-3 mb-2 m-settings__label"
                            flat
                            hide-details
                            background-color="grey lighten-3"
                            label="Kullanıcılar arasında arayın"
                            solo
                    ></v-text-field>

                    <v-list-tile
                            v-for="(user, index) in filteredItems(users, 'user', 'name')"
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

            <v-flex ml-2 lg4>
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
                                <span class="white--text">{{selectedPermissionTitle}} iznine ait yetkiler</span>
                                <v-spacer></v-spacer>
                                <v-progress-circular
                                        v-if="loading.permission"
                                        indeterminate
                                        color="primary-green"
                                        size="18"
                                ></v-progress-circular>
                            </v-card-title>

                            <v-card-text class="py-0 my-0">
                                <span class="d-block caption green--text text--accent-2">
                                    <span class="text-capitalize">
                                        {{selectedPermissionTitle}}
                                    </span> grubuna dahil olan kullanıcılar aşağıdaki yetkilere sahip olacak.
                                </span>
                            </v-card-text>

                            <v-card-text class="pb-1">

                                <v-text-field
                                        v-model="filter.permission"
                                        class="m-settings__label"
                                        flat
                                        hide-details
                                        background-color="deep-purple darken-2"
                                        label="Yetkiler arasında arayın"
                                        solo
                                ></v-text-field>
                            </v-card-text>
                            <!-- Permission checkboxes -->
                            <v-card-text class="px-0 pt-0">
                                <v-layout
                                        justify-start
                                        wrap
                                >
                                    <v-flex
                                            shrink
                                            xs12
                                    >
                                        <!-- Permission types expansion panel -->
                                        <v-expansion-panel class="elevation-0">
                                            <v-expansion-panel-content
                                                    v-for="(permission, index) in filteredItems(permissions, 'permission')"
                                                    :key="`permission-item-${index}`"
                                                    class="deep-purple px-0"
                                            >
                                                <template v-slot:header>
                                                    <div class="text-capitalize">{{permission.title}}</div>
                                                </template>

                                                <v-layout
                                                        wrap
                                                        deep-purple
                                                        darken-2
                                                        px-3
                                                        py-2
                                                >
                                                    <v-flex
                                                            v-for="(type, i) in permission.types"
                                                            :key="`${permission.title}-item-${i}`"
                                                            pa-2
                                                            shrink
                                                    >
                                                        <!-- Checkbox items -->
                                                        <v-checkbox
                                                                v-model="type.active"
                                                                :label="type.title"
                                                                class="mt-0 text-capitalize font-weight-regular m-settings__label"
                                                                color="green accent-2"
                                                                hide-details
                                                        ></v-checkbox>
                                                    </v-flex>
                                                </v-layout>
                                            </v-expansion-panel-content>
                                        </v-expansion-panel>
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

    export default {
        name: "MSettingsUserPermission",

        data() {
            return {
                filter: {
                    role: null,
                    user: null,
                    permission: null
                },

                items: [
                    {
                        id: '',
                        title: 'Operasyon',
                    },
                    {
                        id: '',
                        title: 'Mutabakat',
                    },
                    {
                        id: '',
                        title: 'Muhasebe',
                    },
                ],

                menuActive: false,

                permissions: [
                    {
                        title: 'operasyon',
                        value: 'operations',
                        types: [
                            {
                                title: 'Ekle',
                                value: 'create',
                                active: false,
                            },
                            {
                                title: 'Görüntüle',
                                value: 'read',
                                active: false,
                            },
                            {
                                title: 'Düzenle',
                                value: 'update',
                                active: false,
                            },
                            {
                                title: 'Sil',
                                value: 'delete',
                                active: false,
                            }
                        ]
                    },
                    {
                        title: 'mutabakat',
                        value: 'operations',
                        types: [
                            {
                                title: 'Ekle',
                                value: 'create',
                                active: false,
                            },
                            {
                                title: 'Görüntüle',
                                value: 'read',
                                active: false,
                            },
                            {
                                title: 'Düzenle',
                                value: 'update',
                                active: false,
                            },
                            {
                                title: 'Sil',
                                value: 'delete',
                                active: false,
                            }
                        ]
                    },
                    {
                        title: 'şoför ekle/düzenle',
                        value: 'operations',
                        types: [
                            {
                                title: 'Ekle',
                                value: 'create',
                                active: false,
                            },
                            {
                                title: 'Görüntüle',
                                value: 'read',
                                active: false,
                            },
                            {
                                title: 'Düzenle',
                                value: 'update',
                                active: false,
                            },
                            {
                                title: 'Sil',
                                value: 'delete',
                                active: false,
                            }
                        ]
                    },
                    {
                        id: '',
                        title: 'iş yeri',
                        types: [
                            {
                                title: 'Ekle',
                                value: 'create',
                                active: false,
                            },
                            {
                                title: 'Görüntüle',
                                value: 'read',
                                active: false,
                            },
                            {
                                title: 'Düzenle',
                                value: 'update',
                                active: false,
                            },
                            {
                                title: 'Sil',
                                value: 'delete',
                                active: false,
                            }
                        ]
                    },
                    {
                        title: 'tedarikçi ekle/düzenle',
                        value: 'operations',
                        types: [
                            {
                                title: 'Ekle',
                                value: 'create',
                                active: false,
                            },
                            {
                                title: 'Görüntüle',
                                value: 'read',
                                active: false,
                            },
                            {
                                title: 'Düzenle',
                                value: 'update',
                                active: false,
                            },
                            {
                                title: 'Sil',
                                value: 'delete',
                                active: false,
                            }
                        ]
                    },
                    {
                        title: 'araç ekle/düzenle',
                        value: 'operations',
                        types: [
                            {
                                title: 'Ekle',
                                value: 'create',
                                active: false,
                            },
                            {
                                title: 'Görüntüle',
                                value: 'read',
                                active: false,
                            },
                            {
                                title: 'Düzenle',
                                value: 'update',
                                active: false,
                            },
                            {
                                title: 'Sil',
                                value: 'delete',
                                active: false,
                            }
                        ]
                    },
                    {
                        title: 'sipariş ekle/düzenle',
                        value: 'operations',
                        types: [
                            {
                                title: 'Ekle',
                                value: 'create',
                                active: false,
                            },
                            {
                                title: 'Görüntüle',
                                value: 'read',
                                active: false,
                            },
                            {
                                title: 'Düzenle',
                                value: 'update',
                                active: false,
                            },
                            {
                                title: 'Sil',
                                value: 'delete',
                                active: false,
                            }
                        ]
                    },
                    {
                        title: 'sevkiyat ekle/düzenle',
                        value: 'operations',
                        types: [
                            {
                                title: 'Ekle',
                                value: 'create',
                                active: false,
                            },
                            {
                                title: 'Görüntüle',
                                value: 'read',
                                active: false,
                            },
                            {
                                title: 'Düzenle',
                                value: 'update',
                                active: false,
                            },
                            {
                                title: 'Sil',
                                value: 'delete',
                                active: false,
                            }
                        ]
                    }
                ],

                permissionTypes: ['add', 'show', 'edit', 'delete'],

                loading: {
                    role: true,
                    user: false,
                    permission: false,
                },

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

            filteredItems (items, type, prop = null) {
                let filterText = this.filter[type];
                let filterProp = prop ? prop : 'title';

                if (!filterText) return items;

                return items.filter(item => {
                    return item[filterProp].toLocaleLowerCase()
                        .includes(filterText.toLocaleLowerCase());
                });
            },

            showMenu(index) {
                this.menuActive = true;
            },

            select(index) {
                this.selected = index;
            },

            // Request methods

            getSecuritySubjects () {
                this.loading.permission = true;

                this.$http.get('api/v1/').then((result) => {
                    let subjects = result.data.data.items;

                    for (let subject of subjects) {

                    }
                }).catch((error) => {
                    console.log(error);
                }).finally(() => this.loading.permission = false)
            },

            getPermissions (roleId) {
                this.loading.permission = true;

                this.$http.get('api/v1/').then((result) => {
                    let subjects = result.data.data.items;

                    for (let subject of subjects) {

                    }
                }).catch((error) => {
                    console.log(error);
                }).finally(() => this.loading.permission = false)
            },

            getRoles () {

            },

            getUsers(role) {

            },

            removeUser(id) {

            }
        },

        mounted () {

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
