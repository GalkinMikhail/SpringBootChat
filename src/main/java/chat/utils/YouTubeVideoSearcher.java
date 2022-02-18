package chat.utils;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class YouTubeVideoSearcher {

    private final String apikey;
    private final YouTube youTube;

    public YouTubeVideoSearcher(@Value("${youtube.apikey}") String apikey){
        this.youTube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), httpRequest -> {

        }).setApplicationName("chat-youtube-api").build();
        this.apikey = apikey;
    }



    public String findVideoId(String channelName,String videoName) throws IOException {
        List<SearchResult> videos = findListVideos(videoName);
        String id = null;
        for (SearchResult video : videos){
            String foundedVideoName = this.getVideoName(video);
            String foundedChannelName = this.getChannelName(video);

            if (foundedVideoName.equals(videoName) && foundedChannelName.equals(channelName)){
                id = this.getVideoId(video);
                break;
            }
        }
        if (id == null){
            id = this.getVideoId(videos.get(0));
        }
        return id;

    }
    private List<SearchResult> findListVideos(String channelOrVideoName) throws IOException {
        return youTube.search()
                .list("snippet")
                .setKey(apikey)
                .setMaxResults(25L)
                .setQ(channelOrVideoName)
                .execute()
                .getItems();
    }
    private String getVideoName(SearchResult video){
        return video.getSnippet().getTitle();
    }
    private String getChannelName(SearchResult video) {
        return video.getSnippet().getChannelTitle();
    }
    private String getVideoId(SearchResult video){
        return video.getId().getVideoId();
    }
    public String countOfLikesByVideoId(String id) throws IOException {
        VideoListResponse response = youTube
                .videos()
                .list("statistics")
                .setKey(apikey)
                .setPart("statistics")
                .setId(id)
                .execute();

        return response.getItems()
                .get(0)
                .getStatistics()
                .getLikeCount()
                .toString();
    }
    public String countOfViewsByVideoId(String id) throws IOException{
        VideoListResponse response = youTube
                .videos()
                .list("statistics")
                .setKey(apikey)
                .setPart("statistics")
                .setId(id)
                .execute();
        return response.getItems()
                .get(0)
                .getStatistics()
                .getViewCount()
                .toString();
    }
    public String findPlayListId(String channelId) throws IOException{
        ChannelListResponse channelListResponse = youTube.channels()
                .list("contentDetails")
                .setId(channelId)
                .setKey(apikey)
                .execute();
        return channelListResponse.getItems()
                .get(0)
                .getContentDetails()
                .getRelatedPlaylists()
                .getUploads();
    }
    public List<String> findVideosId(String lastPlayListId) throws IOException{
        List<PlaylistItem> res = youTube.playlistItems()
                .list("contentDetails")
                .setMaxResults(5L)
                .setKey(apikey)
                .setPlaylistId(lastPlayListId)
                .execute()
                .getItems();
        return res.stream()
                .map(video -> video.getContentDetails().getVideoId())
                .collect(Collectors.toList());
    }
    public List<String> getRandomComment(String videoId) throws IOException {
        String result = "";
        CommentThreadListResponse videoCommentsThread = getCommentThread(videoId);

        int countPages = videoCommentsThread.getPageInfo().getTotalResults();
        int countComments = countPages * 20;

        int randomCommentNumber = new Random().nextInt(countComments);

        long page = randomCommentNumber / countPages;

        int video = randomCommentNumber % 20;

        if (page != 0){
            int count = 0;


            do {
                count++;
                String token = videoCommentsThread.getNextPageToken();
                videoCommentsThread = getCommentsThreadByToken(videoId, token);

            } while (count != page);
        }

        List<CommentThread> comments = videoCommentsThread.getItems();

        if (video > comments.size()){
            Comment comment = comments.get(comments.size() -1).getSnippet().getTopLevelComment();
            String author = comment.getSnippet().getAuthorDisplayName();
            String content = comment.getSnippet().getTextDisplay();

            List<String> response = new ArrayList<>();
            response.add(author);
            response.add(content);
            return response;
        }
        else {
            Comment comment = comments.get(video).getSnippet().getTopLevelComment();
            String author = comment.getSnippet().getAuthorDisplayName();
            String content = comment.getSnippet().getTextDisplay();

            List<String> response = new ArrayList<>();
            response.add(author);
            response.add(content);
            return response;
        }
    }
    private CommentThreadListResponse getCommentThread(String videoId) throws IOException{
        return youTube.commentThreads()
                .list("snippet")
                .setKey(apikey)
                .setVideoId(videoId)
                .setTextFormat("plainText")
                .execute();
    }
    private CommentThreadListResponse getCommentsThreadByToken(String videoId, String pageToken) throws IOException {
        return youTube.commentThreads()
                .list("snippet")
                .setKey(apikey)
                .setVideoId(videoId)
                .setPageToken(pageToken)
                .setTextFormat("plainText")
                .execute();
    }
    public String findChannelIdInYouTube(String channelName) throws IOException {
        String id = null;
        List<SearchResult> foundVideos = this.findListVideos(channelName);

        for (SearchResult video : foundVideos) {
            String newChannelName = this.getChannelName(video);

            if (newChannelName.equals(channelName)) {
                id = this.getChannelId(video);
                break;
            }
        }

        if (id == null)
            id = this.getChannelId(foundVideos.get(0));

        return id;
    }
    private String getChannelId(SearchResult video) {
        return video.getSnippet().getChannelId();
    }
}
