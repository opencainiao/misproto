package com.misproto.modules.global.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import org.mou.common.StringUtil;
import org.springframework.stereotype.Service;

import com.misproto.common.util.FileUtil;


@Service("picThumbService")
public class IPicThumbImpl implements IPicThumb {

	@Override
	public void thumbFileWHInner(File oriFile, ThumbParam tp)
			throws IOException {
		if (oriFile == null || !oriFile.exists() || tp == null) {
			return;
		}
		String thumbPath = tp.getThumbParmPath();
		if (StringUtil.isEmpty(thumbPath)) {
			return;
		}

		FileUtil.creatParentDir(thumbPath);

		InputStream is = new FileInputStream(oriFile);
		BufferedImage oriBi = ImageIO.read(is);
		int width = oriBi.getWidth();
		int height = oriBi.getHeight();

		int param_width = tp.getWidth();
		int param_height = tp.getHeight();

		double scall_w = param_width * 1.0 / width;
		double scall_h = param_height * 1.0 / height;

		double scall = scall_w < scall_h ? scall_w : scall_h;

		Thumbnails.of(oriBi).scale(scall).toFile(thumbPath);
	}

	@Override
	public void thumbFileExactlyNoCompress(File oriFile, ThumbParam tp)
			throws IOException {
		if (oriFile == null || !oriFile.exists() || tp == null) {
			return;
		}
		String thumbPath = tp.getThumbParmPath();

		if (StringUtil.isEmpty(thumbPath)) {
			return;
		}

		FileUtil.creatParentDir(thumbPath);

		InputStream is = new FileInputStream(oriFile);
		BufferedImage oriBi = ImageIO.read(is);

		int param_width = tp.getWidth();
		int param_height = tp.getHeight();

		Thumbnails
				.of(oriBi)
				// 从原图哪里开始裁剪 裁减多少
				.sourceRegion(Positions.CENTER, param_width + 100,
						param_height + 100)
				// 新图的大小
				.size(param_width, param_height).keepAspectRatio(false)
				.toFile(thumbPath);
	}

	@Override
	public void thumbFileExactlyCompress(File oriFile, ThumbParam tp)
			throws IOException {
		if (oriFile == null || !oriFile.exists() || tp == null) {
			return;
		}
		String thumbPath = tp.getThumbParmPath();
		if (StringUtil.isEmpty(thumbPath)) {
			return;
		}

		FileUtil.creatParentDir(thumbPath);

		InputStream is = new FileInputStream(oriFile);
		BufferedImage oriBi = ImageIO.read(is);
		int ori_width = oriBi.getWidth();
		int param_width = tp.getWidth();
		int param_height = tp.getHeight();

		// 缩略图的处理
		// 1、将原图进行压缩
		BufferedImage tbi = Thumbnails.of(oriBi)
				.scale((param_width * 1.2) / ori_width).asBufferedImage();
		// 2、进行切割并且保持
		Thumbnails.of(tbi).scale(1.0f)
				.sourceRegion(Positions.CENTER, param_width, param_height)
				.toFile(thumbPath);
	}

	@Override
	public void thumbFile(File oriFile, ThumbParam tp) throws IOException {
		if (oriFile == null || !oriFile.exists() || tp == null) {
			return;
		}

		ThumbType type = tp.getThumbType();
		if (type.equals(ThumbType.COMPRESS_W_H_INNER)) {
			this.thumbFileWHInner(oriFile, tp);
		} else if (type.equals(ThumbType.COMPRESS_CAIJIAN)) {
			this.thumbFileExactlyCompress(oriFile, tp);
		} else if (type.equals(ThumbType.NO_COMPRESS_CAIJIAN)) {
			this.thumbFileExactlyNoCompress(oriFile, tp);
		}
	}

	@Override
	public void thumbFile(File oriFile, List<ThumbParam> tps)
			throws IOException {

		if (oriFile == null || !oriFile.exists() || tps == null
				|| tps.size() == 0) {
			return;
		}

		for (ThumbParam tp : tps) {
			thumbFile(oriFile, tp);
		}
	}

	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		IPicThumb iPicThumb = new IPicThumbImpl();

		String oirFilePath = "C:\\Users\\Public\\Pictures\\Sample Pictures\\Koala.jpg";

		File oriFile = new File(oirFilePath);

		ThumbParam tp = new ThumbParam();
		tp.setHeight(120);
		tp.setWidth(150);

		String newThumbFolder = "C:\\Users\\Public\\Pictures\\Sample Pictures\\thumb\\";
		String thumbFilePath = newThumbFolder + "thumb.jpg";

		String thumbFilePath_NoCompress = newThumbFolder
				+ "thumb_noCompress_caijian.jpg";

		String thumbFilePath_Compress = newThumbFolder
				+ "thumb_Compress_caijian.jpg";

		tp.setThumbParmPath(thumbFilePath);
		iPicThumb.thumbFileWHInner(oriFile, tp);
		tp.setThumbParmPath(thumbFilePath_Compress);
		iPicThumb.thumbFileExactlyCompress(oriFile, tp);
		tp.setThumbParmPath(thumbFilePath_NoCompress);
		iPicThumb.thumbFileExactlyNoCompress(oriFile, tp);

		System.out.println("finished");
	}

}
