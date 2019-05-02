<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">

    <!-- Filter menu -->
    <!-- #---------# -->
    <v-menu
            v-model="isActive"
            :attach="attach"
            right
            light
            z-index=5
            nudge-right=135
            :nudge-width="300"
            :close-on-content-click="false"
            transition="slide-x-reverse-transition"
            content-class="v-shadow-lg filterMenu"
    >
        <v-card elevation="0" light class="accent-3">
            <v-layout column>
                <div class="mt-1 mx-2">

                    <!-- Search text-field -->
                    <!-- Filters data table -->
                    <v-text-field
                            placeholder="Tabloda arayın"
                            v-model="options.search.value"
                            flat
                            solo
                            autofocus
                            hide-details
                            color="green accent-3"
                            background-color="transparent"
                            prepend-inner-icon="search"
                            class="font-weight-regular"
                            @focus="datePickerStatus = null"
                    ></v-text-field>
                </div>
                <v-flex>

                    <!-- ExpansionPanel component -->
                    <!-- Contains Checkboxes representing headers -->
                    <!-- Filters data table headers -->
                    <v-expansion-panel
                            v-model="currentExpandedPanel"
                            class="elevation-0"
                    >
                        <!-- Date filter section -->
                        <v-expansion-panel-content class="accent-3">

                            <!-- DatePicker dialog -->
                            <!-- Contains DatePicker component -->
                            <v-menu
                                    v-model="datePickerStatus"
                                    max-width="290"
                                    min-width="290"
                                    :attach="attach"
                                    right
                                    offset-x
                                    nudge-right=445
                                    :close-on-content-click="false"
                                    :close-on-click="false"
                                    transition="slide-x-reverse-transition"
                                    content-class="v-shadow-lg text-capitalize"
                                    z-index=5
                                    dark
                            >

                                <!-- DatePicker component -->
                                <v-date-picker
                                        v-model="currentPicker"
                                        scrollable
                                        actions
                                        header-color="deep-purple darken-1"
                                        color="primary-green"
                                        class="m-picker--dark"
                                        :first-day-of-week="1"
                                        :min="minDateValue"
                                        :max="maxDateValue"
                                        locale="tr-tr"
                                ></v-date-picker>
                            </v-menu>

                            <!-- Template for ExpansionPanel header -->
                            <template v-slot:header>

                                <!-- Panel title -->
                                <div class="v-label">
                                    TARİHE GÖRE
                                </div>
                            </template>
                            <v-layout
                                    row
                                    py-1
                                    px-2
                                    align-center
                            >
                                <v-flex class="mb-1 pl-1">

                                    <!-- Wraps text-field and emits click -->
                                    <div @click.prevent="toggleDatePicker(date__mode.START)">

                                        <!-- startDate tooltip -->
                                        <v-tooltip bottom>
                                            <template v-slot:activator="{ on }">

                                                <!-- Text-field component for startDate -->
                                                <!-- Only shows selected startDate in readonly mode -->
                                                <v-text-field
                                                        v-on="on"
                                                        id="startDatePicker"
                                                        v-model="formattedStartDate"
                                                        hide-details
                                                        flat
                                                        solo
                                                        readonly
                                                        height="36"
                                                        placeholder="Başlangıç tarihi"
                                                        color="green accent-3"
                                                        background-color="transparent"
                                                        class="font-weight-regular m-filter-date"
                                                        :class="activeDateClass(date__mode.START)"
                                                        @input="datePicked"
                                                ></v-text-field>
                                            </template>

                                            <!-- Tooltip title -->
                                            <span>Başlangıç tarihi</span>
                                        </v-tooltip>
                                    </div>
                                </v-flex>
                                <v-flex shrink>
                                    <v-icon size="16">arrow_forward</v-icon>
                                </v-flex>
                                <v-flex class="mb-1 pr-1">
                                    <div @click.prevent="toggleDatePicker(date__mode.END)">

                                        <!-- endDate tooltip -->
                                        <v-tooltip bottom>
                                            <template v-slot:activator="{ on }">

                                                <!-- Text-field component for startDate -->
                                                <!-- Only shows selected startDate in readonly mode -->
                                                <v-text-field
                                                        v-model="formattedEndDate"
                                                        v-on="on"
                                                        hide-details
                                                        flat
                                                        solo
                                                        readonly
                                                        height="36"
                                                        color="primary-green"
                                                        background-color="transparent"
                                                        placeholder="Bitiş tarihi"
                                                        class="font-weight-regular m-filter-date"
                                                        :class="activeDateClass(date__mode.END)"
                                                        @input="datePicked"
                                                ></v-text-field>
                                            </template>
                                            <!-- Tooltip title -->
                                            <span>Bitiş tarihi</span>
                                        </v-tooltip>
                                    </div>
                                </v-flex>
                            </v-layout>
                        </v-expansion-panel-content>

                        <!-- Column search section -->
                        <v-expansion-panel-content class="accent-3">

                            <!-- Template for ExpansionPanel header -->
                            <template v-slot:header>

                                <!-- Panel title -->
                                <div class="v-label">
                                    Başlığa göre
                                </div>
                            </template>
                            <v-layout
                                    wrap
                                    pb-1
                                    px-2
                            >
                                <v-flex
                                        v-for="(header, index) in searchableHeaders"
                                        :key="`data-table-header-search-item-${index}`"
                                        xs6
                                >
                                    <!-- Text-fields for detailed search -->
                                    <v-text-field
                                            v-model="header.search.value"
                                            :placeholder="header.text"
                                            solo
                                            flat
                                            clearable
                                            single-line
                                            hide-details
                                            height="40"
                                            color="green accent-3"
                                            background-color="transparent"
                                            class="font-weight-regular ma-1 m-filter-input m-input-capitalize"
                                            @input="inputColumnSearchField(header.search)"
                                            @click:clear="clearColumnSearchField(header.search)"
                                    ></v-text-field>
                                </v-flex>
                            </v-layout>
                        </v-expansion-panel-content>

                        <!-- Filter columns section -->
                        <v-expansion-panel-content
                                v-if="toggleableHeaders.length > 0"
                                class=" accent-3"
                        >

                            <!-- Template for ExpansionPanel header -->
                            <template v-slot:header>
                                <div>

                                    <!-- Panel title -->
                                    <div class="v-label">
                                        Sütunları FİLTRELE
                                    </div>
                                </div>
                            </template>
                            <v-layout
                                    wrap
                                    px-4
                                    pt-3
                            >
                                <v-flex
                                        v-for="(header, index) in toggleableHeaders"
                                        :key="`column-filter-item-${index}`"
                                >
                                    <!-- Checkbox items -->
                                    <!-- Toggles data table columns -->
                                    <v-checkbox
                                            v-model="header.show"
                                            :label="header.text"
                                            class="mt-0 text-capitalize font-weight-regular"
                                            color="green accent-2"
                                            :ref="`toggleable-header-item-${index}`"
                                    ></v-checkbox>
                                </v-flex>
                            </v-layout>
                        </v-expansion-panel-content>
                    </v-expansion-panel>
                </v-flex>
                <v-flex ma-1>

                    <!-- Column search button -->
                    <v-btn
                            flat
                            block
                            class="ma-0"
                            color="primary-green"
                            @click.stop="filter(options)"
                    >
                        TAMAM
                    </v-btn>
                </v-flex>
            </v-layout>
        </v-card>
    </v-menu>
