<!-- MDataTableAddNeForm -->
<!-- Will be used for saving and editing data -->
<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">

    <!-- Base dialog object -->
    <!-- This object will contain all field -->
    <v-dialog
        v-model="showDialog"
        max-width="600"
        @keydown.enter.prevent="save"
    >
        <v-card>

            <!-- Title of the dialog -->
            <v-card-title class="headline">
                {{ title }}

                <v-spacer></v-spacer>

                <v-btn
                        flat
                        color="red"
                        :disabled="clearAllDisabled"
                        @click="clear"
                >
                    temizle
                </v-btn>
            </v-card-title>

            <v-form>
                <v-layout row wrap px-3>
                    <v-flex
                            v-for="(field, index) in localData"
                            :key="`add-edit-field-${index}`"
                            :class="flexSize(index)"
                            px-2
                            py-2
                    >
                        <div v-if="field.type === input__types.checkbox">
                            <v-checkbox
                                    v-for="(prop, index) in field.props"
                                    v-model="field.value"
                                    :key="`add-edit-checkbox-${index}`"
                                    :data-vv-name="field.value"
                                    :label="prop"
                                    color="green accent-2"
                                    hide-details
                            ></v-checkbox>
                        </div>

                        <div v-else-if="field.type === input__types.select">
                            <v-select
                                    v-model="field.value"
                                    :data-vv-name="field.key"
                                    :items="field.props"
                                    :label="field.title"
                                    :error-messages="errors.collect(field.key)"
                                    v-validate="'required'"
                                    flat
                                    required
                            ></v-select>
                        </div>

                        <div v-else>
                            <v-text-field
                                    v-model="field.value"
                                    :data-vv-name="field.key"
                                    :type="field.type"
                                    :label="field.title"
                                    :mask="field.mask"
                                    :error-messages="errors.collect(field.key)"
                                    :v-validate="validate(field.rules)"
                                    :counter="field.max"
                                    clearable
                                    flat
                                    required
                            ></v-text-field>
                        </div>

                    </v-flex>
                </v-layout>
            </v-form>

            <!-- Action buttons -->
            <v-card-actions class="mt-3">
                <v-spacer></v-spacer>

                <!-- Cancel button -->
                <v-btn
                        depressed
                        flat="flat"
                        @click="close"
                >
                    iptal
                </v-btn>

                <!-- Save button -->
                <v-btn
                        depressed
                        color="primary"
                        flat="flat"
                        @click="save"
                >
                    kaydet
                </v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script>
    export default {
        $_veeValidate: {
            validator: 'new'
        },

        name: "MDataTableAddNewForm",

        props: {
            // Data object
            // Will define fields and hold their values
            data: {
                type: Array,
                default: null
            },

            title: {
                type: String,
                default: 'Yeni Müşteri'
            }
        },

        data () {
            return {
                localData: this.data,
                input__types: {
                    checkbox: 'checkbox',
                    select: 'select',
                },

                showDialog: false,
                isEdit: false,
            }
        },

        computed: {

            clearAllDisabled () {
                return this.localData.every(item => {
                    return item.value !== null && item.value.length > 0
                });
            }
        },

        methods: {
            validate (rules) {
                if (rules === undefined) return null;
                else {
                    let finalRules = '';

                    rules.forEach(rule => {
                        finalRules += `${rule}|`
                    });

                    console.log(finalRules)

                    return finalRules
                }
            },

            flexSize (index) {
                return index === this.localData.length - 1 && this.localData.length % 2 !== 0 ? 'xs12' : 'xs6'
            },

            // Open the dialog
            // If data is null then in editing mode
            open (data) {
                this.showDialog = true;

                if (data) {
                    this.localData = data;
                    this.isEdit = true;
                } else {
                    this.clear();
                }
            },

            // Closes the dialog and clears all fields
            close() {
                this.showDialog = false;
                this.clear()
                this.$validator.reset()
            },

            // Save action
            // Validates all fields then emits data to parent
            save() {
                this.$validator.validateAll().then((result) => {
                    if (result) {
                        this.$emit("save", this.localData);
                        this.close();
                    }
                });
            },

            // Edit action
            // Validates all fields then emits data to parent
            edit() {
                this.$validator.validateAll().then((result) => {
                    if (result) {
                        this.$emit("edit", this.localData);
                        this.close();
                    }
                });
            },

            // Clears data and toggles edit mode
            clear() {
                this.localData.forEach(data => {
                    data.value = null
                })
                this.isEdit = false;
            }
        }
    }
</script>

<style scoped>

</style>
