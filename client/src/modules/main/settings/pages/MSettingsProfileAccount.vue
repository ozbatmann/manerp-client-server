<template>
    <div>
        <v-layout wrap px-3 py-2>
            <v-flex xs6 py-2 pr-2>
                <v-text-field
                        flat
                        solo
                        hide-details
                        height="48"
                        prepend-inner-icon="vpn_key"
                        background-color="grey lighten-3"
                        color="black"
                        class="m-settings__label"
                        label="Yeni Parola"
                ></v-text-field>
            </v-flex>

            <v-flex xs6 py-2 pl-2>
                <v-text-field
                        flat
                        solo
                        hide-details
                        height="48"
                        prepend-inner-icon="vpn_key"
                        background-color="grey lighten-3"
                        color="black"
                        class="m-settings__label"
                        label="Parola Tekrar"
                ></v-text-field>
            </v-flex>

            <v-flex
                    xs12
                    text-xs-right
            >
                <v-btn
                        depressed
                        class="primary-green--text mr-0"
                >
                    kaydet
                </v-btn>
            </v-flex>
        </v-layout>
    </div>
</template>

<script>
    export default {
        name: "MSettingsProfileAccount"
    }
</script>

<style scoped>

</style>
