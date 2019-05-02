<template>
    <v-dialog v-model="showDialog" persistent max-width="70%" scrollable>
        <!-- <v-btn slot="activator" color="primary" dark>Open Dialog</v-btn> -->
        <v-card class="pa-3">
            <v-card-title>
        <span v-if="isEdit"
              class="headline">Güncelle</span>
                <span v-else
                      class="headline">Yeni Sevkiyat</span>
            </v-card-title>
            <v-card-text style="max-height: 90%;">
                <v-container grid-list-md pa-0>
                    <v-layout wrap>
                        <v-flex xs12>
                            <v-text-field v-model="data.transportWaybillNo"
                                          label="Nakliye İrsaliye Numarası"
                                          name="transportWaybillNo"
                                          :counter="150"
                                          maxlength="150"
                                          background-color="grey lighten-4"
                                          color="green accent-2"
                                          full-width>
                            </v-text-field>
                            <v-text-field v-model="data.deliveryNoteNo"
                                          label="Teslimat Notu Numarası"
                                          name="deliveryNoteNo"
                                          :counter="150"
                                          maxlength="150"
                                          background-color="grey lighten-4"
                                          color="green accent-2"
                                          full-width>
                            </v-text-field>
                            <v-text-field v-model="data.sasNumber"
                                          label="Sas Numarası"
                                          name="sasNumber"
                                          :counter="150"
                                          maxlength="150"
                                          background-color="grey lighten-4"
                                          color="green accent-2"
                                          full-width>
                            </v-text-field>
                        </v-flex>
                    </v-layout>
                </v-container>
            </v-card-text>
            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="orange darken-1" outline @click.native="close">Kapat</v-btn>
                <v-btn v-if="isEdit" color="blue darken-1" outline @click.native="edit">Düzenle</v-btn>
                <v-btn v-else color="blue darken-1" outline @click.native="save">Kaydet</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script>

    const voyageModel = require('@/modules/main/voyage/models/voyage-model-add-edit').default;

    export default {
        data() {
            return {
                showDialog: false,
                isEdit: false,
                data: JSON.parse(JSON.stringify(voyageModel))
            }
        },
        methods: {
            open(data) {
                this.showDialog = true;
                if (data) {
                    this.data = data;
                    this.isEdit = true;
                } else {
                    this.clear();
                }
            },
            close() {
                this.showDialog = false;
            },
            save() {
                this.$validator.validateAll().then((result) => {
                    if (result) {
                        this.$emit("save", this.data);
                        this.close();
                    }
                });
            },
            edit() {
                this.$validator.validateAll().then((result) => {
                    if (result) {
                        this.$emit("edit", this.data);
                        this.close();
                    }
                });
            },
            clear() {
                this.data = JSON.parse(JSON.stringify(voyageModel));
                this.$validator.reset();
                this.isEdit = false;
            }
        },
        mounted() {
        }
    }
</script>
<style>

</style>