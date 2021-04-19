import plotly.graph_objects as go
import plotly.express as px
import numpy as np
import pandas as pd
from fn.monad import Full


def latex_by_coefs(coefs):
    return f'$y = ' + f'{coefs[0]}x^{len(coefs) - 1}' + ''.join([
        f'{coefs[i] if coefs[i] < 0 else "+" + str(coefs[i])}'
        f'{f"x^{len(coefs) - i - 1}" if i < len(coefs) - 2 else ("x" if i < len(coefs) - 1 else "")}'
        for i in range(1, len(coefs))
        if coefs[i] != 0
    ]) + f'$'


def plot_spline_f(n: int):
    df = pd.read_csv(
        f'C:\\Users\\Uiqkos\\IdeaProjects\\scala-nm-3\\lab3\\data\\spline{n}_data.csv', header=0)
    axes = pd.read_csv(
        'C:\\Users\\Uiqkos\\IdeaProjects\\scala-nm-3\\lab3\\data\\axes.csv', header=0)

    X_full = df['X']
    df.drop('X', axis=1, inplace=True)

    X, Y = axes['X'], axes['Y']

    fig = px.scatter(
        x=X,
        y=Y,
        size=[1] * (df.shape[1] + n - 1)
    )

    for i in range(df.shape[1]):
        fig.add_trace(
            go.Scatter(
                visible=False,
                line=dict(color="#00CED1", width=6),
                name=f"{latex_by_coefs(list(map(float, df.columns[i].split())))}",
                x=X_full,
                y=df.iloc[:, i]
            )
        )

    fig.data[1].visible = True

    steps = []
    for i in range(1, len(fig.data)):
        step = dict(
            method="update",
            args=[{"visible": [True] + [False] * (len(fig.data))},
                  {"title": f"{latex_by_coefs(list(map(float, df.columns[i - 1].split())))}"}],
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


if __name__ == '__main__':

    # plot_spline_f(2)
    # plot_spline_f(3)
    # plot_spline_f(4)
    # plot_spline_f(5)

    df = pd.read_csv("C:\\Users\\Uiqkos\\IdeaProjects\\scala-nm-3\\lab3\\data\\data.csv", header=0)
    axes = pd.read_csv("C:\\Users\\Uiqkos\\IdeaProjects\\scala-nm-3\\lab3\\data\\axes.csv", header=0)

    X = df['X']

    fig = px.scatter(
        x=axes['X'],
        y=axes['Y']
    )

    for i in range(1, df.shape[1]):
        fig.add_scatter(
            x=X, y=df.iloc[:, i],
            name=df.columns[i]
        )

    fig.show()

