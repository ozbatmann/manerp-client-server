<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-toolbar
            prominent
            app
            :extended="tabsEnabled"
            :tabs="tabsEnabled"
            class="white toolbar-shadow"
    >
        <v-toolbar-side-icon @click="localDrawerState = !localDrawerState"></v-toolbar-side-icon>
        <v-scroll-y-transition mode="out-in">
            <v-toolbar-title
                    class="headline font-weight-light"
                    key="toolbar-title"
            >
                {{ $route.meta.title }}
            </v-toolbar-title>
        </v-scroll-y-transition>
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
                        v-for="(tab, index) in $route.meta.tabs"
                        :key="`customer-detail-tab-${index}`"
                        :to="tab.to"
                        append
                        exact
                        ripple
                >
                    {{ tab.text }}
                </v-tab>
            </v-tabs>
        </template>
        <!--<v-spacer></v-spacer>-->
        <!--<v-autocomplete-->
                <!--cache-items-->
                <!--hide-no-data-->
                <!--ref="searchAutocomplete"-->
                <!--class="mx-5"-->
                <!--flat-->
                <!--hide-details-->
                <!--label="Bir kişi, plaka, sevkiyat arayın"-->
                <!--solo-->
                <!--@focus="search(true)"-->
        <!--&gt;</v-autocomplete>-->
        <!--<v-spacer></v-spacer>-->

        <v-spacer></v-spacer>

        <!--<v-btn icon>-->

            <!--<v-icon>apps</v-icon>-->
        <!--</v-btn>-->

        <!--<v-btn icon>-->

            <!--<v-icon>notifications</v-icon>-->
        <!--</v-btn>-->

        <router-link :to="{ name: require('@/modules/main/settings/route/index.js').routes.profile }" active="">
            <v-btn icon>
                <v-icon>settings</v-icon>
            </v-btn>
        </router-link>

        <v-btn @click="handleFullScreen()" icon>

            <v-icon>fullscreen</v-icon>
        </v-btn>

        <v-btn color="white" icon>

            <v-avatar size="30">

                <v-menu
                    bottom
                    left
                    :nudge-width="140"
                    :nudge-top="30"
                    :nudge-left="30"
                >
                    <template v-slot:activator="{ on }">
                        <v-img
                                v-on="on"
                                src="https://randomuser.me/api/portraits/men/85.jpg"
                        ></v-img>
                    </template>
                    <v-list dense>
                    <v-list-tile @click="logout">Çıkış Yap</v-list-tile>
                </v-list>
                </v-menu>
            </v-avatar>
        </v-btn>
    </v-toolbar>
</template>

<script>
    import AuthService from 'manerp-vue-auth/src/service/AuthService'
    export default {
        name: "AppToolbar",

        props: {
            value: {
                type: Boolean,
                default: true
            }
        },

        data() {
            return {
                activeTab: 0,

                title: null,
                tabs: null,

                lastRoute: null,
                localDrawerState: this.value,
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

                let requestFullScreen = docEl.requestFullscreen
                    || docEl.mozRequestFullScreen
                    || docEl.webkitRequestFullScreen
                    || docEl.msRequestFullscreen;

                let cancelFullScreen = doc.exitFullscreen
                    || doc.mozCancelFullScreen
                    || doc.webkitExitFullscreen
                    || doc.msExitFullscreen;

                if (!doc.fullscreenElement
                    && !doc.mozFullScreenElement
                    && !doc.webkitFullscreenElement
                    && !doc.msFullscreenElement) {
                    requestFullScreen.call(docEl);
                } else {
                    cancelFullScreen.call(doc);
                }
            },

            logout () {
                AuthService.clearLoginDetails();
                this.$router.push({ name: require('@/modules/main/authentication/route/index').routes.login})
            },

            search(state) {
                if (state) {
                    let searchRoute = require('@/modules/main/search/route/index').routes.search;

                    this.lastRoute = this.$router.currentRoute.name;
                    this.$router.push({ name: searchRoute });
                }
            }
        },

        watch: {
            localDrawerState () {
                this.$emit('input', this.localDrawerState)
            },

            value () {
                this.localDrawerState = this.value
            },

            '$route.meta.title' (newValue, oldValue) {
                //if (newValue !== oldValue) this.activeTab = 0;
            }
        }
    }
</script>

<style scoped>
    .v-toolbar__extension {
        align-items: start !important;
    }

    a {
        color: transparent;
    }
</style>
