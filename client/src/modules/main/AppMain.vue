<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-app>
        <main>
            <app-menu drawer="drawer"></app-menu>

            <app-toolbar></app-toolbar>

            <app-chat-drawer    v-model="drawer"
                                :temporary=true>
            </app-chat-drawer>

            <v-content>

                <v-container>

                    <v-fade-transition mode="out-in">

                        <keep-alive>

                            <router-view></router-view>
                        </keep-alive>
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
            </v-content>
        </main>
    </v-app>
</template>

<script>
    import AppMenu from './shared/components/menu/AppMenu'
    import AppToolbar from './shared/components/toolbar/AppToolbar'
    import AppChatDrawer from '@/modules/main/shared/components/chat/AppChatDrawer'

    export default {
        name: "AppMain",
        components: {
            AppChatDrawer,
            AppMenu,
            AppToolbar
        },

        data () {
            return {
                drawer: null
            }
        }
    }
</script>

<style scoped>
</style>