<template>
    <div>
        <v-layout mt-1>
            <v-flex
                    mr-2
                    lg3
                    deep-purple
            >
                <v-btn
                        small
                        depressed
                        color="rgba(0, 0, 0, .54)"
                        class="m-upload__button white--text ml-1"
                >
                    FOTOĞRAF YÜKLE
                </v-btn>
                <img
                        src="https://images.unsplash.com/photo-1526948128573-703ee1aeb6fa?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80"
                        width="100%"
                        height="200"
                        style="object-fit: cover;"
                        alt="profile_picture"
                >
                <v-list
                        dark
                        class="deep-purple pt-0 pb-2"
                >
                    <v-subheader class="text-uppercase">
                        kişisel bilgiler
                    </v-subheader>

                    <v-list-tile>
                        <v-list-tile-action>
                            <v-icon>person</v-icon>
                        </v-list-tile-action>
                        <v-list-tile-content>
                            <v-list-tile-title class="body-1">
                                Tunahan Bayındır
                            </v-list-tile-title>
                        </v-list-tile-content>
                    </v-list-tile>

                    <v-list-tile>
                        <v-list-tile-action>
                            <v-icon>phone</v-icon>
                        </v-list-tile-action>
                        <v-list-tile-content>
                            <v-list-tile-title class="body-1">
                                0 541 598 4560
                            </v-list-tile-title>
                        </v-list-tile-content>
                    </v-list-tile>

                    <v-list-tile>
                        <v-list-tile-action>
                            <v-icon>email</v-icon>
                        </v-list-tile-action>
                        <v-list-tile-content>
                            <v-list-tile-title class="body-1">
                                tbayndr@yahoo.com
                            </v-list-tile-title>
                        </v-list-tile-content>
                    </v-list-tile>
                </v-list>
            </v-flex>
            <v-flex ml-2 lg9>
                <v-card
                        class="elevation-0 white"
                        style="border: 1px solid #d5d5d5;"
                >
                    <v-tabs
                            light
                            style="border-bottom: 1px solid #eee;"
                    >
                        <v-tabs-slider color="green accent-2"></v-tabs-slider>

                        <v-tab
                                v-for="(tab, index) in tabs"
                                :key="`profile-tab-${index}`"
                                :to="tab.to"
                        >
                            {{tab.title}}
                        </v-tab>
                    </v-tabs>
                    <router-view></router-view>
                </v-card>
            </v-flex>
        </v-layout>
    </div>
</template>

<script>
    const tabRoutes = require('@/modules/main/settings/route/index.js').routes;

    export default {
        name: "MSettingsProfile",

        data () {
            return {
                tabs: [
                    {
                        title: 'kişisel bilgiler',
                        to: { name: tabRoutes.userInfo }
                    },
                    {
                        title: 'parola değişikliği',
                        to: { name: tabRoutes.accountInfo }
                    },
                    {
                        title: 'bildirimler',
                        to: { name: tabRoutes.notification }
                    }
                ]
            }
        }
    }
</script>

<style scoped>
    .m-upload__button {
        position: absolute;
        background: rgba(0, 0, 0, .54);
    }
</style>
