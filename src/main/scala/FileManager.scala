import java.io.File
import java.util

import scala.io.Source

class FileManager {
  def getListOfFiles(dir: String):List[File] = {
    val d = new File(dir)
    if (d.exists && d.isDirectory) {
      d.listFiles.filter(_.isFile).toList
    } else {
      List[File]()
    }
  }

  def getRecords(file: File): util.ArrayList[String] = {
    var lineNumber = 1;
    var movieId = "";
    var message = "";
    var records  = new util.ArrayList[String]()

    for(line <- Source.fromFile(file).getLines()) {
      if (lineNumber == 1) {
        movieId = line.split(":")(0)
      }else {
        message = s"$movieId,$line"
        records.add(message)
      }
      lineNumber += 1
    }
    return records
  }

  def getMessages(): util.ArrayList[String] = {
    var messages = new util.ArrayList[String]()
    val files = getListOfFiles("/home/cloudera/Downloads/download/Test")

    val messagesArray = files.map(file => getRecords(file))
    for (msgs <- messagesArray) {
      messages.addAll(msgs);
    }
    return messages
  }
}
