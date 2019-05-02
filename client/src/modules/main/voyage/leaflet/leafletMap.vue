<template>
    <div id="voyage_map"></div>
</template>
<!--TODO: use marker cluster in the future-->
<script>
    import L from 'leaflet'
    import 'leaflet/dist/leaflet.css'

    export default {
        name: "leafletMap",
        data() {
            return {
                map: null,
                popupContent: '',
                currentMarker: null,
                tileLayer: null,
                greenMarker: null,
                blackMarker: null,
                tileType: 'mapbox.streets-basic',
                zoomLevel: 6,
                layers: [
                    {id: 0, name: 'Rota Oluştur', active: false}
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
                    location.leafletMarker = L.marker(location.location, {icon: this.blackMarker, data: location})
                        .bindPopup(L.popup({closeOnClick: false, autoClose: true}).setContent(this.popupContent))
                        .bindTooltip('<p>' + location.title + '</p>')
                        .on('click', this.onMarkerClick);

                    coordsArr.push(location.location)
                });
                this.locations.polygon = L.polygon(coordsArr);
            },
            initMap() {
                this.map = L.map('voyage_map').setView([39.918836, 32.836816], this.zoomLevel);

                this.tileLayer = L.tileLayer(
                    'https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
                        maxZoom: 18,
                        attribution: '&copy; <a href="http://team9.bilkent.edu.tr/">MANERP</a>, &copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
                        id: this.tileType,
                        accessToken: 'pk.eyJ1IjoiYmVyYXRwb3N0YWxjaSIsImEiOiJjanVpd3RtZmwwaXRsNGVvNDcyd2dvM3lmIn0.nULWCGr3Uad3b9Rqhw2i4A'
                    }
                );
                this.tileLayer.addTo(this.map);

                // get current location
                navigator.geolocation.getCurrentPosition(location => {
                    let pos = new L.LatLng(location.coords.latitude, location.coords.longitude);
                    this.map.setView(pos, this.zoomLevel);
                    L.marker(pos, {icon: this.greenMarker}).addTo(this.map);
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
            save(coords) {
                let title = L.DomUtil.get('title').value;
                let phone = L.DomUtil.get('phone').value;
                let address = L.DomUtil.get('address').value;

                let valid = this.validateInput();
                if (valid) {
                    let vendor = {
                        title: title,
                        phone: phone,
                        address: address,
                        lat: parseFloat(coords.lat).toPrecision(7),
                        lng: parseFloat(coords.lng).toPrecision(7)
                    };
                    this.$emit('save', vendor);
                }
            },
            getLayers() {
                return this.layers;
            },
            setLocations(locations) {
                this.locations = locations;
                this.initObjects();
            },
            onMarkerClick(e) {

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