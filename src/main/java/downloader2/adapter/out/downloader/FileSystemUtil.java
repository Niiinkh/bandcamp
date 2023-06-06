package downloader2.adapter.out.downloader;

public class FileSystemUtil {

    public static String homeDirectory() {
        return System.getProperty("user.home");
    }

    public static String removeForbiddenCharacters(String string) {
        return string.replaceAll("[\"?*`/<>|\":]", "");
    }

}
