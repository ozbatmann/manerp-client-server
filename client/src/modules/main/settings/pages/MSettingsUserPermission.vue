<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div>
        <m-settings-add-role
                v-model="addRoleDialog"
                :data="editRoleItem"
                @save="addRole"
                @edit="editRole"
        ></m-settings-add-role>
        <m-settings-add-user
                v-model="addUserDialog"
                :role="selectedRoleId"
                @save="addUser"
        ></m-settings-add-user>
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
        <v-layout row mt-3 align-stretch>
            <v-flex
                    mr-2
                    md4
                    lg3
                    style="max-height: 65vh;"
                    overflow-hidden
            >
                <v-list
                        two-line
                        dense
                        id="permissionList"
                        class="fill-height m-filter-date pt-0 pb-2 overflow-x-hidden"
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
                                        @click="showRoleDialog(false)"
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
                            <v-list-tile-title>{{item.role.name}}</v-list-tile-title>
                            <v-list-tile-sub-title class="caption text--secondary">{{item.count}} Kullanıcı
                            </v-list-tile-sub-title>
                        </v-list-tile-content>

                        <v-list-tile-action class="m-settings__action">
                            <v-menu
                                    left
                                    offset-x
                                    :nudge-width="140"
                                    content-class="v-shadow-lg"
                                    transition="slide-x-reverse-transition"
                            >
                                <!-- Row action button -->
                                <!-- Activates the menu -->
                                <v-btn
                                        slot="activator"
                                        icon
                                >
                                    <v-icon size="16">more_vert</v-icon>
                                </v-btn>
                                <v-list dense>
                                    <v-list-tile @click="showRoleDialog(true)">
                                        Düzenle
                                    </v-list-tile>
                                    <v-list-tile @click="deleteRole(item.role)">
                                        Sil
                                    </v-list-tile>
                                </v-list>
                            </v-menu>
                        </v-list-tile-action>
                    </v-list-tile>
                    <v-list-tile v-if="!permissionRoles.length && !loading.role">
                        <v-list-tile-content>
                            <v-list-tile-title class="text-xs-center">Henüz bir rol eklemediniz</v-list-tile-title>
                        </v-list-tile-content>
                    </v-list-tile>
                </v-list>
            </v-flex>

            <v-flex mx-2 md4 lg5>
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
                        <v-tooltip right v-if="showAddUserButton">
                            <template v-slot:activator="{ on }">
                                <v-btn
                                        v-on="on"
                                        class="primary-green--text"
                                        icon
                                        @click="addUserDialog = true"
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
                            v-show="user.name !== undefined"
                            :key="`settings-user-item-${index}`"
                    >
                        <v-list-tile-avatar avatar>
                            <v-img src="https://randomuser.me/api/portraits/men/85.jpg"></v-img>
                        </v-list-tile-avatar>

                        <v-list-tile-content>
                            <v-list-tile-title class="black--text">{{user.name}} {{user.surname}}</v-list-tile-title>
                            <!--<v-list-tile-sub-title class="caption">{{user.groups}}</v-list-tile-sub-title>-->
                        </v-list-tile-content>

                        <v-list-tile-action>
                            <v-btn icon @click="deleteUser(user.id)">
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

            <v-flex ml-2 md4 lg4>
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
                                                            v-for="(permission, i) in permission.permissions"
                                                            :key="`${permission.name}-available-item-${i}`"
                                                            pa-2
                                                            shrink
                                                    >
                                                        <!-- Checkbox items -->
                                                        <v-checkbox
                                                                v-model="permission.status"
                                                                :label="permissionTitle(permission.name)"
                                                                class="mt-0 text-capitalize font-weight-regular m-settings__label"
                                                                color="green accent-2"
                                                                hide-details
                                                                @click.native.capture="permission.status ? deletePermission(permission) : addPermission(permission)"
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
    import MSettingsAddUser from "../components/MSettingsAddUser";

    export default {
        name: "MSettingsUserPermission",
        components: {MSettingsAddUser, MSettingsAddRole},
        data() {
            return {
                addRoleDialog: false,
                addUserDialog: false,

                addRoleEdit: false,

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
            showAddUserButton () {
                return !this.loading.user && this.selected
            },

            selectedRole() {
                return this.permissionRoles[this.selected];
            },

            selectedRoleId() {
                return this.selectedRole ? this.selectedRole.role.id : null
            },

            editRoleItem() {
                return this.addRoleEdit ? this.selectedRole ? this.selectedRole : null : null;
            },

            selectedPermissionTitle() {
                let selected = this.permissionRoles[this.selected];

                return selected ? `${selected.role.name} rolüne ` : 'seçilecek role ';
            }
        },

        methods: {

            permissionTitle (name) {
                switch(name) {
                    case 'add':
                        return 'Ekle';
                    case 'edit':
                        return 'Düzenle';
                    case 'delete':
                        return 'Sil';
                    case 'show':
                        return 'Görüntüle';
                }
            },

            showRoleDialog(edit) {
                this.addRoleEdit = edit;
                this.addRoleDialog = true;
            },

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
                    this.permissions = [];
                    this.users = [];

                    let roleId = this.permissionRoles[index].role.id;

                    this.getSecuritySubjects(roleId);
                    this.getUsers(roleId)
                }
            },

            editRole(role) {
                this.loading.role = true;

                let self = this;

                this.$http.post('/api/v1/auth/updateRole',
                    {
                        roleId: role.role.id,
                        name: role.role.name,
                        organizationId: this.user.organization.id
                    }).then((result) => {

                    console.log(result);
                    self.getRoles();

                }).catch((error) => {
                    console.log(error);
                }).finally(() => this.loading.role = false)
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

            deleteRole(role) {
                this.loading.role = true;

                let self = this;

                this.$http.post('/api/v1/auth/deleteRole',
                    {
                        roleId: role.id,
                        organizationId: this.user.organization.id
                    }).then((result) => {

                    console.log(result);
                    self.getRoles();

                }).catch((error) => {
                    console.log(error);
                }).finally(() => this.loading.role = false)
            },

            addUser(user) {
                this.loading.user = true;

                let self = this;
                let roleId = this.selectedRole.role.id;

                this.$http.post('/api/v1/auth/addUserOrganizationRole',
                    {
                        organizationId: this.user.organization.id,
                        roleId: roleId,
                        userId: user.id
                    })
                    .then((result) => {

                        if (result.data) this.getUsers(roleId);
                        // TODO print error message

                    }).catch((error) => {
                    console.log(error);
                }).finally(() => this.loading.user = false)
            },

            // Request methods

            getSecuritySubjects(roleId) {
                this.loading.permission = true;

                this.$http.post('/api/v1/auth/getAllRolePermissionList', {roleId: roleId}).then((result) => {
                    console.log(result);
                    this.permissions = result.data.data;
                }).catch((error) => {
                    console.log(error);
                }).finally(() => this.loading.permission = false)
            },

            getRoles() {
                this.loading.role = true;

                this.$http.post('api/v1/auth/getAllRoleList',
                    {organizationId: this.user.organization.id})
                    .then((result) => {

                        console.log('Roles', result);
                        let count = result.data.itemCount;
                        this.permissionRoles = result.data.itemList;

                        if (this.permissionRoles.length && !this.selected)
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
                        this.users = result.data.itemList;
                    }).catch((error) => {
                    console.log(error);
                }).finally(() => this.loading.user = false)
            },

            deleteUser(id) {
                this.loading.user = true;

                let self = this;

                this.$http.post('/api/v1/auth/deleteUser',
                    {
                        userId: id,
                        organizationId: this.user.organization.id,
                        roleId: this.selectedRoleId
                    }).then((result) => {

                    console.log(result);
                    self.getUsers(this.selectedRoleId)

                }).catch((error) => {
                    console.log(error);
                }).finally(() => this.loading.user = false)
            },

            addPermission(permission) {
                this.loading.permission = true;

                let self = this;
                let roleId = this.permissionRoles[this.selected].role.id;


                this.$http.post('/api/v1/auth/addRolePermission',
                    {id: permission.id, roleId: roleId})
                    .then((result) => {

                        let status = result.status;

                        console.log(result);

                    }).catch((error) => {
                    console.log(error);
                }).finally(() => this.loading.permission = false)
            },

            deletePermission(permission) {
                this.loading.permission = true;

                let self = this;
                let roleId = this.permissionRoles[this.selected].role.id;

                this.$http.post('/api/v1/auth/deleteRolePermission',
                    {
                        id: permission.id,
                        roleId: roleId
                    }).then((result) => {

                    let status = result.status;

                    console.log(result);

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
