(ns reactive-zircon.color-theme
  (:import (org.hexworks.zircon.api ColorThemes)))

;TODO add custom color themes from external resources
(defn color-themes
  [theme]
  (eval `(. ColorThemes ~(symbol theme))))
