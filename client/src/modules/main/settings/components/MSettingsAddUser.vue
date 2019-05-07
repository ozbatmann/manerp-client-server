<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <m-data-table-add-edit-form
            v-model="show"
            title="Yeni Kullanıcı Ekle"
            :width="600"
            @keydown.enter.capture="save"
            @keydown.esc.capture="close"
            @save="save"
    >
        <template v-slot:form>
            <v-alert
                    :value="true"
                    type="info"
            >
                Eklemek istediğiniz kullanıcıyı ad, soyad girerek arayın
            </v-alert>
            <v-autocomplete
                    :items="items"
                    :loading="loading"
                    :search-input.sync="search"
                    hide-no-data
                    hide-selected
                    item-text="fullname"
                    item-value="id"
                    return-object
                    v-validate="'required'"
                    :error-messages="errors.collect('user')"
                    v-model="user"
                    :counter="20"
                    maxlength="20"
                    label="Ad soyad"
                    name="roleName"
                    background-color="grey lighten-4"
                    color="green accent-2"
                    class="m-settings__label mt-3"
                    solo
                    flat
            ></v-autocomplete>
        </template>
    </m-data-table-add-edit-form>
</template>

<script>
    import MDataTableAddEditForm from "../../shared/components/data/components/MDataTableAddEditForm";
    export default {
        name: "MSettingsAddUser",
        components: {MDataTableAddEditForm},
        props: {
            value: {
                type: Boolean,
                default: false,
            },

            role: {
                type: String,
            }
        },

        data () {
            return {
                items: [],
                show: this.value,
                user: null,
                loading: false,
                search: '',
                roleId: this.role ? this.role.role.id : null,
            }
        },

        methods: {
            save() {
                this.$validator.validateAll().then((result) => {
                    if (result) {
                        this.$emit("save", this.user);
                        this.close();
                    }
                });
            },

            close (){
                this.$validator.reset();
                this.user = null;
                this.show = false;
                this.items = [];
                this.loading = false;
                this.search = '';
            },
        },

        watch: {

            role (newVal) {
                this.roleId = newVal
            },

            search (newVal) {
                if (!newVal || !newVal.length) return;

                // Items have already been requested
                if (this.loading) return;

                this.loading = true;

                // Lazily load input items
                this.$http.post('/api/v1/auth/getAllUserListBySearchParam', {
                    organizationId: this.$store.state.shared.organization.id,
                    roleId: this.roleId,
                    searchParam: newVal
                }).then(res => {
                    console.log('Autocomplete result', res);
                    this.items = res.data;
                }).catch(err => {
                    this.items = [];
                    console.log('Error while getting user: ', err);
                }).finally(() => {
                    this.loading = false;
                });
            },

            show (newVal) {
                this.$emit('input', newVal);
            },

            value (newVal) {
                this.show = newVal;
            }
        },
    }
</script>

<style scoped>

</style>
