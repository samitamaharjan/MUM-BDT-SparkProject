import java.util.Date

class Message(dd: String) {
  var data: String = dd
  var time: Date = new Date()

  def getData(): String = {
    return data
  }

  def getTime(): Date = {
    return time
  }
}

