<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div>
        <v-layout row align-center>

            <!-- Data-table header slot -->
            <slot name="header"></slot>
            <v-spacer></v-spacer>

            <!-- Filter menu -->
            <!-- Will be shown if filtering mode is on-->
            <m-data-table-filter-menu
                    v-if="!noFiltering"
                    v-model="filterMenuShowing"
                    attach="#filterButton"
                    :headers="headers"
                    @options="filterTable"
            ></m-data-table-filter-menu>

            <!-- Filter menu button -->
            <!-- Will be shown if filtering mode is on -->
            <m-data-table-action
                    v-if="!noFiltering"
                    @click="filterMenuShowing = !filterMenuShowing"
                    title="FİLTRE"
                    icon="filter_list"
                    id="filterButton"
                    :class="filterButtonClass"
            ></m-data-table-action>
        </v-layout>

        <!-- Filter chips section -->
        <!-- Will be shown if filtering mode is on -->
        <v-layout
                align-center
                v-if="!noFiltering"
        >
            <v-flex pa-0>

                <!-- Search in table chip -->
                <v-chip
                        v-if="filterOptions.search !== undefined"
                        v-model="filterOptions.search.chip"
                        class="mx-0 mr-1"
                        height="56"
                        close
                >
                    {{ filterOptions.search.value }}
                </v-chip>

                <!-- Filter date option chip -->
                <v-chip
                        v-if="filterOptions.date !== undefined"
                        v-model="filterOptions.date.chip"
                        class="mx-0 mr-1"
                        height="56"
                        close
                >
                    <!-- Start date -->
                    {{ formatDate(filterOptions.date.start) }}
                    <v-icon
                            size="16"
                            class="mx-1 black--text text--accent-3"
                    >
                        arrow_forward
                    </v-icon>

                    <!-- End date -->
                    {{ formatDate(filterOptions.date.end) }}
                </v-chip>

                <!-- Filter column search chips -->
                <v-chip
                        v-for="(header, index) in columnWiseSearchChips"
                        :key="`filter-chip-item-${index}`"
                        v-model="header.search.chip"
                        class="mx-1"
                        height="56"
                        close
                >
                    {{ header.search.value }}
                </v-chip>
            </v-flex>
        </v-layout>

        <!-- Data table section -->
        <v-layout mt-2>
            <v-flex>

                <!-- Data table structure -->
                <v-data-table
                        v-model="selected"
                        :headers="headers"
                        :items="items"
                        :search="dataTableSearchModel"
                        :pagination.sync="pagination"
                        select-all
                        item-key="id"
                        style="border: 1px solid #f4f4f4;"
                >
                    <!-- Template for data table header -->
                    <template v-slot:headers="props">
                        <tr>
                            <th>
                                <!-- Checkbox -->
                                <!-- Selects all data table rows -->
                                <v-checkbox
                                        primary
                                        hide-details
                                        :input-value="props.all"
                                        :indeterminate="props.indeterminate"
                                        color="green accent-2"
                                        @click.stop="toggleAll"
                                ></v-checkbox>
                            </th>

                            <!-- Item headers -->
                            <th
                                    v-for="(header, index) in props.headers"
                                    v-show="header.show"
                                    :class="dataTableHeaderClass(header)"
                                    class="text-uppercase"
                                    :key="`data-table-header-item-${index}`"
                                    @click="changeSort(header.value)"
                            >
                                <!-- Header text -->
                                <span class="text-xs-left">
                                    {{ header.text }}
                                </span>

                                <!-- Header sort icon -->
                                <v-icon
                                        v-show="header.sortable"
                                        small
                                >
                                    arrow_upward
                                </v-icon>
                            </th>
                        </tr>
                    </template>

                    <!-- Template for data table rows -->
                    <template v-slot:items="props">
                        <td>
                            <!-- Checkbox for row selection -->
                            <v-checkbox
                                    :input-value="props.selected"
                                    primary
                                    hide-details
                                    color="green accent-2"
                            ></v-checkbox>
                        </td>
                        <td
                                v-for="(key, index) in Object.keys(props.item)"
                                v-show="shouldColumnShown(key)"
                                :key="`data-table-row-item-${index}`"
                                class="text-xs-left m-data-table-row"
                                @click="goTo(props.item)"
                        >
                            {{ props.item[key] }}
                        </td>
                        <td class="text-xs-right">
                            <!-- Row action menu -->
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

                                    <!-- Slot for adding extra options -->
                                    <!-- from parent component -->
                                    <slot
                                            name="action-menu"
                                            :bind="props.item"
                                    ></slot>
                                    <v-list-tile>Sil</v-list-tile>
                                    <v-list-tile>Dışa aktar</v-list-tile>
                                </v-list>
                            </v-menu>
                        </td>
                    </template>

                    <!-- Template for no results -->
                    <template v-slot:no-results>
                        <v-alert :value="true" color="error" icon="warning" class="my-4">
                            Sonuç bulunamadı
                        </v-alert>
                    </template>
                </v-data-table>
            </v-flex>
        </v-layout>
    </div>
