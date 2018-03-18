import java.util.Date

class Message(dd: String) {
  var data: String = dd
  var arrData: Array[String] = data.split(",")
  var movieId: String = arrData(0)
  var customerId: String = arrData(1)
  var rating: String = arrData(2)
  var ratingDate: String = arrData(3)

  def getData(): String = {
    return data
  }

  def getMovieId(): String = {
    return movieId
  }

  def getCustomerId(): String = {
    return customerId
  }

  def getRating(): String = {
    return rating
  }

  def getRatingDate(): String = {
    return ratingDate
  }
}

