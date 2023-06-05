package downloader2.adapter.out.downloader;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class BandcampTrackDownloader {

    public File download(String urlString) {
        try {
            URL url = new URL(urlString);
            File tempFile = File.createTempFile("bandcamp-downloader-", ".mp3");
            FileUtils.copyURLToFile(url, tempFile);
            return tempFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
