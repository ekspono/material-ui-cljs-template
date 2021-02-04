(ns project.app
  (:require [reagent.dom :as rdom]
            [project.components.main :refer [main]]))

(set! *warn-on-infer* true)

(defn mount 
  [el]
  (rdom/render main el))

(defn mount-app-element []
  (when-let [el (js/document.getElementById "app")]
    (mount el)))

(defn init []
  (mount-app-element))

(defn ^:dev/after-load start []
  (mount-app-element))