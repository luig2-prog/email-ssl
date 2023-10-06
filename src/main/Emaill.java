import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EnvioCorreoJava {
    public static void main(String[] args) {
        final String remitente = ""; // Cambia esto por tu dirección de correo electrónico
        final String password = "";   // Cambia esto por la contraseña de tu correo electrónico

        String destinatario = ""; // Cambia esto por la dirección del destinatario
        String asunto = "Prueba de correo desde JavaMail";
        String mensaje = "Hola, esto es una prueba de envío de correo desde JavaMail con SSL.";

        // Configuración de propiedades para el servidor SMTP de Gmail (puedes cambiarlo según tu proveedor de correo)
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com"); // Cambia esto por el servidor SMTP de tu proveedor
        props.put("mail.smtp.port", "465"); // Cambia esto por el puerto SMTP de tu proveedor
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        // Crear una sesión de correo con autenticación
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, password);
            }
        });

        try {
            // Crear un objeto de mensaje
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remitente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);
            message.setText(mensaje);

            // Enviar el mensaje
            Transport.send(message);

            System.out.println("Correo enviado satisfactoriamente.");
        } catch (MessagingException e) {
            System.out.println("Error al enviar el correo: " + e.getMessage());
        }
    }
}
