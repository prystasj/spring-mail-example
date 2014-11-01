package prystasj.spring.mail

import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration
class EmailSenderIntegrationSpec extends Specification implements InitializingBean {

    @Autowired
    EmailSender emailSender

    @Autowired
    Resource emailTextResource

    String emailText

    String fromAddress = 'prystasj@prystasj.org'

    String subject = 'Test Email: EmailSenderIntegrationSpec'

    def 'sends an email given a subject and HTML text'() {
        when:
        emailSender.send subject, emailText
        then:
        notThrown MessageException
    }

    @Override
    void afterPropertiesSet() throws Exception {
        emailText = emailTextResource.file.text
        assert emailText.size()
    }
}
