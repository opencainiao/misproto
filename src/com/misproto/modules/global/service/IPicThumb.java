package com.misproto.modules.global.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

/****
 * 图像压缩接口，提供对图片文件按照指定大小进行压缩
 * 
 * @author NBQ
 *
 */
public interface IPicThumb {
	

	/****
	 * 图片输入流进行压缩或裁剪，压缩到指定的文件路径下
	 * 
	 * @param oriFile
	 * @param tp
	 * @throws IOException
	 */
	public void thumbFile(File oriFile, List<ThumbParam> tps) throws IOException;

	/****
	 * 图片输入流进行压缩或裁剪，压缩到指定的文件路径下
	 * 
	 * @param oriFile
	 * @param tp
	 * @throws IOException
	 */
	public void thumbFile(File oriFile, ThumbParam tp) throws IOException;

	/****
	 * 图片输入流进行压缩，压缩到指定的文件路径下(压缩之后的文件宽高在指定的大小之内)
	 * 
	 * @param oriFile
	 * @param tp
	 * @throws IOException
	 */
	public void thumbFileWHInner(File oriFile, ThumbParam tp)
			throws IOException;

	/****
	 * 图片输入流进行裁剪，生成裁剪后的图片到指定的文件路径下，从中心原点裁剪成指定的大小
	 * 
	 * @param oriFile
	 * @param tp
	 * @throws IOException
	 */
	public void thumbFileExactlyNoCompress(File oriFile, ThumbParam tp)
			throws IOException;

	/****
	 * 图片输入流进行裁剪（先压缩，后裁剪），生成裁剪后的图片到指定的文件路径下，从中心原点裁剪成指定的大小
	 * 
	 * @param oriFile
	 * @param tp
	 * @throws IOException
	 */
	public void thumbFileExactlyCompress(File oriFile, ThumbParam tp)
			throws IOException;

}
