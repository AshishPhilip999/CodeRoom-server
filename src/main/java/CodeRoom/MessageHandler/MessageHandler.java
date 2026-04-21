package CodeRoom.MessageHandler;

import org.json.JSONObject;

import CodeRoom.RoomHandler.RoomHandler;
import CodeRoom.RoomHandler.SessionInfo;

public class MessageHandler {
    private RoomHandler roomHandler;

    public MessageHandler() {
        roomHandler = new RoomHandler();
    }

    public void handleMessage(String payload, SessionInfo sessionInfo) throws Exception {
        JSONObject json = new JSONObject(payload);
        String type = json.getString("type");

        switch (type) {
            case "create":
                String createRoomID = json.getString("roomID");
                sessionInfo.roomID = createRoomID;
                roomHandler.createRoom(sessionInfo);
                break;
            case "join":
                String joinRoomID = json.getString("roomID");
                sessionInfo.roomID = joinRoomID;
                roomHandler.joinRoom(sessionInfo);
                break;
            case "leave":
                String leaveRoomID = json.getString("roomID");
                sessionInfo.roomID = leaveRoomID;
                roomHandler.leaveRoom(sessionInfo);
                break;
            case "editor":
                // Handle editor-specific messages
                String broadCastingRoomID = json.getString("roomID");
                sessionInfo.roomID = broadCastingRoomID;
                roomHandler.broadCastToRoom(sessionInfo, payload);
                break;
            default:
                System.out.println("Unknown message type: " + type);
        }
    }
}
