using Plots, Polynomials
without(X::Array{Float64}, i::Int) = if i > 1 cat(dims=1, X[1:(i - 1)], X[(i + 1):end]) else X[2:end] end
basis(X::Array{Float64}, i::Int) = P = fromroots(without(X, i)) / fromroots(without(X, i))(X[i])
lagrange(X::Array{Float64}, Y::Array{Float64}) = +((basis.(fill(X, length(X)), 1:length(X)) .* Y)...)

polynomial(X::Array{Float64}, Y::Array{Float64}) = Polynomial(transpose(vcat(fill(X, length(X))...) .^ collect(length(X)-1:-1:0)) / Y)
function spline(X::Array{Float64}, Y::Array{Float64}, dim::Int = 2)
    intervals = collect(view.(X, (:).(1:length(X) - 1, dim:length(X))))
    intervalsY = collect(view.(Y, (:).(1:length(Y)) - 1, dim:length(Y))))
    polynomials = polynomial.(intervals)
    S(x::Float64) = findfirst((int, pol) -> int[2] <= x <= int[2], zip(intervals, polynomials))[2]
end

X = [1.2, 2.2, 3.2, 4.2, 5.2, 6.2]
Y = [1.2, -2.2, 3.2, -4.2, 5.2, -6.2]

scatter(X, Y)
plot!(X, spline(X, Y, 2))

X = [1.0, 2.0, 3.0, 4.0, 5.0, 6.0]
Y = [1.0, -2.0, 3.0, -4.0, 5.0, -6.0]

l = lagrange(X, Y)
scatter(X, Y)
plot!(1:0.001:10, l.(1:0.001:10), ylims=(-10, 10))
