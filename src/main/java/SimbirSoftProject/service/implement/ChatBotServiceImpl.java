package SimbirSoftProject.service.implement;

import SimbirSoftProject.dto.*;
import SimbirSoftProject.exceptions.AccessDeniedException;
import SimbirSoftProject.model.Room;
import SimbirSoftProject.model.RoomType;
import SimbirSoftProject.model.User;
import SimbirSoftProject.repository.RoomRepository;
import SimbirSoftProject.repository.UserRepository;
import SimbirSoftProject.security.SecurityUser;
import SimbirSoftProject.service.interfaces.ChatBotService;
import SimbirSoftProject.service.interfaces.MessagesService;
import SimbirSoftProject.service.interfaces.RoomService;
import SimbirSoftProject.service.interfaces.UserService;
import SimbirSoftProject.utils.YouTubeVideoSearcher;
import com.google.api.services.youtube.YouTube;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;


@Service
@AllArgsConstructor
public class ChatBotServiceImpl implements ChatBotService {
    private final RoomService roomService;
    private final UserService userService;
    private final RoomRepository roomRepository;
    private final YouTubeVideoSearcher youTubeVideoSearcher;
    private final MessagesService messagesService;
    private static final ResponseEntity<String> SUCCESS_RESPONSE = ResponseEntity.ok("Action completed successfully");
    private static final ResponseEntity<String> BAD_RESPONSE = ResponseEntity.ok("Command is not valid");
    private static final String youTubeLinkForm = "https://www.youtube.com/watch?v=";
    private static final String YOUTUBE_INFO =
            "1. //yBot find -v -l {название канала}||{название видео} - в ответ бот присылает\n" +
                    "ссылку на ролик;\n" +
                    "-v - выводит количество текущих просмотров.\n" +
                    "-l - выводит количество лайков под видео.\n" +
                    "2. //yBot help - список доступных команд для взаимодействия." +
                    "3. //yBot channelInfo {имя канала}. - Первым сообщением от бота выводится имя канала, вторым - ссылки на последние 5 роликов\n" +
                    "4. //yBot videoCommentRandom {имя канала}||{Название ролика} - Среди комментариев к ролику рандомно выбирается 1 - " +
                    "Первым сообщением бот выводит login человека, который оставил этот комментарий - Вторым сообщением бот выводит сам комментарий\n";
    private static final String Commands =
            "Комнаты:\n" +
                    "1. //room create {Название комнаты} - создает комнаты;\n" +
                    "-c закрытая комната. Только (владелец, модератор и админ) может\n" +
                    "добавлять/удалять пользователей из комнаты.\n" +
                    "2. //room remove {Название комнаты} - удаляет комнату (владелец и админ);\n" +
                    "3. //room rename {Название комнаты} {Новое название} - переименование комнаты (владелец и\n" +
                    "админ);\n" +
                    "4. //room connect {Название комнаты} -l {login пользователя} - добавить пользователя в комнату\n" +
                    "5. //room disconnect {Название комнаты} - выйти из заданной комнаты;\n" +
                    "-l {login пользователя} - выгоняет пользователя из комнаты (для владельца,\n" +
                    "модератора и админа).\n" +
                    "-m {Количество минут} - время на которое пользователь не сможет войти (для\n" +
                    "владельца, модератора и админа).\n" +
                    "Пользователи:\n" +
                    "1. //user rename {login пользователя} {новый login} (владелец и админ);\n" +
                    "2. //user ban;\n" +
                    "-l {login пользователя} - выгоняет пользователя из всех комнат\n" +
                    "-m {Количество минут} - время на которое пользователь не сможет войти.\n" +
                    "3. //user moderator {login пользователя} - действия над модераторами.\n" +
                    "-n - назначить пользователя модератором.\n" +
                    "-d - “разжаловать” пользователя.\n" +
                    "Другие:\n" +
                    "1. //help - выводит список доступных команд.\n";

