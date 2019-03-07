package tr.com.manerp.common

import grails.gorm.transactions.Transactional
import org.grails.web.json.JSONArray


@Transactional
class JsonArrayParserService {

    def jsonArray2LongArray(JSONArray jsonArray) {
        long[] longArray = new long[jsonArray.length()]
        for (int i = 0; i < longArray.length; ++i) {
            longArray[i] = jsonArray.optInt(i)
        }
        return longArray
    }

}
