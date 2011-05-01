package main;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import resiz0r.Resiz0r;

public class Main {

	public static void main(String[] args) throws IOException {
		ImageIO.write(Resiz0r.open("troll.png", 30, 30), "png", new File("output.png"));
	}

}
