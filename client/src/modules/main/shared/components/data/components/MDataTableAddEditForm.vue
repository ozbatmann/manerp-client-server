<template>
    <v-dialog
            v-model="show"
            lazy
            persistent
            scrollable
            max-width="820"
            @keydown.enter.prevent="save"
            @keydown.esc.stop="show = false"
            content-class="transition-height"
    >
        <v-card>
            <v-card-title
                    :class="[ !tabbed ? 'py-2' : 'pt-2 pb-1' ]"
                    class="pr-1"
            >
                <v-layout align-center>

                    <!-- Dialog title -->
                    <v-flex>
                        <h4 class="headline font-weight-light text-capitalize">{{title}}</h4>
                    </v-flex>
                    <v-spacer></v-spacer>

                    <!-- Dialog close button -->
                    <v-flex shrink>
                        <v-btn
                                icon
                                @click="show = false"
                        >
                            <v-icon>close</v-icon>
                        </v-btn>
                    </v-flex>
                </v-layout>
            </v-card-title>
            <v-divider v-if="!tabbed"></v-divider>
            <v-layout
                    v-if="tabbed"
                    wrap
            >
                <v-flex xs12 pl-3>
                    <v-tabs
                            v-model="active"
                            slider-color="primary-green"
                            show-arrows
                    >
                        <v-tab
                                v-for="(tab, index) in tabs"
                                :key="`dialog-tab-item-${index}`"
                                ripple
                        >
                            {{ tab }}
                        </v-tab>
                    </v-tabs>
                </v-flex>
                <v-flex xs12>
                    <v-divider></v-divider>
                </v-flex>
            </v-layout>
            <v-card-text class="transition-height">
                    <div v-if="active === 0">
                        <slot name="form"></slot>
                    </div>
                    <div
                            v-for="(tab, index) in tabs"
                            v-show="index + 1 === active"
                            :key="`tab-${index}`"
                    >
                        <slot :name="`tab-${index + 2}`"></slot>
                    </div>
            </v-card-text>
            <v-divider></v-divider>
            <v-card-actions>
                <v-layout pa-2 justify-end>
                    <v-flex shrink>
                        <!-- Clear all -->
                        <v-btn
                                depressed
                                @click="clear"
                        >
                            HEPSİNİ TEMİZLE
                        </v-btn>

                        <!-- Save button -->
                        <v-btn
                                depressed
                                color="deep-purple white--text mr-0"
                                @click="save"
                        >
                            {{ saveButtonTitle }}
                        </v-btn>
                    </v-flex>
                </v-layout>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script>
    export default {
        name: "MDataTableAddEditForm",

        props: {
            // Title of the dialog
            title: {
                type: String,
                default: null
            },

            // V-model variable
            value: {
                type: Boolean,
                default: false
            },

            editing: {
                type: Boolean,
                default: false
            },

            tabbed: {
                type: Boolean,
                default: false
            },

            tabs: {
                type: Array,
            },

            nextRule: {
                type: [Function, Object, Boolean]
            }
        },

        data() {
            return {
                active: 0,
                edit: false,
                next: this.nextRule,
                show: this.value,
            }
        },

        computed: {
            saveButtonTitle() {
                // return this.tabbed && this.active !== this.tabs.length - 1 ? 'SONRAKİ' :
                return 'KAYDET';
            }
        },

        methods: {
            enableTab () {
                if (this.next instanceof Function) {
                    return this.next();
                } else if (this.next instanceof Object) {
                    this.veeFields = this.next;

                    this.$validator.validateAll(valid => {
                        return valid;
                    });
                }

                return this.next;
            },

            clear() {
                this.$emit('clear');
            },

            save() {
                // if (this.tabbed && this.active !== this.tabs.length - 1) {
                //     if (this.enableTab()) this.active += 1;
                // } else
                    this.$emit(this.edit ? 'edit' : 'save');
            }
        },

        watch: {
            active(newVal) {
                this.$emit('activeTab', newVal);
            },

            editing(newVal) {
                this.edit = newVal;
            },

            nextRule(newVal) {
                this.next = newVal;
            },

            show(newVal) {
                this.$emit('input', newVal);
            },

            value(newVal) {
                this.show = newVal;
            }
        }
    }
</script>

<style scoped>
    .transition-height {
        -webkit-transition: height 300ms ease-in-out;
        -moz-transition: height 300ms ease-in-out;
        -ms-transition: height 300ms ease-in-out;
        -o-transition: height 300ms ease-in-out;
        transition: height 300ms ease-in-out;
    }
</style>
