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
            <h4 class="d-inline-block subheading font-weight-light text-uppercase">kişisel bilgiler</h4>
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
                pl-3
                pr-2
                mt-3
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
                pl-2
                pr-3
                mt-3
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
                pl-3
                pr-2
                mt-3
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
                pl-2
                pr-3
                mt-3
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
                pa-3
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
    </v-layout>
</template>

<script>
    export default {
        name: "MSettingsProfileInformation"
    }
</script>

<style scoped>

</style>
