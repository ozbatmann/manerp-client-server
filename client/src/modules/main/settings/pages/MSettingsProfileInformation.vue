<template>
    <v-layout wrap pa-2>
        <v-flex
                xs6
                pa-2
        >
            <v-text-field
                    flat
                    solo
                    hide-details
                    height="48"
                    prepend-inner-icon="edit"
                    background-color="grey lighten-3"
                    color="black"
                    class="m-settings__label"
                    label="Ad"
            ></v-text-field>
        </v-flex>

        <v-flex
                xs6
                pa-2
        >
            <v-text-field
                    flat
                    solo
                    hide-details
                    height="48"
                    prepend-inner-icon="edit"
                    background-color="grey lighten-3"
                    color="black"
                    class="m-settings__label"
                    label="Soyad"
            ></v-text-field>
        </v-flex>

        <v-flex
                xs6
                pa-2
        >
            <v-text-field
                    flat
                    solo
                    hide-details
                    height="48"
                    prepend-inner-icon="phone"
                    background-color="grey lighten-3"
                    color="black"
                    class="m-settings__label"
                    label="Telefon"
            ></v-text-field>
        </v-flex>

        <v-flex
                xs6
                pa-2
        >
            <v-text-field
                    flat
                    solo
                    hide-details
                    height="48"
                    prepend-inner-icon="email"
                    background-color="grey lighten-3"
                    color="black"
                    class="m-settings__label"
                    label="E-posta"
            ></v-text-field>
        </v-flex>

        <v-flex
                xs12
                pa-2
        >
            <v-textarea
                    flat
                    solo
                    hide-details
                    prepend-inner-icon="home"
                    background-color="grey lighten-3"
                    color="black"
                    class="m-settings__label"
                    label="Adres"
                    row="5"
            ></v-textarea>
        </v-flex>
        <v-flex
                xs12
                text-xs-right
        >
            <v-btn
                    depressed
                    class="primary-green--text"
            >
                KAYDET
            </v-btn>
        </v-flex>
    </v-layout>
</template>

<script>
    export default {
        name: "MSettingsProfileInformation"
    }
</script>

<style scoped>

</style>
