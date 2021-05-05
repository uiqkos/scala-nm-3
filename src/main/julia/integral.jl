rectangular(Y::Array{Float64}, h::Float64 = 0.001) = h * sum(Y[1:length(Y) - 1])
trapezoid(Y::Array{Float64}, h::Float64 = 0.001) = (h / 2) * (Y[1] + 2 * sum(Y[2:length(Y) - 1]) + Y[end])
simpson(Y::Array{Float64}, h::Float64 = 0.001) = (h / 3) * (Y[1] + Y[end] + 2 * sum(Y[2:2:length(Y) - 2]) + 4 * sum(Y[1:2:length(Y)-1]))

function integral(f::Function, a::Float64, b::Float64, I::Function = trapezoid, ϵ::Float64 = 0.001)
    """
        Вычисляет определенный интеграл f на интервале (a, b) с заданной точностью ϵ
    """
    n = 10
    while true
        h = (b - a) / n
        Y1 = f.(collect(a:h:b)) # Неоптимально
        Y2 = f.(collect(a:(h / 2):b)) 

        I1, I2 = I(Y1, h), I(Y2, h / 2)

        if abs(I2 - I1) < ϵ
            println("Оптимальный шаг $h (n = $n) для достижения точности $ϵ")
            return I2
        end
        n = 2n
    end
end

a, b = 1., 3.

f(x) = x ^ -0.5 * log(x)

true_int = 0.877501 # from wolfram alpha
rect_int = integral(f, a, b, rectangular)

println("Точность: $(true_int - rect_int)")

X1 = collect(a:0.001:b)
X2 = collect(a:0.0005:b)
Y1 = f.(X1)
Y2 = f.(X2)

rectangular_int1, rectangular_int2 = rectangular(Y1, 0.001), rectangular(Y2, 0.0005)
trapezoid_int1, trapezoid_int2 = trapezoid(Y1, 0.001), trapezoid(Y2, 0.0005)
simpson_int1, simpson_int2 = simpson(Y1, 0.001), simpson(Y2, 0.0005)

println("Точность формулы прямоугольников с шагом 0.001 = $(rectangular_int1 - true_int)")
println("Точность формулы прямоугольников с шагом 0.0005 = $(rectangular_int2 - true_int)")

println("Точность формулы трапеции с шагом 0.001 = $(trapezoid_int1 - true_int)")
println("Точность формулы трапеции с шагом 0.0005 = $(trapezoid_int2 - true_int)")

println("Точность формулы симпсона с шагом 0.001 = $(simpson_int1 - true_int)")
println("Точность формулы симпсона с шагом 0.0005 = $(simpson_int2 - true_int)")

X = [0.145, 0.147, 0.149, 0.151, 0.153]
Y = [4.97674, 4.99043, 5.00391, 5.01730, 5.03207]

println("Определенный интеграл от $(X[1]) до $(X[end]) по формуле прямоугольников = ", rectangular(Y, X[2] - X[1]))
println("Определенный интеграл от $(X[1]) до $(X[end]) по формуле трапеций = ", trapezoid(Y, X[2] - X[1]))
println("Определенный интеграл от $(X[1]) до $(X[end]) по формуле симпсона = ", simpson(Y, X[2] - X[1]))

# ([Y] .|> [rectangular, trapezoid, simpson]) .|> int -> println(int - true_int)
