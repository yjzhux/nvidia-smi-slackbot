(ns nvidia-smi-slackbot.slack
  (:require [clojure.java.io :as io]
            [org.httpkit.client :as http]
            [cheshire.core]
            [clojure.java.shell :as shell]
            [clojure.string :as str]))

(defn hostname []
  (try
    (-> (shell/sh "hostname") (:out) (str/trim))
    (catch Exception _e
      (try
        (str/trim (slurp "/etc/hostname"))
        (catch Exception _e)))))

(def config (read-string (slurp (io/resource "config.clj"))))


(defn format-process
  [{:keys [user pid memory]}]
  {:title (format "Used by \"%s\"" user)
   :value (format "pid %s, using %s gpu memory." pid memory)})


(defn busy-msg
  [processes]
  {:attachments [{:fallback "The server's GPU is curently BUSY"
                  :color    "danger"
                  :fields   (apply vector {:title (format "Busy %s" (hostname))
                                           :value ""}
                                   (map format-process processes))}]})


(def free-msg
  {:attachments [{:fallback "The server's GPU is curently FREE"
                  :color    "good"
                  :fields   [{:title (format "Free %s" (hostname))
                              :value "The GPU is currently free."}]}]})


(defn- send-slack-msg
  [slack-msg]
  (http/post (:post-url config)
             {:body (cheshire.core/generate-string slack-msg)}))


(defn send-nvidia-free-msg
  []
  (send-slack-msg free-msg))


(defn send-nvidia-busy-msg
  [processes]
  (print processes)
  (send-slack-msg (busy-msg processes)))
