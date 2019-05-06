<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <m-data-table-add-edit-form
            v-model="show"
            title="Yeni Rol Ekle"
            :width="600"
            @save="save"
            @edit="edit"
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
                    :loading="isLoading"
                    :search-input.sync="search"
                    color="white"
                    hide-no-data
                    hide-selected
                    item-text="Description"
                    item-value="API"
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

            data: {
                type: String,
                default: null
            }
        },

        data () {
            return {
                show: this.value,
                user: this.data
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

            edit() {
                this.$validator.validateAll().then((result) => {
                    if (result) {
                        this.$emit("edit", this.user);
                        this.close();
                    }
                });
            },

            close (){
                this.$validator.reset();
                this.user = null;
                this.show = false;
            }
        },

        watch: {
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
