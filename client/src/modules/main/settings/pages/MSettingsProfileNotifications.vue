<template>
    <v-layout
            column
            px-3 pt-1 pb-3
    >
        <v-flex
                v-for="(notification, index) in notifications"
                :key="`notification-checkbox-${index}`"
                mt-2
        >
            <v-checkbox
                    v-model="notification.active"
                    :label="notification.title"
                    class="mt-0 text-capitalize font-weight-regular m-settings__label"
                    color="green accent-2"
                    hide-details
            ></v-checkbox>
        </v-flex>

        <v-flex mt-3>
            <v-btn
                    depressed
                    class="primary-green--text ml-0"
            >
                KAYDET
            </v-btn>
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
