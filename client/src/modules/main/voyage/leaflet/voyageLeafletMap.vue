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
                currentMarker: null,
                tileLayer: null,
                greenMarker: null,
                blackMarker: null,
                tileType: 'mapbox.streets-basic',
                zoomLevel: 6,
                layers: [
                    {id: 0, name: 'Bayileri Göster', active: false},
                    {id: 1, name: 'Rotaları Göster', active: false}
                ],
                locations: [],
                waypointPolyline: null,
                waypointsArr: [],
                colorSet: {
                    aqua: "#00ffff",
                    black: "#000000",
                    blue: "#0000ff",
                    brown: "#a52a2a",
                    cyan: "#00ffff",
                    darkblue: "#00008b",
                    darkcyan: "#008b8b",
                    darkgreen: "#006400",
                    darkmagenta: "#8b008b",
                    darkolivegreen: "#556b2f",
                    darkorchid: "#9932cc",
                    darkred: "#8b0000",
                    darksalmon: "#e9967a",
                    darkviolet: "#9400d3",
                    fuchsia: "#ff00ff",
                    green: "#008000",
                    indigo: "#4b0082",
                    lightgreen: "#90ee90",
                    lime: "#00ff00",
                    magenta: "#ff00ff",
                    maroon: "#800000",
                    navy: "#000080",
                    olive: "#808000",
                    purple: "#800080",
                    violet: "#800080",
                    red: "#ff0000",
                }
            }
        },
        methods: {
            pickRandomColor() {
                let result;
                let count = 0;
                for (let prop in this.colorSet)
                    if (Math.random() < 1 / ++count)
                        result = prop;
                return result;
            },
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
                    if (this.waypointPolyline) {
                        if (active) {
                            this.waypointPolyline.addTo(this.map);
                        } else {
                            this.waypointPolyline.removeFrom(this.map);
                        }
                    }
                }
            },
            initMarkers() {
                this.locations.forEach((location) => {
                    location.leafletMarker = L.marker(location.location, {icon: this.blackMarker, data: location})
                        .bindTooltip('<p>' + location.title + '</p>')
                        .on('click', this.onMarkerClick);
                });
            },
            initPolyline() {
                this.waypointPolyline = L.polyline(this.waypoints).setStyle({color: this.pickRandomColor()});
            },
            initMap() {
                this.map = L.map('voyage_map').setView([39.918836, 32.836816], this.zoomLevel);

                this.tileLayer = L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
                    attribution: '&copy; <a href="http://team9.bilkent.edu.tr/">MANERP</a>, &copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
                }).addTo(this.map);

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
            save() {

            },
            getLayers() {
                return this.layers;
            },
            setLocations(locations) {
                this.locations = locations;
                this.initMarkers();
            },
            setWaypoints(waypoints) {
                this.waypoints = waypoints;
                this.initPolyline();
            },
            onMarkerClick(e) {

            },
            cleanData() {
                this.waypointPolyline = null;
                this.waypoints = [];
                this.locations = [];
            }
        },
        mounted() {
            this.initIcons();
            this.initMap();
            this.initMarkers();
        }
    }
</script>

<style scoped>

</style>