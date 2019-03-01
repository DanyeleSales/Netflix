(ns hello.core
  (:require [reagent.core :as reagent :refer [atom]]))


(def catalogo-filmes [{:id 1
                       :nome "Lady gaga"
                       :video "https://i.ytimg.com/vi/WGsA2aXYBGo/maxresdefault.jpg"
                       :favoritado? false
                       :capa "https://m.media-amazon.com/images/M/MV5BNmE5ZmE3OGItNTdlNC00YmMxLWEzNjctYzAwOGQ5ODg0OTI0XkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX182_CR0,0,182,268_AL_.jpg"}
                      {:id 2
                       :nome "Fredy mercury"
                       :video "https://s2.glbimg.com/lMC9uMH29_s0Tleemgr_nl4vUQ0=/e.glbimg.com/og/ed/f/original/2018/11/14/bohemian-rhapsody3.jpg"
                       :favoritado? false
                       :capa "https://m.media-amazon.com/images/M/MV5BNDg2NjIxMDUyNF5BMl5BanBnXkFtZTgwMzEzNTE1NTM@._V1_UX182_CR0,0,182,268_AL_.jpg"}
                      {:id 3
                       :nome "Rei leão"
                       :video "https://media.hugogloss.uol.com.br/uploads/2019/02/o-rei-leao-234892394-900x350.jpg"
                       :favoritado? true
                       :capa "https://m.media-amazon.com/images/M/MV5BMjIwMjE1Nzc4NV5BMl5BanBnXkFtZTgwNDg4OTA1NzM@._V1_UX182_CR0,0,182,268_AL_.jpg"}])




(defonce estado-do-filme-selecionado (reagent/atom nil))
(defonce estado-dos-filmes (reagent/atom catalogo-filmes))


(defn filtrar-filme [event]
  (let [valor-do-input (-> event .-target .-value)
        filmes-filtrados (filter (fn [item-da-lista]
                                   (re-find (re-pattern (str "(?i)" valor-do-input))
                                            (:nome item-da-lista)))
                                 catalogo-filmes)]
    (reset! estado-do-filme-selecionado nil)
    (reset! estado-dos-filmes (if (empty? valor-do-input)
                                catalogo-filmes
                                filmes-filtrados))))

(defn busca []

  [:input {:placeholder "buscar filme" :on-change filtrar-filme}])

(defn filme-nao-encontrado []
  (when (zero? (count @estado-dos-filmes))
    [:div [:p "Filme não encontrado :("]]))

(defn renderizar-filme-selecionado [filme]
  (when filme  [:div {:style {:background-color :purple
                              :padding 10}}
                [:h3 {:style {:color "#ffffff"
                              :text-align :center}} (:nome filme)]
                [:img {:style {:width "100%"}
                       :src (:video filme)}]]))

(defn selecionar-filme [filme]
  (reset! estado-do-filme-selecionado filme))

(defn chamada-do-filme [filme]
  [:div {:on-click #(selecionar-filme filme)
         :key (:id filme)
         :style {:margin 10}}
   [:img {:src (get filme :capa)
          :style {:width 150}}]
   [:p {:style {:color "white"
                :text-align "center"}} (get filme :nome)
    [:img {:style {:width 20}
           :on-click (fn []
                       (println (update filme :favoritado? not)))
           :src
           (if (:favoritado? filme)
             "https://image.flaticon.com/icons/svg/148/148839.svg"
             "https://image.flaticon.com/icons/svg/660/660463.svg")}]]
   [:p (if (:favoritado? filme) "favoritou" "não favoritou")]])

(def contador (reagent/atom 0))

(defn netflix []

  [:div
   [:p  {:on-click (fn []
                      ; (swap! contador inc)
                     (swap! contador (fn [estado-atual]
                                       (println estado-atual)
                                       "funcionou")))
         :style
         {:color "white"}} @contador]
   [busca]
   [filme-nao-encontrado]

   [:div {:style {:display "flex"
                  :justify-content "left"}} (map chamada-do-filme @estado-dos-filmes)]
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