    @Override
    public ResponseEntity<String> parser(MessageResponseDTO message, User user) throws IOException {
        String operand = message.getContent().split(" ")[0];
        SecurityUser myUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = myUser.getLogin();
        user = userService.findByLogin(login);
        Optional<Room> chatBotRoom = roomRepository.findByName("chatBotRoomFor"+login);
        messagesService.sendMessage(new MessageDto(new Date(), message.getContent(), chatBotRoom.get().getName()),user);

        switch (operand) {
            case "//room":
                return this.room(message, user);
            case "//user":
                return this.user(message, user);
            case "//yBot":
                return this.youTube(message, user);
            case "//help":
                return ResponseEntity.ok(Commands);
            default:
                return BAD_RESPONSE;
        }
    }

    @Override
    public ResponseEntity<String> user(MessageResponseDTO message, User user) {
        String[] splitRequest = message.getContent().split(" ");
        String operation = splitRequest[1];

        switch (operation){
            case "rename":
                String previousLogin = splitRequest[2];
                String newLogin = splitRequest[3];
                User userToRename = userService.findByLogin(previousLogin);
                if (userToRename == null){
                    return new ResponseEntity<>("User not found",HttpStatus.NO_CONTENT);
                }
                this.userService.renameUser(new UserToAddDto(newLogin),userToRename.getId());
                return SUCCESS_RESPONSE;
            case "ban":
                User userToBlock = userService.findByLogin(splitRequest[3]);
                this.userService.blockUser(userToBlock.getId(), new UserBlockDto(Long.parseLong(splitRequest[5])));
                return SUCCESS_RESPONSE;
            case "moderator":
                if (splitRequest[3].equals("-n")){
                    User userToBeModerator = userService.findByLogin(splitRequest[2]);
                    this.userService.setModerator(userToBeModerator.getId());
                    return SUCCESS_RESPONSE;
                }
                if (splitRequest[3].equals("-d")){
                    User userToBeModerator = userService.findByLogin(splitRequest[2]);
                    this.userService.deleteModerator(userToBeModerator.getId());
                    return SUCCESS_RESPONSE;
                }
                else return BAD_RESPONSE;
            default:
                return BAD_RESPONSE;
        }
    }

    @Override
    public ResponseEntity<String> room(MessageResponseDTO message, User user) {
        String[] splitRequest = message.getContent().split(" ");
        String operation = splitRequest[1];
        SecurityUser myUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = myUser.getLogin();
        user = userService.findByLogin(login);
        switch (operation){
            case "create":
                if (splitRequest[splitRequest.length - 1].equals("-c")){
                    this.roomService.createRoom(new RoomDto(splitRequest[2], RoomType.PRIVATE),user);
                    return SUCCESS_RESPONSE;
                }
                this.roomService.createRoom(new RoomDto(splitRequest[2], RoomType.PUBLIC),user);
                return SUCCESS_RESPONSE;
            case "remove":
                if (!splitRequest[2].isEmpty()){
                    Optional<Room> room = this.roomRepository.findByName(splitRequest[2]);
                    Long id = room.get().getId();
                    this.roomService.deleteRoomById(id);
                    return SUCCESS_RESPONSE;
                }
                else
                    return BAD_RESPONSE;
            case "rename":
                if (!splitRequest[splitRequest.length - 1].isEmpty() && !splitRequest[splitRequest.length - 2].isEmpty()) {
                    String newName = splitRequest[splitRequest.length - 1];
                    String previousName = splitRequest[splitRequest.length - 2];
                    Optional<Room> room = this.roomRepository.findByName(previousName);
                    Long id = room.get().getId();
                    this.roomService.renameRoom(new RoomRenameDto(newName), id);
                    return SUCCESS_RESPONSE;
                }
                else
                    return BAD_RESPONSE;
            case "connect":
                Optional<Room> room = this.roomRepository.findByName(splitRequest[2]);
                if (room.isPresent()){
                    if (room.get().getType().equals(RoomType.PUBLIC)) {
                        Long id = room.get().getId();
                        this.roomService.addParticipant(new UserToAddDto(splitRequest[4]), id);
                        return SUCCESS_RESPONSE;
                    }
                    else {
                        if (!room.get().getCreatorId().getLogin().equals(user.getLogin())){
                            throw new AccessDeniedException("You cannot connect to this room","need an invitation");
                        }
                        Long id = room.get().getId();
                        this.roomService.addParticipant(new UserToAddDto(splitRequest[4]), id);
                        return SUCCESS_RESPONSE;
                    }
                }
                else
                    return BAD_RESPONSE;
            case "disconnect":
                Optional<Room> roomForKickUser = this.roomRepository.findByName(splitRequest[2]);
                if (roomForKickUser.isPresent() && splitRequest[3].equals("-l") && splitRequest[splitRequest.length-2].equals("-m")){
                    User userToBan = this.userService.findByLogin(splitRequest[4]);
                    userToBan.setBlocked(true);
                    userToBan.setBlockingDurationInMinutes(Long.parseLong(splitRequest[6]));
                    Long id = roomForKickUser.get().getId();
                    this.roomService.deleteParticipant(new UserToAddDto(splitRequest[4]),id);
                    return SUCCESS_RESPONSE;
                }
                else if (roomForKickUser.isPresent() && splitRequest[3].equals("-l")){
                    Long id = roomForKickUser.get().getId();
                    this.roomService.deleteParticipant(new UserToAddDto(splitRequest[4]),id);
                    return SUCCESS_RESPONSE;
                }
                else
                    return BAD_RESPONSE;
            default:
                return BAD_RESPONSE;
        }
    }

