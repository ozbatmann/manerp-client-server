export const template = '<div class="map-popup-container">\
                            <form id="map-popup-form">\
                                <table>\
                                    <tr>\
                                        <td>\
                                            <h3>Bayi</h3>\
                                        </td>\
                                        <td class="map-popup-coords">\
                                            <span id="map-popup-coords-p"></span>\
                                        </td>\
                                    </tr>\
                                </table>\
                                <fieldset>\
                                    <input maxlength="150" id="title" name="title" placeholder="Unvan" type="text" tabindex="1" autofocus>\
                                    <span id="title-warn">Bu alan gereklidir</span>\
                                </fieldset>\
                                <fieldset>\
                                    <input maxlength="20" id="phone" name="phone" placeholder="Telefon" type="text" tabindex="2">\
                                </fieldset>\
                                <fieldset>\
                                    <textarea maxlength="255" id="address" name="address" placeholder="Adres" tabindex="3"></textarea>\
                                    <span id="address-warn">Bu alan gereklidir</span>\
                                </fieldset>\
                                <fieldset>\
                                    <label id="checkbox-container">Aktif\
                                      <input type="checkbox" id="active" checked="checked">\
                                      <span class="checkmark"></span>\
                                    </label>\
                                </fieldset>\
                                <fieldset>\
                                    <button id="popup-submit" name="submit" type="button">Kaydet</button>\
                                </fieldset>\
                                <fieldset>\
                                    <button id="popup-delete" name="delete" type="button">Sil</button>\
                                </fieldset>\
                            </form>\
                        </div>';