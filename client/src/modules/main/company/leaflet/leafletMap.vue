<template>
    <div id="map"></div>
</template>

<script>
    import L from 'leaflet'
    import 'leaflet/dist/leaflet.css'

    export default {
        name: "leafletMap",
        data() {
            return {
                map: null,
                popupContent: '',
                tileLayer: null,
                greenMarker: null,
                blackMarker: null,
                layers: []
            }
        },
        methods: {
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
                        feature.leafletObject = L.marker(feature.coords, {icon: this.blackMarker})

                            .bindPopup(this.popupContent)
                            .bindTooltip('<h1>' + feature.name + '</h1>')
                            .on('click', this.onMarkerClick)
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

                // get current location
                navigator.geolocation.getCurrentPosition(location => {
                    let pos = new L.LatLng(location.coords.latitude, location.coords.longitude);
                    this.map.setView(pos, 11);
                    L.marker(pos, {icon: this.greenMarker}).addTo(this.map);
                });

                // register right click popup event handler
                this.map.on('contextmenu', (e) => {
                    let popup = L.popup({closeOnClick: false, autoClose: true})
                        .setLatLng(e.latlng)
                        .setContent(this.popupContent);
                    this.map.openPopup(popup);

                    let buttonSubmit = L.DomUtil.get('button-submit');
                    if (buttonSubmit) {
                        L.DomEvent.addListener(buttonSubmit, 'click', () => this.save(e));
                    }
                });

            },
            initMarkers() {
                const shadow = require('@/assets/leaflet/icons/marker-shadow.png');

                const greenIcon = require('@/assets/leaflet/icons/marker-icon-2x-green.png');
                this.greenMarker = new L.Icon({
                    iconUrl: greenIcon,
                    shadowUrl: shadow,
                    iconSize: [25, 41],
                    iconAnchor: [12, 41],
                    popupAnchor: [1, -34],
                    shadowSize: [41, 41]
                });

                const blackIcon = require('@/assets/leaflet/icons/marker-icon-2x-black.png');
                this.blackMarker = new L.Icon({
                    iconUrl: blackIcon,
                    shadowUrl: shadow,
                    iconSize: [25, 41],
                    iconAnchor: [12, 41],
                    popupAnchor: [1, -34],
                    shadowSize: [41, 41]
                });
            },
            save(data) {
                console.log('data:', data)
                console.log('latlng:', data.latlng);

            },
            edit() {
            },
            setLayers(layers) {
                this.layers = layers;
                this.initLayers();
            },
            setPopupContent(html) {
                this.popupContent = html;
                this.initLayers();
            },
            onMarkerClick(e) {
                console.log('e:', e)
            }
        },
        mounted() {
            this.initMarkers();
            this.initMap();
            this.initLayers();
        }
    }
</script>

<style scoped>

</style>