(ns hello.core
  (:require [reagent.core :as reagent :refer [atom]]))



(def catalogo-filmes [
                      {
                       :id 1
                       :nome "Lady gaga" 
                       :video "https://i.ytimg.com/vi/WGsA2aXYBGo/maxresdefault.jpg"
                       :capa "https://m.media-amazon.com/images/M/MV5BNmE5ZmE3OGItNTdlNC00YmMxLWEzNjctYzAwOGQ5ODg0OTI0XkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX182_CR0,0,182,268_AL_.jpg"}
                      {
                       :id 2
                       :nome "Fredy mercury"
                       :video "https://s2.glbimg.com/lMC9uMH29_s0Tleemgr_nl4vUQ0=/e.glbimg.com/og/ed/f/original/2018/11/14/bohemian-rhapsody3.jpg"
                       :capa "https://m.media-amazon.com/images/M/MV5BNDg2NjIxMDUyNF5BMl5BanBnXkFtZTgwMzEzNTE1NTM@._V1_UX182_CR0,0,182,268_AL_.jpg"}
                      {
                       :id 3
                       :nome "Rei leão"
                       :video "https://media.hugogloss.uol.com.br/uploads/2019/02/o-rei-leao-234892394-900x350.jpg"
                       :capa "https://m.media-amazon.com/images/M/MV5BMjIwMjE1Nzc4NV5BMl5BanBnXkFtZTgwNDg4OTA1NzM@._V1_UX182_CR0,0,182,268_AL_.jpg"}
                      ])


(defonce estado-do-filme-selecionado (reagent/atom nil))


(defn renderizar-filme-selecionado [filme]
  [:div {:style {:background-color :purple
                 :padding 10}}
   [:h3 {:style {:color "#ffffff"
                 :text-align :center}} (:nome filme)]
   [:img {
          :style {:width "100%"}
          :src (:video filme)}]])

(defn selecionar-filme [filme]
  (reset! estado-do-filme-selecionado filme))


(defn chamada-do-filme [filme]
 [:div { :on-click #(selecionar-filme filme)
        :style {:margin 10}}
  [:img {:src (get filme :capa) 
         :style {:width 150}
         }]
  [:p {:style {:color "white"
               :text-align "center"}}(get filme :nome)]] )

(defn netflix []

  [:div 
   [:div {:style {:display "flex"
                  :justify-content "left"}} (map chamada-do-filme catalogo-filmes)
    ]
   [renderizar-filme-selecionado @estado-do-filme-selecionado]])







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
