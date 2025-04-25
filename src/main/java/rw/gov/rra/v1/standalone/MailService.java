package rw.gov.rra.v1.standalone;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import rw.gov.rra.v1.exceptions.AppException;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    @Value("${app.frontend.reset-password}")
    private String resetPasswordUrl;

    @Value("${app.frontend.support-email}")
    private String supportEmail;

    private String getCommonSignature() {
        return "<br><br>If you need help, contact us at: <a href='mailto:" + supportEmail + "'>" + supportEmail + "</a><br>© " + LocalDate.now().getYear();
    }

    public void sendResetPasswordMail(String to, String fullName, String resetCode) {
        String subject = "Password Reset Request";
        String html = "<p>Dear " + fullName + ",</p>"
                + "<p>You requested to reset your password. Use the following code:</p>"
                + "<h2>" + resetCode + "</h2>"
                + "<p>Or click <a href='" + resetPasswordUrl + "'>here</a> to reset your password.</p>"
                + getCommonSignature();
        sendEmail(to, subject, html);
    }

    public void sendActivateAccountEmail(String to, String fullName, String verificationCode) {
        String subject = "Account Activation Request";
        String html = "<p>Hello " + fullName + ",</p>"
                + "<p>Please use the following code to activate your account:</p>"
                + "<h2>" + verificationCode + "</h2>"
                + getCommonSignature();
        sendEmail(to, subject, html);
    }

    public void sendAccountVerifiedSuccessfullyEmail(String to, String fullName) {
        String subject = "Account Verification Successful";
        String html = "<p>Hi " + fullName + ",</p>"
                + "<p>Your account has been verified successfully. Welcome aboard!</p>"
                + getCommonSignature();
        sendEmail(to, subject, html);
    }

    public void sendPasswordResetSuccessfully(String to, String fullName) {
        String subject = "Password Reset Successful";
        String html = "<p>Hello " + fullName + ",</p>"
                + "<p>Your password has been reset successfully.</p>"
                + getCommonSignature();
        sendEmail(to, subject, html);
    }

    public void sendWithdrawalSuccessfulEmail(String to, String fullName, String amount, String balance, String accountCode, UUID userId) {
        String subject = "Withdrawal Successful 🎉";
        String html = "<p>Hello " + fullName + ",</p>"
                + "<p>You have withdrawn <strong>" + amount + "</strong> from account <strong>" + accountCode + "</strong>.</p>"
                + "<p>Remaining balance: <strong>" + balance + "</strong></p>"
                + "<p>User ID: " + userId + "</p>"
                + getCommonSignature();
        sendEmail(to, subject, html);
    }

    public void sendSavingsStoredSuccessfullyEmail(String to, String fullName, String amount, String balance, String accountCode, UUID userId) {
        String subject = "Savings Stored Successfully 🥳";
        String html = "<p>Dear " + fullName + ",</p>"
                + "<p>You have successfully saved <strong>" + amount + "</strong> to account <strong>" + accountCode + "</strong>.</p>"
                + "<p>Current balance: <strong>" + balance + "</strong></p>"
                + "<p>User ID: " + userId + "</p>"
                + getCommonSignature();
        sendEmail(to, subject, html);
    }

    public void sendTransferSuccessfulEmail(String to, String fullName, String amount, String balance, String receiverNames, String accountCode, UUID userId) {
        String subject = "Money Transfer Successful 🥳";
        String html = "<p>Dear " + fullName + ",</p>"
                + "<p>You transferred <strong>" + amount + "</strong> to <strong>" + receiverNames + "</strong>.</p>"
                + "<p>Account: " + accountCode + " | Remaining Balance: " + balance + "</p>"
                + "<p>User ID: " + userId + "</p>"
                + getCommonSignature();
        sendEmail(to, subject, html);
    }

    public void sendReceivedAmountEmail(String to, String fullName, String senderNames, String received, String balance) {
        String subject = "Just Received " + received + " FRW 🥳";
        String html = "<p>Hi " + fullName + ",</p>"
                + "<p>You just received <strong>" + received + "</strong> from <strong>" + senderNames + "</strong>.</p>"
                + "<p>New balance: <strong>" + balance + "</strong></p>"
                + getCommonSignature();
        sendEmail(to, subject, html);
    }

    private void sendEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = this.mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            this.mailSender.send(message);
        } catch (MessagingException e) {
            throw new AppException("Error sending email", e);
        }
    }
}
