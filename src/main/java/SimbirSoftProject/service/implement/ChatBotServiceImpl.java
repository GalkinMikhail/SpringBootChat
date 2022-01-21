package SimbirSoftProject.service.implement;

import SimbirSoftProject.dto.*;
import SimbirSoftProject.model.Room;
import SimbirSoftProject.model.RoomType;
import SimbirSoftProject.model.User;
import SimbirSoftProject.repository.RoomRepository;
import SimbirSoftProject.repository.UserRepository;
import SimbirSoftProject.security.SecurityUser;
import SimbirSoftProject.service.interfaces.ChatBotService;
import SimbirSoftProject.service.interfaces.RoomService;
import SimbirSoftProject.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class ChatBotServiceImpl implements ChatBotService {
    private final RoomService roomService;
    private final UserService userService;
    private final RoomRepository roomRepository;
    private static final ResponseEntity<String> SUCCESS_RESPONSE = ResponseEntity.ok("Action completed successfully");
    private static final ResponseEntity<String> BAD_RESPONSE = ResponseEntity.ok("Command is not valid");
    private static final String YOUTUBE_INFO =
            "1. //yBot find -k -l {название канала}||{название видео} - в ответ бот присылает\n" +
                    "ссылку на ролик;\n" +
                    "-v - выводит количество текущих просмотров.\n" +
                    "-l - выводит количество лайков под видео.\n" +
                    "2. //yBot help - список доступных команд для взаимодействия.";
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
    public ResponseEntity<String> parser(MessageResponseDTO message, User user) {
        String operand = message.getContent().split(" ")[0];

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
        SecurityUser myUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = myUser.getLogin();
        user = userService.findByLogin(login);

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
                    Long id = room.get().getId();
                    this.roomService.addParticipant(new UserToAddDto(splitRequest[4]), id);
                    return SUCCESS_RESPONSE;
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
    public ResponseEntity<String> youTube(MessageResponseDTO message, User user) {
        return null;
    }
}
