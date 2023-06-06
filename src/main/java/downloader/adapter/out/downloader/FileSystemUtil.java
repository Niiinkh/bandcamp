package downloader.adapter.out.downloader;

public class FileSystemUtil {

    public static String homeDirectory() {
        return System.getProperty("user.home");
    }

    public static String sanitize(String string) {
        return string.replaceAll("[\"?*`/<>|\":]", "");
    }

}
