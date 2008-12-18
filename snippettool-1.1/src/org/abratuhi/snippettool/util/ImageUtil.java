package org.abratuhi.snippettool.util;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.abratuhi.snippettool.model.InscriptCharacter;

public class ImageUtil {

	public static File[] cutSnippets(File inputImageFile, ArrayList<InscriptCharacter> characters, String directory, String basename){
		if(!directory.endsWith(File.separator)) directory += File.separator;
		File[] outputImageFiles = new File[characters.size()];
		BufferedImage inputImage = load(inputImageFile);
		for(int i=0; i<characters.size(); i++){
			InscriptCharacter ch = characters.get(i);
			BufferedImage outputImage = cutImage(inputImage, ch.shape.main, ch.shape.center, ch.shape.angle);
			outputImageFiles[i] = new File(directory + basename + "_" + characters.get(i).inscript.id+"_"+characters.get(i).getNumber()+".png");
			store(outputImage, "PNG", outputImageFiles[i]);
		}
		return outputImageFiles;
	}
	
	public static BufferedImage cutImage(BufferedImage inputImage, Polygon shape, Point center, float angle){
		Rectangle border = shape.getBounds();
		BufferedImage bounds = inputImage.getSubimage(Math.max(0, border.x), 
				Math.max(0, shape.getBounds().y), 
				Math.max(1, shape.getBounds().width), 
				Math.max(1, shape.getBounds().height));
		AffineTransformOp filter = new AffineTransformOp(AffineTransform.getRotateInstance(angle, center.x-border.x, center.y-border.y), AffineTransformOp.TYPE_BICUBIC);
		BufferedImage outputImage = new BufferedImage(bounds.getWidth(), bounds.getHeight(), BufferedImage.TYPE_INT_ARGB);
		filter.filter(bounds, outputImage);
		return outputImage;
	}

	public static void store(BufferedImage image, String format, File f){
		try {
			ImageIO.write(image, format, f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static BufferedImage load(File f){
		try {
			return ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
