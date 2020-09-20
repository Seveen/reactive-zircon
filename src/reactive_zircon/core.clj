(ns reactive-zircon.core
  (:require [reactive-zircon.app :as app]
            [reactive-zircon.watcher :as w]
            [reactive-zircon.component :as c]
            [reactive-zircon.internal-state :as state]))

(defn build-and-bind [component-def]
  (let [name (:id component-def)
        component (c/->component component-def)
        children-def (:children component-def)]
    (if (seq children-def)
      (do
        (doseq [child-def children-def
                :let [[child-name child-component] (build-and-bind child-def)
                      handle (.addComponent component child-component)]]
          (swap! state/named-components assoc child-name handle))
        [name component])
      [name component])))

(defn make-app [app-config view-config]
  (let [app (app/->app app-config)
        screen (app/->screen app)]
    (doseq [component-def view-config
            :let [[name component] (build-and-bind component-def)
                  handle (.addComponent screen component)]]
      (swap! state/named-components assoc name handle))
    (reset! state/view view-config)
    (add-watch state/view :watcher w/watcher)
    state/view))

(comment

  (def app (app/->app {:renderer       :swing
                       :size           [80 50]
                       :tileset        :bisasam16x16
                       :close-behavior :no-close}))

  (def screen (->screen app))

  (def button (c/->component {:type     :button
                              :size     [10 1]
                              :position [10 10]
                              :text     "BUTTON"}))

  (def handle (.addComponent screen button))

  (def button (.detach handle))

  (make-app global-map)

  )