import com.ccfraser.muirwik.components.*
import com.ccfraser.muirwik.components.button.MButtonVariant
import com.ccfraser.muirwik.components.button.mButton
import fullcalendar.*
import kotlinx.browser.document
import kotlinx.css.*
import org.w3c.dom.Element
import org.w3c.dom.HTMLElement
import react.*
import react.dom.br
import react.dom.div
import react.dom.findDOMNode
import react.dom.img
import styled.css
import styled.styledDiv
import styled.styledP

interface FullCalendarRProps : RProps {
    var eventsList: ArrayList<Event>
}

class Fullcalendar : RComponent<FullCalendarRProps, RState>() {

    private var fullCalendarRef = createRef<FullCalendarRef>()
    private var externalEventsRef: Element? = null

    private val handleEventDataDraggable: (HTMLElement) -> EventData = {
        val jsonDataEvent = it.getAttribute("data-event")
        val dataEvent = EventData.fromJson(jsonDataEvent)
        console.log("handleEventData: ${it.innerText} ${dataEvent}")
        //EventData(it.innerText)
        dataEvent
    }

    private val handleEventAdd: (AddInfo) -> Unit = {
        console.log("add!  ${it.event.toString()}")
        //val calendarApi = fullCalendarRef.current?.getApi()
        //calendarApi?.addEvent(it.event)
    }

    private val handleDrop: (DropInfo) -> Unit = {
        console.log("dropped! datos: ${it.draggedEl.getAttribute("data-event")}")
        // Convert the received data-event "{ "title": "my event 2", "duration": "02:00" }" to DataEvent
        // I do this vy serialization
        // val dataEvent = it.draggedEl.getAttribute("data-event") as DataEvent
        val receivedDataEvent = it.draggedEl.getAttribute("data-event")
        if (receivedDataEvent != null) {
            //val dataEvent = Json.decodeFromString<EventData>(receivedDataEvent)
            val dataEvent = EventData.fromJson(receivedDataEvent)
            val calendarApi = fullCalendarRef.current?.getApi()
            if (calendarApi != null) {
                calendarApi.addEvent(Event(dataEvent.title, it.date))
                console.log("Eventos en la api: ${calendarApi.getEvents().joinToString { it.title }}")
            }
        }
    }

    private val handleEventDragStop: (DragInfo) -> Unit = {
        val trashEl = document.getElementById("fcTrash") as HTMLElement

        val x1 = trashEl.offsetLeft
        val x2 = trashEl.offsetLeft + trashEl.offsetWidth
        val y1 = trashEl.offsetTop
        val y2 = trashEl.offsetTop + trashEl.offsetHeight

        if (it.jsEvent.asDynamic().pageX >= x1 && it.jsEvent.asDynamic().pageX <= x2 &&
            it.jsEvent.asDynamic().pageY >= y1 && it.jsEvent.asDynamic().pageY <= y2
        ) {
            it.event.asDynamic().remove()
        }
    }

    // Call to test
    private fun handleOnClickWhatEvents() {
        val calendarApi = fullCalendarRef.current?.getApi()
        if (calendarApi != null) {
            console.log(
                "Eventos calendar: ${
                    calendarApi.getEvents().joinToString { "title: ${it.title} date: ${it.start}" }
                }"
            )

            /* There is an issue with the selectable option and hooks.
            If you take out selectable it should work.
            Alternatively, you need to pass a reference to the calendar and unselect it before manipulating the state.
             */
            //calendarApi.unselect()
            //externalDraggable?.destroy()
            //externalDraggable = null
            //setState {}
        }

    }


    override fun RBuilder.render() {

        mPaper {
            css { width = 100.pct; marginTop = 3.spacingUnits }
            mTypography("Fullcalendar KOTLIN example", variant = MTypographyVariant.h4)
        }
        br { }
        mGridContainer(justify = MGridJustify.center, spacing = MGridSpacing.spacing1) {
            mGridItem(xs = MGridSize.cells9) {
                fullCalendar {
                    attrs {
                        defaultView = "dayGridMonth"
                        plugins = arrayOf(dayGridPlugin, interactionPlugin)
                        locale = localeES
                        weekends = false
                        events = props.eventsList
                        droppable = true
                        //drop = handleDrop
                        //eventAdd = handleEventAdd
                        editable = true
                        ref = fullCalendarRef
                        eventDragStop = handleEventDragStop
                    }
                }
            }
            mGridItem(xs = MGridSize.cells3) {
                div(classes = "external-events") {
                    styledP {
                        css { marginBottom = 10.px }
                        +"Draggable Events"
                    }
                    styledDiv {
                        css {
                            +"fc-event fc-h-event fc-daygrid-event fc-daygrid-block-event"; marginBottom =
                            10.px; cursor = Cursor.pointer
                        }
                        attrs["data-event"] = "{ \"title\": \"my event 1\", \"startTime\": \"01:00\" }"
                        +"My Event1"
                    }
                    styledDiv {
                        css {
                            +"fc-event fc-h-event fc-daygrid-event fc-daygrid-block-event"; marginBottom =
                            10.px; cursor = Cursor.pointer
                        }
                        attrs["data-event"] = "{ \"title\": \"my event 2\", \"startTime\": \"02:00\" }"
                        +"My Event2"
                    }
                    ref {
                        externalEventsRef = findDOMNode(it)
                        ExternalDraggable(
                            element = externalEventsRef,
                            settings = DraggableSettings(
                                itemSelector = ".fc-event",
                                eventData = handleEventDataDraggable
                            )
                        )
                    }
                }

                div {
                    mButton(
                        "What are the fullcalendar events?",
                        MColor.primary, MButtonVariant.contained,
                        onClick = { handleOnClickWhatEvents() }) {}

                }

                div {
                    attrs["id"] = "fcTrash"
                    img(src = "images/trash.png") {}
                }
            }
        }
    }
}
