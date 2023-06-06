package downloader2.adapter.out.downloader;

import downloader2.application.AlbumDownloader;
import downloader2.domain.Album;

import java.nio.file.Path;

import static downloader2.adapter.out.downloader.FileSystemUtil.removeForbiddenCharacters;

public class BandcampAlbumDownloader implements AlbumDownloader {

    private final String baseDirectory;

    public BandcampAlbumDownloader(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    @Override
    public void download(Album album) {
        String folderName = getSavePath(album);
        createDirectoryFor(folderName);
    }

    private void createDirectoryFor(String folderName) {
        Path.of(baseDirectory, folderName).toFile().mkdirs();
    }

    private String getSavePath(Album album) {
        String folderName = album.getArtist() + " - " + album.getTitle();
        folderName = removeForbiddenCharacters(folderName);
        return folderName;
    }

}
