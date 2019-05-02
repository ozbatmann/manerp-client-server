<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-app>
        <main style="min-height: 100%">
            <app-menu v-model="menu"></app-menu>

            <app-toolbar v-model="menu"></app-toolbar>

            <app-chat-drawer    v-model="drawer"
                                :temporary=true>
            </app-chat-drawer>

            <v-content style="min-height: 100%;">

                <v-container style="padding-bottom: 126px;">

                    <v-fade-transition mode="out-in">

                            <router-view></router-view>
                    </v-fade-transition>

                    <v-tooltip left>

                        <template v-slot:activator="{ on }">

                            <v-fab-transition>

                                <v-btn
                                        v-on="on"
                                        color="blue accent-3"
                                        dark
                                        fab
                                        fixed
                                        bottom
                                        right
                                        @click="drawer = !drawer">

                                    <v-icon>group</v-icon>
                                </v-btn>
                            </v-fab-transition>
                        </template>

                        <span>Mesaj penceresini göster</span>
                    </v-tooltip>
                </v-container>

                <app-footer></app-footer>
            </v-content>
        </main>
    </v-app>
</template>

<script>
    import AppMenu from './shared/components/menu/AppMenu'
    import AppToolbar from './shared/components/toolbar/AppToolbar'
    import AppChatDrawer from '@/modules/main/shared/components/chat/AppChatDrawer'
    import AppFooter from "./AppFooter";

    export default {
        name: "AppMain",
        components: {
            AppFooter,
            AppChatDrawer,
            AppMenu,
            AppToolbar
        },

        data () {
            return {
                drawer: null,

                menu: false
            }
        }
    }
</script>

<style scoped>
</style>
