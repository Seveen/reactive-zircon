(ns reactive-zircon.event
  (:require [reactive-zircon.interop :as i])
  (:import (org.hexworks.zircon.api.uievent ComponentEventType MouseEventType KeyboardEventType)))

(defmulti ->event (fn [_ m] (:type m)))


;; ComponentEvent
(defmethod ->event :focus-given [component params]
  (.processComponentEvents
    component
    ComponentEventType/FOCUS_GIVEN
    (i/fn->fn1 (:fn params))))

(defmethod ->event :focus-taken [component params]
  (.processComponentEvents
    component
    ComponentEventType/FOCUS_TAKEN
    (i/fn->fn1 (:fn params))))

(defmethod ->event :activated [component params]
  (.processComponentEvents
    component
    ComponentEventType/ACTIVATED
    (i/fn->fn1 (:fn params))))

(defmethod ->event :deactivated [component params]
  (.processComponentEvents
    component
    ComponentEventType/DEACTIVATED
    (i/fn->fn1 (:fn params))))


;; MouseEvent
(defmethod ->event :mouse-clicked [component params]
  (.processMouseEvents
    component
    MouseEventType/MOUSE_CLICKED
    (i/fn->fn2 (:fn params))))

(defmethod ->event :mouse-pressed [component params]
  (.processMouseEvents
    component
    MouseEventType/MOUSE_PRESSED
    (i/fn->fn2 (:fn params))))

(defmethod ->event :mouse-released [component params]
  (.processMouseEvents
    component
    MouseEventType/MOUSE_RELEASED
    (i/fn->fn2 (:fn params))))

(defmethod ->event :mouse-entered [component params]
  (.processMouseEvents
    component
    MouseEventType/MOUSE_ENTERED
    (i/fn->fn2 (:fn params))))

(defmethod ->event :mouse-exited [component params]
  (.processMouseEvents
    component
    MouseEventType/MOUSE_EXITED
    (i/fn->fn2 (:fn params))))

(defmethod ->event :mouse-wheel-up [component params]
  (.processMouseEvents
    component
    MouseEventType/MOUSE_WHEEL_ROTATED_UP
    (i/fn->fn2 (:fn params))))

(defmethod ->event :mouse-wheel-down [component params]
  (.processMouseEvents
    component
    MouseEventType/MOUSE_WHEEL_ROTATED_DOWN
    (i/fn->fn2 (:fn params))))

(defmethod ->event :mouse-dragged [component params]
  (.processMouseEvents
    component
    MouseEventType/MOUSE_DRAGGED
    (i/fn->fn2 (:fn params))))

(defmethod ->event :mouse-moved [component params]
  (.processMouseEvents
    component
    MouseEventType/MOUSE_MOVED
    (i/fn->fn2 (:fn params))))

;; KeyboardEvent
(defmethod ->event :key-pressed [component params]
  (.processKeyboardEvents
    component
    KeyboardEventType/KEY_PRESSED
    (i/fn->fn2 (:fn params))))

(defmethod ->event :key-typed [component params]
  (.processKeyboardEvents
    component
    KeyboardEventType/KEY_TYPED
    (i/fn->fn2 (:fn params))))

(defmethod ->event :key-released [component params]
  (.processKeyboardEvents
    component
    KeyboardEventType/KEY_RELEASED
    (i/fn->fn2 (:fn params))))


(defn bind-event-handlers [component params]
  (doseq [handler (:handlers params)]
    (->event component handler))
  component)