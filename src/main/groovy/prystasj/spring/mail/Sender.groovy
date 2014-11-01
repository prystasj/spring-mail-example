package prystasj.spring.mail

/**
 * Sends messages.
 */
interface Sender {

    /**
     * Sends a message to a recipient.
     * <p/>
     * A default recipient and sender will be used.
     *
     * @param subject the subject to include in the email
     * @param text the text to include as the email body
     *
     * @throws MessageException if the message send attempt fails
     */
    void send(String subject, String text)

    /**
     * Sends a message to a recipient from a sender.
     * <p/>
     * A default recipient will be used.
     *
     * @param sender the email address to associate with the sender
     * @param subject the subject to include in the email
     * @param text the text to include as the email body
     *
     * @throws MessageException if the message send attempt fails
     */
    void send(String sender, String subject, String text)

}

