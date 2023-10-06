import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class CertificadoSSLValidator {

    public static void main(String[] args) {
        String url = "https://www.example.com";

        try {
            // Configura un TrustManager que no realiza ninguna validación
            TrustManager[] trustAllCertificates = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            // Configura la conexión SSL para confiar en todos los certificados
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCertificates, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

            // Crea una conexión HTTPS
            URL urlObj = new URL(url);
            HttpsURLConnection connection = (HttpsURLConnection) urlObj.openConnection();

            // Realiza la conexión y obtiene el certificado
            connection.connect();
            X509Certificate[] certs = (X509Certificate[]) connection.getServerCertificates();

            // Imprime la información del certificado
            for (X509Certificate cert : certs) {
                System.out.println("Sujeto: " + cert.getSubjectDN());
                System.out.println("Emisor: " + cert.getIssuerDN());
                System.out.println("Número de serie: " + cert.getSerialNumber());
                System.out.println();
            }

            // Cierra la conexión
            connection.disconnect();
        } catch (IOException | CertificateException | SSLException | java.security.NoSuchAlgorithmException
                 | java.security.KeyManagementException e) {
            e.printStackTrace();
        }
    }
}
