(ns ekspono.project.components.drawer-contents
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]
            [goog.object :as gobj]
            ["@material-ui/core" :as mui]
            ["@material-ui/core/styles" :refer [withStyles]]
            ["@material-ui/icons" :as mui-icons]
            ["@material-ui/core/colors" :as mui-colors]))

;; TODO: Abstract away the rituals around styling a component

(def drawer-styles (withStyles (clj->js {:list {:width 250}
                                         :full-list {:width "auto"}})))
(defn drawer-list
  [{:keys [classes toggle_drawer] :as props}]
  (println (js->clj props))
  [:div {:role "presentation"
         :class-name (.-list classes)
         :onClick #(toggle_drawer false)
         :onKeyDown #(toggle_drawer false)}
   [:> mui/List
    [:> mui/ListItem
     [:> mui/ListItemIcon
      [:> mui-icons/MoveToInbox]
      [:> mui/ListItemText {:primary "Choice A"}]]]]])

;; TODO: r/reactify-component mangles the prop names
;; kebab-case turns into camelCase..
(defn drawer-contents
  [toggle-drawer]
  (let [root (drawer-styles (r/reactify-component drawer-list))]
    [:> root {:toggle_drawer toggle-drawer}]))