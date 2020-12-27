@file:JsModule("@fullcalendar/interaction")
@file:JsNonModule

package fullcalendar

import org.w3c.dom.Element
import org.w3c.dom.HTMLElement

@JsName("default")
external val interactionPlugin: Plugin

@JsName("Draggable")
external class ExternalDraggable() {
    constructor(el: Element?)
    constructor(element: Element?, settings: ExternalDraggableSettings)
    fun destroy()
}

//el: HTMLElement, settings?: ExternalDraggableSettings
external interface ExternalDraggableSettings {
    var itemSelector: String
    var eventData: ((HTMLElement) -> EventData)?
    //var eventData: EventData?
}
