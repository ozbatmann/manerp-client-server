<template>
    <v-layout
            white
            wrap
            align-center
    >
        <v-flex
                xs6
                px-3
        >
            <h4 class="d-inline-block subheading font-weight-light text-uppercase">bildirimler</h4>
        </v-flex>
        <v-flex
                xs6
                px-2
                py-1
                text-xs-right
        >
            <v-btn
                    flat
                    class="d-inline-block primary-green--text"
            >
                KAYDET
            </v-btn>
        </v-flex>
        <v-flex xs12>
            <v-divider></v-divider>
        </v-flex>
        <v-flex
                v-for="(notification, index) in notifications"
                :key="`notification-checkbox-${index}`"
                pa-3
        >
            <v-checkbox
                    v-model="notification.active"
                    :label="notification.title"
                    class="mt-0 text-capitalize font-weight-regular m-settings__label"
                    color="green accent-2"
                    hide-details
            ></v-checkbox>
        </v-flex>
    </v-layout>
</template>

<script>
    export default {
        name: "MSettingsProfileNotifications",

        data () {
            return {
                notifications: {
                    app: { active: true, title: 'Uygulama içi bildirimler' },
                    sms: { active: true, title: 'SMS bildirimi' },
                    mail: { active: true, title: 'E-posta bildirimi' }
                }
            }
        }
    }
</script>

<style scoped>

</style>
