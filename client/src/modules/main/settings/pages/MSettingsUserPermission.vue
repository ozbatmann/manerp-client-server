<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div>
        <m-settings-add-role
                v-model="addRoleDialog"
                @save="addRole"
        ></m-settings-add-role>
        <v-layout>
            <v-flex>
                <v-alert
                        :value="true"
                        type="info"
                        dismissible
                >
                    Yeni kullanıcı rolü ekleyin, role sahip kullanıcıları yönetin, yeni izin ekleyin ve kullanıcıları
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
                        kullanıcı rolleri
                        <v-spacer></v-spacer>
                        <v-tooltip right v-if="!loading.role">
                            <template v-slot:activator="{ on }">
                                <v-btn
                                        v-on="on"
                                        class="primary-green--text"
                                        icon
                                        @click="addRoleDialog = true"
                                >
                                    <v-icon>add</v-icon>
                                </v-btn>
                            </template>
                            <span>Yeni rol ekle</span>
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
                            label="Roller arasında arayın"
                            solo
                    ></v-text-field>

                    <v-list-tile
                            v-for="(item, index) in filteredItems(permissionRoles, 'role')"
                            :key="`permission-list-item-${index}`"
                            :class="activeClass(index)"
                            @click="select(index)"
                    >
                        <v-list-tile-content style="max-width: 24px; !important">
                            <div class="m-permission__icon"></div>
                        </v-list-tile-content>
                        <v-list-tile-content>
                            <v-list-tile-title>{{item.name}}</v-list-tile-title>
                            <v-list-tile-sub-title class="caption text--secondary">{{item.count}} Kullanıcı
                            </v-list-tile-sub-title>
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
                    <v-list-tile v-if="!permissionRoles.length && !loading.role">
                        <v-list-tile-content>
                            <v-list-tile-title class="text-xs-center">Henüz bir rol eklemediniz</v-list-tile-title>
                        </v-list-tile-content>
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
                        {{selectedPermissionTitle}} sahip kullanıcılar
                        <v-spacer></v-spacer>
                        <v-tooltip right v-if="!users.length && !loading.user && this.selected">
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
                            <v-list-tile-title class="black--text">{{user.name}} {{user.surname}}</v-list-tile-title>
                            <v-list-tile-sub-title class="caption">{{user.groups}}</v-list-tile-sub-title>
                        </v-list-tile-content>

                        <v-list-tile-action>
                            <v-btn icon>
                                <v-icon size="18">close</v-icon>
                            </v-btn>
                        </v-list-tile-action>
                    </v-list-tile>
                    <v-list-tile v-if="!users.length && !loading.user && !this.selected">
                        <v-list-tile-content>
                            <v-list-tile-title
                                    class="text-xs-center black--text"
                            >
                                Önce kullanıcı rolü seçin
                            </v-list-tile-title>
                        </v-list-tile-content>
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
                                <span class="white--text">{{selectedPermissionTitle}} ait yetkiler</span>
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
                                        <v-expansion-panel
                                                class="elevation-0"
                                                v-if="filteredItems(permissions, 'permission', 'name').length"
                                        >
                                            <v-expansion-panel-content
                                                    v-for="(permission, index) in filteredItems(permissions, 'permission', 'name')"
                                                    :key="`permission-item-${index}`"
                                                    class="deep-purple px-0"
                                            >
                                                <template v-slot:header>
                                                    <div class="text-capitalize">{{ permission.name }}
                                                    </div>
                                                </template>

                                                <v-layout
                                                        wrap
                                                        deep-purple
                                                        darken-2
                                                        px-3
                                                        py-2
                                                >
                                                    <v-flex
                                                            v-for="(available, i) in permission.availablePermissions"
                                                            :key="`${permission.name}-available-item-${i}`"
                                                            pa-2
                                                            shrink
                                                    >
                                                        <!-- Checkbox items -->
                                                        <v-checkbox
                                                                :label="available[2]"
                                                                input-value="false"
                                                                class="mt-0 text-capitalize font-weight-regular m-settings__label"
                                                                color="green accent-2"
                                                                hide-details
                                                                @click.stop="addPermission(permission.name, available)"
                                                        ></v-checkbox>
                                                    </v-flex>

                                                    <v-flex
                                                            v-for="(unavailable, i) in permission.unavailablePermissions"
                                                            :key="`${permission.name}-unavailable-item-${i}`"
                                                            pa-2
                                                            shrink
                                                    >
                                                        <!-- Checkbox items -->
                                                        <v-checkbox
                                                                :label="unavailable[2]"
                                                                input-value="true"
                                                                class="mt-0 text-capitalize font-weight-regular m-settings__label"
                                                                color="green accent-2"
                                                                hide-details
                                                                @click.stop="deletePermission(permission.name, unavailable)"
                                                        ></v-checkbox>
                                                    </v-flex>
                                                </v-layout>
                                            </v-expansion-panel-content>
                                        </v-expansion-panel>
                                        <div
                                                v-if="!filteredItems(permissions, 'permission', 'name').length"
                                                class="pt-3 text-xs-center"
                                        >
                                            <span>
                                                Önce kullanıcı rolü seçin
                                            </span>
                                        </div>
                                    </v-flex>
                                </v-layout>
                            </v-card-text>
                        </v-card>
                    </v-flex>
                </v-layout>
            </v-flex>
        </v-layout>
    </div>
</template>

