package downloader2.adapter.out.downloader;

import com.mpatric.mp3agic.*;
import downloader2.application.AlbumDownloader;
import downloader2.domain.Album;
import downloader2.domain.MetaData;
import downloader2.domain.Track;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static downloader2.adapter.out.downloader.FileSystemUtil.sanitize;

public class BandcampAlbumDownloader implements AlbumDownloader {

    private final BandcampTrackDownloader trackDownloader;
    private final String baseDirectory;

    public BandcampAlbumDownloader(BandcampTrackDownloader trackDownloader, String baseDirectory) {
        this.trackDownloader = trackDownloader;
        this.baseDirectory = baseDirectory;
    }

    @Override
    public void download(Album album) {
        Path directory = createDirectoryFor(album);
        for (Track track : album.getTracks()) {
            if (!StringUtils.isEmpty(track.downloadLink())) {
                String fileName = track.trackNumber() + " " + sanitize(track.title()) + ".mp3";
                System.out.println("Downloading: \"" + fileName + "\"");
                MetaData metaData = new MetaData(track, album);
                File mp3TempFile = trackDownloader.download(track.downloadLink());
                saveFile(directory, fileName, mp3TempFile, metaData);
            }
        }
    }

    private void saveFile(Path path, String fileName, File mp3TempFile, MetaData metaData) {
        try {
            Mp3File mp3File = new Mp3File(mp3TempFile.getCanonicalPath());
            setTags(mp3File, metaData);
            mp3File.save(Path.of(path.toString(), fileName).toString());
        } catch (IOException | UnsupportedTagException | InvalidDataException | NotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setTags(Mp3File mp3File, MetaData metaData) {
        setId3v1Tag(mp3File, metaData);
        setId3v2Tag(mp3File, metaData);
    }

    private void setId3v1Tag(Mp3File mp3File, MetaData metaData) {
        if (mp3File.getId3v1Tag() == null) {
            ID3v1Tag tag = new ID3v1Tag();
            setMetaData(tag, metaData);
            mp3File.setId3v1Tag(tag);
        }
    }

    private void setId3v2Tag(Mp3File mp3File, MetaData metaData) {
        if (mp3File.getId3v2Tag() == null) {
            ID3v24Tag tag = new ID3v24Tag();
            setMetaData(tag, metaData);
            mp3File.setId3v2Tag(tag);
        }
    }

    private void setMetaData(ID3v1 tag, MetaData metaData) {
        tag.setTrack(metaData.trackNumber());
        tag.setTitle(metaData.title());
        tag.setArtist(metaData.artist());
        tag.setAlbum(metaData.album());
        tag.setYear(metaData.year());
    }

    private Path createDirectoryFor(Album album) {
        Path path = Path.of(baseDirectory, descriptiveName(album));
        path.toFile().mkdirs();
        return path;
    }

    private String descriptiveName(Album album) {
        String folderName = album.getArtist() + " - " + album.getTitle();
        folderName = sanitize(folderName);
        return folderName;
    }

}
