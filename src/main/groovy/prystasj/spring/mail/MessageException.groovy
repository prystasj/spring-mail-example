package prystasj.spring.mail

class MessageException extends RuntimeException {

    MessageException(Exception e) {
        super("Could not send email due to: ${e.message}")
    }
}