</template>

<script>
    import moment from 'moment'
    import MDataTableAction from "@/modules/main/shared/components/data/components/MDataTableAction";
    import MDataTableFilterMenu from "@/modules/main/shared/components/data/components/MDataTableFilterMenu";

    export default {
        name: "MDataTable",
        components: {MDataTableFilterMenu, MDataTableAction},
        props: {

            // A Boolean indicating
            // whether filter mode is enabled or not
            noFiltering: {
                type: Boolean,
                default: false
            },

            // An Array that contains
            // data table headers
            headers: {
                type: Array
            },

            // An Array that contains
            // data table items
            items: {
                type: Array
            },

            // Route of the row click
            to: {
                type: Object
            }
        },

        data() {
            return {
                // A Boolean indicating whether
                // Filter menu is active or not
                filterMenuShowing: false,

                // Receives filtering options
                // from filter-table
                filterOptions: {},

                // Pagination options of data table
                pagination: {
                    sortBy: 'name'
                },

                // An Array that
                // contains selected rows
                selected: [],

                // Holds search text and
                // feeds data table :search prop
                search: null,
            }
        },

        computed: {
            // Filters headers and returns
            // the ones with search.value
            columnWiseSearchChips () {
                return this.headers.filter(item => {
                    return item.search !== undefined && item.search.chip
                })
            },

            // Data-table search model
            // If search value is undefined will
            // return null to keep data-table satisfied
            dataTableSearchModel () {
                return this.filterOptions.search === undefined
                    ? null : this.filterOptions.search.value
            },

            // Filter button class
            // Gives background color and shadow effect
            filterButtonClass() {
                return this.filterMenuShowing ?
                    ['v-shadow-lg', 'deep-purple', 'white--text text--accent-3'] : ''
            },
        },

        methods: {
            // Copies an array with given predicate
            copy (array, predicate) {
                return array.map(item => {
                    return predicate(item)
                })
            },

            // Header classes based on
            // data-table header options
            dataTableHeaderClass(header) {
                return ['column sortable',
                    this.pagination.descending ? 'desc' : 'asc',
                    header.value === this.pagination.sortBy ? 'active' : '',
                    header.sortable ? 'text-xs-left' : '']
            },

            // Welcomes filter emit
            filterTable(newValue) {
                this.filterOptions = newValue
            },

            // Formats given date
            formatDate(date) {
                return date ? moment(date).locale('tr').format('ddd, Do MMM YYYY') : ''
            },

            // Appends item to route object
            goTo(item) {
                let routeObject = this.to

                if (routeObject.params === undefined)
                    routeObject['params'] = {}

                routeObject.params['id'] = item.id
                routeObject.params['item'] = item

                this.$router.push(routeObject)
            },

            // Decides whether column should be shown
            // Based on data-table toggleable selections
            shouldColumnShown(key) {
                let header = this.headers.find(item => item.value === key)
                return header.show
            },

            // Toggles all rows in the data-table
            toggleAll() {
                if (this.selected.length) this.selected = []
                else this.selected = this.items.slice()
            },

            // Changes sort direction
            changeSort(column) {
                if (this.pagination.sortBy === column) {
                    this.pagination.descending = !this.pagination.descending
                } else {
                    this.pagination.sortBy = column
                    this.pagination.descending = false
                }
            },
        },
    }
</script>

<style scoped>

</style>
