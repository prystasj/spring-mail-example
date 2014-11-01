package prystasj.spring.mail

import groovy.util.logging.Slf4j

@Slf4j
class Logger {

    static void logSent(toAddress, subject, text) {
        log.info "Successfully sent message to '{}' with subject '{}' an with text: {}", toAddress, subject, text
    }

    static void logFailure(Exception e) {
        log.warn 'Could not send email: {}', e.message
    }
}
