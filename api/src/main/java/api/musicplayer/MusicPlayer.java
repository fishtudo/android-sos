package api.musicplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;

/*
 * Classe responsavel por encontrar arquivos em algum diretorio
 * Pode selecionar arquivos por tipo ou todos
 * 
 * Singleton
 */

public class MusicPlayer {
	private static MusicPlayer instance = new MusicPlayer();
	private MediaPlayer media;

	private boolean active = true;

	private MusicPlayer() {

	}

	public void play(Context context, String strMusica) {
		stop();
		if (active) {
			media = MediaPlayer.create(context, Uri.parse(strMusica));
			media.start();
		}
	}

	public void play(Context context, int intMusica) {
		stop();
		if (active) {
			media = MediaPlayer.create(context, intMusica);
			media.start();
		}
	}

	public void playIfIsNotPlaying(Context context, String strMusica) {
		if (media != null && media.isPlaying()) {
			return;
		}
		if (active) {
			media = MediaPlayer.create(context, Uri.parse(strMusica));
			media.start();
		}
	}

	public void playIfIsNotPlaying(Context context, int intMusica) {
		if (media != null && media.isPlaying()) {
			return;
		}
		if (active) {
			media = MediaPlayer.create(context, intMusica);
			media.start();
		}
	}

	public void setListener(OnCompletionListener listener) {
		media.setOnCompletionListener(listener);
	}

	public void stop() {
		if (media == null)
			return;
		if (media.isPlaying())
			media.stop();
		media.release();
		media = null;
	}

	public static MusicPlayer getInstance() {
		return instance;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}