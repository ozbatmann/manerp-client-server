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
            <h4 class="d-inline-block subheading font-weight-light text-uppercase">parola değişikliği</h4>
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
                xs6
                py-3
                pl-3
                pr-2
        >
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

        <v-flex
                xs6
                py-3
                pl-2
                pr-3
        >
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
    </v-layout>
</template>

<script>
    export default {
        name: "MSettingsProfileAccount"
    }
</script>

<style scoped>

</style>
