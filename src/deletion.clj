(ns deletion
  (:require [clojure.java.io :as io]
            [nextjournal.beholder :as beholder]
            [org.corfield.build :as bb])
  (:gen-class))

(defn prn-files
  [dir]
  (println "files:")
  (doseq [f (file-seq (io/file dir))]
    (println "  " (.getPath f))))

(defn -main
  [& args]

  ;; Populate testdir/ with before.txt
  (io/make-parents "testdir/*")
  (spit "testdir/before.txt" "created before instantiating a watcher")
  (prn-files "testdir")

  ;; Create watcher, wait 1 second for good measure
  (println "(starting watcher process..)")
  (beholder/watch (fn [{:keys [type path]}]
                    (println "watcher:" (name type) (str path)))
                  "testdir")
  (Thread/sleep 1000)

  ;; Create after.txt
  (spit "testdir/after.txt" "created after instantiating a watcher")
  (prn-files "testdir")

  ;; Wait another second for good measure
  (Thread/sleep 1000)

  ;; Delete both files
  (io/delete-file "testdir/before.txt")
  (io/delete-file "testdir/after.txt")
  (prn-files "testdir")

  ;; Wait another second for good measure
  (Thread/sleep 1000))

(comment
  (-main)

  (-> {:uber-file "beholder-issue.jar"
       :lib       'beholder-issue
       :main      'deletion}
      (bb/clean)
      (bb/uber))
  #_.)
