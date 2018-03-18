
import plotly._, element._, layout._

object PlotData extends {
  val x = 0.0 to 10.0 by 0.1
  val y1 = x.map(d => 2.0 * d + util.Random.nextGaussian())
  val y2 = x.map(math.exp)

  val plot = Seq(
    Scatter(
      x, y1, name = "Approx twice"
    ),
    Scatter(
      x, y2, name = "Exp"
    )
  )

  val labels = Seq("Banana", "Banano", "Grapefruit")
  val valuesA = labels.map(_ => util.Random.nextGaussian())
  val valuesB = labels.map(_ => 0.5 + util.Random.nextGaussian())

  /*Seq(
    Bar(labels, valuesA, name = "A"),
    Bar(labels, valuesB, name = "B")
  ).plot(
    title = "Level"
  )*/
}
