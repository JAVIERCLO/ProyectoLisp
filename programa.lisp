(+ 8 12)
(- 20 7)
(* 4 6)
(/ 50 10)

(setq x 3)
(setq y 7)

(defun producto (a b) (* a b))
(producto 4 9)

(defun factorial (n)
    (cond
        ((equal n 0) 1)
        (true (* n (factorial (- n 1))))
    )
)
(factorial 4)

(defun sumatoria (n)
    (cond
        ((equal n 0) 0)
        (true (+ n (sumatoria (- n 1))))
    )
)
(sumatoria 5)

(quote hola)
(quote (uno dos tres))

(equal x y)
(< x y)
(> y x)
(atom palabra)
(list (a b c))

(cond ((> x 5) 100) ((< x 5) 50) ((equal x 3) 30))
