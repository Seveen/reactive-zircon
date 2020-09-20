(ns reactive-zircon.test
  (:require [reactive-zircon.core :as z])
  (:use [clojure.walk]))

(def application
  {:app  {:renderer       :swing
          :size           [80 50]
          :tileset        :bisasam16x16
          :close-behavior :no-close}
   :root [{:id        :my-panel
           :component {:type        :panel
                       :decorations [{:type  :box
                                      :title "I'm box"}]
                       :position    [1 1]
                       :size        [20 20]}
           :children  [{:id        :my-panel2
                        :component {:type        :panel
                                    :decorations [{:type :shadow}]
                                    :size        [15 15]}
                        :children  [{:id        :my-button
                                     :component {:type     :button
                                                 :text     "BUTTON"
                                                 :size     [10 1]
                                                 :position [1 1]}}
                                    {:id        :my-button2
                                     :component {:type     :button
                                                 :text     "BUTTON54654"
                                                 :size     [10 1]
                                                 :position [1 2]}}
                                    {:id        :byte
                                     :component {:type        :button
                                                 :text        "BYTE"
                                                 :decorations [{:type  :box
                                                                :title "I'm box too"}]
                                                 :size        [12 3]
                                                 :position    [1 3]}}]}]}
          {:id        :my-panel3
           :component {:type     :panel
                       :position [21 1]
                       :size     [20 20]}
           :children  [{:id        :vbox
                        :component {:type :vbox
                                    :size [15 15]}
                        :children  [{:id        :my-button3
                                     :component {:type :button
                                                 :text "BUTTON3"
                                                 :size [10 1]}}
                                    {:id        :my-button4
                                     :component {:type :button
                                                 :text "BUTTON4"
                                                 :size [10 1]}}
                                    {:id        :bite2
                                     :component {:type :button
                                                 :text "BITE"
                                                 :size [10 1]}}
                                    {:id        :checkbox1
                                     :component {:type :check-box
                                                 :text "C"
                                                 :size [10 3]}}
                                    {:id        :toggle
                                     :component {:type :toggle-button
                                                 :text "T"
                                                 :size [10 3]}}]}]}
          {:id        :bar
           :component {:type            :progress-bar
                       :position        [42 1]
                       :size            [13 3]
                       :decorations     [{:type :box}]
                       :display-percent true}}]})

(def view (z/make-view application))

(comment

  (swap! view assoc-in [1 :children 0 :children 2 :component :text] "coucu")
  (swap! view assoc-in [1 :children 0 :children 2 :component :text] "coucou")

  )
