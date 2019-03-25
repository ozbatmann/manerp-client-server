<!-- MDataTableAddNeForm -->
<!-- Will be used for saving and editing data -->
<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">

    <!-- Base dialog object -->
    <!-- This object will contain all field -->
    <v-dialog
        v-model="showDialog"
        max-width="750"
        persistent
        @keydown.enter.prevent="save"
        @keydown.esc.stop="close"
    >
        <v-card>

            <!-- Title of the dialog -->
            <v-card-title class="headline font-weight-light py-2">
                {{ title }}
                <v-spacer></v-spacer>

                <!-- Clear all button -->
                <v-btn
                        flat
                        color="error"
                        :disabled="clearAllDisabled"
                        @click="clear"
                >
                    <v-slide-x-reverse-transition>
                        <v-icon
                                v-if="!clearAllDisabled"
                                left
                                size="20"
                                class="mr-2"
                        >
                            delete
                        </v-icon>
                    </v-slide-x-reverse-transition>
                    temizle
                </v-btn>
            </v-card-title>

            <v-divider></v-divider>
            <!-- Form element -->
            <v-form>
                <v-layout row wrap px-3 pt-4>
                    <v-flex
                            v-for="(field, index) in inputs"
                            :key="`add-edit-field-${index}`"
                            :class="flexSize(index)"
                            px-2
                    >
                        <!-- Checkbox inputs -->
                        <div v-if="field.type === input__types.checkbox">
                            <v-checkbox
                                    v-for="(prop, index) in field.props"
                                    v-model="localData[field.key]"
                                    :key="`add-edit-checkbox-${index}`"
                                    :data-vv-name="field.value"
                                    :label="prop"
                                    class="m-input-capitalize"
                                    color="green accent-2"
                                    hide-details
                            ></v-checkbox>
                        </div>

                        <!-- Select inputs -->
                        <div v-else-if="field.type === input__types.select">
                            <v-select
                                    v-model="localData[field.key]"
                                    :data-vv-name="field.key"
                                    :items="field.props"
                                    :label="field.title"
                                    :error-messages="errors.collect(field.key)"
                                    v-validate="'required'"
                                    class="m-input-capitalize"
                                    background-color="grey lighten-4"
                                    color="green accent-2"
                                    solo
                                    flat
                                    required
                            ></v-select>
                        </div>

                        <!-- Text-field inputs -->
                        <div v-else>
                            <v-text-field
                                    v-model="localData[field.key]"
                                    :data-vv-name="field.key"
                                    :type="field.type"
                                    :label="field.title"
                                    :mask="field.mask"
                                    :error-messages="errors.collect(field.key)"
                                    :v-validate="validate(field.rules)"
                                    :counter="field.max"
                                    class="m-input-capitalize  "
                                    clearable
                                    background-color="grey lighten-4"
                                    color="black"
                                    solo
                                    flat
                                    required
                            ></v-text-field>
                        </div>
                    </v-flex>
                </v-layout>
            </v-form>

            <v-divider></v-divider>

            <!-- Action buttons -->
            <v-card-actions class="py-3">
                <v-spacer></v-spacer>

                <!-- Cancel button -->
                <v-btn
                        depressed
                        flat="flat"
                        class="my-0"
                        @click="close"
                >
                    iptal
                </v-btn>

                <!-- Save button -->
                <v-btn
                        depressed
                        :disabled="clearAllDisabled"
                        color="primary"
                        class="my-0"
                        flat
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
            // Will hold values
            data: {
                type: Object,
                default: null
            },

            // Inputs array
            // Will define fields
            inputs: {
                type: Array,
                default: null
            },

            // Title of the dialog
            title: {
                type: String,
                default: null
            }
        },

        data () {
            return {
                // Copy of the data prop
                // Object.assign does the shallow copying
                localData: Object.assign({}, this.data),

                // Input type enumeration
                input__types: {
                    checkbox: 'checkbox',
                    select: 'select',
                },

                // A Boolean indicating
                // whether dialog is active or not
                showDialog: false,

                // A Boolean indicating
                // whether dialog is in edit mode or not
                isEdit: false,
            }
        },

        computed: {
            // Toggles clear all
            // button if inputs are not null
            clearAllDisabled () {
                return Object.values(this.localData).every(value => {
                    return value === null ? true : value.length < 1
                });
            }
        },

        methods: {
            // Parses validation rules
            validate (rules) {
                if (rules === undefined) return null;
                else {
                    let finalRules = '';

                    rules.forEach(rule => {
                        finalRules += `${rule}|`
                    });

                    return finalRules
                }
            },

            // Determines flex size
            // based on field size (odd => xs12, even => xs6)
            flexSize (index) {
                return index === this.inputs.length - 1 && this.inputs.length % 2 !== 0 ? 'xs12' : 'xs6'
            },

            // Open the dialog
            // If data is null then in editing mode
            open (data) {
                this.clear()
                this.showDialog = true;

                if (data) {
                    this.localData = Object.assign({}, data);
                    this.isEdit = true;
                } else {
                    this.clear();
                }
            },

            // Closes the dialog and clears all inputs
            close () {
                this.showDialog = false;
                this.$validator.reset()
            },

            // Save action
            // Validates all inputs then emits data to parent
            save () {
                this.$validator.validateAll().then((result) => {
                    if (result) {
                        this.$emit("save", this.localData);
                        this.close();
                    }
                });
            },

            // Edit action
            // Validates all inputs then emits data to parent
            edit () {
                this.$validator.validateAll().then((result) => {
                    if (result) {
                        this.$emit("edit", this.localData);
                        this.close();
                    }
                });
            },

            // Clears data and toggles edit mode
            clear () {
                Object.keys(this.localData).forEach(key => {
                    this.localData[key] = null
                });

                this.isEdit = false;
            }
        }
    }
</script>

<style scoped>

</style>
