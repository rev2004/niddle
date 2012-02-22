package Objects;

import java.sql.Time;

public interface Audio extends Multimedia{
	int getTrackNumber();
	String getTitle();
	String getArtist();
	String getAlbum();
	int getYear();
	Time getTime();

}
