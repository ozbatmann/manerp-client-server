<template>
    <div>
        <v-layout mt-1 wrap>
            <v-flex
                    md4
                    sm4
                    lg3
                    xs12
                    deep-purple
            >
                <v-btn
                        small
                        depressed
                        color="rgba(0, 0, 0, .72)"
                        class="m-upload__button white--text ml-2 mt-2"
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
            <v-flex
                    pl-3
                    md8
                    sm8
                    lg9
                    xs12
            >
                <v-layout column>
                    <v-flex>
                        <m-settings-profile-information></m-settings-profile-information>
                    </v-flex>
                    <v-flex mt-3>
                        <m-settings-profile-account></m-settings-profile-account>
                    </v-flex>
                    <v-flex mt-3>
                        <m-settings-profile-notifications></m-settings-profile-notifications>
                    </v-flex>
                </v-layout>
            </v-flex>
        </v-layout>
    </div>
</template>

<script>
    import MSettingsProfileAccount from "../components/MSettingsProfileAccount";
    import MSettingsProfileInformation from "../components/MSettingsProfileInformation";
    import MSettingsProfileNotifications from "../components/MSettingsProfileNotifications";
    const tabRoutes = require('@/modules/main/settings/route/index.js').routes;

    export default {
        name: "MSettingsProfile",
        components: {MSettingsProfileNotifications, MSettingsProfileInformation, MSettingsProfileAccount},
        data () {
            return {
                user:null,
                tabs: [
                    {
                        title: 'kişisel bilgiler',
                        to: { name: tabRoutes.profile }
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
        },
        mounted(){
            this.getUserDetails()
        },
        methods:{
            getUserDetails(){
                console.log(this.$store.state.shared["user"].staffId)
                this.$http.get(`api/v1/driver/${this.$store.state.shared["user"].staffId}`,
               ).then((result) => {
                    this.user = result.data.data.data
                    console.log(result);

                }).catch((error) => {
                    console.log(error);
                }).finally(() => this.loading.role = false)
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
