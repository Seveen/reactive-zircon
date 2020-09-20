(ns examples.all-components)

(def app-config {:renderer       :swing
                 :size           [80 50]
                 :tileset        :bisasam16x16
                 :color-theme    :entrappedInAPalette
                 :close-behavior :no-close})

(def view-config [{:id          :my-panel
                   :type        :panel
                   :decorations [{:type  :box
                                  :title "I'm box"}]
                   :position    [1 1]
                   :size        [20 20]
                   :children    [{:id          :my-panel2
                                  :type        :panel
                                  :decorations [{:type :shadow}]
                                  :size        [15 15]
                                  :children    [{:id       :my-button
                                                 :type     :button
                                                 :text     "BUTTON"
                                                 :size     [10 1]
                                                 :position [1 1]}
                                                {:id       :my-button2
                                                 :type     :button
                                                 :text     "BUTTON54654"
                                                 :size     [10 1]
                                                 :position [1 2]}
                                                {:id          :byte
                                                 :type        :button
                                                 :text        "BYTE"
                                                 :decorations [{:type  :box
                                                                :title "I'm box too"}]
                                                 :size        [12 3]
                                                 :position    [1 3]}]}]}])
