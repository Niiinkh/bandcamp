package downloader2.application;

import downloader2.domain.Album;

import java.io.IOException;

public interface AlbumInformationFetcher {

    Album fetch(String url) throws IOException;

}
