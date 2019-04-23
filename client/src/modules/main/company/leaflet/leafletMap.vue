<template>
    <div id="map"></div>
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
                violetMarker: null,
                zoomLevel: 6,
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
                    location.leafletMarker = L.marker(location.location, {icon: this.blackMarker, data: location})
                        .bindPopup(L.popup({closeOnClick: false, autoClose: true}).setContent(this.popupContent))
                        .bindTooltip('<p>' + location.title + '</p>')
                        .on('click', this.onMarkerClick);

                    coordsArr.push(location.location)
                });
                this.locations.polygon = L.polygon(coordsArr);
            },
            initMap() {
                this.map = L.map('map').setView([39.918836, 32.836816], this.zoomLevel);

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
                    this.map.setView(pos, this.zoomLevel);
                    L.marker(pos, {icon: this.greenMarker}).addTo(this.map);
                });

                // register right click event handler
                this.map.on('contextmenu', (e) => {
                    if (!this.map._popup) {
                        let popup = L.popup({closeOnClick: false, autoClose: true})
                            .setLatLng(e.latlng)
                            .setContent(this.popupContent);

                        this.currentMarker = L.marker(e.latlng, {
                            icon: this.violetMarker,
                            draggable: true
                        });
                        this.currentMarker.bindPopup(popup).addTo(this.map).openPopup();
                        this.displayNoneDeleteButton();
                        this.displayNoneActiveButton();

                        this.currentMarker.on('dragend', () => {
                            this.currentMarker.openPopup();
                            this.displayCoordsOnPopup(this.currentMarker.getLatLng());
                            this.registerPopupSubmit(this.currentMarker.getLatLng());
                            this.displayNoneDeleteButton();
                            this.displayNoneActiveButton();
                        });

                        this.currentMarker._popup._closeButton.onclick = () => {
                            this.map.removeLayer(this.currentMarker);
                        };

                        this.displayCoordsOnPopup(e.latlng);
                        this.registerPopupSubmit(this.currentMarker.getLatLng());
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

                const violetIcon = require('@/assets/leaflet/icons/marker-icon-2x-violet.png');
                this.violetMarker = new L.Icon({
                    iconUrl: violetIcon,
                    shadowUrl: shadow,
                    iconSize: [25, 41],
                    iconAnchor: [12, 41],
                    popupAnchor: [1, -34],
                    shadowSize: [41, 41]
                });

            },
            validateInput() {
                let valid = true;
                let title = L.DomUtil.get('title').value;
                let address = L.DomUtil.get('address').value;

                if (!title) {
                    valid = false;
                    L.DomUtil.get('title-warn').style.display = 'block';
                } else {
                    L.DomUtil.get('title-warn').style.display = 'none';
                }
                if (!address) {
                    valid = false;
                    L.DomUtil.get('address-warn').style.display = 'block';
                } else {
                    L.DomUtil.get('address-warn').style.display = 'none';
                }

                return valid;
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
            edit(id, coords) {
                let title = L.DomUtil.get('title').value;
                let phone = L.DomUtil.get('phone').value;
                let address = L.DomUtil.get('address').value;
                let active = L.DomUtil.get('active').checked;

                let valid = this.validateInput();
                if (valid) {
                    let vendor = {
                        id: id,
                        title: title,
                        phone: phone,
                        address: address,
                        active: active,
                        lat: parseFloat(coords.lat).toPrecision(7),
                        lng: parseFloat(coords.lng).toPrecision(7)
                    };
                    this.$emit('edit', vendor);
                }
            },
            delete(id) {
                this.$emit('delete', id);
            },
            getLayers() {
                return this.layers;
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
                L.DomUtil.get('popup-submit').innerHTML = 'Güncelle';
                L.DomUtil.get('popup-delete').style.display = 'block';
                let buttonDelete = L.DomUtil.get('popup-delete');
                L.DomEvent.addListener(buttonDelete, 'click', () => this.delete(e.target.options.data.id));
                let buttonSubmit = L.DomUtil.get('popup-submit');
                L.DomEvent.addListener(buttonSubmit, 'click', () => this.edit(e.target.options.data.id, e.latlng));

                this.displayCoordsOnPopup(e.latlng);
                this.fillFormData(e.target.options.data);
            },
            displayCoordsOnPopup(latlng) {
                let coordsEl = L.DomUtil.get('map-popup-coords-p');
                coordsEl.innerHTML = parseFloat(latlng.lat).toPrecision(5) + ', ' + parseFloat(latlng.lng).toPrecision(5);
            },
            registerPopupSubmit(latlng) {
                let buttonSubmit = L.DomUtil.get('popup-submit');
                L.DomEvent.addListener(buttonSubmit, 'click', () => this.save(latlng));
            },
            closePopup() {
                this.map.closePopup();
                if (this.currentMarker) this.map.removeLayer(this.currentMarker);
            },
            displayNoneDeleteButton() {
                let deleteField = L.DomUtil.get('popup-delete');
                if (deleteField) deleteField.style.display = 'none';
            },
            displayNoneActiveButton() {
                let activeButton = L.DomUtil.get('checkbox-container');
                if (activeButton) activeButton.style.display = 'none';
            },
            disableClickOnMarkers() {
                this.locations.forEach((location) => {
                    location.leafletMarker.off('click');
                });
            },
            fillFormData(data) {
                L.DomUtil.get('title').value = data.title;
                L.DomUtil.get('phone').value = data.phone;
                L.DomUtil.get('address').value = data.address;
                L.DomUtil.get('active').checked = data.active;
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