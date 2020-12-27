@file:JsModule("@fullcalendar/react")
@file:JsNonModule

package fullcalendar

import org.w3c.dom.Element
import react.Component
import react.RClass
import react.RProps
import react.RState
import kotlin.js.Date

@JsName("default")
external val fullCalendar: RClass<FullCalendarProps>

external interface FullCalendarRef {
    fun getApi(): CalendarApi
}

external interface FullCalendarProps : RProps {
    var ref: Any?
    var defaultView: String
    var plugins: Array<Plugin>
    var locale: Locale
    var events: ArrayList<Event>
    var weekends: Boolean
    var droppable: Boolean
    var editable: Boolean
    var drop: (DropInfo) -> Unit
    var eventAdd: (AddInfo) -> Unit
    var eventDragStop: (DragInfo) -> Unit
}

external interface CalendarApi {
    fun unselect()
    fun next()
    fun getEvents(): Array<Event>
    fun removeAllEvents()
    fun addEvent(event: Event)
}

external interface AddInfo {
    var event: Event
}

external interface DropInfo {
    var draggedEl: Element
    var date: Date
}

external interface DragInfo {
    var event: Any
    var jsEvent: Any
}

