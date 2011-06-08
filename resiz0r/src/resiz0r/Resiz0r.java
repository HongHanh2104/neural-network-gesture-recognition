package resiz0r;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class Resiz0r {

	public static BufferedImage open(String path, int width, int height) throws Exception 
	{
		// Open the image to resize
		BufferedImage image = ImageIO.read(new File(path));
		
		// Resize the image
		image = resize(image, width * 2, height * 2);
		
		// Blur the image to increase output quality
		image = blur(image);
		
		// Resize again to the output size
		image = resize(image, width, height);
		
		return image;
	}
	
	private static BufferedImage resize(BufferedImage original_image, int width, int height){
		
		BufferedImage resized_image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		Graphics2D graphics = resized_image.createGraphics();
		
		graphics.setComposite(AlphaComposite.Src);
		graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		graphics.drawImage(original_image, 0, 0, width, height, null);
		graphics.dispose();
		
		return resized_image;
	}
	
	private static BufferedImage blur(BufferedImage original_image){
		
		float ninth = 1.0f / 9.0f;
		
		float[] blur_kernel = {
			ninth, ninth, ninth,
			ninth, ninth, ninth,
			ninth, ninth, ninth
		};
		
		Map<RenderingHints.Key, Object> map = new HashMap<RenderingHints.Key, Object>();
		map.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		map.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		RenderingHints hints = new RenderingHints(map);
		BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, blur_kernel), ConvolveOp.EDGE_NO_OP, hints);
		
		return op.filter(original_image, null);
	}
}
