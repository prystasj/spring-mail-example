package prystasj.spring.mail

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.InitializingBean
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.mail.javamail.MimeMessageHelper

import javax.mail.internet.MimeMessage

import static prystasj.spring.mail.Logger.logFailure
import static prystasj.spring.mail.Logger.logSent

/** Sends emails. */
@Slf4j
class EmailSender implements Sender, InitializingBean {

    /** The mail host or server to use. */
    String mailHost

    /** The port of the mail host to connect to. */
    int mailPort

    /** The email address to identify the sender with if one is not provided. */
    String defaultFromAddress

    /** The email address to send emails to. */
    String toAddress

    /** Built during initialization using the provided mail host and port. */
    protected JavaMailSender mailSender

    /**
     * Sends an HTML text email from a configured sender to a configured recipient.
     *
     * @param subject the subject to include in the email
     * @param text the text to include as the email body
     *
     * @throws MessageException if the email attempt fails
     */
    @Override
    void send(String subject, String text) {
        send defaultFromAddress, subject, text
    }

    /**
     * Sends an HTML text email from to a configured recipient.
     *
     * @param subject the subject to include in the email
     * @param text the text to include as the email body
     *
     * @throws MessageException if the email attempt fails
     */
    @Override
    void send(String sender, String subject, String text) {
        try {
            mailSender.send htmlMessageWith(sender, subject, text)
        }
        catch (e) {
            logFailure e
            throw new MessageException(e)
        }

        logSent toAddress, subject, text
    }

    private MimeMessage htmlMessageWith(sender, subject, text) {
        def mimeMessage = new MimeMessage(null)
        def messageHelper = new MimeMessageHelper(mimeMessage)

        messageHelper.with {
            addTo toAddress
            setFrom sender
            setSubject subject
            setText text, true
        }

        messageHelper.mimeMessage
    }

    /**
     * Builds a mail sender with the provided host and port to spare the user
     * from building and providing one, making configuration simpler.
     *
     * @throws Exception if finalizing the initialization fails
     */
    @Override
    void afterPropertiesSet() throws Exception {
        mailSender = new JavaMailSenderImpl()
        mailSender.setHost mailHost
        mailSender.setPort mailPort
    }

}
