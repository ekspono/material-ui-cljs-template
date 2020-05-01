(ns ekspono.project.components.main
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]
            [goog.object :as gobj]
            ["@material-ui/core" :as mui]
            ["@material-ui/core/styles" :refer [withStyles createMuiTheme]]
            ["@material-ui/icons" :as mui-icons]
            ["@material-ui/core/colors" :as mui-colors]))

(def custom-theme
  (createMuiTheme
   #js {:palette #js {:primary #js {:main (gobj/get (.-blue ^js/Mui.Colors mui-colors) 100)}}}))

(defn custom-styles [^js/Mui.Theme theme]
  #js {:button #js {:margin (.spacing theme 1)}
       :textField #js {:width 200
                       :marginLeft (.spacing theme 1)
                       :marginRight (.spacing theme 1)}})

(def with-custom-styles (withStyles custom-styles))

(defn menu-app-bar
  [{:keys [classes] :as props}]
  [:div {:class-name (.-root classes)
         :style {:flex-grow 1}}
   [:> mui/AppBar {:position "static"}
    [:> mui/Toolbar
     [:> mui/IconButton {:edge "start" :class-name (.-menuButton classes) :color "inherit" :aria-label "menu"
                         :style {:margin-right "1em"}}
      [:> mui-icons/Menu]]
     [:> mui/Typography {:variant "h6" :class-name (.-title classes)
                         :style {:flex-grow 1}}
      "ekspono - project!"]
     [:> mui/Button {:color "inherit"}
      "Login"]]]])

(defn main []
  ;; fragment
  [:<>
   [:> mui/CssBaseline]
   [:> mui/MuiThemeProvider
    {:theme custom-theme}
    [:> mui/Grid
     {:container true
      :direction "row"
      :justify "center"}
     [:> mui/Grid
      {:item true
       :xs 12}
      [:> (with-custom-styles (r/reactify-component menu-app-bar))]]]]])
