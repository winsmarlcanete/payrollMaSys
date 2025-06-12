package Config;

import com.zkteco.biometric.FingerprintSensorEx;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ZkFinger {

    public class FingerprintTemplate {
        public byte[] template;
        public int size;

        public FingerprintTemplate(byte[] template, int size) {
            this.template = template;
            this.size = size;
        }

        public byte[] getTemplate() {return template;}
        public int getSize(){return size;}

    }

    private long devHandle = 0;
    private long dbHandle = 0;

    public boolean init() {
        System.out.println("Starting fingerprint SDK initialization...");

        int result = FingerprintSensorEx.Init();
        System.out.println("Init() returned: " + result);

        if (result != 0) {
            System.err.println("SDK Init failed. Error: " + result);
            return false;
        }

        System.out.println("Trying to open fingerprint device...");
        devHandle = FingerprintSensorEx.OpenDevice(0);
        System.out.println("OpenDevice(0) returned handle: " + devHandle);

        if (devHandle == 0) {
            System.err.println("Failed to open fingerprint device.");
            FingerprintSensorEx.Terminate();
            return false;
        }

        System.out.println("Trying to initialize fingerprint DB...");
        dbHandle = FingerprintSensorEx.DBInit();
        System.out.println("DBInit() returned handle: " + dbHandle);

        if (dbHandle == 0) {
            System.err.println("DBInit failed!");
            FingerprintSensorEx.CloseDevice(devHandle);
            FingerprintSensorEx.Terminate();
            return false;
        }

        System.out.println("Initialization successful.");
        return true;
    }

    public void close() {
        System.out.println("Releasing SDK resources...");

        if (dbHandle != 0) {
            System.out.println("Freeing DB...");
            FingerprintSensorEx.DBFree(dbHandle);
            dbHandle = 0;
        }

        if (devHandle != 0) {
            System.out.println("Closing device...");
            FingerprintSensorEx.CloseDevice(devHandle);
            FingerprintSensorEx.Terminate();
            devHandle = 0;
        }

        System.out.println("Resources released.");
    }

    public FingerprintTemplate enrollFingerprint(JLabel imageLabel) {
        if (devHandle == 0 || dbHandle == 0) {
            System.out.println("Device not initialized.");
            return null;
        }

        byte[] widthBuf = new byte[4];
        byte[] heightBuf = new byte[4];
        int[] len = new int[]{4};
        FingerprintSensorEx.GetParameters(devHandle, 1, widthBuf, len);
        FingerprintSensorEx.GetParameters(devHandle, 2, heightBuf, len);
        int width = (widthBuf[0] & 0xFF) | ((widthBuf[1] & 0xFF) << 8);
        int height = (heightBuf[0] & 0xFF) | ((heightBuf[1] & 0xFF) << 8);

        byte[] img = new byte[width * height];
        byte[] template = new byte[2048];
        int[] size = new int[]{2048};

        int result = -1;
        int attempts = 30;

        while (attempts-- > 0) {
            result = FingerprintSensorEx.AcquireFingerprint(devHandle, img, template, size);
            if (result == 0) {
                ImageIcon icon = createFingerprintImage(img, width, height);
                if (imageLabel != null) {
                    SwingUtilities.invokeLater(() -> {
                        imageLabel.setIcon(null); // force reset
                        imageLabel.setIcon(icon); // then show new icon
                    });
                }
                return new FingerprintTemplate(template, size[0]);
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return null;
    }



    public boolean saveTemplateToDatabase(int employeeId, FingerprintTemplate fingerprint) {

        Connection conn;


        try {
            conn = JDBC.getConnection();
            String sql = "UPDATE employees SET fingerprint = ? WHERE employee_id = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setBytes(1, java.util.Arrays.copyOf(fingerprint.getTemplate(), fingerprint.getSize()));
            stmt.setInt(2, employeeId);

            int rows = stmt.executeUpdate();
            conn.close();

            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private ImageIcon createFingerprintImage(byte[] img, int width, int height) {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        bufferedImage.getRaster().setDataElements(0, 0, width, height, img);
        return new ImageIcon(bufferedImage);
    }


}



