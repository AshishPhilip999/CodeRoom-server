package CodeRoom.RoomHandler;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.Set;

public class RoomHandler {
    Map<String, Room> rooms = new ConcurrentHashMap<>();

    public void createRoom(SessionInfo sessionInfo) {
        Room room = new Room();
        room.roomID = sessionInfo.roomID;
        room.clients.add(sessionInfo);

        rooms.put(sessionInfo.roomID, room);
        System.out.println("Room created: " + sessionInfo.roomID);
    }

    public void joinRoom(SessionInfo sessionInfo) {
        Room room = rooms.get(sessionInfo.roomID);
        if (room != null) {
            room.clients.add(sessionInfo);
            System.out.println("Client joined room: " + sessionInfo.roomID);
        } else {
            System.out.println("Room not found: " + sessionInfo.roomID);
        }
    }

    public void leaveRoom(SessionInfo sessionInfo) {
        Room room = rooms.get(sessionInfo.roomID);
        for(int i = 0; i < room.clients.size(); i++) {
            SessionInfo s = (SessionInfo) room.clients.toArray()[i];
            if (s.session.getId().equals(sessionInfo.session.getId())) {
                room.clients.remove(s);
                System.out.println("Client left room: " + sessionInfo.roomID);
                System.out.println("Current clients in room " + sessionInfo.roomID + ": " + room.clients.size());
                break;
            }
        }
    }

    public void broadCastToRoom(SessionInfo sender, String payload) throws Exception {
        Room room = rooms.get(sender.roomID);

        for (SessionInfo s : room.clients) {
            if (!s.session.getId().equals(sender.session.getId()) && s.session.isOpen()) {
                s.session.sendMessage(new TextMessage(payload));
            }
        }
        System.out.println("Broadcasted message to room: " + sender.roomID);
    }
}

class Room {
    public String roomID;
    public String roomName;
    public Set<SessionInfo> clients = ConcurrentHashMap.newKeySet();
}
