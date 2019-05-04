<template>
    <div>
        <v-layout>
            <v-flex xs3 pr-2>
                <v-card class="elevation-0" color="deep-purple" dark>
                    <v-card-title>
                        <h4 class="subheading text-uppercase">Arama geçmişi</h4>
                    </v-card-title>
                    <v-divider></v-divider>
                    <v-card-text class="py-1 px-0">
                        <v-list class="deep-purple">
                            <v-list-tile
                                    v-for="(search, index) in prevSearches"
                                    :key="`prev-search-item-${index}`"
                                    @click=""
                            >
                                <v-list-tile-title>{{ search }}</v-list-tile-title>
                                <v-list-tile-action>
                                    <v-spacer></v-spacer>
                                    <v-btn
                                            icon
                                            class="ma-0"
                                    >
                                        <v-icon>close</v-icon>
                                    </v-btn>
                                </v-list-tile-action>
                            </v-list-tile>
                        </v-list>
                    </v-card-text>
                </v-card>
            </v-flex>
            <v-flex xs9 pl-2>
                <v-card class="elevation-0">
                    <v-card-title>
                        <h4 class="headline font-weight-light">
                            {{  }} hakkındaki arama sonuçları gösteriliyor...
                        </h4>
                        <v-spacer></v-spacer>
                        <v-icon
                                size="18"
                                class="primary-green--text"
                        >
                            sort
                        </v-icon>
                        <span class="body-1 ml-2">Toplam 1360 sonuç bulundu</span>
                    </v-card-title>
                    <v-card-text>
                    </v-card-text>
                </v-card>
            </v-flex>
        </v-layout>
    </div>
</template>

<script>
    export default {
        name: "MSearch",

        data () {
            return {
                prevSearches: [
                    'Ahmet Aliakça', '06 BD 4786'
                ]
            }
        }
    }
</script>

<style scoped>

</style>
