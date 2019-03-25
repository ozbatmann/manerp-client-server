<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-toolbar
            prominent
            app
            :extended="tabsEnabled"
            :tabs="tabsEnabled"
            class="white toolbar-shadow"
    >
        <v-toolbar-side-icon></v-toolbar-side-icon>
        <v-scroll-y-transition mode="out-in">
            <v-toolbar-title
                    class="headline font-weight-light"
                    key="toolbar-title"
            >
                {{ $route.meta.title === undefined ? title : $route.meta.title }}
            </v-toolbar-title>
        </v-scroll-y-transition>
        <!--<v-breadcrumbs :items="$route.meta.breadcrumb" class="py-0">-->
        <!--<template v-slot:item="props">-->
        <!--<router-link-->
        <!--tag="span"-->
        <!--:to="{ name: props.item.href }"-->
        <!--:class="[props.item.disabled ? '' : 'm-active-breadcrumb']"-->
        <!--&gt;-->
        <!--{{ props.item.text }}-->
        <!--</router-link>-->
        <!--</template>-->
        <!--</v-breadcrumbs>-->
        <template
                v-if="tabsEnabled"
                v-slot:extension
        >
            <v-tabs
                    align-with-title
                    color="transparent"
                    slider-color="green accent-2"
            >
                <v-tab
                        :key="`customer-detail-tab-${index}`"
                        :to="tab.to"
                        append
                        exact
                        ripple
                        v-for="(tab, index) in $route.meta.tabs"
                >
                    {{ tab.text }}
                </v-tab>
            </v-tabs>
        </template>
        <v-spacer></v-spacer>
        <v-autocomplete
                cache-items
                class="mx-5"
                flat
                hide-details
                hide-no-data
                label="Bir kişi, plaka, sevkiyat arayın"
                solo>
        </v-autocomplete>
        <v-spacer></v-spacer>

        <v-spacer></v-spacer>

        <v-btn icon>

            <v-icon>apps</v-icon>
        </v-btn>

        <v-btn icon>

            <v-icon>notifications</v-icon>
        </v-btn>

        <v-btn @click="handleFullScreen()" icon>

            <v-icon>fullscreen</v-icon>
        </v-btn>

        <v-btn color="white" icon>

            <v-avatar size="30">

                <v-img src="https://randomuser.me/api/portraits/men/85.jpg"/>
            </v-avatar>
        </v-btn>
    </v-toolbar>
</template>

<script>
    import MDataTableAction from "../data/components/MDataTableAction";
    export default {
        name: "AppToolbar",
        components: {MDataTableAction},
        data() {
            return {
                title: null,
                tabs: null,
            }
        },

        computed: {
            tabsEnabled() {
                return this.$route.meta.tabbed
            }
        },

        methods: {
            handleFullScreen() {
                let doc = window.document;
                let docEl = doc.documentElement;

                let requestFullScreen = docEl.requestFullscreen || docEl.mozRequestFullScreen || docEl.webkitRequestFullScreen || docEl.msRequestFullscreen;
                let cancelFullScreen = doc.exitFullscreen || doc.mozCancelFullScreen || doc.webkitExitFullscreen || doc.msExitFullscreen;

                if (!doc.fullscreenElement && !doc.mozFullScreenElement && !doc.webkitFullscreenElement && !doc.msFullscreenElement) {
                    requestFullScreen.call(docEl);
                } else {
                    cancelFullScreen.call(doc);
                }
            }
        },

        watch: {
            '$route'(to, from) {
            }
        }
    }
</script>

<style scoped>
    .v-toolbar__extension {
        align-items: start !important;
    }
</style>
