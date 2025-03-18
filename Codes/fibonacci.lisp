(defun fibonacci (n)
  (if (<= n 1)
      n
      (+ (fibonacci (- n 1)) (fibonacci (- n 2)))))

(setq resultado (fibonacci 10))
(format t "El décimo número de Fibonacci es: ~A~%" resultado)

