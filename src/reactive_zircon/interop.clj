(ns reactive-zircon.interop
  (:import (org.hexworks.zircon.api.data Size Position)
           (kotlin.jvm.functions Function1 Function2)))

(defn vec->size
  [[x y]]
  (Size/create x y))

(defn vec->pos
  [[x y]]
  (Position/create x y))

(defn fn->fn1
  [function]
  (reify Function1
    (invoke [_ p1]
      (function p1))))

(defn fn->fn2
  [function]
  (reify Function2
    (invoke [_ p1 p2]
      (function p1 p2))))
