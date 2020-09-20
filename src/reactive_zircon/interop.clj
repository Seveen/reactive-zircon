(ns reactive-zircon.interop
  (:import (org.hexworks.zircon.api.data Size Position)))

(defn vec->size
  [[x y]]
  (Size/create x y))

(defn vec->pos
  [[x y]]
  (Position/create x y))
