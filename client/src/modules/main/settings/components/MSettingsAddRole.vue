<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <m-data-table-add-edit-form
            v-model="show"
            title="Yeni Rol Ekle"
            :width="600"
            @save="save"
            @edit="edit"
            @clear="clear"
            @close="close"
    >
        <template v-slot:form>
            <v-alert
                    :value="true"
                    type="info"
            >
                Rol adını girin. Kaydete tıkladıktan sonra bu role sahip olacak kullanıcıları ve bu role ait yetkileri belirleyin
            </v-alert>
            <v-text-field
                    v-validate="'required'"
                    :error-messages="errors.collect('roleName')"
                    v-model="roleName"
                    :counter="20"
                    maxlength="20"
                    label="Rol adı"
                    name="roleName"
                    background-color="grey lighten-4"
                    color="green accent-2"
                    class="m-settings__label mt-3"
                    solo
                    flat
            ></v-text-field>
        </template>
    </m-data-table-add-edit-form>
</template>

<script>
    import MDataTableAddEditForm from "../../shared/components/data/components/MDataTableAddEditForm";
    export default {
        name: "MSettingsAddRole",
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
                roleName: this.data
            }
        },

        methods: {
            save() {
                this.$validator.validateAll().then((result) => {
                    if (result) {
                        this.$emit("save", this.roleName);
                        this.close();
                    }
                });
            },

            edit() {
                this.$validator.validateAll().then((result) => {
                    if (result) {
                        this.$emit("edit", this.roleName);
                        this.close();
                    }
                });
            },

            close (){
                this.roleName = null;
                this.show = false;
                this.$validator.reset();
            },

            clear () {
                this.roleName = null;
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
