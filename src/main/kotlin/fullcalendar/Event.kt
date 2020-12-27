package fullcalendar

import kotlin.js.Date
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

// Esta clase se usa para html
@Serializable
data class EventData(var title: String) {
    var startTime: String? = null
    var duration: String? = null

    constructor(title: String, startTime: START_TIME?) : this(title) {
        this.startTime = startTime?.startTime
    }

    constructor(title: String, startTime: START_TIME?, duration: String?) : this(title) {
        this.startTime = startTime?.startTime
        this.duration = duration
    }

    override fun toString(): String {
        return "<EventData> title: ${title} startTime: $startTime duration: $duration"
    }

    companion object {
        fun fromJson(jsonEventData: String?): EventData {
            return Json.decodeFromString<EventData>(jsonEventData!!)
        }
    }
}

// esta clase es la que usa el js
data class Event(var title: String, var start: Date?) {
    var id: String? = null
    var end: Date? = null
    var allDay: Boolean = true

    override fun toString(): String {
        return "<Event> title: ${title} start: $start"
    }
}