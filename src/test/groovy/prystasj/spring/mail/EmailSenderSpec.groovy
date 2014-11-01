package prystasj.spring.mail

import org.springframework.mail.MailSendException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import spock.lang.Specification
import spock.lang.Subject

import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class EmailSenderSpec extends Specification {

    String mailHost = 'mailhost.org'
    int mailPort = 25

    String fromAddress = 'specs@prystasj.org'
    String toAddress = 'friend@prystasj.org'

    JavaMailSender javaMailSender = Mock()

    @Subject
    EmailSender emailSender = new EmailSender(
        mailHost: mailHost,
        mailPort: mailPort,
        fromAddress: fromAddress,
        toAddress: toAddress
    )

    def subject = 'Subject'
    def text = '<html><body>text</body></html>'

    def 'sends an email given a subject and text'() {
        given:
        emailSender.mailSender = javaMailSender

        when:
        emailSender.send subject, text

        then:
        1 * javaMailSender.send(_ as MimeMessage) >> { args ->
            isExpectedMessage args[0], fromAddress
        }

        and:
        notThrown Exception
    }

    def 'complains if an email cannot be sent'() {
        given:
        emailSender.mailSender = javaMailSender

        when:
        emailSender.send subject, text

        then:
        1 * javaMailSender.send(_ as MimeMessage) >> { args ->
            isExpectedMessage args[0], fromAddress
            throw new MailSendException('boom')
        }

        and:
        thrown MessageException
    }

    def 'is initialized with a mail sender'() {
        given:
        assert emailSender.mailSender == null

        when:
        emailSender.afterPropertiesSet()

        and:
        def javaMailSender = (JavaMailSenderImpl)emailSender.mailSender

        then:
        javaMailSender.host == mailHost
        javaMailSender.port == mailPort
    }

    void isExpectedMessage(MimeMessage message, String fromAddress) {

        assert message.subject == subject
        assert message.content == text

        assert message.from.size() == 1
        assert message.allRecipients.size() == 1

        InternetAddress address = message.from[0]
        assert address.address == fromAddress

        address = message.allRecipients[0]
        assert address.address == toAddress
    }

}