<script>

    import MSettingsAddRole from "../components/MSettingsAddRole";

    export default {
        name: "MSettingsUserPermission",
        components: {MSettingsAddRole},
        data() {
            return {
                addRoleDialog: false,
                addUserDialog: false,

                // -------------------

                user: this.$store.state.shared,
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

                permissions: [],

                subjects: [],

                permissionTypes: ['add', 'show', 'edit', 'delete'],

                loading: {
                    role: true,
                    user: false,
                    permission: false,
                },

                selected: null,

                users: [],

                permissionRoles: []
            }
        },

        computed: {

            selectedPermissionTitle() {
                let selected = this.permissionRoles[this.selected];

                return selected ? `${selected.name} rolüne ` : 'seçilecek role ';
            }
        },

        methods: {

            activeClass(index) {
                return this.selected === index ? 'm-settings__active' : ''
            },

            filteredItems(items, type, prop = null) {
                let filterText = this.filter[type];
                let filterProp = prop ? prop : 'name';

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
                if (this.selected !== index) {
                    this.selected = index;

                    let roleId = this.permissionRoles[index].id;

                    this.getSecuritySubjects(roleId);
                    this.getUsers(roleId)
                }
            },

            addRole(roleName) {
                this.loading.role = true;

                let self = this;

                this.$http.post('/api/v1/auth/addRole',
                    {name: roleName, organizationId: this.user.organization.id})
                    .then((result) => {

                        console.log(result);
                        self.getRoles();

                    }).catch((error) => {
                    console.log(error);
                }).finally(() => this.loading.role = false)
            },

            addUser(roleId) {
                this.loading.user = true;

                this.$http.post('/api/v1/auth/getAllRolePermissionList',
                    {roleId: roleId})
                    .then((result) => {

                        console.log(result);

                    }).catch((error) => {
                    console.log(error);
                }).finally(() => this.loading.user = false)
            },

            // Request methods

            getSecuritySubjects(roleId) {
                this.loading.permission = true;

                this.$http.post('/api/v1/auth/getAllRolePermissionList', {roleId: roleId}).then((result) => {
                    console.log(result);
                    this.permissions = result.data;
                }).catch((error) => {
                    console.log(error);
                }).finally(() => this.loading.permission = false)
            },

            getRoles() {
                this.loading.role = true;

                this.$http.post('api/v1/auth/getAllRoleList',
                    {organizationId: this.user.organization.id})
                    .then((result) => {

                        this.permissionRoles = result.data;

                        if (this.permissionRoles.length)
                            this.$nextTick(() => {
                                this.select(0);
                            });
                    }).catch((error) => {
                    console.log(error);
                }).finally(() => this.loading.role = false)
            },

            getUsers(roleId) {
                this.loading.user = true;

                this.$http.post('/api/v1/auth/getAllUserList',
                    {organizationId: this.user.organization.id, roleId: roleId})
                    .then((result) => {
                        console.log(result);
                        this.users = result.data;
                    }).catch((error) => {
                    console.log(error);
                }).finally(() => this.loading.user = false)
            },

            removeUser(id) {

            },

            addPermission(name, item) {
                console.log('add:', name, ':', item[0], ':', item[2]);

                this.loading.permission = true;

                let self = this;
                let roleId = this.permissionRoles[this.selected].id;


                this.$http.post('/api/v1/auth/addRolePermission',
                    {id: item[0], roleId: roleId})
                    .then((result) => {

                        let status = result.status;

                        console.log(result);

                        if (status === 200) {
                            let permId = result.data.id;
                            let permission = this.permissions.find(permission => {
                                return permission.name === name;
                            });

                            if (permId) {
                                for (let i = 0; i < permission.unavailablePermissions.length; i++) {
                                    let c = permission.unavailablePermissions[i];

                                    if (c[0] === item[0]) {
                                        permission.availablePermissions.splice(i, 1);
                                        permission.unavailablePermissions.push(item);

                                        break;
                                    }
                                }
                            }
                        }
                    }).catch((error) => {
                    console.log(error);
                }).finally(() => this.loading.permission = false)
            },

            deletePermission(name, item) {
                console.log('del:', name, ':', item[0], ':', item[2]);
                this.loading.permission = true;

                let self = this;
                let roleId = this.permissions[this.selected].id;

                this.$http.post('/api/v1/auth/deleteRolePermission',
                    {id: item[0], roleId: roleId})
                    .then((result) => {
                        let status = result.status;

                        if (status === 200) {
                            let statusName = result.data.status.name;
                            let permission = this.permissions.find(permission => {
                                return permission.name === name;
                            });
                            let index = 0;

                            switch (statusName) {
                                case 'NO_CONTENT':

                                    for (let i = 0; i < permission.unavailablePermissions.length; i++) {
                                        let c = permission.unavailablePermissions[i];

                                        if (c[0] === item[0]) {
                                            permission.unavailablePermissions.splice(i, 1);
                                            permission.availablePermissions.push(item);
                                            break;
                                        }

                                        index++;
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                        console.log(result);
                        //self.getRoles();

                    }).catch((error) => {
                    console.log(error);
                }).finally(() => this.loading.permission = false)
            }
        },

        findPermission(name) {
            return this.permissions.find(permission => {
                return permission.name === name;
            });
        },

        findSubject(id) {
            return this.permissions.find()
        },

        mounted() {
            this.getRoles();
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
