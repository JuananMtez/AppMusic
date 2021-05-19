package um.tds.appmusic.dominio;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public enum Reproductor {

	INSTANCE;

	private MediaPlayer mediaPlayer;

	private Reproductor() {
		try {
			com.sun.javafx.application.PlatformImpl.startup(() -> {
			});
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Exception: " + ex.getMessage());
		}
	}

	public Duration reproducirCancion(Cancion cancion, double volumen) {

		Media hit = null;
		if (cancion.getRutaDisco().startsWith("http://") || cancion.getRutaDisco().startsWith("https://")) {

			String binPath = Reproductor.class.getClassLoader().getResource(".").getPath();
			binPath = binPath.replaceFirst("/", "");
			String tempPath = binPath.replace("/bin", "/temp");

			URL uri = null;

			try {

				com.sun.javafx.application.PlatformImpl.startup(() -> {
				});

				uri = new URL(cancion.getRutaDisco());

				System.setProperty("java.io.tmpdir", tempPath);
				Path mp3 = Files.createTempFile("now-playing", ".mp3");

				System.out.println(mp3.getFileName());
				try (InputStream stream = uri.openStream()) {
					Files.copy(stream, mp3, StandardCopyOption.REPLACE_EXISTING);
				}
				System.out.println("finished-copy: " + mp3.getFileName());

				hit = new Media(mp3.toFile().toURI().toString());
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		} else {

			String fileName = cancion.getRutaDisco();
			File f = new File(fileName);
			hit = new Media(f.toURI().toString());
		}

		mediaPlayer = new MediaPlayer(hit);

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		mediaPlayer.setVolume(volumen);
		mediaPlayer.play();
		return hit.getDuration();

	}

	public void pausarCancion() {
		if (mediaPlayer != null) {
			mediaPlayer.pause();
		}
	}

	public void continuarCancion() {
		if (mediaPlayer != null) {
			mediaPlayer.play();
		}
	}

	public void terminarCancion() {
		if (mediaPlayer != null)
			mediaPlayer.stop();
	}

	public void cambiarPosicionCancion(int posicion) {

		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.setStartTime(Duration.seconds(posicion));
			mediaPlayer.play();
		}
	}

	public void cambiarVolumenCancion(Double volumen) {
		if (mediaPlayer != null)
			mediaPlayer.setVolume(volumen);
	}

}
