package models;

import javax.persistence.*;

import util.UUIDToBinaryConverter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Room")
public class Room {

	@Id
	@GeneratedValue
	@Column(name = "roomId", columnDefinition = "BINARY(16)")  
	private UUID roomId;
	
	@Column(nullable = false)
	private String roomCode;

	@OneToMany(mappedBy = "room")
	private List<Shelf> shelves;

	public Room() {
		super();
	}

	public UUID getRoomId() {
		return roomId;
	}

	public void setRoomId(UUID roomId) {
		this.roomId = roomId;
	}

	public String getRoomCode() {
		return roomCode;
	}

	public void setRoomCode(String roomCode) {
		this.roomCode = roomCode;
	}

	public List<Shelf> getShelves() {
		return shelves;
	}

	public void setShelves(List<Shelf> shelves) {
		this.shelves = shelves;
	}

}
