<template>
    <v-toolbar
            prominent
            app
            class="white toolbar-shadow">

        <v-layout row fill-height wrap align-content-center justify-space-between>

            <v-flex lg3>
                <v-btn icon class="mt-0">

                    <v-icon>menu</v-icon>
                </v-btn>


                    <div class="d-inline-flex fill-height align-center">
                        <v-scroll-y-transition mode="out-in">

                        <span class="custom-font headline font-weight-light"
                             :key="`toolbar-${$route.name}`">{{$route.name}}</span>
                        </v-scroll-y-transition>
                    </div>

            </v-flex>
            <v-flex lg5>


                <v-autocomplete
                        v-model="select"
                        :loading="loading"
                        :items="items"
                        :search-input.sync="search"
                        cache-items
                        class="mx-5"
                        flat
                        hide-no-data
                        hide-details
                        label="Bir kişi, plaka, sevkiyat arayın"
                        solo>
                </v-autocomplete>

            </v-flex>

            <v-flex shrink>

                <v-spacer></v-spacer>

                <v-btn icon>

                    <v-icon>apps</v-icon>
                </v-btn>

                <v-btn icon>

                    <v-icon>notifications</v-icon>
                </v-btn>

                <v-btn icon @click="handleFullScreen()">

                    <v-icon>fullscreen</v-icon>
                </v-btn>

                <v-btn icon color="white">

                    <v-avatar size="30">

                        <v-img src="https://randomuser.me/api/portraits/men/85.jpg"/>
                    </v-avatar>
                </v-btn>

            </v-flex>
        </v-layout>

    </v-toolbar>
</template>

<script>
    export default {
        name: "AppToolbar",

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
        }
    }
</script>

<style scoped>
    .v-toolbar__extension {
        align-items: start !important;
    }
</style>