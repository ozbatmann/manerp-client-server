<template>
    <div>
        <v-app>
            <router-view></router-view>
        </v-app>
    </div>
</template>

<script>
    export default {
        name: 'App',
        data () {
            return {
                //
            }
        }
    }
</script>

<style>
    html, body {
        min-height: 100%;
    }

    .m-found {
        position: fixed;
        z-index: 50;
    }

    .custom-font {
        font-family: 'Poppins', sans-serif !important;
    }

    .toolbar-shadow {
        box-shadow: 0 2px 4px 0 rgba(0,0,0,0.10) !important;
        -webkit-box-shadow: 0 2px 4px 0 rgba(0,0,0,0.10) !important;
    }

    .max-lines-2 {
        line-height: 1.5 !important;        /* fallback */
        height: 3em !important;
    }

    .v-shadow-lg {
        box-shadow: 0 15px 30px 0 rgba(0,0,0,0.11),
        0 5px 15px 0 rgba(0,0,0,0.08) !important;
        -webkit-box-shadow: 0 15px 30px 0 rgba(0,0,0,0.11),
        0 5px 15px 0 rgba(0,0,0,0.08) !important;
        -moz-box-shadow: 0 15px 30px 0 rgba(0,0,0,0.11),
        0 5px 15px 0 rgba(0,0,0,0.08) !important;
    }

    .m-active-breadcrumb {
        cursor: pointer !important;
        color: #13d893 !important;
    }

    .m-data-table-row {
        vertical-align: middle;
    }

    .m-data-table {
        border: 1px solid #f4f4f4;
        white-space: nowrap;
        position: relative;
        padding: 0 52px 0 48px;
    }

    .v-datatable__actions {
        border: none !important;
    }

    .m-data-table .v-table__overflow {
        border-left: 1px solid rgba(0, 0, 0, .12) !important;
        border-right: 1px solid rgba(0, 0, 0, .12) !important;
    }

    .m-data-table table thead {
        height: 56px !important;
        line-height: 56px !important;
    }

    .m-data-table .v-datatable__actions {
        margin-left: -48px !important;
        margin-right: -52px !important;
    }

    .m-data-table-sticky-col-left {
        left: 0 !important;
        position: absolute !important;
        top: auto !important;
        width: 48px !important;
        height: 49px !important;
        border-bottom: 1px solid rgba(0, 0, 0, .12) !important;
        background-color: white;
        padding: 11px !important;
    }

    th.m-data-table-sticky-col-left {
        padding-top: 16px !important;
    }

    th.m-data-table-sticky-col-left,
    th.m-data-table-sticky-col-right {
        height: 57px !important;
        line-height: 57px !important;
        border-bottom-color: rgba(0, 0, 0, .12) !important;
    }

    .m-data-table-sticky-col-right {
        right: 0;
        position: absolute;
        top: auto;
        width: 52px !important;
        height: 49px !important;
        line-height: 49px !important;
        padding: 0 !important;
        border-bottom: 1px solid rgba(0, 0, 0, .12) !important;
        background: white;
    }

    .m-picker--dark .v-picker__body.theme--dark,
    .m-picker--dark .v-picker__actions {
        background-color: #673AB7;
    }

    .m-filter-input .v-icon {
        font-size: 16px !important;
    }

    .m-input-capitalize input,
    .m-input-capitalize div,
    .m-input-capitalize label {
        text-transform: capitalize !important;
    }

    .m-filter-date input {
        cursor: pointer !important;
        -webkit-transition: 0.2s all ease-in-out;
        -moz-transition: 0.2s all ease-in-out;
        -ms-transition: 0.2s all ease-in-out;
        -o-transition: 0.2s all ease-in-out;
        transition: 0.2s all ease-in-out;
    }

    .m-active-date input {
        color: #13d893 !important;
    }

    .m-settings__label .v-label,
    .m-settings__label input {
        font-size: 14px;
    }
</style>
