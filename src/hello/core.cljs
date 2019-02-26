(ns hello.core
  (:require [reagent.core :as reagent :refer [atom]]))

(def catalogo-filmes [
                      {:id 1
                       :nome "Lady gaga" 
                       :capa "https://m.media-amazon.com/images/M/MV5BNmE5ZmE3OGItNTdlNC00YmMxLWEzNjctYzAwOGQ5ODg0OTI0XkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX182_CR0,0,182,268_AL_.jpg"}
                      {
                       :id 2
                       :nome "Fredy mercury"
                       :capa "https://m.media-amazon.com/images/M/MV5BNDg2NjIxMDUyNF5BMl5BanBnXkFtZTgwMzEzNTE1NTM@._V1_UX182_CR0,0,182,268_AL_.jpg"}
                      {
                       :id 3
                       :nome "Rei leão"
                       :capa "https://m.media-amazon.com/images/M/MV5BMjIwMjE1Nzc4NV5BMl5BanBnXkFtZTgwNDg4OTA1NzM@._V1_UX182_CR0,0,182,268_AL_.jpg"}
                      ])
(defn filme-selecionado [filme]
  [:div {:style {:background-color :red}}[:p (:nome filme)]])


(defn chamada-do-filme [filme]
 [:div { :on-click #(js/alert "pegou")
        :style {:margin 10}}
  [:img {:src (get filme :capa) 
         :style {:width 150}
         }]
  [:p {:style {:color "white"
               :text-align "center"}}(get filme :nome)]] )

(defn netflix []
  (println catalogo-filmes)
  [:div 
   [:div {:style {:display "flex"
                  :justify-content "left"}} (map chamada-do-filme catalogo-filmes)
    ]
   [filme-selecionado (first catalogo-filmes)]])






(defn start []
  (reagent/render-component [netflix]
                            (. js/document (getElementById "app"))))

(defn ^:export init []
  ;; init is called ONCE when the page loads
  ;; this is called in the index.html and must be exported
  ;; so it is available even in :advanced release builds
  (start))

(defn stop []
  ;; stop is called before any code is reloaded
  ;; this is controlled by :before-load in the config
  (js/console.log "stop"))
