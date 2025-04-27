package rw.gov.rra.v1.standalone;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import rw.gov.rra.v1.exceptions.AppException;
import rw.gov.rra.v1.models.Vehicle;

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

    public void sendTransferNotification(String oldOwnerEmail, String newOwnerEmail, Vehicle vehicle) {
        String subject = "Vehicle Ownership Transfer Notification";

        String oldOwnerMessage = String.format(
                "Dear Customer,\n\n" +
                        "This is to inform you that your vehicle (Chassis Number: %s, Model: %s) has been successfully transferred to a new owner.\n\n" +
                        "Thank you for using our services.\n\n" +
                        "Rwanda Revenue Authority.",
                vehicle.getChassisNumber(), vehicle.getModelName()
        );

        String newOwnerMessage = String.format(
                "Dear Customer,\n\n" +
                        "Congratulations! You are now the new owner of the vehicle (Chassis Number: %s, Model: %s).\n" +
                        "Please ensure to complete any remaining formalities if required.\n\n" +
                        "Thank you for choosing our services.\n\n" +
                        "Rwanda Revenue Authority.",
                vehicle.getChassisNumber(), vehicle.getModelName()
        );

        // Use your EmailService to send emails
        sendEmail(oldOwnerEmail, subject, oldOwnerMessage);
        sendEmail(newOwnerEmail, subject, newOwnerMessage);
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
