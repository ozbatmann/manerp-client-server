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
                layers: [
                    {id: 0, name: 'Bayileri Göster', active: false},
                    {id: 1, name: 'Bayi Sınır Çizgileri', active: false}
                ],
                locations: []
            }
        },
        methods: {
            layerChanged(layerId, active) {
                const layer = this.layers.find(layer => layer.id === layerId);

                if (layer.id === 0) {
                    this.locations.forEach((location) => {
                        if (active) {
                            location.leafletMarker.addTo(this.map)
                        } else {
                            location.leafletMarker.removeFrom(this.map)
                        }
                    });
                } else if (layer.id === 1) {
                    if (active) {
                        this.locations.polygon.addTo(this.map)
                    } else {
                        this.locations.polygon.removeFrom(this.map)
                    }
                }
            },
            initObjects() {
                let coordsArr = [];
                this.locations.forEach((location) => {
                    location.leafletMarker = L.marker(location.coords, {icon: this.blackMarker})
                        .bindPopup(this.popupContent)
                        .bindTooltip('<p>' + location.name + '</p>')
                        .on('click', this.onMarkerClick);

                    coordsArr.push(location.coords)
                });
                this.locations.polygon = L.polygon(coordsArr);
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
            initIcons() {
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
            getLayers() {
                return this.layers;
            },
            edit() {
            },
            setLocations(locations) {
                this.locations = locations;
                this.initObjects();
            },
            setPopupContent(html) {
                this.popupContent = html;
                this.initObjects();
            },
            onMarkerClick(e) {
                console.log('e:', e)
            }
        },
        mounted() {
            this.initIcons();
            this.initMap();
            this.initObjects();
        }
    }
</script>

<style scoped>

</style>