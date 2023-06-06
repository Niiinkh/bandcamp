package downloader.application;

import downloader.domain.Album;

import java.io.IOException;

public interface InformationFetcher {

    Album fetch(String url) throws IOException;

}
