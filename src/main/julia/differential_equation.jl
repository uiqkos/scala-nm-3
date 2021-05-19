im
using Plots;

function euler(
    f::Function, 
    y₀::Float64, a::Float64, b::Float64, h::Float64
)
    X = collect(a:h:b)
    Y = fill(y₀, length(X))

    for k in 1:(length(X) - 1)
        Y[k + 1] = Y[k] + h * f(X[k], Y[k])
    end
    Y
end


function runge_kutta(
    f::Function, 
    y₀::Float64, a::Float64, b::Float64, h::Float64
)
    X = collect(a:h:b)
    Y = fill(y₀, length(X))

    for k in 1:(length(X) - 1)
        F₁ = f(X[k], Y[k])
        F₂ = f(X[k] + (h / 2), Y[k] + (h / 2) * F₁)
        F₃ = f(X[k] + (h / 2), Y[k] + (h / 2) * F₂)
        F₄ = f(X[k + 1] + (h / 2), Y[k] + h * F₃)

        Y[k + 1] = Y[k] + (h / 6) * (F₁ + 2 * F₂ + 2 * F₃ + F₄)
    end
    Y
end


# f(x, y) = (y / x) * (2y * log(x) - 1)
f(x, y) = (y^3 * e^-x) / x - (y / x)
wolfram(x) = 1 / (2 * log(x) + 2 - (4 / 3) * x)

y₀ = 1.5
a = 1.0
b = 3.0
h = 0.1

runge_kutta_Y = runge_kutta(f, y₀, a, b, h)
euler_Y = euler(f, y₀, a, b, h)

X = collect(a:h:b)
Y = wolfram.(X)
println(Y)

plot(X, Y, label="Точнное решение")
plot!(X, runge_kutta_Y, label="Метод Рунге-Кутта")
plot!(X, euler_Y, label="Метод Эйлера")
