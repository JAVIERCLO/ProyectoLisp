; Definir una función recursiva para sumar dos números
(defun suma (a b)
  (if (= b 0)
      a
      (suma (+ a 1) (- b 1))))

; Definir una función recursiva para restar dos números
(defun resta (a b)
  (if (= b 0)
      a
      (resta (- a 1) (- b 1))))

; Definir una función recursiva para multiplicar dos números
(defun multiplicar (a b)
  (if (= b 0)
      0
      (+ a (multiplicar a (- b 1)))))

; Definir una función recursiva para dividir dos números (solo enteros)
(defun dividir (a b)
  (if (< a b)
      0
      (+ 1 (dividir (- a b) b))))

; Factorial de un número (n!)
(defun factorial (n)
  (if (<= n 1)
      1
      (* n (factorial (- n 1)))))

; Serie de Fibonacci
(defun fibonacci (n)
  (if (<= n 1)
      n
      (+ (fibonacci (- n 1)) (fibonacci (- n 2)))))
