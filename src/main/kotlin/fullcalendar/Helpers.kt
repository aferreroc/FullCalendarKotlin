package fullcalendar

import org.w3c.dom.HTMLElement
import react.RClass
import react.RProps

typealias Locale = RClass<RProps>
typealias Plugin = RClass<RProps>

data class DraggableSettings(override var itemSelector: String) : ExternalDraggableSettings {
    override var eventData: ((HTMLElement) -> EventData)? = null

    constructor(itemSelector: String, eventData: ((HTMLElement) -> EventData)?) : this(itemSelector) {
        this.eventData = eventData
    }
    /*override var eventData: EventData? = null

    constructor(itemSelector: String, eventData: EventData) : this(itemSelector) {
        this.eventData = eventData
    }*/
}

// Tiempo que empieza
enum class START_TIME(val startTime: String) {
    FIRST_DISH("T00:00"), SECOND_DISH("T02:00"), DESSERT("T03:00")
}

