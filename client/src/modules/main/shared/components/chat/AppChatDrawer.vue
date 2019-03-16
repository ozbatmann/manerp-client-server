<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div>
        <v-navigation-drawer
                ref="chatDrawer"
                app
                dark
                right
                drawer
                stateless
                :temporary="temporary"
                width="240"
                class="grey darken-4"
                v-model="localDrawer"
                v-click-outside="clickOutsideFunction"
                @keydown.esc="clickOutsideFunction">

            <v-list class="pa-0" subheader>

                <v-toolbar flat>

                    <v-list class="pa-0">

                        <v-list-tile>

                            <v-list-tile-content>

                                <v-list-tile-title>

                                    <span class="font-weight-light subheading">İş Arkadaşları</span>
                                </v-list-tile-title>
                            </v-list-tile-content>

                            <v-spacer></v-spacer>

                            <v-list-tile-content class="align-end">

                                <v-tooltip bottom>

                                    <template v-slot:activator="{ on }">

                                        <v-chip small
                                                v-on="on"
                                                color="grey darken-3"
                                                text-color="green accent-3"
                                                class="font-weight-light">
                                            126
                                        </v-chip>
                                    </template>

                                    <span>Toplam 126 iş arkadaşı</span>
                                </v-tooltip>
                            </v-list-tile-content>
                        </v-list-tile>
                    </v-list>
                </v-toolbar>

                <v-divider></v-divider>

                <v-text-field
                        flat
                        solo
                        hide-details
                        color="green accent-3"
                        class="white--text"
                        placeholder="Kişi arayın"
                        prepend-inner-icon="search"
                        background-color="grey darken-4"
                        height="48">
                </v-text-field>

                <v-divider class="mb-2"></v-divider>

                <v-subheader>ŞU AN AKTİF

                    <v-chip small
                            text-color="green accent-3"
                            class="ml-3 font-weight-light grey darken-3">
                        15
                    </v-chip>
                </v-subheader>

                <v-menu
                        transition="slide-x-reverse-transition"
                        bottom
                        left
                        dark
                        offset-x
                        z-index="5"
                        max-width="150"
                        v-for="i in 5"
                        :key="`chat-menu-item-${i}`">

                    <template v-slot:activator="{ on }">

                        <v-list-tile avatar
                                     v-on="on">

                            <v-list-tile-avatar size="40">

                                <img :src="'https://randomuser.me/api/portraits/men/' + i + '.jpg'">
                            </v-list-tile-avatar>

                            <v-list-tile-content>

                                <v-list-tile-title class="body-1">John Leider</v-list-tile-title>

                                <v-list-tile-sub-title
                                        v-if="i % 2 == 0"
                                        class="caption">

                                    5 dk önce aktif
                                </v-list-tile-sub-title>

                                <v-list-tile-sub-title
                                        v-else
                                        class="caption green--text text--accent-3">

                                    Şu an aktif
                                </v-list-tile-sub-title>
                            </v-list-tile-content>
                        </v-list-tile>

                    </template>

                    <v-list class="grey darken-4" dense>
                        <v-list-tile @click="startConversation({    name: 'Tunahan',
                                                                    surname: 'Bayındır',
                                                                    activeStatus: '5 dk önce aktif' })">

                            <v-list-tile-title class="body-1">Konuşma başlat</v-list-tile-title>
                        </v-list-tile>

                        <v-list-tile @click="">

                            <v-list-tile-title class="body-1">Bilgileri gör</v-list-tile-title>
                        </v-list-tile>
                    </v-list>
                </v-menu>

                <v-divider class="my-2"></v-divider>

                <v-subheader>DİĞER

                    <v-chip small
                            text-color="black"
                            class="ml-3 font-weight-light grey darken-3">
                        111
                    </v-chip>
                </v-subheader>

                <v-list-tile avatar v-for="i in 5"
                             :key="'showChatArea-item' + i">

                    <v-list-tile-avatar size="40">

                        <img :src="'https://randomuser.me/api/portraits/men/' + i + '.jpg'">
                    </v-list-tile-avatar>

                    <v-list-tile-content>

                        <v-list-tile-title class="body-1">John Leider</v-list-tile-title>

                        <v-list-tile-sub-title v-if="i % 2 == 0" class="caption">5 dk önce aktif</v-list-tile-sub-title>

                        <v-list-tile-sub-title v-else class="caption">Şu an aktif</v-list-tile-sub-title>
                    </v-list-tile-content>
                </v-list-tile>
            </v-list>
        </v-navigation-drawer>

        <v-bottom-sheet inset
                        lazy
                        hide-overlay
                        v-model="showChatArea"
                        max-width="calc(100% - 520px)">

            <app-chat-area v-model="personToChat"></app-chat-area>
        </v-bottom-sheet>
    </div>
</template>

<script>
    import AppChatArea from './components/AppChatArea'
    import Vue from 'vue'

    export default {
        name: "AppChatDrawer",
        components: {
            AppChatArea
        },

        props: {
            value: {
                type: Boolean,
                default: null
            },

            temporary: {
                type: Boolean,
                default: false
            }
        },

        data () {
            return {
                localDrawer: this.value,
                localTemporary: this.temporary,
                showChatArea: false,
                personToChat: null,
                shouldOpenDrawer: true
            }
        },

        computed: {
            clickOutsideFunction () {
                return this.outsideClicked
            }
        },

        methods: {
            startConversation: function (user) {
                this.personToChat = user
                this.showChatArea = true
            },

            outsideClicked: function () {
                if (!this.shouldOpenDrawer) {
                    this.shouldOpenDrawer = true
                    this.localDrawer = false
                }
            }
        },

        watch: {
            value: function () {
                setTimeout(() => {
                    if (this.value) this.shouldOpenDrawer = false
                }, 300)

                this.localDrawer = this.value
            },

            temporary: function () {
                this.localTemporary = this.temporary
            },

            localDrawer: function () {
                this.$emit('input', this.localDrawer)
            }
        }
    }
</script>

<style scoped>

</style>