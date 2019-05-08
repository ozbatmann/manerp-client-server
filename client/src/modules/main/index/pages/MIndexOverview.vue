<template>
    <div>
        <v-layout row>

            <v-chip label class="grey lighten-4 mx-0">

                <div class="black--text">
                    {{ todayDate }},
                    <span class="primary--text">{{ todayDay }}</span>
                </div>
            </v-chip>

            <v-spacer></v-spacer>

            <!--<v-btn-->
                    <!--depressed-->
                    <!--class="mx-0">-->

                <!--<v-icon left-->
                        <!--size="20"-->
                        <!--color="red">notifications_active-->
                <!--</v-icon>-->

                <!--3 YENİ MESAJ-->
            <!--</v-btn>-->
        </v-layout>

        <v-layout row mt-2>

            <v-flex pr-2 lg4>

                <m-index-overview-component
                        :number=10
                        title="Siparişleriniz"
                        text="adet bekleyen siparişiniz var"
                        empty-text="Bekleyen sipariş bulunmuyor"
                        action-text="SİPARİŞLERİ GÖRÜNTÜLE"
                        :to="{ name: 'orders.current' }"
                        :statistics="value">
                </m-index-overview-component>
            </v-flex>

            <v-flex pl-1 pr-1 lg4>

                <m-index-overview-component
                        :number=5
                        title="Sevkiyatlarınız"
                        text="adet devam eden sevkiyatınız var"
                        empty-text="Bekleyen sevkiyat bulunmuyor"
                        action-text="SEVKİYATLARI GÖRÜNTÜLE"
                        :to="{ name: 'voyages.incomplete' }"
                        :statistics="value2"
                        bar>
                </m-index-overview-component>
            </v-flex>

            <v-flex pl-2 lg4>

                <v-expansion-panel style="box-shadow: none;">

                    <v-expansion-panel-content>

                        <template v-slot:header>

                            <v-flex col>

                                <div class="headline font-weight-light pt-1">Yapılacaklar</div>

                                <div class="grey--text pb-1">

                                    <span class="black--text">35</span>

                                    tamamlanmamış
                                </div>
                            </v-flex>
                        </template>

                        <v-card elevation="0" class="pt-2">

                            <v-card-text class="pt-0"
                                         v-for="i in 3"
                                            :key="'card-text-' + i">

                                <v-layout row>

                                    <v-flex shrink>

                                        <v-checkbox class="py-0 my-0" color="green accent-2" :input-value="true"></v-checkbox>
                                    </v-flex>

                                    <v-flex grow>

                                        <div class="body-1">#13835 nolu aracın çıkışı verilecek.</div>

                                        <div class="grey--text">3 sa kaldı</div>
                                    </v-flex>
                                </v-layout>
                            </v-card-text>

                            <v-card-actions>

                                <v-btn depressed block>
                                    TÜMÜNÜ GÖR
                                </v-btn>
                            </v-card-actions>
                        </v-card>
                    </v-expansion-panel-content>
                </v-expansion-panel>
            </v-flex>
        </v-layout>
    </div>
</template>

<script>
    import moment from 'moment';
    import MIndexOverviewComponent from '../components/MIndexOverviewComponent'

    export default {
        name: "MIndexOverview",
        components: {
            MIndexOverviewComponent
        },

        data() {
            return {
                value: [
                    423,
                    446,
                    675,
                    510,
                    590,
                    610,
                    650
                ],
                value2: [
                    75, 80, 100, 50, 72, 63, 90, 82
                ]
            }
        },

        computed: {
            todayDate () {
                return moment(new Date()).locale('tr').format('Do MMMM YYYY');
            },

            todayDay () {
                return moment(new Date()).locale('tr').format('dddd');
            }
        }
    }
</script>

<style scoped>
</style>
