(ns ekspono.project.components.main
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]
            [goog.object :as gobj]
            ["@material-ui/core" :as mui]
            ["@material-ui/core/styles" :refer [withStyles makeStyles createMuiTheme]]
            ["@material-ui/icons" :as mui-icons]
            ["@material-ui/core/colors" :as mui-colors]
            [ekspono.project.components.drawer-contents :refer [drawer-contents]]))

;; Example of how to make Material UI components interact with reagent state
(def state (r/atom {:drawer-active false}))

(defn toggle-drawer
  [active]
  (swap! state assoc :drawer-active active))

;; An example of how to use Material UI's theme facility to override default colors
(def custom-theme
  (createMuiTheme
   #js {:palette #js {:primary #js {:main (gobj/get (.-blue ^js/Mui.Colors mui-colors) 100)}}}))

(defn app-bar-styles [^js/Mui.Theme theme]
  #js {:button #js {:margin (.spacing theme 1)}
       :textField #js {:width 200
                       :marginLeft (.spacing theme 1)
                       :marginRight (.spacing theme 1)}})

(def with-app-bar-styles (withStyles app-bar-styles))

(def drawer-styles (withStyles (clj->js {:list {:width 250}
                                         :full-list {:width "auto"}})))

;; The :> directive allows us to directly embed JS react components without
;; first converting them using r/adapt-react-class, reducing the amount of 
;; boilerplate code

(defn app-bar
  [{:keys [classes] :as props}]
  [:div {:class-name (.-root classes)
         :style {:flex-grow 1}}
   [:> mui/Drawer {:anchor "left"
                   :open (:drawer-active @state)
                   :on-close #(toggle-drawer false)}
    (drawer-contents toggle-drawer)]
   [:> mui/AppBar {:position "static"}
    [:> mui/Toolbar
     [:> mui/IconButton {:edge "start" 
                         :class-name (.-menuButton classes) 
                         :color "inherit" 
                         :aria-label "menu"
                         :style {:margin-right "1em"}
                         :on-click #(toggle-drawer true)}
      [:> mui-icons/Menu]]
     [:> mui/Typography {:variant "h6" :class-name (.-title classes)
                         :style {:flex-grow 1}}
      (let [drawer-state (:drawer-active @state)]
        (str "ekspono - project! " drawer-state))]
     [:> mui/Button {:color "inherit"}
      "Login"]]]])

;; The app-bar component contains both the mui/AppBar and the mui/Drawer components
;; Since app-bar is a reagent component it needs to be converted to a react component
;; using r/reactify-component

(defn main []
  [:<> ;; return a react fragment
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
      [:> (with-app-bar-styles (r/reactify-component app-bar))]
      [:> mui/Typography {:variant "h3"}
       "Welcome to the project!"]
      [:> mui/Typography {:variant "body1"}
       "This is where you can put the main contents of the app"]]]]])
