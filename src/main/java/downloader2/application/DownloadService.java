package downloader2.application;

import downloader2.domain.Album;

import java.io.IOException;

public interface DownloadService {

    Album requestDownload(String url) throws IOException;

}
