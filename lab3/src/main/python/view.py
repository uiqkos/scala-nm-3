import plotly.graph_objects as go
import plotly.express as px
import numpy as np


def latex_by_coefs(coefs):
    return f'$y = ' + f'{coefs[0]}x^{len(coefs) - 1}' + ''.join([
        f'{coefs[i] if coefs[i] < 0 else "+" + str(coefs[i])}'
        f'{f"x^{len(coefs) - i - 1}" if i < len(coefs) - 2 else ("x" if i < len(coefs) - 1 else "")}'
        for i in range(1, len(coefs))
        if coefs[i] != 0
    ]) + f'$'
# {coefs[-1] if coefs[-1] < 0 else "+" + str(coefs[-1])}


def plot_splines(n: int):
    data = np.genfromtxt(
        f'C:\\Users\\Uiqkos\\IdeaProjects\\scala-nm-3\\lab3\\data\\spline{n}_data.txt', delimiter=',')
    X, Y = np.genfromtxt(
        'C:\\Users\\Uiqkos\\IdeaProjects\\scala-nm-3\\lab3\\data\\axes.txt', delimiter=',')
    labels = np.genfromtxt(
        f'C:\\Users\\Uiqkos\\IdeaProjects\\scala-nm-3\\lab3\\data\\spline{n}_labels.txt', delimiter=',')

    fig = px.scatter(
        x=X,
        y=Y,
        size=[1] * (len(X))
    )

    # fig = go.Figure()
    #
    # fig.add_trace(go.Scatter(
    #     visible=False,
    #     line=dict(color="#00CED1", width=6),
    #     name=f"",
    #     x=X,
    #     y=Y
    # ))

    for i, y in enumerate(data[1:]):
        fig.add_trace(
            go.Scatter(
                visible=False,
                line=dict(color="#00CED1", width=6),
                name=f"{latex_by_coefs(labels[i])}",
                x=data[0],
                y=y
            )
        )

    fig.data[1].visible = True

    steps = []
    for i in range(1, len(fig.data)):
        step = dict(
            method="update",
            args=[{"visible": [True] + [False] * (len(fig.data))},
                  {"title": f"{latex_by_coefs(labels[i - 1])}"}],
        )
        step["args"][0]["visible"][i] = True
        steps.append(step)

    sliders = [dict(
        active=0,
        currentvalue={"prefix": "Interval: "},
        pad={"t": 50},
        steps=steps
    )]

    fig.update_layout(
        sliders=sliders
    ).update_yaxes(
        range=[-7.0, 7.0]
    )

    fig.to_html("splines.html")
    fig.show()


def plot_lagrange():
    pass


def plot_newton():
    pass


plot_splines(9)
