<template>
    <v-app>
        <section-main></section-main>
    </v-app>
</template>

<script>
    import SectionMain from './modules/main/AppMain'

    export default {
        name: 'App',
        components: {
            SectionMain
        },
        data () {
            return {
                //
            }
        }
    }
</script>

<style>
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

    .m-data-table-row {
        cursor: pointer !important;
    }

    .m-picker--dark .v-picker__body.theme--dark,
    .m-picker--dark .v-picker__actions {
        background-color: #673AB7;
    }

    .m-filter-input .v-icon {
        font-size: 16px !important;
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
</style>