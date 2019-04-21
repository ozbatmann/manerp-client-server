export const template = '<form id="popup-form">\
                                      <label for="input-speed">New speed:</label>\
                                      <input id="input-speed" class="popup-input" type="number" />\
                                      <table class="popup-table">\
                                        <tr class="popup-table-row">\
                                          <th class="popup-table-header">Arc numer:</th>\
                                          <td id="value-arc" class="popup-table-data"></td>\
                                        </tr>\
                                        <tr class="popup-table-row">\
                                          <th class="popup-table-header">Current speed:</th>\
                                          <td id="value-speed" class="popup-table-data"></td>\
                                        </tr>\
                                      </table>\
                                      <button id="button-submit" type="button">Save Changes</button>\
                                    </form>';