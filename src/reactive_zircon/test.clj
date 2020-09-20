(ns reactive-zircon.test
  (:require [reactive-zircon.core :as z])
  (:use [clojure.walk]
        [editscript.core]
        [editscript.edit]))

(def view
  (z/make-app
    {:renderer       :swing
     :size           [20 20]
     :tileset        :rogueYun16x16
     :close-behavior :no-close}

    [{:id          :my-panel
      :type        :panel
      :decorations [{:type  :box
                     :title "Zircon"}
                    {:type :shadow}]
      :position    [0 0]
      :size        [20 20]
      :children    [{:id       :my-vbox
                     :type     :vbox
                     :size     [17 17]
                     :children [{:id   :my-button
                                 :type :button
                                 :text "BUTTON"
                                 :size [10 1]}
                                {:id   :my-button2
                                 :type :button
                                 :text "BUTTON2"
                                 :size [10 1]}
                                {:id       :my-hbox
                                 :type     :hbox
                                 :size     [15 5]
                                 :children [{:id   :my-label
                                             :type :label
                                             :text "Hello"
                                             :size [7 1]}
                                            {:id   :my-label
                                             :type :label
                                             :text "Zircon"
                                             :size [8 1]}]}]}]}]))


(comment

  (swap! view assoc-in [0 :children 0 :children 2 :children 1 :text] "everyone")
  (swap! view assoc-in [0 :children 0 :children 2 :children 1 :text] "Zircon")

  )
