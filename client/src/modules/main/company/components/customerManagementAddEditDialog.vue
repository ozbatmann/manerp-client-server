<template>
    <v-dialog v-model="showDialog" persistent max-width="80%" scrollable>
        <!-- <v-btn slot="activator" color="primary" dark>Open Dialog</v-btn> -->
        <v-card class="pa-3">
            <v-card-title>
                <span v-if="isEdit"
                      class="headline">Güncelle</span>
                <span v-else
                      class="headline">Yeni Müşteri İş Yeri</span>
            </v-card-title>
            <v-tabs
                fixed-tabs
                slider-color="green accent-2"
            >
                <v-tab ripple>
                    İş Yeri Bilgileri
                </v-tab>
                <v-tab ripple v-on:change="vendorTabChanged">
                    Bayiler
                </v-tab>
                <v-tab-item>
                    <v-card flat>
                        <v-card-text style="max-height: 90%;">
                            <v-container grid-list-md pa-0>
                                <v-layout wrap>
                                    <v-flex xs12>
                                        <v-text-field v-validate="'required'"
                                                      :error-messages="errors.collect('firstName')"
                                                      v-model="data.firstName"
                                                      label="Ad"
                                                      name="firstName"
                                                      :counter="30"
                                                      maxlength="30"
                                                      background-color="grey lighten-4"
                                                      color="green accent-2"
                                                      full-width>
                                        </v-text-field>
                                    </v-flex>
                                </v-layout>
                            </v-container>
                            <v-card-actions>
                                <v-spacer></v-spacer>
                                <v-btn color="orange darken-1" outline @click.native="close">Kapat</v-btn>
                                <v-btn v-if="isEdit" color="blue darken-1" outline @click.native="edit">Düzenle</v-btn>
                                <v-btn v-else color="blue darken-1" outline @click.native="save">Kaydet</v-btn>
                            </v-card-actions>
                        </v-card-text>
                    </v-card>
                </v-tab-item>
                <v-tab-item>
                    <v-card flat>
                        <v-card-text>
                            <v-layout row wrap>
                                <v-flex xs3>
                                    <div class="form-check" v-for="layer in layers" :key="layer.id">
                                        <v-checkbox v-model="layer.active"
                                                    :label="layer.name"
                                                    @change="layerChanged(layer.id, layer.active)">
                                        </v-checkbox>
                                    </div>
                                </v-flex>
                                <v-flex xs9>
                                    <div id="map"></div>
                                </v-flex>
                            </v-layout>
                            <v-card-actions>
                                <v-spacer></v-spacer>
                                <v-btn color="orange darken-1" outline @click.native="close">Kapat</v-btn>
                            </v-card-actions>
                        </v-card-text>
                    </v-card>
                </v-tab-item>
            </v-tabs>
        </v-card>
    </v-dialog>
</template>

<script>
    const companyModel = require('@/modules/main/company/models/company-model-add-edit').default;

    export default {
        data() {
            return {
                tabs: null,
                showDialog: false,
                isEdit: false,
                data: JSON.parse(JSON.stringify(companyModel)),


                map: null,
                tileLayer: null,
                currentLocation: {lat: null, lng: null},
                layers: [{
                    id: 0,
                    name: 'Ankara Bayileri',
                    active: false,
                    features: [{
                        id: 0,
                        name: 'Bogart\'s Smokehouse',
                        type: 'marker',
                        coords: [39.813119, 32.711207],
                    },
                        {
                            id: 1,
                            name: 'Pappy\'s Smokehouse',
                            type: 'marker',
                            coords: [39.853454, 32.722818],
                        },
                        {
                            id: 2,
                            name: 'Broadway Oyster Bar',
                            type: 'marker',
                            coords: [39.865876, 32.709700],
                        },
                        {
                            id: 3,
                            name: 'Charlie Gitto\'s On the Hill',
                            type: 'marker',
                            coords: [39.862163, 32.764860],
                        },
                        {
                            id: 4,
                            name: 'Charlie Gitto\'s On the Hill',
                            type: 'marker',
                            coords: [39.874472, 32.764193],
                        }
                    ],
                },
                    {
                        id: 1,
                        name: 'Ankara Bayileri Sınır Çizgileri',
                        active: false,
                        features: [{
                            id: 0,
                            name: 'City of St. Louis',
                            type: 'polygon',
                            coords: [
                                [39.813119, 32.711207],
                                [39.853454, 32.722818],
                                [39.865876, 32.709700],
                                [39.862163, 32.764860],
                                [39.874472, 32.764193]
                            ],
                        }],
                    }
                ],
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
                this.data = JSON.parse(JSON.stringify(companyModel));
                this.$validator.reset();
                this.isEdit = false;
            },
            vendorTabChanged() {
                // to display all tiles
                window.dispatchEvent(new Event('resize'));
            },

            layerChanged(layerId, active) {
                const layer = this.layers.find(layer => layer.id === layerId);

                layer.features.forEach((feature) => {
                    if (active) {
                        feature.leafletObject.addTo(this.map);
                    } else {
                        feature.leafletObject.removeFrom(this.map);
                    }
                });
            },
            initLayers() {
                this.layers.forEach((layer) => {
                    const markerFeatures = layer.features.filter(feature => feature.type ===
                        'marker');
                    const polygonFeatures = layer.features.filter(feature => feature.type ===
                        'polygon');

                    markerFeatures.forEach((feature) => {
                        feature.leafletObject = L.marker(feature.coords)
                            .bindPopup(feature.name);
                    });

                    polygonFeatures.forEach((feature) => {
                        feature.leafletObject = L.polygon(feature.coords)
                            .bindPopup(feature.name);
                    });
                });
            },
            initMap() {

                this.map = L.map('map').setView([39.918836, 32.836816], 12);
                this.tileLayer = L.tileLayer(
                    'https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
                        maxZoom: 18,
                        attribution: '&copy; <a href="http://team9.bilkent.edu.tr/">MANERP</a>, &copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
                        id: 'mapbox.streets',
                        accessToken: 'pk.eyJ1IjoiYmVyYXRwb3N0YWxjaSIsImEiOiJjanVpd3RtZmwwaXRsNGVvNDcyd2dvM3lmIn0.nULWCGr3Uad3b9Rqhw2i4A'
                    }
                );

                this.tileLayer.addTo(this.map);

                // icon for marker
                var greenIcon = new L.Icon({
                    iconUrl: 'https://cdn.rawgit.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-green.png',
                    shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
                    iconSize: [25, 41],
                    iconAnchor: [12, 41],
                    popupAnchor: [1, -34],
                    shadowSize: [41, 41]
                });

                // get current location
                navigator.geolocation.getCurrentPosition(location => {
                    let pos = new L.LatLng(location.coords.latitude, location.coords.longitude);
                    this.map.setView(pos, 11);
                    L.marker(pos, {icon: greenIcon}).addTo(this.map);
                })
            }

            // data retrieve methods

        },
        mounted() {
            this.initMap();
            this.initLayers();
        }
    }
</script>
<style>
    #map {
        height: 500px;
    }

    #map img {
        max-height: none;
    }
</style>