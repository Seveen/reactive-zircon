(ns reactive-zircon.app
  (:require [reactive-zircon.tileset :as t]
            [reactive-zircon.color-theme :as cl])
  (:import (org.hexworks.zircon.api.builder.application AppConfigBuilder)
           (org.hexworks.zircon.api.application CloseBehavior CursorStyle)
           (org.hexworks.zircon.api.color TileColor)
           (org.hexworks.zircon.api SwingApplications LibgdxApplications)
           (org.hexworks.zircon.api.screen Screen)))

(def cursor-styles
  {:character-foreground CursorStyle/USE_CHARACTER_FOREGROUND
   :fixed-background     CursorStyle/FIXED_BACKGROUND
   :under-bar            CursorStyle/UNDER_BAR
   :vertical-bar         CursorStyle/VERTICAL_BAR})

(def close-behaviors
  {:no-close CloseBehavior/DO_NOTHING_ON_CLOSE
   :close    CloseBehavior/EXIT_ON_CLOSE})

(defn ^:private app-config
  [{:keys [size title cursor-blink blink-length
           fullscreen debug-mode clipboard cursor-style
           cursor-color tileset close-behavior]
    :or   {size         [80 24] title "Zircon Application" cursor-blink false
           blink-length 500 fullscreen false debug-mode false clipboard false
           cursor-style :fixed-background
           cursor-color "#FFFFFF" tileset :wanderlust16x16 close-behavior :close}}]
  (let [[width height] size]
    (-> (.newBuilder AppConfigBuilder/Companion)
      (.withTitle title)
      (.withSize width height)
      (.withCursorBlinking cursor-blink)
      (.withBlinkLengthInMilliSeconds blink-length)
      (as-> b (if fullscreen
                (.fullScreen b)
                b))
      (.withDebugMode debug-mode)
      (.withClipboardAvailable clipboard)
      (.withCursorColor (TileColor/fromString cursor-color))
      (.withCursorStyle (cursor-style cursor-styles))
      (.withDefaultTileset (t/cp437-tilesets tileset))
      (.withCloseBehavior (close-behavior close-behaviors))
      (.enableBetaFeatures)
      (.build))))

(defn ->app [config]
  (case (:renderer config)
    :swing (SwingApplications/startApplication (app-config config))
    :libgdx (LibgdxApplications/startApplication (app-config config))
    (SwingApplications/startApplication (app-config config))))

(defn ->screen [app {:keys [color-theme] :or {color-theme :tron}}]
  (doto (Screen/create (.getTileGrid app))
    (.setTheme (cl/color-themes color-theme))
    (.display)))