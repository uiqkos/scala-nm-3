rectangular(Y::Array{Float64}, h::Float64 = 0.001) = h * sum(Y[1:length(Y) - 1])
trapezoid(Y::Array{Float64}, h::Float64 = 0.001) = (h / 2) * (Y[1] + 2 * sum(Y[2:length(Y) - 1]) + Y[length(Y)])
simpson(Y::Array{Float64}, h::Float64 = 0.001) = (h / 3) * (Y[1] + Y[end] + 2 * sum(Y[2:2:length(Y) - 2]) + 4 * sum(Y[1:2:length(Y)-1]))

function integral(f::Function, a::Float64, b::Float64; I::Function = trapezoid, ϵ::Float64 = 0.001, nₒ::Int64 = 10)
    """
        Вычисляет определенный интеграл f на интервале (a, b) с заданной точностью ϵ
    """
    n = nₒ
    while true
        h = (b - a) / 2n
        Y1 = f.(collect(a:2h:b)) # Неоптимально
        Y2 = f.(collect(a:h:b))

        I1, I2 = I(Y1, 2h), I(Y2, h)

        if abs(I2 - I1) < ϵ
            println("Оптимальный шаг $(h) (n = $(2n)) для достижения точности $ϵ")
            return I2, h
        end
        n = 2n
    end
end



a, b = 1., 3.

f(x) = x ^ -0.5 * log(x)

true_int = 0.877501 # from wolfram alpha


for (I, method_name) in ((rectangular, "прямоугольников"), (trapezoid, "трапеции"), (simpson, "Симпсона"))
    int, h = integral(f, a, b, I = I)
    local X2 = collect(a:2h:b)

    println("Точность формулы $method_name с шагом h ($(h)) = $(abs(true_int - int))")
    println("Точность формулы $method_name с шагом 2h ($(2h)) = $(abs(true_int - I(f.(X2), 2h)))\n")
end

X = [0.145, 0.147, 0.149, 0.151, 0.153]
Y = [4.97674, 4.99043, 5.00391, 5.01730, 5.03207]

println("Определенный интеграл от $(X[1]) до $(X[end]) по формуле прямоугольников = ", rectangular(Y, X[2] - X[1]))
println("Определенный интеграл от $(X[1]) до $(X[end]) по формуле трапеций = ", trapezoid(Y, X[2] - X[1]))
println("Определенный интеграл от $(X[1]) до $(X[end]) по формуле симпсона = ", simpson(Y, X[2] - X[1]))

# ([Y] .|> [rectangular, trapezoid, simpson]) .|> int -> println(int - true_int)
