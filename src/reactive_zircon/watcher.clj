(ns reactive-zircon.watcher
  (:use [editscript.core]
        [editscript.edit])
  (:require [reactive-zircon.internal-state :as state]
            [reactive-zircon.component :as c]))

(defn get-component-name [path]
  (get-in @state/view path))

(defmulti apply-change (fn [operation & _] operation))

(defmethod apply-change :+ [_ path field new-value]
  (prn "plus"))

(defmethod apply-change :- [_ path field new-value]
  (prn "minus"))

(defmethod apply-change :r [_ path field new-value]
  (let [component (c/handle->component
                    (get @state/named-components (get-component-name path)))]
    (c/set-field component field new-value)))

(defn split-path [path]
  (let [[path change] (split-with #(or (= :children %) (number? %)) path)]
    [(conj (vec path) :id) (last change)]))

(defn watcher [key atom old-state new-state]
  (doseq [[total-path operation new-value] (get-edits (diff old-state new-state))
          :let [[path field] (split-path total-path)]]
    (apply-change operation path field new-value)))
