package Objects;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;

public class MP3 implements Audio {

	private FFmpeg ff;
	private File audio;
	private String Title;
	private String Artist;
	private int Year;
	private String Album;
	private URL Location;
	private long Size;
	private Time t;
	private boolean extracted_data = false;

	public MP3(FFmpeg ff) {
		this.ff = ff;
		extractData();
	}

	private void extractData() {
		if (!extracted_data) {
			int p = -1;
			for (String line : ff.FFmpegArray()) {
				if (audio != null)
					p = line.indexOf(", from '");
				if (p > -1) {
					audio = new File(line.substring(p + 8,
							line.lastIndexOf("':")));
				} else {
					p = line.indexOf("    title           : ");
					if (p > -1) {
						Title = line.substring(p + 22);
					} else {
						p = line.indexOf("    artist          : ");
						if (p > -1) {
							Artist = line.substring(p + 22);
						} else {
							p = line.indexOf("    date            : ");
							if (p > -1) {
								Year = Integer.parseInt(line.substring(p + 22));
							} else {
								p = line.indexOf("    album           : ");
								if (p > -1)
									Album = line.substring(p + 22);
								else {
									p = line.indexOf("  Duration: ");
									if (p > -1) {
										String time = line.substring(p + 12,
												line.indexOf(","));
										String[] lolz = time.split(":");
										long ms = Long.parseLong(lolz[2].substring(lolz[2].indexOf(".") + 1));
										long s = Long.parseLong(lolz[2].substring(0, lolz[2].indexOf(".")));
										long m = Long.parseLong(lolz[1]);
										long h = Long.parseLong(lolz[0]);
										t = new Time(ms + s * 1000 + m * 60000
												+ h * 3600000);
									}
								}
							}
						}
					}
				}
				p = -1;
				extracted_data = true;
			}
		}
		File dafile = new File(ff.getInputFile());
		try {
			Location = dafile.toURI().toURL();
		} catch (MalformedURLException e) {
			System.out.println("Cannot locate file!");
		}
		Size = dafile.length();

	}

	@Override
	public URL getLocation() {
		return Location;
	}

	@Override
	public long getSize() {
		// TODO Auto-generated method stub
		return Size;
	}

	@Override
	public int getTrackNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return Title;
	}

	@Override
	public String getArtist() {
		// TODO Auto-generated method stub
		return Artist;
	}

	@Override
	public String getAlbum() {
		// TODO Auto-generated method stub
		return Album;
	}

	@Override
	public int getYear() {
		// TODO Auto-generated method stub
		return Year;
	}

	@Override
	public Time getTime() {
		// TODO Auto-generated method stub
		return t;
	}
	
	public String toString(){
		return Title + " - " + Artist + " - " + Album + " (" + Year + ") ";
	}
}
