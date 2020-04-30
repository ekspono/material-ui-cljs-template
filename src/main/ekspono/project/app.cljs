(ns ekspono.project.app
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]
            ["@material-ui/core" :as mui]))

(set! *warn-on-infer* true)

(def index-page
  [:div
   [:h1 "hello world!"]
   [:> mui/Button {:variant "contained"
                   :color "primary"}
    "hello"]])

(defn mount [el]
  (rdom/render index-page el))

(defn mount-app-element []
  (when-let [el (js/document.getElementById "app")]
    (mount el)))

(defn init []
  (println "Hello World")
  (mount-app-element))