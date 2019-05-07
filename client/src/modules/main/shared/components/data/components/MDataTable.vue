<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div>
        <v-layout>
            <v-flex>
                <v-alert
                        v-if="alertText"
                        :value="showAlert"
                        type="info"
                        dismissible
                >
                    {{alertText}}
                </v-alert>
            </v-flex>
        </v-layout>
        <v-layout row align-center>


            <!-- Filter menu -->
            <!-- Will be shown if filtering mode is on-->
            <m-data-table-filter-menu
                    v-if="!noFiltering"
                    v-model="filterMenuShowing"
                    attach="#filterButton"
                    :headers="headers"
                    :filter="filterTable"
            ></m-data-table-filter-menu>

            <!-- Filter menu button -->
            <!-- Will be shown if filtering mode is on -->
            <m-data-table-action
                    v-if="!noFiltering"
                    title="FİLTRELE"
                    icon="filter_list"
                    id="filterButton"
                    background="white"
                    text-color="black"
                    :class="filterButtonClass"
                    @click="filterMenuShowing = !filterMenuShowing"
            ></m-data-table-action>

            <!--<v-flex shrink ml-2>-->
                <!--<v-icon size="18">sort</v-icon>-->
            <!--</v-flex>-->

            <!--&lt;!&ndash; Data-table header slot &ndash;&gt;-->
            <!--<v-flex shrink mx-2>-->
                <!--<h4 class="subheading d-inline-block ml-2">Toplam 3.550 kayıt</h4>-->
                <!--<span v-if="false" class="primary-green&#45;&#45;text"> - 45 seçildi</span>-->
            <!--</v-flex>-->
            <v-spacer></v-spacer>

            <m-data-table-action
                    title="sil"
                    icon="delete_sweep"
                    class="mr-2"
                    color="red"
                    background="white"
                    text-color="black"
                    :disabled="!selected.length"
            ></m-data-table-action>

            <m-data-table-action
                    title="dışa aktar"
                    icon="import_export"
                    :class="{ 'mr-2' : !noImport }"
                    color="primary-green"
                    background="white"
                    text-color="black"
                    :disabled="!selected.length"
                    @click="onexport"
            ></m-data-table-action>

            <!-- Import xlsx file -->
            <input
                    v-if="!noImport"
                    type="file"
                    ref="file"
                    accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                    style="display: none"
                    :disabled="!!selected.length"
                    @change="onimport($event)"
            >

            <m-data-table-action
                    v-if="!noImport"
                    title="içe aktar"
                    icon="import_export"
                    :class="{ 'mr-2' : !!this.$slots.header }"
                    color="primary-green"
                    background="white"
                    text-color="black"
                    :disabled="!!selected.length"
                    @click="$refs.file.click()"
            ></m-data-table-action>

            <slot name="header"></slot>
        </v-layout>

        <!-- Filter chips section -->
        <!-- Will be shown if filtering mode is on -->
        <v-layout
                align-center
                v-if="!noFiltering"
                mt-1
                mb-2
        >
            <v-flex>
                <!-- Search in table chip -->
                <v-chip
                        v-if="filterOptions.search !== undefined"
                        v-model="filterOptions.search.chip"
                        class="ma-0 mr-2"
                        height="56"
                        close
                        label
                >
                    {{ filterOptions.search.value }}
                </v-chip>

                <!-- Filter date option chip -->
                <v-chip
                        v-if="filterOptions.date !== undefined"
                        v-model="filterOptions.date.chip"
                        class="ma-0 mr-2"
                        height="56"
                        close
                        label
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
                        class="mx-1 my-0"
                        height="56"
                        close
                        label
                >
                    {{ header.search.value }}
                </v-chip>
            </v-flex>
        </v-layout>

        <!-- Data table section -->
        <v-layout white>
            <v-flex>

                <!-- Data table structure -->
                <v-data-table
                        v-model="selected"
                        :headers="headers"
                        :items="items"
                        :search="dataTableSearchModel"
                        :pagination.sync="pagination"
                        :loading="localLoading"
                        select-all
                        item-key="id"
                        class="m-data-table"
                >
                    <!-- Template for data table header -->
                    <template v-slot:headers="props">
                        <tr>
                            <th class="m-data-table-sticky-col-left">
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

                            <th class="m-data-table-sticky-col-right">
                                #
                            </th>
                        </tr>
                    </template>

                    <!-- Template for data table rows -->
                    <template v-slot:items="props">
                        <tr>
                            <td class="m-data-table-sticky-col-left">

                                <!-- Checkbox for row selection -->
                                <v-checkbox
                                        v-model="props.selected"
                                        :input-value="props.selected"
                                        primary
                                        hide-details
                                        color="green accent-2"
                                ></v-checkbox>
                            </td>
                            <td
                                    v-for="(header, index) in headers"
                                    v-show="header.show"
                                    :key="`data-table-row-item-${index}`"
                                    class="text-xs-left m-data-table-row"
                                    @click="goTo(props.item)"
                            >
                                <span v-html="field(props.item[header.value])"></span>

                                <!-- Expand button -->
                                <!-- Will be shown only if cell is expandable -->
                                <v-btn
                                        v-if="header.expandable"
                                        icon
                                        @click="props.expanded = !props.expanded"
                                >
                                    <v-icon size="16">{{expandIcon(props.expanded)}}</v-icon>
                                </v-btn>
                            </td>
                            <td class="text-xs-right m-data-table-sticky-col-right">

                                <!-- Row action menu -->
                                <v-menu
                                        left
                                        offset-x
                                        :nudge-width="140"
                                        content-class="v-shadow-lg"
                                        transition="slide-x-reverse-transition"
                                        :disabled="selected.length !== 0"
                                >
                                    <!-- Row action button -->
                                    <!-- Activates the menu -->
                                    <v-btn
                                            :disabled="selected.length !== 0"
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
                                        <v-list-tile
                                                v-if="!noEdit"
                                                @click="editItem(props.item)"
                                        >
                                            Düzenle
                                        </v-list-tile>
                                        <v-list-tile
                                                v-if="!noDelete"
                                                @click="deleteItem(props.item)"
                                        >
                                            Sil
                                        </v-list-tile>
                                    </v-list>
                                </v-menu>
                            </td>
                        </tr>
                    </template>

                    <!-- Template for expansion slot -->
                    <template v-slot:expand="props">
                        <slot name="expand" :bind="props"></slot>
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
    import moment from 'moment';
    import XLSX from 'xlsx';
    import MDataTableAction from "@/modules/main/shared/components/data/components/MDataTableAction";
    import MDataTableFilterMenu from "@/modules/main/shared/components/data/components/MDataTableFilterMenu";

    export default {
        name: "MDataTable",
        components: {MDataTableFilterMenu, MDataTableAction},
        props: {

            alertText: {
                type: String,
                default: null,
            },

            noImport: {
                type: Boolean,
                default: false
            },

            // A Boolean indicating
            // whether filter mode is enabled or not
            noFiltering: {
                type: Boolean,
                default: false
            },

            noEdit: {
                type: Boolean,
                default: false
            },

            noDelete: {
                type: Boolean,
                default: false
            },

            // A Boolean indicating
            // whether rows are expandable or not
            expand: {
                type: Boolean,
                default: false
            },

            // An Array that contains
            // data table headers
            headers: {
                type: Array,
                default: null
            },

            // An Array that contains
            // data table items
            items: {
                type: Array,
                default: null
            },

            loading: {
                type: Boolean,
                default: true
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

                // Loading state of the data-table
                localLoading: this.loading,

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

                showAlert: true,

                tempItems: [
                    {
                        id: 0,
                        name: 'Temp Item',
                        order: 1
                    },
                    {
                        id: 1,
                        name: 'Another Temp Item',
                        order: 2
                    }
                ]
            }
        },

        computed: {
            // Filters headers and returns
            // the ones with search.value
            columnWiseSearchChips() {
                if (this.headers === null) return null;

                return this.headers.filter(item => {
                    return item.search !== undefined && item.search.chip
                })
            },

            // Data-table search model
            // If search value is undefined will
            // return null to keep data-table satisfied
            dataTableSearchModel() {
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
            copy(array, predicate) {
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

            // Changes expandable icon
            // relatively to expand state
            expandIcon (expanded) {
                return expanded ? 'arrow_drop_up' : 'arrow_drop_down'
            },

            // Checks whether item has name prop or not
            // Used for differentiating sysRefs and regular text props
            field (item) {
                return item ? item.name ? item.name : item : '-'
            },

            // Welcomes filter emit
            filterTable(newValue) {
                this.filterOptions = newValue;
                this.filterMenuShowing = false;
                this.showAlert = false;
            },

            // Formats given date
            formatDate(date) {
                return date ? moment(date).locale('tr').format('ddd, Do MMM YYYY') : ''
            },

            // Appends item to route object
            goTo (item) {
                if (this.to === undefined) return;

                let routeObject = this.to;

                if (routeObject.params === undefined)
                    routeObject['params'] = {};

                routeObject.params['id'] = item.id;
                routeObject.params['item'] = item;

                this.$router.push(routeObject)
            },

            // Toggles all rows in the data-table
            toggleAll() {
                if (this.selected.length) this.selected = [];
                else this.selected = this.items.slice()
            },

            // Changes sort direction
            changeSort(column) {
                if (this.pagination.sortBy === column) {
                    this.pagination.descending = !this.pagination.descending
                } else {
                    this.pagination.sortBy = column;
                    this.pagination.descending = false
                }
            },

            // Emits delete action to the parent
            deleteItem(item) {
                this.$emit('deleteItem', item)
            },

            editItem (item) {
                this.$emit('editItem', item);
            },

            onexport () { // On Click Excel download button
                let title = this.$route.meta.tabbed ? this.$route.meta.tabs.find(tab => {
                        return tab.to.name === this.$route.name
                    }).text.replace(/\b\w/g, function(l){ return l.toUpperCase() }) : this.$route.meta.title;

                title = title.replace(/\s/g,'').toLocaleString('en');

                let today = moment(new Date()).locale('tr').format('ddd-Do-MMM-YYYY_HH-mm');
                // export json to Worksheet of Excel
                // only array possible
                var animalWS = XLSX.utils.json_to_sheet(this.items, this.headers.map(header => header.text));

                // A workbook is the name given to an Excel file
                var wb = XLSX.utils.book_new(); // make Workbook of Excel

                // add Worksheet to Workbook
                // Workbook contains one or more worksheets
                XLSX.utils.book_append_sheet(wb, animalWS, ''); // sheetAName is name of Worksheet

                // export Excel file
                XLSX.writeFile(wb, `${title}_${today}.xlsx`) // name of the file is 'book.xlsx'
            },

            onimport (e) {
                let files = e.target.files, f = files[0];
                let reader = new FileReader();
                reader.onload = function(e) {
                    let data = new Uint8Array(e.target.result);
                    let workbook = XLSX.read(data, {type: 'array'});
                    let sheet_name_list = workbook.SheetNames;
                    let json = XLSX.utils.sheet_to_json(workbook.Sheets[sheet_name_list[0]]);

                    console.log(sheet_name_list);
                    console.log(json);

                    // Post data to server
                };

                reader.readAsArrayBuffer(f);
            }
        },

        watch: {
            // Watches loading state and
            // informs data-table accordingly
            loading (newVal) {
                this.localLoading = newVal
            }
        },
    }
</script>

<style scoped>
</style>