</template>

<script>
    import moment from 'moment'

    export default {
        name: "MDataTableFilterMenu",

        props: {
            // DOM object which filter-menu
            // will be attached to
            attach: {
                type: String,
                default: null
            },

            // A Function that will be
            // triggered when #filterButton is clicked
            filter: {
                type: Function
            },

            // An Array that contains data-table headers
            // Used for filtering which columns
            // will be shown and column-wise search
            headers: {
                type: Array,
            },

            // A Boolean indicating filter-menu states
            // v-model prop
            value: {
                type: Boolean,
                default: false
            },
        },

        data() {
            return {
                // Holds current expanded
                // panel information for activating
                // or deactivating date-picker menu
                currentExpandedPanel: null,

                // Date mode enumeration
                date__mode: {
                    START: 0,
                    END: 1
                },

                // A Boolean indicating whether
                // FilterMenu is active or not
                isActive: this.value,

                // Filter-menu options object
                // This object will communicate with
                // parent data-table
                options: {

                    // Holds search information
                    // inside the table
                    search: {
                        chip: false,
                        value: null
                    },

                    // Holds Date information in a range structure
                    // `mode` indicates whether
                    // start date or end date is active
                    date: {
                        start: null,
                        end: null,
                        mode: 0, // date__mode.START
                        chip: false
                    },
                },

                // Today' s date reference
                // Will restrict end date' s max prop
                today: new Date().toISOString().substr(0, 10),
            }
        },

        computed: {

            // Returns current picker mode
            // Sets new date value by mode
            currentPicker: {
                set (newValue) {
                    this.options.date.mode === this.date__mode.START ?
                        this.options.date.start = newValue : this.options.date.end = newValue
                },

                get () {
                    return this.options.date.mode === this.date__mode.START ?
                        this.options.date.start : this.options.date.end
                }
            },

            // Shows date-picker
            // if date panel is expanded
            datePickerStatus: {
                set () {
                    return this.currentExpandedPanel = null
                },

                get () {
                    return this.currentExpandedPanel === 0
                }
            },

            // Formatted start date value
            formattedStartDate () {
                return this.formatDate(this.options.date.start)
            },

            // Formatted end date value
            formattedEndDate () {
                return this.formatDate(this.options.date.end)
            },

            // Maximum selectable
            // date for end date
            maxDateValue() {
                return this.options.date.mode === this.date__mode.START ? this.options.date.end ?
                    this.options.date.end : this.today : this.today
            },

            // Minimum selectable
            // date for start date
            minDateValue() {
                return this.options.date.mode === this.date__mode.START ? '' : this.options.date.start
            },

            // Column-wise search
            // enabled headers array
            searchableHeaders () {
                if (this.headers === null) return null;

                return this.headers.filter(header => {
                    return header.search !== undefined
                        && header.show
                })
            },

            // Toggle enabled headers
            toggleableHeaders () {
                if (this.headers === null) return null;

                return this.headers.filter(header => {
                    return header.toggleable
                })
            }
        },

        methods: {
            // Returns active class
            // relatively to date.mode
            activeDateClass (mode) {
                return this.options.date.mode === mode ? 'm-active-date' : ''
            },

            // Changes chip status
            // when text-field clear is clicked
            clearColumnSearchField(searchObject) {
                searchObject.chip = false
            },

            // Activates date chip
            // that's in the parent table
            datePicked () {
                this.options.date.chip = true
            },

            // Formats given date
            formatDate (date) {
                return date ? moment(date).locale('tr').format('ddd, Do MMM YYYY') : ''
            },

            // Changes chip status
            // if there is an input or not
            inputColumnSearchField(searchObject) {
                searchObject.chip = searchObject.value !== null
                    && searchObject.value.length > 0
            },

            // Toggles date-picker mode
            toggleDatePicker(mode) {
                this.options.date.mode = mode;
                this.currentExpandedPanel = 0
            },
        },

        watch: {
            // Watches filter menu v-model
            // and informs parent when state changes
            isActive () {
                this.$emit('input', this.isActive)
            },


            // Watches table search
            // and sets chip state
            'options.search.value' (newValue) {
                this.options.search.chip = newValue !== null
                    && newValue.length > 0;
            },

            // Watches table search chip
            // and clears text-field if needed
            'options.search.chip' (newValue) {
                if (!newValue) this.options.search.value = null
            },

            'options.date.start' (newValue) {
                if (newValue) this.options.date.chip = true;
            },

            'options.date.end' (newValue) {
                if (newValue) this.options.date.chip = true;
            },

            'options.date.chip' (newValue) {
                if (!newValue) {
                    this.options.date.start = null;
                    this.options.date.end = null;
                    this.options.date.mode = this.date__mode.START;
                }
            },

            // Watches filter menu for
            // changing menu state from outside
            value () {
                this.isActive = this.value
            },
        }
    }
</script>

<style scoped>
    .v-input,
    .v-label {
        font-size: 14px !important;
    }

    .v-icon {
        font-size: 20px !important;
    }

    .v-expansion-panel__header {
        min-height: 40px !important;
        height: 40px !important;
    }
</style>