    @Override
    public ResponseEntity<String> youTube(MessageResponseDTO message, User user) throws IOException {
        String[] splitRequest = message.getContent().split(" ");

        String operation = splitRequest[1];

        switch (operation){
            case "help":
                return ResponseEntity.ok(YOUTUBE_INFO);
            case "find":
                String channelAndVideoNames = message.getContent().substring(18).trim() + " ";
                String[] names = channelAndVideoNames.split("\\|\\|");
                String channelName = names[0];
                String videoName = names[1];
                String videoId = this.youTubeVideoSearcher.findVideoId(channelName,videoName);
                if (videoId == null){
                    return BAD_RESPONSE;
                }
                String result = youTubeLinkForm + videoId;
                boolean viewsCountFlag = Arrays.asList(splitRequest).contains("-v");
                boolean likesCountFlag = Arrays.asList(splitRequest).contains("-l");

                if (viewsCountFlag && likesCountFlag){
                    String viewsCount = this.youTubeVideoSearcher.countOfViewsByVideoId(videoId);
                    String likesCount = this.youTubeVideoSearcher.countOfLikesByVideoId(videoId);

                    result += "\n count of views - " + viewsCount + "\n count of likes - " + likesCount;
                }
                else if (viewsCountFlag){
                    String viewsCount = this.youTubeVideoSearcher.countOfViewsByVideoId(videoId);

                    result += "\n count of views - " + viewsCount;
                }
                else if (likesCountFlag){
                    String likesCount = this.youTubeVideoSearcher.countOfLikesByVideoId(videoId);

                    result += "\n count of likes - " + likesCount;
                }

                return ResponseEntity.ok(result);
            case "channelInfo":
                channelName = message.getContent().substring(19).trim();
                String channelId = this.youTubeVideoSearcher.findChannelIdInYouTube(channelName);

                if (channelId == null){
                    return BAD_RESPONSE;
                }

                String lastVidPlayListId = this.youTubeVideoSearcher.findPlayListId(channelId);

                StringBuilder links = new StringBuilder();
                links.append(channelName).append("\n");
                this.youTubeVideoSearcher.findVideosId(lastVidPlayListId)
                        .forEach(id -> links.append(youTubeLinkForm).append(id).append("\n"));
                return ResponseEntity.ok(String.valueOf(links));

            case "videoCommentRandom":
                names = message.getContent().substring(26).split("||");

                channelName = names[0];
                videoName = names[1];

                videoId = this.youTubeVideoSearcher.findVideoId(channelName, videoName);

                if (videoId == null){
                    return BAD_RESPONSE;
                }

                List<String> comment = this.youTubeVideoSearcher.getRandomComment(videoId);

                String com = comment.get(0) + "\n" + comment.get(1);

                return ResponseEntity.ok(com);
            default:
                return BAD_RESPONSE;
        }

    }
}
