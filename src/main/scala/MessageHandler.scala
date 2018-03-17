class MessageHandler {

  def handleMessage(msg: Message) {
    println(msg.getTime() + " " + msg.getData())
  }
}
