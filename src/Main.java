import java.io.File;

import Objects.FFmpeg;
import Objects.MP3;

public class Main {

	final static String location = "C:\\Users\\rabeher\\Downloads\\ffmpeg.exe";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File f = new File(location);
		FFmpeg ff = new FFmpeg(f,
				"C:\\Users\\rabeher\\Downloads\\HoldinBack.mp3", new String[0]);
		String[] output = ff.FFmpegArray();
		for (String i : output) {
			System.out.println(i);
		}
		MP3 m = new MP3(ff);
		System.out.println(m);
	}
}
