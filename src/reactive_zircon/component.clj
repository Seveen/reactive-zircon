(ns reactive-zircon.component
  (:require [reactive-zircon.interop :as i]
            [reactive-zircon.event :as event])
  (:import (org.hexworks.zircon.api.component ComponentAlignment)
           (org.hexworks.zircon.api.game ProjectionMode)
           (org.hexworks.zircon.api ComponentDecorations Components)
           (org.hexworks.zircon.api.graphics BoxType)
           (org.hexworks.zircon.api.component.renderer ComponentDecorationRenderer)))

(def alignments
  {:center        `ComponentAlignment/CENTER
   :left-center   `ComponentAlignment/LEFT_CENTER
   :right-center  `ComponentAlignment/RIGHT_CENTER
   :top-left      `ComponentAlignment/TOP_LEFT
   :bottom-left   `ComponentAlignment/BOTTOM_LEFT
   :top-center    `ComponentAlignment/TOP_CENTER
   :bottom-center `ComponentAlignment/BOTTOM_CENTER
   :top-right     `ComponentAlignment/TOP_RIGHT
   :bottom-right  `ComponentAlignment/BOTTOM_RIGHT})

;(defn ^:private m->alignment [{:keys alignment strategy component}]
;  ;; TODO: gérer l'alignement (besoin de pouvoir référencer les components de la scene)
;  )

(def projections
  {:top-down `ProjectionMode/TOP_DOWN
   :oblique  `ProjectionMode/TOP_DOWN_OBLIQUE})

(defn ^:private property? [builder property fun]
  (if (some? property)
    (fun builder)
    builder))

(defmulti ->decoration (fn [m] (:type m)))

(def box-types
  {:basic             BoxType/BASIC
   :single            BoxType/SINGLE
   :double            BoxType/DOUBLE
   :top-bottom-double BoxType/TOP_BOTTOM_DOUBLE
   :left-right-double BoxType/LEFT_RIGHT_DOUBLE})

(defmethod ->decoration :box
  [{:keys [title box-type] :or {title "" box-type :basic}}]
  (ComponentDecorations/box (box-type box-types) title))

(defmethod ->decoration :shadow [_]
  (ComponentDecorations/shadow))

(defmethod ->decoration :half-block [_]
  (ComponentDecorations/halfBlock))

(defmethod ->decoration :border [_]
  ;; TODO add custom borders
  (ComponentDecorations/border))

(defmethod ->decoration :side
  [{:keys [left right] :or {left \[ right \]}}]
  (ComponentDecorations/side left right))

(defn ^:private with-decorations [builder decorations]
  (if (seq decorations)
    (.withDecorations
      builder
      (into-array
        ComponentDecorationRenderer
        (map ->decoration decorations)))
    builder))

;; TODO: base builder:
;; styleset
;; tileset
;; colortheme
;; alignment
;; renderer
(defn ^:private base-component
  [builder {:keys [size position decorations]
            :or {size [1 1]}
            :as params}]
  (let [[width height] size]
    (-> builder
        (.withSize width height)
        (with-decorations decorations)
        (property? position
                   #(.withPosition %1 (i/vec->pos position)))
        (.build)
        (event/bind-event-handlers params))))

(defn ^:private base-component-with-text
  [builder {:keys [text] :or {text ""} :as params}]
  (-> builder
      (.withText text)
      (base-component params)))

(defmulti ->component (fn [m] (:type m)))

;; TODO
; numberInput
; textBox
; icon

; groups ? (radiogroup) -> pas des composants, faire autrement

;; DONE
(defmethod ->component :button [params]
  (base-component-with-text (Components/button) params))

(defmethod ->component :label [params]
  (base-component-with-text (Components/label) params))

(defmethod ->component :text-area [params]
  (base-component-with-text (Components/textArea) params))

(defmethod ->component :check-box [params]
  (base-component-with-text (Components/checkBox) params))

(defmethod ->component :header [params]
  (base-component-with-text (Components/header) params))

(defmethod ->component :list-item [params]
  (base-component-with-text (Components/listItem) params))

(defmethod ->component :paragraph [params]
  (-> (Components/paragraph)
      (property? (:typing-speed params)
                 #(.withTypingEffect % (:typing-speed params)))
      (base-component-with-text params)))

(defmethod ->component :toggle-button [params]
  (-> (Components/toggleButton)
      (property? (:selected params)
                 #(.withIsSelected % (:selected params)))
      (base-component-with-text params)))

(defmethod ->component :radio-button [params]
  (-> (Components/radioButton)
      (.withKey (:key params))
      (base-component-with-text params)))

(defmethod ->component :panel [params]
  (-> (Components/panel)
      (base-component params)))

(defmethod ->component :log-area [params]
  (-> (Components/logArea)
      (base-component params)))

(defmethod ->component :hbox [params]
  (-> (Components/hbox)
      (property? (:spacing params)
                 #(.withSpacing % (:spacing params)))
      (base-component params)))

(defmethod ->component :vbox [params]
  (-> (Components/vbox)
      (property? (:spacing params)
                 #(.withSpacing % (:spacing params)))
      (base-component params)))

(defmethod ->component :progress-bar
  [{:keys [range steps display-percent]
    :or {range 100 steps 10 display-percent false}
    :as params}]
  (-> (Components/progressBar)
      (.withRange range)
      (.withNumberOfSteps steps)
      (.withDisplayPercentValueOfProgress display-percent)
      (base-component params)))


;; Component manipulation

(defn handle->component [handle]
  (.asInternalComponent handle))

(defn set-field [component field value]
  (case field
    :text (.setText component value)
    :hidden (.setHidden component value)
    ()))


;; Tests

(comment

  (def button {:type     :button
               :size     [10 10]
               :position [1 1]
               :text     "BUTTON"})

  (m->component button)

  )